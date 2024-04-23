package com.pelayo.ambientlab.modelo;

public class Resultado {
    private int id;
    private String parametro;
    private float valor;
    private String unidad;
    private int idAnalisis;

    // Creamos los metodos constructores.

    public Resultado() {
    }

    public Resultado(int id, String parametro, float valor, String unidad, int idAnalisis) {
        this.id = id;
        this.parametro = parametro;
        this.valor = valor;
        this.unidad = unidad;
        this.idAnalisis = idAnalisis;
    }

    public Resultado(String parametro, float valor, String unidad, int idAnalisis) {
        this.parametro = parametro;
        this.valor = valor;
        this.unidad = unidad;
        this.idAnalisis = idAnalisis;
    }

    // Creamos los getters y setters.


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getIdAnalisis() {
        return idAnalisis;
    }

    public void setIdAnalisis(int idAnalisis) {
        this.idAnalisis = idAnalisis;
    }

    // Creamos el metodo toString().

    @Override
    public String toString() {
        return "Resultado{" +
                "id=" + id +
                ", parametro='" + parametro + '\'' +
                ", valor=" + valor +
                ", unidad='" + unidad + '\'' +
                ", idAnalisis=" + idAnalisis +
                '}';
    }
}
