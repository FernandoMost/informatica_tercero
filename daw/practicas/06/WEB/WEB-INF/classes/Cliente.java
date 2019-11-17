import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Cliente {
    // Atributos

    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private String dni;
    private String contrasena;

    private String calle;
    private int num;
    private String piso;
    private String ciudad;
    private String provincia;
    private String codigoPostal;

    private boolean facturacionIgualEnvio;
    private String metodoPago;
    private String tarjeta;
    private String caducidad;
    private String cvv;

    // ────────────────────────────────────────────────────

    public Cliente(String email, String nombre, String apellidos) {
        this.email = email;
        this.contrasena = null;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Cliente(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    public Cliente(int id, String nombre, String apellidos, String email,
                   String dni, String contrasena, String calle, int num,
                   String piso, String ciudad, String provincia, String codigoPostal,
                   boolean facturacionIgualEnvio, String metodoPago,
                   String tarjeta, String caducidad, String cvv) {
                        this.id = id;
                        this.nombre = nombre;
                        this.apellidos = apellidos;
                        this.email = email;
                        this.dni = dni;
                        this.contrasena = contrasena;
                        this.calle = calle;
                        this.num = num;
                        this.piso = piso;
                        this.ciudad = ciudad;
                        this.provincia = provincia;
                        this.codigoPostal = codigoPostal;
                        this.facturacionIgualEnvio = facturacionIgualEnvio;
                        this.metodoPago = metodoPago;
                        this.tarjeta = tarjeta;
                        this.caducidad = caducidad;
                        this.cvv = cvv;
    }

    // ────────────────────────────────────────────────────

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public void setFacturacionIgualEnvio(boolean facturacionIgualEnvio) {
        this.facturacionIgualEnvio = facturacionIgualEnvio;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }


    // ────────────────────────────────────────────────────


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getDni() {
        return dni;
    }

    public String getContrasena() {
        return contrasena;
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

    public boolean isFacturacionIgualEnvio() {
        return facturacionIgualEnvio;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public String getCvv() {
        return cvv;
    }
}