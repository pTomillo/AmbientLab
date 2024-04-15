package com.pelayo.ambientlab.servicios;

import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;

public class ServicioUsuario {
    ServicioLogin servicioLogin = new ServicioLogin();

    public ServicioUsuario() throws SQLException {
    }

    public void crearUsuario(Usuario creador, String nombre, String apellidos, int rol, String email, String contrasena) throws SQLException {
        if (creador.getRol() == 1) {

            String hash = this.servicioLogin.hashContrasena(contrasena);

            Usuario usuario = new Usuario(nombre, apellidos, rol, email, hash);
            System.out.println(usuario);

            try {
                usuario.crearUsuario();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {

        }
    }

    public void borrarUsuario(Usuario creador, int id) throws HTTPStatusException, SQLException {
        if (!creador.esAdmin()) {
            throw new HTTPStatusException(403);
        }
        Usuario usuarioABorrar = new Usuario();
        usuarioABorrar.setId(id);
        usuarioABorrar.borrarUsuario();
    }




}
