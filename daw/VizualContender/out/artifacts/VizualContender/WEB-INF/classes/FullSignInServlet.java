import AccesoBD.FachadaBD;
import ModeloNegocio.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FullSignInServlet extends HttpServlet {
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
        int idBD = fachadaBD.getIdBDprofe(((String) request.getSession().getAttribute("emailRegistro")));
        int idInput = Integer.parseInt(request.getParameter("validaID"));
        String pass = ((String) request.getParameter("signContrasena"));

        request.getSession().removeAttribute("mensajeLogin");
        request.getSession().removeAttribute("mensajeSignin");

        if (idBD == idInput) {
            HttpSession sesion = request.getSession();

            Usuario cli = new Usuario(
                idBD,
                ((String) sesion.getAttribute("nombreRegistro")),
                ((String) sesion.getAttribute("apellidosRegistro")),
                ((String) sesion.getAttribute("emailRegistro")),
                ((String) sesion.getAttribute("dniRegistro")),
                pass
            );

            Direccion d = new Direccion(
                0,
                cli,
                ((String) sesion.getAttribute("direccioncalle")),
                ((int) sesion.getAttribute("direccionnum")),
                ((String) sesion.getAttribute("direccionpiso")),
                ((String) sesion.getAttribute("direccionciudad")),
                ((String) sesion.getAttribute("direccionprovincia")),
                ((String) sesion.getAttribute("direccioncodigoPostal"))
            );

            MetodoPago mp = new MetodoPago(
                0,
                cli,
                ((boolean) sesion.getAttribute("facturacionPago")),
                ((String) sesion.getAttribute("metodoPagoPago")),
                ((String) sesion.getAttribute("tarjetaPago")),
                ((String) sesion.getAttribute("caducidadPago")),
                ((String) sesion.getAttribute("cvvPago"))
            );

            cli.addDireccion(d);
            cli.addMetodoPago(mp);

            fachadaBD.insertUsuarioBD(cli);

            sesion.setAttribute("bienvenidaTienda", "Registrado con éxito! Bienvenido/a " + cli.getNombre());
            sesion.setAttribute("loggedUsuario", cli);

            Cookie cuqui = new Cookie("mosteiroDelPilar", "mosteiroDelPilar");
            cuqui.setMaxAge(-1);
            response.addCookie(cuqui);

            response.sendRedirect("/mosteiroDelPilar/#!tienda");
        } else {
            request.getSession().setAttribute("mensajeId", "Id incorrecto");
            response.sendRedirect("/mosteiroDelPilar/#!validarId");
        }
	}
}