package sample.teatro;

import java.util.ArrayList;

public class Obra {
    private Teatro teatro;
    private String nombre;
    private String descripcion;
    private ArrayList<Funcion> funciones;

    public Obra(Teatro teatro, String nombre, String descripcion) {
        this.teatro = teatro;
        this.teatro.addObra(this);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.funciones = new ArrayList<>();
    }

    // ───────────────────────────────────────────────────────

    public Teatro getTeatro() {
        return teatro;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public void setTeatro(Teatro teatro) {
        this.teatro = teatro;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }
    public void addFuncion(Funcion funcion) {
        this.funciones.add(funcion);
    }
}
