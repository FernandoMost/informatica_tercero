package myRMIchatP2P.Server;

import java.sql.*;
import java.util.HashSet;

public class DAOChatRoom {
    private Connection conexion;

    public DAOChatRoom() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            this.conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ChatRoom_CompDis", "mosteiroDelPilar", "1234");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    // ─────────────────────────────────────────────────────────────────────────────────────

    public boolean usuarioExiste(String nombre) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM usuario WHERE nombre=?";
            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean loginCorrecto(String nombre, String contrasena) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        if (!usuarioExiste(nombre))
            return false;

        try {
            String query = "SELECT * FROM usuario WHERE nombre=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();

            if (rs.next())
                return contrasena.equals(rs.getString("contrasena"));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean tengoSolicitudesAmistad(String nombre) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM solicitudPendiente WHERE destino=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();

            if (rs.next())
                return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public HashSet<String> getSolicitudesAmistad(String nombre) {
        HashSet<String> usuariosSolicitando = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM solicitudPendiente WHERE destino=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();

            while (rs.next())
                usuariosSolicitando.add(rs.getString("origen"));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return usuariosSolicitando;
    }

    public HashSet<String> getAmigos(String nombre) {
        HashSet<String> amigos = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM amistad WHERE amigo1=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, nombre);
            rs = preparedStatement.executeQuery();

            while (rs.next())
                amigos.add(rs.getString("amigo2"));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return amigos;
    }

    public boolean existeAmistad(String amigo1, String amigo2) {
        HashSet<String> amigos = getAmigos(amigo1);

        return amigos.contains(amigo2);
    }

    // ─────────────────────────────────────────────────────────────────────────────────────

    public void guardarNuevoUsuario(String nombre, String contrasena) {
        PreparedStatement preparedStatement;

        if (!usuarioExiste(nombre)) {
            try {
                String query = "INSERT INTO usuario VALUES (?,?)";

                preparedStatement = this.getConexion().prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, contrasena);
                preparedStatement.executeUpdate();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void guardarNuevaSolicitudAmistad(String origen, String destino) {
        PreparedStatement preparedStatement;

        if (!existeAmistad(origen, destino)) {
            try {
                String query = "INSERT INTO amistad VALUES (?,?)";

                preparedStatement = this.getConexion().prepareStatement(query);
                preparedStatement.setString(1, origen);
                preparedStatement.setString(2, destino);
                preparedStatement.executeUpdate();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rechazarSolicitudAmistad(String destino, String origen) {
        borrarSolicitudAmistad(origen, destino);
    }

    public void aceptarSolicitudAmistad(String destino, String origen) {
        guardarNuevaAmistad(destino, origen);
        borrarSolicitudAmistad(origen, destino);
    }

    public void borrarSolicitudAmistad(String origen, String destino) {
        PreparedStatement preparedStatement;

        try {
            String query = "DELETE FROM solicitudPendiente WHERE origen=? AND destino=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setString(1, origen);
            preparedStatement.setString(2, destino);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void guardarNuevaAmistad(String amigo1, String amigo2) {
        PreparedStatement preparedStatement;

        if (!existeAmistad(amigo1, amigo2)) {
            try {
                String query = "INSERT INTO amistad VALUES (?,?)";

                preparedStatement = this.getConexion().prepareStatement(query);
                preparedStatement.setString(1, amigo1);
                preparedStatement.setString(2, amigo2);
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, amigo2);
                preparedStatement.setString(2, amigo1);
                preparedStatement.executeUpdate();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void borrarAmistad(String amigo1, String amigo2) {
        PreparedStatement preparedStatement;

        if (existeAmistad(amigo1, amigo2)) {
            try {
                String query = "DELETE FROM amistad WHERE amigo1=? AND amigo2=?";

                preparedStatement = this.getConexion().prepareStatement(query);
                preparedStatement.setString(1, amigo1);
                preparedStatement.setString(2, amigo2);
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, amigo2);
                preparedStatement.setString(2, amigo1);
                preparedStatement.executeUpdate();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
