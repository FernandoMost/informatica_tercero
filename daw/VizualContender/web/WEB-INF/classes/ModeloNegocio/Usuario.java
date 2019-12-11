package ModeloNegocio;

import java.util.HashMap;
import java.util.HashSet;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String dni;
    private String contrasena;

    private HashSet<Direccion> direcciones;
    private HashSet<MetodoPago> metodosPago;

    private HashMap<Articulo, Integer> carrito;

    private HashSet<Pedido> pedidos;

    // ────────────────────────────────────────────────────



    // ────────────────────────────────────────────────────

}