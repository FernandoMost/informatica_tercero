package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.HashSet;

public class DAOPedido extends AbstractDAO {
    public DAOPedido(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    public HashSet<Pedido> getPedidos(Usuario usuario) {
        HashSet<Pedido> pedidos = new HashSet<>();
        PreparedStatement preparedStatement;
        ResultSet rs1, rs2;

        try {
            String query = "SELECT * FROM pedido WHERE usuario=?";

            Connection connection = this.getConexion();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs1 = preparedStatement.executeQuery();

            while (rs1.next()) {
                Pedido p = new Pedido(
                    rs1.getInt("id"),
                    usuario,
                    rs1.getDate("fecha"),
                    rs1.getInt("direccionEnvio"),
                    rs1.getInt("metodoPago"),
                    rs1.getDouble("total")
                );

                query = "SELECT * FROM articuloEnPedido WHERE pedido=?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, rs1.getInt("id"));
                rs2 = preparedStatement.executeQuery();

                while (rs2.next())
                    p.addArticulo(getFachadaBD().getArticulo(rs2.getInt("articulo")), rs2.getInt("cantidad"));

                pedidos.add(p);
            }

            return pedidos;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return pedidos;
    }
}
