// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import ModeloNegocio.Cliente;
import ModeloNegocio.ClienteDAO;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogInServlet extends HttpServlet {
    private ClienteDAO clienteDAO;

    // ────────────────────────────────────────────────────

    public void init() {
        clienteDAO = new ClienteDAO();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("LogInEmail"), pass = request.getParameter("LogInPassword"), resposta = "";

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");
        Cliente c = new Cliente(email, pass);

        try {
            if (clienteDAO.validarLogin(c)) {
                request.getSession().setAttribute("bienvenidaTienda", "Bienvenido/a de nuevo!");

                Cliente cliente = clienteDAO.getClienteBDalumno(c.getEmail());
                request.getSession().setAttribute("loggedClient", cliente);

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