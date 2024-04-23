package com.pelayo.ambientlab.modelo;

public class Tarea {
    private int id;
    private String titulo;
    private String observaciones;
    private String estado;
    private int idProyecto;
    private int idUsuario;

    // Creamos los metodos constructores.


    public Tarea() {
    }

    public Tarea(int id, String titulo, String observaciones, String estado, int idProyecto, int idUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idProyecto = idProyecto;
        this.idUsuario = idUsuario;
    }

    public Tarea(String titulo, String observaciones, String estado, int idProyecto, int idUsuario) {
        this.titulo = titulo;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idProyecto = idProyecto;
        this.idUsuario = idUsuario;
    }

    // Creamos los getters y setters.


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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Creamos el metodo ToString()

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", estado='" + estado + '\'' +
                ", idProyecto=" + idProyecto +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
