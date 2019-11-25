package sample.teatro;

import java.util.ArrayList;
import java.util.Arrays;

public class Teatro {
    private String nombre;
    private ArrayList<Obra> obras;

    public static final int NUM_FILAS = 7;
    public static final int NUM_COLUMNAS = 15;
    public static final ArrayList<Integer> COLUMNAS_PASILLO = new ArrayList<>(Arrays.asList(3, 12));

    public Teatro(String nombre) {
        this.nombre = nombre;
        this.obras = new ArrayList<>();
    }

    // ───────────────────────────────────────────────────────

    public String getNombre() {
        return nombre;
    }
    public ArrayList<Obra> getObras() {
        return obras;
    }
    public static int getNumFilas() {
        return NUM_FILAS;
    }
    public static int getNumColumnas() {
        return NUM_COLUMNAS;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setObras(ArrayList<Obra> obras) {
        this.obras = obras;
    }
    public void addObra(Obra obra) {
        this.obras.add(obra);
    }
}
