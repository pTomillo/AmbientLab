package com.pelayo.ambientlab.modelo;
import java.util.Date;

public class Sesion {
    private int idUsuario;
    private String cookie;
    private Date fechaCreacion;

    public Sesion() {

    }

    public Sesion(int idUsuario, String cookie, Date fechaCreacion) {
        this.idUsuario = idUsuario;
        this.cookie = cookie;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
