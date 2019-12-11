package ModeloNegocio;

public class MetodoPago {
    private int id;
    private int usuario;
    private boolean facturacionIgualEnvio;
    private String metodoPago;
    private String tarjeta;
    private String caducidadTarjeta;
    private String codSeguridadTarjeta;

    // ────────────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public int getUsuario() {
        return usuario;
    }

    public boolean isFacturacionIgualEnvio() {
        return facturacionIgualEnvio;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public String getCaducidadTarjeta() {
        return caducidadTarjeta;
    }

    public String getCodSeguridadTarjeta() {
        return codSeguridadTarjeta;
    }

    // ────────────────────────────────────────────────────


    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public void setFacturacionIgualEnvio(boolean facturacionIgualEnvio) {
        this.facturacionIgualEnvio = facturacionIgualEnvio;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setCaducidadTarjeta(String caducidadTarjeta) {
        this.caducidadTarjeta = caducidadTarjeta;
    }

    public void setCodSeguridadTarjeta(String codSeguridadTarjeta) {
        this.codSeguridadTarjeta = codSeguridadTarjeta;
    }
}
