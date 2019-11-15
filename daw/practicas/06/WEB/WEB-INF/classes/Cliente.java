import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Cliente {
    // Atributos

    private String nombre;
    private String apellidos;
    private String email;
    private String id;

    // ────────────────────────────────────────────────────

    public Cliente() {
        conectandoBD("mysql", "127.0.0.1", "3306", "alumnosDB", "daw", "daw");
    }

    // ────────────────────────────────────────────────────

    private boolean conectandoBD(String gestor, String servidor, String puerto, String baseDatos, String usuario, String contrasena) {
        Connection conexion = null;
        Properties usuario = null;

        try {
            String URL = "jdbc:" + gestor + "://" + servidor + ":" + puerto + "/" + baseDatos;

            usuario = new Properties();
            usuario.setProperty("user", usuario);
            usuario.setProperty("password", contrasena);

            conexion = java.sql.DriverManager.getConnection(URL, usuario);

            return true;

        } catch(ClassNotFoundException e) {
            System.out.println("Clase no encontrada. " + e.getMessage());
        } catch(InstantiationException e) {
            System.out.println("Objeto no creado. " + e.getMessage());
        } catch(IllegalAccessException e) {
            System.out.println("Acceso ilegal. " + e.getMessage());
        } catch(SQLException e) {
            System.out.println("Excepción SQL. " + e.getMessage());
        }
    }
}
