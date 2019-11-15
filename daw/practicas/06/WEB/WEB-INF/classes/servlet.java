// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;

public class servlet extends HttpServlet {
    private LoginDAO loginDAO;

    // ────────────────────────────────────────────────────

    public void init() {
        loginDAO = new LoginDAO();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("LogInEmail");
        String pass = request.getParameter("LogInPassword");

        Cliente c = new Cliente(email, pass);

        try {
            if (loginDAO.validar(c))
                response.sendRedirect("/VizualContender/#!impresion3d");
            else
                response.sendRedirect("/VizualContender/#!tienda");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
/*
        response.setContentType("text/html");  // Obtenemos un objeto Print Writer para enviar respuesta
		PrintWriter writer = response.getWriter();

        request.setAttribute("respuesta", resposta);
        request.getRequestDispatcher("/#!/tienda").forward(request, response);

        writer.println(
		    "<html>" +
                "<head><title> respuestaServlet </title> </head>\n" +
                "<body>\n" +
                    "<h2> "+ resposta +" </h2>\n" +
                    "<ul>\n" +
                        "<li> E-mail: " + request.getParameter("LogInEmail") + "</li>\n" +
                        "<li> Contraseña: " + request.getParameter("LogInPassword") + "</li>\n" +
                    "</ul>\n" +
                "</body>" +
            "</html>"
        );

		writer.flush();
		writer.close();
        */
	}
}