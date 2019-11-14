// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class servlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  // Obtenemos un objeto Print Writer para enviar respuesta

		PrintWriter writer = response.getWriter();

		writer.println(
		    "<html>" +

                "<head>" +
                        "<title> Obtenido del LogIn </title>" +
                "</head>\n" +

                "<body>\n" +
                    "<h2> Parámetros Leídos </h2>\n" +
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