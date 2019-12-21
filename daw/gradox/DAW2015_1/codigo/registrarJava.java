package registro;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class registrarJava extends HttpServlet {

	Connection conexion = null;
	Statement sentenciaSQL = null;
	ResultSet consulta = null;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		
		try{
			String controlador = "com.mysql.jdbc.Driver";
			Class.forName(controlador).newInstance();
			
			String unoUno = "jdbc:mysql://127.0.0.1:3306/";
			String unoDos = "bdpersonal";
			String uno = unoUno + unoDos;
			String dos = "uscEtse";
			String tres = "dawEtse";
			
			conexion = DriverManager.getConnection(uno, dos, tres);
			sentenciaSQL = conexion.createStatement();
		}
		catch(ClassNotFoundException e) {System.out.println("Clase no encontrada. ")
	+ e.getMessage());
		} catch(InstantiationException e) {System.out.println("Objeto no creado. ")
	+ e.getMessage());
		} catch(IllegalAccessException e) {System.out.println("Acceso ilegal. ")
	+ e.getMessage());
		} catch(SQLException e) {System.out.println("Excepción SQL. ")}
	+ e.getMessage());}}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("idUser");
		String usuario = request.getParameter("usuario");
		String email = request.getParameter("email");
		
		String webMaster = getServletContext()._____");
		String textoVuelta;
		
		try {
			consulta = sentenciaSQL.executeQuery(
			"SELECT COUNT(*) as filas FROM personal WHERE ID='"+id+"'");
			consulta.next();
			if(consulta.getInt("filas")!=0){
				textoVuelta = "Este usuario ya está registrado.";
				request._____
			}
			
			else{
				sentenciaSQL.executeUpdate(
				"INSERT INTO" + "personal VALUES ('"+id+"', '"+usuario+"')");
				textoVuelta = "Registro del usuario: "+id+" ("+usuario+") completado correctamente";
				request.setAttribute("salida", textoVuelta);
				
			}
			
			request._____("salidaUno", "webmaster email: " + webMaster);
			RequestDispatcher view = request.getRequestDispatcher("vista.jsp");
			view.forward(request, response);
			
		} catch(SQLException e) {out.println("<p>Excepcion SQL. " + e.getMessage()+"</p>");
		}
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			_____