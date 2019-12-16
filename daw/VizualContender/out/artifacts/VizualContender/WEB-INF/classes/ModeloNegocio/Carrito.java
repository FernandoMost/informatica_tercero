package ModeloNegocio;

import java.util.HashMap;
import java.util.Set;

public class Carrito {
    private HashMap<Articulo, Integer> articulos;

    public Carrito() {
        this.articulos = new HashMap<>();
    }

    // ────────────────────────────────────────────────────

    public Set<Articulo> getArticulos() { return articulos.keySet(); }
    public void setArticulos(HashMap<Articulo, Integer> articulos) { this.articulos = articulos;}

    public int getNumArticulos() {
        return this.articulos.size();
    }

    public int getNumArticulos(Articulo articulo) {
        for (Articulo a : this.articulos.keySet())
            if (a.getId() == articulo.getId())
                return this.articulos.get(a);

        return 0;
    }

    public double getPrecioTotal() {
        double suma = 0.0;

        for (Articulo a : this.articulos.keySet())
            suma += a.getPrecio() * this.articulos.get(a);

        return suma;
    }

    public double getPrecioTotalSinIva() {
        return getPrecioTotal() * (1.00 - 0.21);
    }

    // ────────────────────────────────────────────────────

    public void addToCarrito(Articulo articulo, int cantidad) {
        for (Articulo a : this.articulos.keySet()) {
            if (a.getId() == articulo.getId()) {
                int cantidadVieja = this.articulos.get(a);
                this.articulos.remove(a);

                this.articulos.put(articulo, cantidadVieja + cantidad);

                return;
            }
        }

        this.articulos.put(articulo, cantidad);
    }

    public void setCantidadArticulo(Articulo articulo, int cantidad) {
        for (Articulo a : this.articulos.keySet()) {
            if (a.getId() == articulo.getId()) {
                this.articulos.remove(a);
                this.articulos.put(articulo, cantidad);
                return;
            }
        }

        this.articulos.put(articulo, cantidad);
    }

    public void removeFromCarrito(Articulo articulo, int cantidad) {
        for (Articulo a : this.articulos.keySet())
            if (a.getId() == articulo.getId()) {
                int cantidadVieja = this.articulos.get(a);
                this.articulos.remove(a);

                int cantidadNueva = cantidadVieja - cantidad;

                if (cantidadNueva > 0)
                    this.articulos.put(articulo, cantidadNueva);

                return;
            }
    }

    public void removeAllFromCarrito(Articulo articulo) {
        for (Articulo a : this.articulos.keySet())
            if (a.getId() == articulo.getId()) {
                this.articulos.remove(a);
                return;
            }
    }

    public void resetCarrito() {
        this.articulos.clear();
    }
}
