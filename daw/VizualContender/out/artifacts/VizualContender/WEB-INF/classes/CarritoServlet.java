import AccesoBD.FachadaBD;
import ModeloNegocio.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CarritoServlet extends HttpServlet {
    private FachadaBD fachadaBD;

    // ────────────────────────────────────────────────────

    public void init() {
        fachadaBD = new FachadaBD();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("loggedUsuario");

        int articulo = Integer.parseInt(request.getParameter("idArticulo"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        System.out.println(request.getParameter("tipo"));

        switch (request.getParameter("tipo")) {
            case "anhadirUnidades":
                usuario.getCarrito().addToCarrito(fachadaBD.getArticulo(articulo), cantidad);

                Cookie cook = new Cookie("mostrarMensaje", "true");
                cook.setMaxAge(3);
                response.addCookie(cook);
            break;

            case "cambiarUnaUnidad":
                usuario.getCarrito().setCantidadArticulo(fachadaBD.getArticulo(articulo), cantidad);
            break;

            case "eliminarArticulo":
                usuario.getCarrito().removeAllFromCarrito(fachadaBD.getArticulo(articulo));
            break;

            default:
                return;
        }

        fachadaBD.insertCarrito(usuario);

        if (((String) request.getParameter("tipo")).equals("anhadirUnidades"))
            response.sendRedirect("/mosteiroDelPilar/#!tienda");
        else
            response.sendRedirect("/mosteiroDelPilar/#!carrito");
    }
}