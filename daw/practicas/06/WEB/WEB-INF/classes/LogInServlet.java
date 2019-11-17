// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import java.sql.*;
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

        Cliente c = new Cliente(email, pass);

        try {
            if (clienteDAO.validarLogin(c)) {
                request.getSession().setAttribute("bienvenidaTienda", "Bienvenido/a de nuevo!");
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