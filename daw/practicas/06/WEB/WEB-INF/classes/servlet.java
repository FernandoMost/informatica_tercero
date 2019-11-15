// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;

public class servlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conexion = null;
        String resposta = "FAILURE";
        boolean exito = false;

        response.setContentType("text/html");  // Obtenemos un objeto Print Writer para enviar respuesta
		PrintWriter writer = response.getWriter();
        String gestor = "mysql", servidor = "127.0.0.1", puerto = "3306", baseDatos = "bd_alumnos", usuario = "daw", contrasena = "daw";

        try {
            String controlador = "com.mysql.jdbc.Driver";
            Class.forName(controlador).newInstance();

            String URL = "jdbc:" + gestor + "://" + servidor + ":" + puerto + "/" + baseDatos;

            conexion = DriverManager.getConnection(URL, usuario, contrasena);

            resposta = "EXITO"
        } catch (Exception e) {
            resposta = e + e.getMessage();
        }

		writer.println(
		    "<html>" +
                "<head>" +
                        "<title> " + "respuestaServlet" + " </title>" +
                "</head>\n" +

                "<body>\n" +
                    "<h2> "+ resposta +" </h2>\n" +
                    "<ul>\n" +
                        "<li> E-mail: " + request.getParameter("LogInEmail") + "</li>\n" +
                        "<li> Contraseña: " + request.getParameter("LogInPassword") + "</li>\n" +
                    "</ul>\n" +
                "</body>" +
            "</html>");

		writer.flush();
		writer.close();
	}
}