package AccesoBD;

import ModeloNegocio.Usuario;

import java.sql.*;

public class UsuarioDAO {
    private Connection conectandoBD(String baseDatos, String usuario, String contrasena) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/" + baseDatos, usuario, contrasena);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // ────────────────────────────────────────────────────
    
    public boolean validarLogin(Usuario usuario) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query= "SELECT * FROM usuario WHERE email=? AND contrasena=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, usuario.getEmail());
            preparedStatement.setString(2, usuario.getContrasena());

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // ────────────────────────────────────────────────────

    public boolean existAlready(Usuario usuario) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query= "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, usuario.getEmail());

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // ────────────────────────────────────────────────────

    public int insertBDprofe(Usuario usuario) {
        PreparedStatement preparedStatement;
        ResultSet result;
        int id = -69;

        try {
            Connection connection = conectandoBD("bd_alumnos", "daw", "daw");

            String query = "INSERT INTO altadaw (nombre, apellidos, email) VALUES (?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellidos());
            preparedStatement.setString(3, usuario.getEmail());

            preparedStatement.executeUpdate();

            // ────────────────────────────────────
            return getIdBDprofe(usuario.getEmail());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    // ────────────────────────────────────────────────────

    public void insertBDalumno(Usuario usuario) {
        PreparedStatement preparedStatement;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query = "INSERT INTO usuario VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,  usuario.getId());
            preparedStatement.setString(2,  usuario.getNombre());
            preparedStatement.setString(3,  usuario.getApellidos());
            preparedStatement.setString(4,  usuario.getEmail());
            preparedStatement.setString(5,  usuario.getDni());
            preparedStatement.setString(6,  usuario.getContrasena());

            preparedStatement.setString(7,  usuario.getCalle());
            preparedStatement.setInt(8,  usuario.getNum());
            preparedStatement.setString(9,  usuario.getPiso());
            preparedStatement.setString(10, usuario.getCiudad());
            preparedStatement.setString(11, usuario.getProvincia());
            preparedStatement.setString(12, usuario.getCodigoPostal());

            preparedStatement.setBoolean(13, usuario.isFacturacionIgualEnvio());
            preparedStatement.setString(14, usuario.getMetodoPago());
            preparedStatement.setString(15, usuario.getTarjeta());
            preparedStatement.setString(16, usuario.getCaducidad());
            preparedStatement.setString(17, usuario.getCvv());

            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ────────────────────────────────────────────────────

    public Usuario getClienteBDalumno(String email) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        Usuario C = null;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query = "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                C = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("email"),
                    rs.getString("dni"),
                    rs.getString("contrasena"),
                    rs.getString("calle"),
                    rs.getInt("num"),
                    rs.getString("piso"),
                    rs.getString("ciudad"),
                    rs.getString("provincia"),
                    rs.getString("codigoPostal"),
                    rs.getBoolean("facturacionIgualEnvio"),
                    rs.getString("metodoPago"),
                    rs.getString("tarjeta"),
                    rs.getString("caducidadTarjeta"),
                    rs.getString("codSeguridadTarjeta")
                );
            }

            return C;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return C;
    }

    // ────────────────────────────────────────────────────

     public int getIdBDprofe(String email) {
         PreparedStatement preparedStatement;
         ResultSet result;
         int id = -2;

         try {
             Connection connection = conectandoBD("bd_alumnos", "daw", "daw");

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

}