// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import ModeloNegocio.Usuario;
import ModeloNegocio.UsuarioDAO;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogInServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    // ────────────────────────────────────────────────────

    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("LogInEmail"), pass = request.getParameter("LogInPassword"), resposta = "";

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");
        Usuario c = new Usuario(email, pass);

        try {
            if (usuarioDAO.validarLogin(c)) {
                request.getSession().setAttribute("bienvenidaTienda", "Bienvenido/a de nuevo!");

                Usuario usuario = usuarioDAO.getClienteBDalumno(c.getEmail());
                request.getSession().setAttribute("loggedClient", usuario);

                Cookie cuqui = new Cookie("mosteiroDelPilar", "mosteiroDelPilar");
                cuqui.setMaxAge(-1);
                response.addCookie(cuqui);

                response.sendRedirect("/VizualContender/#!tienda");
            } else {
                request.getSession().setAttribute("mensajeLogin", "E-mail y/o contraseña incorrectos");
                response.sendRedirect("/VizualContender/#!login");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}