package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.Date;
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

    public int getIdUltimoPedido(Usuario usuario) {
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "select MAX(id) as id from pedido where usuario = ?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next())
                return rs.getInt("id");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public void insertArticulo(Pedido pedido, Articulo articulo, int cantidad) {
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO articuloEnPedido VALUES (?,?,?)";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, pedido.getId());
            preparedStatement.setInt(2, articulo.getId());
            preparedStatement.setInt(3, cantidad);
            preparedStatement.executeUpdate();

            getFachadaBD().retirarStock(articulo, cantidad);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertPedido(Usuario usuario, int direccionEnvio, int metodoPago) {
        Carrito carrito = usuario.getCarrito();     // carrito del usuario
        // Nuevo pedido a insertar (aún no se sabe su id)
        Pedido pedido = new Pedido(0, usuario, new Date(), direccionEnvio, metodoPago, carrito.getPrecioTotal());
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO pedido (usuario, fecha, direccionEnvio, metodoPago, total) VALUES (?, ?, ?, ?, ?)";

            preparedStatement = this.getConexion().prepareStatement(query);

            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, direccionEnvio);
            preparedStatement.setInt(4, metodoPago);
            preparedStatement.setDouble(5, carrito.getPrecioTotal());

            // se inserta el pedido
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // se le pone su id correspondiente
        pedido.setId(getIdUltimoPedido(usuario));
        // se añaden los articulos y sus cantidades según el carrito
        for (Articulo a : carrito.getArticulos()) insertArticulo(pedido, a, carrito.getNumArticulos(a));
        // se vacía el carrito y se refleja en la BD
        carrito.resetCarrito(); getFachadaBD().insertCarrito(usuario);
    }
}
