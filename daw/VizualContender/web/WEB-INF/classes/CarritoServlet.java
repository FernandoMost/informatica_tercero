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
        // usuario logueado como atributo en la sesión
        Usuario usuario = (Usuario) request.getSession().getAttribute("loggedUsuario");

        // valores del artículo según el formulario seleccionado
        int articulo = Integer.parseInt(request.getParameter("idArticulo"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        // el servlet es válido para tres tipos de cambios en el carrito
        switch (request.getParameter("tipo")) {
            case "anhadirUnidades":
                // añadiendo X cantidad al carrito desde el catálogo
                usuario.getCarrito().addToCarrito(fachadaBD.getArticulo(articulo), cantidad);

                // Cookie para mostrar mensaje de éxito
                Cookie cook = new Cookie("mostrarMensaje", "true");
                cook.setMaxAge(3);
                response.addCookie(cook);
                break;
            case "cambiarUnaUnidad":
                // añadiendo o eliminando una unidad de un artículo en el carrito
                usuario.getCarrito().setCantidadArticulo(fachadaBD.getArticulo(articulo), cantidad);
                break;
            case "eliminarArticulo":
                // eliminando todas las unidades de un artículo en el carrito
                usuario.getCarrito().removeAllFromCarrito(fachadaBD.getArticulo(articulo));
                break;
            default:
                return;
        }

        synchronized (request) {    // cambios en la base de datos
            fachadaBD.insertCarrito(usuario);
        }

        // redirigiendo a los correspondientes jsp según el tipo de cambio
        if (((String) request.getParameter("tipo")).equals("anhadirUnidades"))
            response.sendRedirect("/mosteiroDelPilar/#!tienda");
        else
            response.sendRedirect("/mosteiroDelPilar/#!carrito");
    }
}