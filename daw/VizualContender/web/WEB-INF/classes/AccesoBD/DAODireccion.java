package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.HashSet;

public class DAODireccion extends AbstractDAO {
    public DAODireccion(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    public HashSet<Direccion> getDirecciones(Usuario usuario) {
        HashSet<Direccion> direccions = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM direccionEnvio WHERE usuario=?";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                direccions.add(new Direccion(
                    rs.getInt("id"),
                    usuario,
                    rs.getString("calle"),
                    rs.getInt("num"),
                    rs.getString("piso"),
                    rs.getString("ciudad"),
                    rs.getString("provincia"),
                    rs.getString("codigoPostal")
                ));
            }

            return direccions;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return direccions;
    }

    public void insertDireccion(Direccion direccion) {
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO direccionEnvio (usuario, calle, num, piso, ciudad, provincia, codigoPostal) VALUES (?,?,?,?,?,?,?)";

            preparedStatement = this.getConexion().prepareStatement(query);

            preparedStatement.setInt(1, direccion.getUsuario().getId());
            preparedStatement.setString(2, direccion.getCalle());
            preparedStatement.setInt(3, direccion.getNum());
            preparedStatement.setString(4, direccion.getPiso());
            preparedStatement.setString(5, direccion.getCiudad());
            preparedStatement.setString(6, direccion.getProvincia());
            preparedStatement.setString(7, direccion.getCodigoPostal());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
