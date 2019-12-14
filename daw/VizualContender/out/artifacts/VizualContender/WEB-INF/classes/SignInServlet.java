import AccesoBD.FachadaBD;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SignInServlet extends HttpServlet {
    private FachadaBD fachadaBD;

    // ────────────────────────────────────────────────────

    public void init() {
        fachadaBD = new FachadaBD();
    }

    // ────────────────────────────────────────────────────

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean facturacion;

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");

        if (request.getParameter("signinNumero") == null) facturacion = false;
        else facturacion = true;

        int numDireccion;

        if (request.getParameter("signinNumero").equals(""))
            numDireccion = 0;
        else
            numDireccion = Integer.parseInt(request.getParameter("signinNumero"));

        try {
            if (fachadaBD.existAlready(request.getParameter("signinEmail"))) {
                request.getSession().setAttribute("mensajeSignin", "El correo ya está registrado en otra cuenta");
                response.sendRedirect("/mosteiroDelPilar/#!login");

                return;
            } else {
                int id = fachadaBD.insertBDprofe(request.getParameter("signinNombre"), request.getParameter("signinApellidos"), request.getParameter("signinEmail"));

                Cookie c1 = new Cookie("mosteiroDelPilar", String.valueOf(id));
                c1.setMaxAge(60*5);
                response.addCookie(c1);

                request.getSession().setAttribute("nombreRegistro",         request.getParameter("signinNombre"));
                request.getSession().setAttribute("apellidosRegistro",      request.getParameter("signinApellidos"));
                request.getSession().setAttribute("emailRegistro",          request.getParameter("signinEmail"));
                request.getSession().setAttribute("dniRegistro",            request.getParameter("signinDNI"));

                request.getSession().setAttribute("direccioncalle",         request.getParameter("signinCalle"));
                request.getSession().setAttribute("direccionnum",           numDireccion);
                request.getSession().setAttribute("direccionpiso",          request.getParameter("signinPiso"));
                request.getSession().setAttribute("direccionciudad",        request.getParameter("signinCiudad"));
                request.getSession().setAttribute("direccionprovincia",     request.getParameter("signinProvincia"));
                request.getSession().setAttribute("direccioncodigoPostal",  request.getParameter("signinCP"));

                request.getSession().setAttribute("facturacionPago",        facturacion);
                request.getSession().setAttribute("metodoPagoPago",         request.getParameter("pago"));
                request.getSession().setAttribute("tarjetaPago",            request.getParameter("numTarjeta"));
                request.getSession().setAttribute("caducidadPago",          request.getParameter("caducidadTarjeta"));
                request.getSession().setAttribute("cvvPago",                request.getParameter("cvvTarjeta"));

                request.getSession().removeAttribute("mensajeId");

                response.sendRedirect("/mosteiroDelPilar/#!validarId");

                return;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
	}
}