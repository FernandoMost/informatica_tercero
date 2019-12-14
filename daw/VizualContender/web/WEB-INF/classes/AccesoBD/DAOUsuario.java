package AccesoBD;

import ModeloNegocio.Articulo;
import ModeloNegocio.Usuario;

import java.sql.*;
import java.util.HashMap;

public class DAOUsuario extends AbstractDAO {
    public DAOUsuario(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    private Connection getConexionBDprofesor() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bd_alumnos", "daw", "daw");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // ────────────────────────────────────────────────────

    public int insertBDprofe(String nombre, String apellidos, String email) {
        PreparedStatement preparedStatement;
        int id = -69;

        try {
            Connection connection = getConexionBDprofesor();

            String query = "INSERT INTO altadaw (nombre, apellidos, email) VALUES (?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, email);

            preparedStatement.executeUpdate();

            // ────────────────────────────────────
            return getIdBDprofe(email);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    // ────────────────────────────────────────────────────

    public int getIdBDprofe(String email) {
        PreparedStatement preparedStatement;
        ResultSet result;
        int id = -2;

        try {
            Connection connection = getConexionBDprofesor();

            String query = "SELECT id FROM altadaw WHERE email=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            result = preparedStatement.executeQuery();

            if (result.next())
                id = result.getInt("id");

            return id;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    // ─────────────────────────────────────────────────────────────────────────────────────────────────────────────────
    
    public boolean validarLogin(String email, String password) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = this.getConexion();

            String query= "SELECT * FROM usuario WHERE email=? AND contrasena=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // ────────────────────────────────────────────────────

    public boolean existAlready(String email) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = this.getConexion();

            String query= "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


    // ────────────────────────────────────────────────────

    public void insertUsuarioBD(Usuario usuario) {
        PreparedStatement preparedStatement;

        try {
            Connection connection = this.getConexion();

            String query = "INSERT INTO usuario (id, nombre, apellidos, email, dni, contrasena) VALUES (?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,  usuario.getId());
            preparedStatement.setString(2,  usuario.getNombre());
            preparedStatement.setString(3,  usuario.getApellidos());
            preparedStatement.setString(4,  usuario.getEmail());
            preparedStatement.setString(5,  usuario.getDni());
            preparedStatement.setString(6,  usuario.getContrasena());

            preparedStatement.executeUpdate();

            getFachadaBD().insertDireccion(usuario.getDirecciones().iterator().next());
            getFachadaBD().insertMetodoPago(usuario.getMetodosPago().iterator().next());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ────────────────────────────────────────────────────

    public Usuario getUsuarioBD(String email) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        Usuario usuario = null;

        try {
            Connection connection = this.getConexion();

            String query = "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("dni"),
                    rs.getString("contrasena")
                );

                usuario.setDirecciones(getFachadaBD().getDirecciones(usuario));
                usuario.setMetodosPago(getFachadaBD().getMetodosPago(usuario));

                usuario.setCarrito(getCarrito(usuario));

                usuario.setPedidos(getFachadaBD().getPedidos(usuario));
            }

            return usuario;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return usuario;
    }

    public HashMap<Articulo, Integer> getCarrito(Usuario usuario) {
        HashMap<Articulo, Integer> carrito = new HashMap<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM carrito WHERE usuario=?";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next())
                carrito.put(getFachadaBD().getArticulo(rs.getInt("articulo")), rs.getInt("cantidad"));

            return carrito;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}