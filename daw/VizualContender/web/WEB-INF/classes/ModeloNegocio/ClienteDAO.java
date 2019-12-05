package ModeloNegocio;

import java.sql.*;

public class ClienteDAO {
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
    
    public boolean validarLogin(Cliente cliente) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query= "SELECT * FROM usuario WHERE email=? AND contrasena=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cliente.getEmail());
            preparedStatement.setString(2, cliente.getContrasena());

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // ────────────────────────────────────────────────────

    public boolean existAlready(Cliente cliente) {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query= "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cliente.getEmail());

            result = preparedStatement.executeQuery();

            return result.next();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    // ────────────────────────────────────────────────────

    public int insertBDprofe(Cliente cliente) {
        PreparedStatement preparedStatement;
        ResultSet result;
        int id = -69;

        try {
            Connection connection = conectandoBD("bd_alumnos", "daw", "daw");

            String query = "INSERT INTO altadaw (nombre, apellidos, email) VALUES (?,?,?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(3, cliente.getEmail());

            preparedStatement.executeUpdate();

            // ────────────────────────────────────
            return getIdBDprofe(cliente.getEmail());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return id;
    }

    // ────────────────────────────────────────────────────

    public void insertBDalumno(Cliente cliente) {
        PreparedStatement preparedStatement;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query = "INSERT INTO usuario VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1,  cliente.getId());
            preparedStatement.setString(2,  cliente.getNombre());
            preparedStatement.setString(3,  cliente.getApellidos());
            preparedStatement.setString(4,  cliente.getEmail());
            preparedStatement.setString(5,  cliente.getDni());
            preparedStatement.setString(6,  cliente.getContrasena());

            preparedStatement.setString(7,  cliente.getCalle());
            preparedStatement.setInt(8,  cliente.getNum());
            preparedStatement.setString(9,  cliente.getPiso());
            preparedStatement.setString(10, cliente.getCiudad());
            preparedStatement.setString(11, cliente.getProvincia());
            preparedStatement.setString(12, cliente.getCodigoPostal());

            preparedStatement.setBoolean(13, cliente.isFacturacionIgualEnvio());
            preparedStatement.setString(14, cliente.getMetodoPago());
            preparedStatement.setString(15, cliente.getTarjeta());
            preparedStatement.setString(16, cliente.getCaducidad());
            preparedStatement.setString(17, cliente.getCvv());

            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ────────────────────────────────────────────────────

    public Cliente getClienteBDalumno(String email) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        Cliente C = null;

        try {
            Connection connection = conectandoBD("mosteiroDelPilar", "mosteiroDelPilar", "1234");

            String query = "SELECT * FROM usuario WHERE email=?";

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, email);

            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                C = new Cliente(
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