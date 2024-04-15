package com.pelayo.ambientlab.modelo;


import com.pelayo.ambientlab.dao.DAOUsuario;

import java.sql.SQLException;

public class Usuario {

    DAOUsuario daoUsuario = new DAOUsuario();

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

    // Metodos de la clase Usuario.

    public void crearUsuario() throws SQLException {
        daoUsuario.crearUsuario(this);
    }

    public void borrarUsuario() throws SQLException {
        daoUsuario.borrarUsuario(this.getId());
    }

    // Chequeos de rol:

    public boolean esAdmin(){
        return getRol() == 1;
    }
}
