import AccesoBD.FachadaBD;
import ModeloNegocio.Usuario;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogInServlet extends HttpServlet {
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
        String email = request.getParameter("LogInEmail"), pass = request.getParameter("LogInPassword"), resposta = "";

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");

        try {
            if (fachadaBD.validarLogin(email, pass)) {
                request.getSession().setAttribute("bienvenidaTienda", "Bienvenido/a de nuevo!");

                Usuario usuario = fachadaBD.getUsuarioBD(email);
                request.getSession().setAttribute("loggedClient", usuario);

                Cookie cuqui = new Cookie("mosteiroDelPilar", "mosteiroDelPilar");
                cuqui.setMaxAge(-1);
                response.addCookie(cuqui);

                response.sendRedirect("/mosteiroDelPilar/#!tienda");
            } else {
                request.getSession().setAttribute("mensajeLogin", "E-mail y/o contraseña incorrectos");
                response.sendRedirect("/mosteiroDelPilar/#!login");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}