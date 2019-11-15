import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Cliente {
    // Atributos

    private String email;
    private String contrasena;
    private String id;
    private String nombre;
    private String apellidos;

    // ────────────────────────────────────────────────────

    public Cliente(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
        this.id = null;
        this.nombre = null;
        this.apellidos = null;
    }

    public Cliente(String email, String contrasena,  String id, String nombre, String apellidos) {
        this.email = email;
        this.contrasena = contrasena;
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // ────────────────────────────────────────────────────

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // ────────────────────────────────────────────────────


    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
}