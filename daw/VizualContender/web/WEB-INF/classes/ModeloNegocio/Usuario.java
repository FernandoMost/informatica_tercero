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

    private Carrito carrito;

    private HashSet<Pedido> pedidos;

    // ────────────────────────────────────────────────────

    public Usuario(int id, String nombre, String apellidos, String email, String dni, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.dni = dni;
        this.contrasena = contrasena;

        this.direcciones = new HashSet<>();
        this.metodosPago = new HashSet<>();
        this.carrito = new Carrito();
        this.pedidos = new HashSet<>();
    }

    // ────────────────────────────────────────────────────

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }
    public String getDni() { return dni; }
    public String getContrasena() { return contrasena; }

    public HashSet<Direccion> getDirecciones() { return direcciones; }
    public HashSet<MetodoPago> getMetodosPago() { return metodosPago; }

    public Carrito getCarrito() { return carrito; }
    public HashSet<Pedido> getPedidos() { return pedidos; }

    // ────────────────────────────────────────────────────

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setEmail(String email) { this.email = email; }
    public void setDni(String dni) { this.dni = dni; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setDirecciones(HashSet<Direccion> direcciones) { this.direcciones = direcciones; }
    public void setMetodosPago(HashSet<MetodoPago> metodosPago) { this.metodosPago = metodosPago; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }
    public void setPedidos(HashSet<Pedido> pedidos) { this.pedidos = pedidos; }

    // ────────────────────────────────────────────────────

    public void addDireccion(Direccion d) {
        this.direcciones.add(d);
        d.setUsuario(this);
    }

    public void addMetodoPago(MetodoPago mp) {
        this.metodosPago.add(mp);
        mp.setUsuario(this);
    }

    public void addPedido(Pedido p) {
        this.pedidos.add(p);
        p.setUsuario(this);
    }

    public void removeDireccion(Direccion d) { this.direcciones.remove(d); }
    public void removeMetodoPago(MetodoPago mp) { this.metodosPago.remove(mp); }
    public void removePedido(Pedido p) { this.pedidos.remove(p); }
}