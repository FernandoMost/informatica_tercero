package ModeloNegocio;

import java.util.Date;
import java.util.HashMap;

public class Pedido {
    private int id;
    private Usuario usuario;
    private Date fecha;
    private int direccionEnvio;
    private int metodoPago;
    private double total;

    private HashMap<Articulo, Integer> articulos;

    // ────────────────────────────────────────────────────

    public Pedido(int id, Usuario usuario, Date fecha, int direccionEnvio, int metodoPago, double total) {
        this.id = id;
        this.usuario = usuario;
        this.fecha = fecha;
        this.direccionEnvio = direccionEnvio;
        this.metodoPago = metodoPago;
        this.total = total;
        this.articulos = new HashMap<>();
    }

    // ────────────────────────────────────────────────────

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Date getFecha() { return fecha; }
    public int getDireccionEnvio() { return direccionEnvio; }
    public int getMetodoPago() { return metodoPago; }
    public double getTotal() { return total; }
    public HashMap<Articulo, Integer> getArticulos() { return articulos; }

    // ────────────────────────────────────────────────────

    public void setId(int id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setDireccionEnvio(int direccionEnvio) { this.direccionEnvio = direccionEnvio; }
    public void setMetodoPago(int metodoPago) { this.metodoPago = metodoPago; }
    public void setTotal(double total) { this.total = total; }

    // ────────────────────────────────────────────────────

    public void addArticulo(Articulo articulo, int cantidad) {
        this.articulos.put(articulo, cantidad);
    }
}
