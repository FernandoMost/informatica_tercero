import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class LoginDAO {
    
    public boolean validar(Cliente cliente) throws ClassNotFoundException {
        PreparedStatement preparedStatement;
        ResultSet result;

        try {
            Connection connection = conectandoBD("mysql", "127.0.0.1", "3306", "bd_alumnos", "daw", "daw");

            String query= "SELECT * FROM altadaw WHERE email=?";

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

    private Connection conectandoBD(String gestor, String servidor, String puerto, String baseDatos, String usuario, String contrasena) {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String URL = "jdbc:" + gestor + "://" + servidor + ":" + puerto + "/" + baseDatos;

            return java.sql.DriverManager.getConnection(URL, usuario, contrasena);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}