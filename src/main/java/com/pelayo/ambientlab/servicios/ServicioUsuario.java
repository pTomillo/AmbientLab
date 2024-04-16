package com.pelayo.ambientlab.servicios;

import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;

public class ServicioUsuario {
    ServicioLogin servicioLogin = new ServicioLogin();
    DAOUsuario daoUsuario = new DAOUsuario();

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

    public void editarAdmin(Usuario creador, Usuario usuarioAeditar) throws HTTPStatusException, SQLException {
        if (!creador.esAdmin()) {
            throw new HTTPStatusException(403);
        }
        daoUsuario.editarUsuarioAdmin(usuarioAeditar);
    }

    public void editarUsuario(Usuario creador, Usuario usuarioAEditar) throws HTTPStatusException, SQLException {
        if (!creador.esAdmin() && creador.getId() != usuarioAEditar.getId()) {
             throw new HTTPStatusException(403);
        }
        int idAEditar = usuarioAEditar.getId();
        Usuario usuarioModificar = usuarioAEditar;
        daoUsuario.editarUsuario(usuarioModificar);
    }

    public void asignarRol(Usuario creador, int id, int rol) throws HTTPStatusException, SQLException {
        if (!creador.esAdmin()) {
            throw new HTTPStatusException(403);
        }
        Usuario usuarioModificar = daoUsuario.usuarioPorId(id);
        daoUsuario.asignarRol(rol, usuarioModificar);
    }

    public void cambiarContrasena(Usuario creador, String contrasena1, String contrasena2, String newContrasena) throws SQLException, HTTPStatusException {
        Usuario aCambiar = daoUsuario.usuarioPorId(creador.getId());
        System.out.println(aCambiar);
        System.out.println(contrasena1);
        System.out.println(contrasena2);
        boolean comprobacion1 = servicioLogin.chequeoContrasena(contrasena1, aCambiar.getHash());
        boolean comprobacion2 = servicioLogin.chequeoContrasena(contrasena2, aCambiar.getHash());

        if (!comprobacion1 || !comprobacion2) {
            throw new HTTPStatusException(404);
        } else {
            String newHash = servicioLogin.hashContrasena(newContrasena);
            System.out.println(newHash);
            int idUsuario = creador.getId();

            daoUsuario.cambiarContrasena(idUsuario, newHash);
        }

    }


    public void resetContrasenaByAdmin(Usuario chequeo, int id, String newCon) throws HTTPStatusException, SQLException {
        if (!chequeo.esAdmin()) {
            throw new HTTPStatusException(403);
        }
        String newHash = servicioLogin.hashContrasena(newCon);

        daoUsuario.resetContrasena(id, newHash);
    }
}
