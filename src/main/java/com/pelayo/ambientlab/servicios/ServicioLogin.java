package com.pelayo.ambientlab.servicios;

import com.password4j.Password;
import com.pelayo.ambientlab.dao.DAOSesion;
import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Sesion;
import com.pelayo.ambientlab.modelo.Usuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ServicioLogin {

    DAOSesion daoSesion = new DAOSesion();
    DAOUsuario daoUsuario = new DAOUsuario();

    public ServicioLogin() throws SQLException {
    }

    /**
     *
     * @param request
     * @param response
     * @return Devuelve el Usuario chequeo si la sesion es correcta.
     * @throws HTTPStatusException Error 401: Acceso no autorizado.
     * @throws SQLException
     */

    public Usuario chequeoSesion(HttpServletRequest request, HttpServletResponse response) throws HTTPStatusException, SQLException {
            // Comprobamos que existen Cookies, si no las hay devolvemos error.
            if (request.getCookies() == null) {
                throw new HTTPStatusException(401);
            }
            // Inicializamos la cookie
            Cookie cookieSesion = null;
            // Comprobamos que la cookie este en el navegador.
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sesion")) {
                    cookieSesion = cookie;
                }
            }
            if (cookieSesion == null) {
                throw new HTTPStatusException(401);
            }
            // Comprobanos que la sesion, via cookie, este registrada en la BD.
            Sesion sesion = this.daoSesion.sesionPorCookie(cookieSesion.getValue());
            if(sesion == null) {
                throw new HTTPStatusException(401);
            }
            // Se busca el usuario correspondiente con la sesion en la BD.
            Usuario usuario = this.daoUsuario.usuarioPorId(sesion.getIdUsuario());
            if (usuario == null) {
                throw new HTTPStatusException(401);
            }
            // Si tanto la cookie como el ID de Usuario se corresponden devolvemos el usuario.
            return usuario;

    }

    /**
     * Devuelve la contraseña encriptada mediante el uso de la libreria password4j.
     * @param contrasena Contraseña introducida por el usuario en el cliente.
     * @return Devuelve Hash generado por la libreria de encriptacion password4j.
     */
    public String hashContrasena(String contrasena) {
        // Devolvemos la contraseña hasheada.
        return Password.hash(contrasena).addRandomSalt().withScrypt().getResult();
    }


    /**
     *
     * @param contrasena Contraseña introducida por el usuario en el cliente.
     * @param hash Hash almacenado en la BD que contiene la contraseña del Usuario.
     * @return Booleano en funcion de si la contraseña es correcta o no.
     */
    public boolean chequeoContrasena(String contrasena, String hash){
        // Devolvemos un booleano despues de comprobar que la contraseña se corresponde.
        return Password.check(contrasena, hash).withScrypt();
    }
}
