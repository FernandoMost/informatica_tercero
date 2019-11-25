package sample.teatro;

public class Butaca {
    private Funcion funcion;
    private boolean comprada;
    private double precio;

    public Butaca(Funcion funcion, boolean comprada, double precio) {
        this.funcion = funcion;
        this.comprada = comprada;
        this.precio = precio;
    }

    // ───────────────────────────────────────────────────────

    public Funcion getFuncion() {
        return funcion;
    }
    public boolean isComprada() {
        return comprada;
    }
    public double getPrecio() {
        return precio;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }
    public void setComprada(boolean comprada) {
        this.comprada = comprada;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void cambiarEstado() {
        if (this.comprada) setComprada(false);
        else setComprada(true);
    }
}
