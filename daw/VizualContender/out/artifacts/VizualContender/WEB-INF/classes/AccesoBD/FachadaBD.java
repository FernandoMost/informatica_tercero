package AccesoBD;

import ModeloNegocio.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;

public class FachadaBD {
    private Connection conexion;

    private DAOUsuario daoUsuario;
    private DAODireccion daoDireccion;
    private DAOMetodoPago daoMetodoPago;
    private DAOArticulo daoArticulo;
    private DAOPedido daoPedido;

    // ───────────────────────────────────────────────────────

    public FachadaBD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mosteiroDelPilar", "mosteiroDelPilar", "1234");

            this.daoUsuario = new DAOUsuario(this.conexion, this);
            this.daoDireccion = new DAODireccion(this.conexion, this);
            this.daoMetodoPago = new DAOMetodoPago(this.conexion, this);
            this.daoArticulo = new DAOArticulo(this.conexion, this);
            this.daoPedido = new DAOPedido(this.conexion, this);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────

    public void insertUsuarioBD(Usuario usuario) { daoUsuario.insertUsuarioBD(usuario); }
    public void insertDireccion(Direccion direccion) { daoDireccion.insertDireccion(direccion); }
    public void insertMetodoPago(MetodoPago metodoPago) { daoMetodoPago.insertMetodoPago(metodoPago); }

    // ───────────────────────────────────────────────────────

    public int getIdBDprofe(String email) { return daoUsuario.getIdBDprofe(email); }
    public int insertBDprofe(String nombre, String apellidos, String email) { return daoUsuario.insertBDprofe(nombre, apellidos, email); }
    public boolean existAlready(String email) { return daoUsuario.existAlready(email); }
    public boolean validarLogin(String email, String password) { return daoUsuario.validarLogin(email, password); }

    // ───────────────────────────────────────────────────────

    public Usuario getUsuarioBD(String email) { return daoUsuario.getUsuarioBD(email); }
    public HashSet<Direccion> getDirecciones(Usuario usuario) { return daoDireccion.getDirecciones(usuario); }
    public HashSet<MetodoPago> getMetodosPago(Usuario usuario) { return daoMetodoPago.getMetodosPago(usuario); }
    public Articulo getArticulo(int id) { return daoArticulo.getArticulo(id); }
    public HashSet<Pedido> getPedidos(Usuario usuario) { return daoPedido.getPedidos(usuario); }
}