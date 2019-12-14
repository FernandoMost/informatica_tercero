package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.HashSet;

public class DAOArticulo extends AbstractDAO {
    public DAOArticulo(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    public HashSet<Articulo> getAllArticulos() {
        HashSet<Articulo> articulos = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM articulo";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                articulos.add(new Articulo(
                    rs.getInt("id"),
                    rs.getString("imagen"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("descripcion"),
                    rs.getInt("stock")
                ));
            }

            return articulos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return articulos;
    }

    public Articulo getArticulo(int id) {
        Articulo articulo = null;
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM articulo WHERE id=?";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                articulo = new Articulo(
                    rs.getInt("id"),
                    rs.getString("imagen"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("descripcion"),
                    rs.getInt("stock")
                );
            }

            return articulo;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void insertArticulo(Articulo articulo) {
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO articulo (nombre, descripcion, precio, stock, imagen) VALUES (?,?,?,?,?)";

            preparedStatement = this.getConexion().prepareStatement(query);

            preparedStatement.setString(1, articulo.getNombre());
            preparedStatement.setString(2, articulo.getDescripcion());
            preparedStatement.setDouble(3, articulo.getPrecio());
            preparedStatement.setInt(4, articulo.getStock());
            preparedStatement.setString(5, articulo.getImagen());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}