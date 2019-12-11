// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import ModeloNegocio.Usuario;
import AccesoBD.UsuarioDAO;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FullSignInServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

    // ────────────────────────────────────────────────────

    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idBD = usuarioDAO.getIdBDprofe(((String) request.getSession().getAttribute("emailRegistro")));
        int idInput = Integer.parseInt(request.getParameter("validaID"));
        String pass = ((String) request.getParameter("signContrasena"));

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");

        if (idBD == idInput) {
            HttpSession sesion = request.getSession();

            Usuario cli = new Usuario(
                idBD,                                                    // id
                ((String) sesion.getAttribute("nombreRegistro")),        // nombre
                ((String) sesion.getAttribute("apellidosRegistro")),     // apellidos
                ((String) sesion.getAttribute("emailRegistro")),         // email
                ((String) sesion.getAttribute("dniRegistro")),           // dni
                pass,                                                    // contrasena
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

            usuarioDAO.insertBDalumno(cli);

            sesion.setAttribute("bienvenidaTienda", "Registrado con éxito! Bienvenido/a " + cli.getNombre());
            sesion.setAttribute("loggedClient", cli);

            Cookie cuqui = new Cookie("mosteiroDelPilar", "mosteiroDelPilar");
            cuqui.setMaxAge(-1);
            response.addCookie(cuqui);

            response.sendRedirect("/VizualContender/#!tienda");
        } else {
            request.getSession().setAttribute("mensajeId", "Id incorrecto");
            response.sendRedirect("/VizualContender/#!validarId");
        }
	}
}