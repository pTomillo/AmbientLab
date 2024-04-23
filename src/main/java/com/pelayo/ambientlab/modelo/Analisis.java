package com.pelayo.ambientlab.modelo;

import java.util.Date;


public class Analisis {
    private int id;
    private String observaciones;
    private String tipo;
    private Date fechaAnalisis;
    private String estado;
    private int idMuestra;
    private int idUsuario;
    private int idProyecto;

    // Creamos los metodos constructores necesarios.

    public Analisis() {
    }

    public Analisis(int id, String observaciones, String tipo, Date fechaAnalisis, String estado, int idMuestra, int idUsuario, int idProyecto) {
        this.id = id;
        this.observaciones = observaciones;
        this.tipo = tipo;
        this.fechaAnalisis = fechaAnalisis;
        this.estado = estado;
        this.idMuestra = idMuestra;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
    }

    public Analisis(String observaciones, String tipo, Date fechaAnalisis, String estado, int idMuestra, int idUsuario, int idProyecto) {
        this.observaciones = observaciones;
        this.tipo = tipo;
        this.fechaAnalisis = fechaAnalisis;
        this.estado = estado;
        this.idMuestra = idMuestra;
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
    }

    // Creamos los getters y setters.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaAnalisis() {
        return fechaAnalisis;
    }

    public void setFechaAnalisis(Date fechaAnalisis) {
        this.fechaAnalisis = fechaAnalisis;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdMuestra() {
        return idMuestra;
    }

    public void setIdMuestra(int idMuestra) {
        this.idMuestra = idMuestra;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    // Creamos el metodo toString().
    @Override
    public String toString() {
        return "Analisis{" +
                "id=" + id +
                ", observaciones='" + observaciones + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaAnalisis=" + fechaAnalisis +
                ", estado='" + estado + '\'' +
                ", idMuestra=" + idMuestra +
                ", idUsuario=" + idUsuario +
                ", idProyecto=" + idProyecto +
                '}';
    }
}
