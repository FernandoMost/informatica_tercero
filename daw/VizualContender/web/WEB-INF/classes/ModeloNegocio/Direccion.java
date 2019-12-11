package ModeloNegocio;

public class Direccion {
    private int id;
    private int usuario;
    private String calle;
    private int num;
    private String piso;
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    // ────────────────────────────────────────────────────

    public int getId() {
        return id;
    }

    public int getUsuario() {
        return usuario;
    }

    public String getCalle() {
        return calle;
    }

    public int getNum() {
        return num;
    }

    public String getPiso() {
        return piso;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    // ────────────────────────────────────────────────────


    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
