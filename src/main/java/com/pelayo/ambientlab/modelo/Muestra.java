package com.pelayo.ambientlab.modelo;

import java.util.Date;

public class Muestra {
    private int id;
    private String referencia;
    private String tipo;
    private String origen;
    private String estado;
    private Date fechaRegistro;
    private int idProyecto;

    // Creamos los metodos constructores.

    public Muestra() {

    }

    public Muestra(int id, String referencia, String tipo, String origen, String estado, Date fechaRegistro, int idProyecto) {
        this.id = id;
        this.referencia = referencia;
        this.tipo = tipo;
        this.origen = origen;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.idProyecto = idProyecto;
    }

    public Muestra(String referencia, String tipo, String origen, String estado, Date fechaRegistro, int idProyecto) {
        this.referencia = referencia;
        this.tipo = tipo;
        this.origen = origen;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.idProyecto = idProyecto;
    }

    // Creamos los Getters y Setters.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    // Creamos el metodo ToString().


    @Override
    public String toString() {
        return "Muestra{" +
                "id=" + id +
                ", referencia='" + referencia + '\'' +
                ", tipo='" + tipo + '\'' +
                ", origen='" + origen + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", idProyecto=" + idProyecto +
                '}';
    }
}
