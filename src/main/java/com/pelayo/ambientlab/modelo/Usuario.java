package com.pelayo.ambientlab.modelo;


import com.google.gson.Gson;
import com.pelayo.ambientlab.dao.DAOUsuario;

import java.sql.SQLException;

public class Usuario {

    private int id;
    private String nombre;
    private String apellidos;
    private int rol;
    private String email;
    private String hash;


    // Constructor vacio.
    public Usuario() throws SQLException {
    }

    // Constructor con id.
    public Usuario(int id, String nombre, String apellidos, int rol, String email, String hash) throws SQLException {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.email = email;
        this.hash = hash;
    }

    // Constructor sin id.
    public Usuario(String nombre, String apellidos, int rol, String email, String hash) throws SQLException {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.email = email;
        this.hash = hash;
    }

    public Usuario(int id, String nombre, String apellidos, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    public Usuario(int id, String nombre, String apellidos, int rol, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.email = email;
    }

    public Usuario(String nombre, String apellidos, String email, String hash) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.hash = hash;
    }

    public Usuario(int id) throws SQLException {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", rol=" + rol +
                ", email='" + email + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    // Metodos de la clase Usuario.

    public void crearUsuario() throws SQLException {
        DAOUsuario daoUsuario = new DAOUsuario();
        daoUsuario.crearUsuario(this);
    }

    public void borrarUsuario() throws SQLException {
        DAOUsuario daoUsuario = new DAOUsuario();
        daoUsuario.borrarUsuario(this.getId());
    }

    public String obtenerJSON() {
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(this);
        return json;
    }



    // Chequeos de rol:

    public boolean esAdmin(){
        return getRol() == 1;
    }

    public boolean esSupervisor() { return  getRol() == 2;}

    public boolean esAnalista() { return getRol()== 3;}

    public boolean esRegistro() { return getRol()== 4;}

    public boolean esCliente() {return  getRol()== 9;}

    public boolean esCreador() {return  getId() == this.id;}
}
