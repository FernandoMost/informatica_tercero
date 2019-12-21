package registro;
import ...
public class consultarJava extends HttpServlet {
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
			String dos = "dawUSC";
			String tres = "usc";
			conexion = DriverManager.getConnection(uno, dos, tres);
			sentenciaSQL = conexion.createStatement();
		}
		catch(...

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
		HashMap<String,String> usuarios = new HashMap<String,String>();
	
		try{
			consulta=sentenciaSQL.executeQuery("_____");
			while (_____) {
				String id = consulta.getString("ID");
				String nombre = consulta.getString("_____");
				usuarios.put(_____);
			}
			request.setAttribute("_____", _____);
			RequestDispatcher view = _____;
			view._____;
		}
		catch(SQLException e) {out.println("<p>Excepcion SQL. " + e.getMessage()+"</p>");
		}