package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.HashSet;

public class DAOMetodoPago extends AbstractDAO {
    public DAOMetodoPago(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    public HashSet<MetodoPago> getMetodosPago(Usuario usuario) {
        HashSet<MetodoPago> metodosPago = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM metodoPago WHERE usuario=?";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                metodosPago.add(new MetodoPago(
                        rs.getInt("id"),
                        usuario,
                        rs.getBoolean("facturacionIgualEnvio"),
                        rs.getString("metodoPago"),
                        rs.getString("tarjeta"),
                        rs.getString("caducidadTarjeta"),
                        rs.getString("codSeguridadTarjeta")
                ));
            }

            return metodosPago;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return metodosPago;
    }

    public void insertMetodoPago(MetodoPago metodoPago) {
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO metodoPago (usuario, facturacionIgualEnvio, metodoPago, tarjeta, caducidadTarjeta, codSeguridadTarjeta) VALUES (?,?,?,?,?,?)";

            preparedStatement = this.getConexion().prepareStatement(query);

            preparedStatement.setInt(1, metodoPago.getUsuario().getId());
            preparedStatement.setBoolean(2, metodoPago.isFacturacionIgualEnvio());
            preparedStatement.setString(3, metodoPago.getMetodoPago());
            preparedStatement.setString(4, metodoPago.getTarjeta());
            preparedStatement.setString(5, metodoPago.getCaducidadTarjeta());
            preparedStatement.setString(6, metodoPago.getCodSeguridadTarjeta());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
