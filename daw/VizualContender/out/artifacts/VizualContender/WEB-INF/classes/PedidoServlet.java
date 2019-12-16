import AccesoBD.FachadaBD;
import ModeloNegocio.Usuario;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PedidoServlet extends HttpServlet {
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

        int direccion = Integer.parseInt(request.getParameter("direccionEnvio"));
        int metodoPago = Integer.parseInt(request.getParameter("metodoPago"));

        synchronized (request) {
            fachadaBD.insertPedido(usuario, direccion, metodoPago);
        }

        Cookie cook = new Cookie("mostrarMensaje", "true");
        cook.setMaxAge(4);
        response.addCookie(cook);

        response.sendRedirect("/mosteiroDelPilar/#!pedidos");
    }
}
