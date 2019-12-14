package ModeloNegocio;

public class Direccion {
    private int id;
    private String calle;
    private int num;
    private String piso;
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    private Usuario usuario;

    // ────────────────────────────────────────────────────

    public Direccion(int id, Usuario usuario, String calle, int num, String piso, String ciudad, String provincia, String codigoPostal) {
        this.id = id;
        this.usuario = usuario;
        this.calle = calle;
        this.num = num;
        this.piso = piso;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }

    // ────────────────────────────────────────────────────

    public int getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public String getCalle() { return calle; }
    public int getNum() { return num; }
    public String getPiso() { return piso; }
    public String getCiudad() { return ciudad; }
    public String getProvincia() { return provincia; }
    public String getCodigoPostal() { return codigoPostal; }

    // ────────────────────────────────────────────────────

    public void setId(int id) { this.id = id; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public void setCalle(String calle) { this.calle = calle; }
    public void setNum(int num) { this.num = num; }
    public void setPiso(String piso) { this.piso = piso; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
}
