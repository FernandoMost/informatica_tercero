// Comando de compilación: javac -cp /usr/share/java/servlet-api-3.1.jar servlet.java

import java.io.*;
import javax.servlet.*;
import java.sql.*;
import javax.servlet.http.*;
import java.util.HashSet;
import java.util.Set;

public class SignInServlet extends HttpServlet {
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
        boolean facturacion;

        if (request.getParameter("signinNumero") == null) facturacion = false;
        else facturacion = true;

        int numDireccion;

        if (request.getParameter("signinNumero").equals(""))
            numDireccion = 0;
        else
            numDireccion = Integer.parseInt(request.getParameter("signinNumero"));

        Cliente c = new Cliente(
            0,                                          // id
            request.getParameter("signinNombre"),       // nombre
            request.getParameter("signinApellidos"),    // apellidos
            request.getParameter("signinEmail"),        // email
            request.getParameter("signinDNI"),          // dni
            request.getParameter("signContrasena"),     // contrasena
            request.getParameter("signinCalle"),        // calle
            numDireccion,                               // num
            request.getParameter("signinPiso"),         // piso
            request.getParameter("signinCiudad"),       // ciudad
            request.getParameter("signinProvincia"),    // provincia
            request.getParameter("signinCP"),           // codigoPostal
            facturacion,                                // facturacionIgualEnvio
            request.getParameter("pago"),               // metodoPago
            request.getParameter("numTarjeta"),         // tarjeta
            request.getParameter("caducidadTarjeta"),   // caducidad
            request.getParameter("cvvTarjeta"));        // cvv

        try {
            if (clienteDAO.existAlready(c)) {
                response.sendRedirect("/VizualContender/#!loginFailure");
                return;
            } else {
                int id = clienteDAO.insertBDprofe(c);

                Cookie c1 = new Cookie("mosteiroDelPilar", String.valueOf(id));
                c1.setMaxAge(60*5);
                response.addCookie(c1);

                request.getSession().setAttribute("nombreRegistro",         c.getNombre());
                request.getSession().setAttribute("apellidosRegistro",      c.getApellidos());
                request.getSession().setAttribute("emailRegistro",          c.getEmail());
                request.getSession().setAttribute("dniRegistro",            c.getDni());
                request.getSession().setAttribute("contrasenaRegistro",     c.getContrasena());

                request.getSession().setAttribute("direccioncalle",         c.getCalle());
                request.getSession().setAttribute("direccionnum",           c.getNum());
                request.getSession().setAttribute("direccionpiso",          c.getPiso());
                request.getSession().setAttribute("direccionciudad",        c.getCiudad());
                request.getSession().setAttribute("direccionprovincia",     c.getProvincia());
                request.getSession().setAttribute("direccioncodigoPostal",  c.getCodigoPostal());

                request.getSession().setAttribute("facturacionPago",        c.isFacturacionIgualEnvio());
                request.getSession().setAttribute("metodoPagoPago",         c.getMetodoPago());
                request.getSession().setAttribute("tarjetaPago",            c.getTarjeta());
                request.getSession().setAttribute("caducidadPago",          c.getCaducidad());
                request.getSession().setAttribute("cvvPago",                c.getCvv());

                request.getSession().setAttribute("cliente", c);
                request.getSession().removeAttribute("mensajeId");

                response.sendRedirect("/VizualContender/#!validarId");

                return ;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}