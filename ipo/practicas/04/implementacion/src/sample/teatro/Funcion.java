package sample.teatro;

import java.util.Date;

public class Funcion {
    private Obra obra;
    private Butaca[][] butacas = new Butaca[Teatro.NUM_FILAS][Teatro.NUM_COLUMNAS];
    private Date fechaHora;

    public Funcion() {
    }

    public Funcion(Obra obra, Date fechaHora, double precioNormal, double precioEspecial) {
        this.obra = obra;
        this.obra.addFuncion(this);
        this.fechaHora = fechaHora;
        creaButacas(precioNormal, precioEspecial);
    }

    public Funcion(Obra obra, Date fechaHora) {
        this.obra = obra;
        this.fechaHora = fechaHora;
    }

    private void creaButacas(double precioNormal, double precioEspecial) {
        int f = 0, c = 0;

        for (Butaca[] fila : butacas) {
            for (Butaca columna : fila) {
                if (!Teatro.COLUMNAS_PASILLO.contains(c)) {
                    if (f-(Teatro.NUM_FILAS-1) == 0) {
                        if (c <= 5 || c >= 10)
                            columna = new Butaca(this, false, precioEspecial);
                        else
                            columna = new Butaca(this, false, precioNormal);
                    } else
                        columna = new Butaca(this, false, precioNormal);
                } else
                    columna = null;

                c++;
            }

            c = 0; f++;
        }
    }

    // ───────────────────────────────────────────────────────

    public Obra getObra() {
        return obra;
    }
    public Butaca[][] getButacas() {
        return butacas;
    }
    public Date getFechaHora() {
        return fechaHora;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
    public void setButacas(double precioNormal, double precioEspecial) {
        this.creaButacas(precioNormal, precioEspecial);
    }
    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
