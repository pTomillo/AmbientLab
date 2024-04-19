package com.pelayo.ambientlab.modelo;

import java.util.Date;

public class Proyecto {
    private int id;
    private String titulo;
    private String descrripcion;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;


    // Creamos los metodos constructores necesarios.


    public Proyecto(int id, String titulo, String descrripcion, String estado, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.titulo = titulo;
        this.descrripcion = descrripcion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Proyecto(String titulo, String descrripcion, String estado, Date fechaInicio, Date fechaFin) {
        this.titulo = titulo;
        this.descrripcion = descrripcion;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Creamos los getters and setters,,


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrripcion() {
        return descrripcion;
    }

    public void setDescrripcion(String descrripcion) {
        this.descrripcion = descrripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Metodo toString.


    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descrripcion='" + descrripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
