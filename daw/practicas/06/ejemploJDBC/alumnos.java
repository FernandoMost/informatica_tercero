import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class alumnos extends HttpServlet
{
  Connection conexion= null;
  Statement sentenciaSQL= null;
  ResultSet consulta= null;

 public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
    try
    {
     String controlador = "com.mysql.jdbc.Driver";
    Class.forName(controlador).newInstance();

	String URL_bd = "jdbc:mysql://127.0.0.1:3306/alumnosDB";
     String usuario = "daw";
     String contraseña = "etse";
	  
	   conexion = DriverManager.getConnection(URL_bd, usuario, contraseña);

      sentenciaSQL = conexion.createStatement();
    }
    catch(ClassNotFoundException e)
    { 
      System.out.println("Clase no encontrada. " + e.getMessage());
    }
    catch(InstantiationException e)
    { 
      System.out.println("Objeto no creado. " + e.getMessage());
    }
    catch(IllegalAccessException e)
    { 
      System.out.println("Acceso ilegal. " + e.getMessage());
    }
    catch(SQLException e)
    { 
      System.out.println("Excepción SQL. " + e.getMessage());
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException  {
	
	 response.setContentType("text/html");
    PrintWriter out = response.getWriter();
	 	  
	 String nombre= request.getParameter("nombre");
    String apellidos= request.getParameter("apellido");
		
    int ID= Integer.parseInt(request.getParameter("ID"));
    int curso= Integer.parseInt(request.getParameter("curso"));
	 String titulacion= request.getParameter("titulacion");

	 out.println("<html>");
    out.println("<head><title>Respuesta a la solicitud</title></head>");
	 out.println("<body><p>Insertando alumno: " + nombre + " " + apellidos + ", " + titulacion);	
	 out.println("</p></body></html>");
	 	
	 try
    {	   
    sentenciaSQL.executeUpdate("INSERT INTO " + "altadaw" +
      " VALUES (" + ID + ", '" + nombre + "', '" +
            apellidos + "', " + curso + ", " + titulacion + ")"
      );


		String query= "SELECT * FROM altadaw";
		
		consulta= sentenciaSQL.executeQuery(query);

		out.println("<p> Los alumnos insertados hasta el momento son: </p>");		
		out.println("<ul>");
		while (consulta.next()){
			
			out.println("<li>");
			out.println(consulta.getString("nombre") + " " + consulta.getString("apellidos")
         + ", " + consulta.getString("titulacion"));
			out.println("</li>");
		}
		out.println("</ul>");
	 }
    catch(SQLException e)
    {
	 	out.println("Excepción SQL. " + e.getMessage());
	 }
   }
 }
