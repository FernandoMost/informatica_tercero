package AccesoBD;

import ModeloNegocio.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DAOCarrito extends AbstractDAO {
    public DAOCarrito(Connection conexion, FachadaBD fachadaBD) {
        super.setConexion(conexion);
        super.setFachadaBD(fachadaBD);
    }

    // ────────────────────────────────────────────────────

    public Carrito getCarrito(Usuario usuario) {
        Carrito carrito = new Carrito();
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM carrito WHERE usuario=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Articulo ar = getFachadaBD().getArticulo(rs.getInt("articulo"));
                int cant = rs.getInt("cantidad");

                carrito.addToCarrito(
                    getFachadaBD().getArticulo(rs.getInt("articulo")),
                    rs.getInt("cantidad")
                );
            }

            return carrito;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return carrito;
    }

    public boolean isArticuloInCarrito(Usuario usuario, Articulo articulo) {
        boolean respuesta = false;
        PreparedStatement preparedStatement;
        ResultSet rs;

        try {
            String query = "SELECT * FROM carrito WHERE usuario=? AND articulo=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, articulo.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next())
                respuesta = true;

            return respuesta;
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return respuesta;
    }

    public void addArticuloToCarrito(Usuario usuario, Articulo articulo, int cantidad) {
        PreparedStatement preparedStatement;

        try {
            String query = "INSERT INTO carrito (usuario, articulo, cantidad) VALUES (?,?,?)";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, articulo.getId());
            preparedStatement.setInt(3, cantidad);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeArticuloFromCarrito(Usuario usuario, Articulo articulo) {
        PreparedStatement preparedStatement;

        try {
            String query = "DELETE FROM carrito WHERE usuario=? AND articulo=?";

            preparedStatement = this.getConexion().prepareStatement(query);
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, articulo.getId());
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertCarrito(Usuario usuario) {
        Carrito carritoNuevo = usuario.getCarrito();

        for (Articulo a : carritoNuevo.getArticulos()) {
            if (isArticuloInCarrito(usuario, a)) {
                PreparedStatement preparedStatement;

                try {
                    String query = "UPDATE carrito SET cantidad=? WHERE usuario=? AND articulo=?";

                    preparedStatement = this.getConexion().prepareStatement(query);
                    preparedStatement.setInt(1, carritoNuevo.getNumArticulos(a));
                    preparedStatement.setInt(2, usuario.getId());
                    preparedStatement.setInt(3, a.getId());
                    preparedStatement.executeUpdate();
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                addArticuloToCarrito(usuario, a, carritoNuevo.getNumArticulos(a));
            }
        }

        Carrito carritoEnBaseDeDatos = getCarrito(usuario);
        ArrayList<Articulo> articulosEnBaseDeDatos = new ArrayList<>(carritoEnBaseDeDatos.getArticulos());

        for (Articulo a : articulosEnBaseDeDatos) {
            for (Articulo b : carritoNuevo.getArticulos()) {
                if (a.getId() == b.getId()) {
                    carritoEnBaseDeDatos.removeAllFromCarrito(a);
                    break;
                }
            }
        }

        for (Articulo a : carritoEnBaseDeDatos.getArticulos())
            removeArticuloFromCarrito(usuario, a);
    }
}
