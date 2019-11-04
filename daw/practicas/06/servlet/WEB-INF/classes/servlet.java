// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class servlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");  // Obtenemos un objeto Print Writer para enviar respuesta
		PrintWriter p = res.getWriter();
		p.println("<html><head><title>Información do formulario</title></head>\n" +
			"<body bgcolor=\"#ccbbaa\">\n" +
		    "<h2>Parámetros lidos no formulario:</h2><p>\n" + 
		    "<ul>\n" + 
		    "Nome: " + req.getParameter("nome") + "<br>\n" + 
		    "Chave: "  + req.getParameter("chave") + " <br>\n" + 
		    "</body></html>");
		p.close();
	}
} 
