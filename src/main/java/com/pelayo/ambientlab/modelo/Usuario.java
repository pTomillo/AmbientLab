package com.pelayo.ambientlab.modelo;


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
    public Usuario() {
    }

    // Constructor con id.
    public Usuario(int id, String nombre, String apellidos, int rol, String email, String hash) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.email = email;
        this.hash = hash;
    }

    // Constructor sin id.
    public Usuario(String nombre, String apellidos, int rol, String email, String hash) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.email = email;
        this.hash = hash;
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

    public void crearUsuario() throws SQLException {
        DAOUsuario daoUsuario = new DAOUsuario();
        daoUsuario.crearUsuario(this);
    }
}
