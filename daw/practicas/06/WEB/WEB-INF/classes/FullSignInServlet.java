// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.util.HashSet;
import java.util.Set;

public class FullSignInServlet extends HttpServlet {
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
        int idBD = clienteDAO.getIdBDprofe(((String) request.getSession().getAttribute("emailRegistro")));
        int idInput = Integer.parseInt(request.getParameter("validaID"));

        if (idBD == idInput) {
            HttpSession sesion = request.getSession();

            Cliente cli = new Cliente(
                idBD,                                                    // id
                ((String) sesion.getAttribute("nombreRegistro")),        // nombre
                ((String) sesion.getAttribute("apellidosRegistro")),     // apellidos
                ((String) sesion.getAttribute("emailRegistro")),         // email
                ((String) sesion.getAttribute("dniRegistro")),           // dni
                ((String) sesion.getAttribute("contrasenaRegistro")),    // contrasena
                ((String) sesion.getAttribute("direccioncalle")),        // calle
                ((int) sesion.getAttribute("direccionnum")),             // num
                ((String) sesion.getAttribute("direccionpiso")),         // piso
                ((String) sesion.getAttribute("direccionciudad")),       // ciudad
                ((String) sesion.getAttribute("direccionprovincia")),    // provincia
                ((String) sesion.getAttribute("direccioncodigoPostal")), // codigoPostal
                ((boolean) sesion.getAttribute("facturacionPago")),      // facturacionIgualEnvio
                ((String) sesion.getAttribute("metodoPagoPago")),        // metodoPago
                ((String) sesion.getAttribute("tarjetaPago")),           // tarjeta
                ((String) sesion.getAttribute("caducidadPago")),         // caducidad
                ((String) sesion.getAttribute("cvvPago")));              // cvv

            clienteDAO.insertBDalumno(cli);

            sesion.setAttribute("bienvenidaTienda", "Registrado con éxito! Bienvenido/a " + cli.getNombre());
            response.sendRedirect("/VizualContender/#!tienda");
        } else {
            request.getSession().setAttribute("mensajeId", "Id incorrecto");
            response.sendRedirect("/VizualContender/#!validarId");
        }
	}
}