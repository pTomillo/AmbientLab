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

    public Usuario chequeoSesion(HttpServletRequest request, HttpServletResponse response) throws HTTPStatusException, SQLException {
            if (request.getCookies() == null) {
                throw new HTTPStatusException(401);
            }

            Cookie cookieSesion = null;
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("sesion")) {
                    cookieSesion = cookie;
                }
            }
            if (cookieSesion == null) {
                throw new HTTPStatusException(401);
            }
            Sesion sesion = this.daoSesion.sesionPorCookie(cookieSesion.getValue());
            if(sesion == null) {
                throw new HTTPStatusException(401);
            }
            Usuario usuario = this.daoUsuario.usuarioPorId(sesion.getIdUsuario());
            if (usuario == null) {
                throw new HTTPStatusException(401);
            }
            return usuario;

    }


    public String hashContrasena(String contrasena) {
        return Password.hash(contrasena).addRandomSalt().withScrypt().getResult();
    }

    public boolean chequeoContrasena(String contrasena, String hash){
        return Password.check(contrasena, hash).withScrypt();
    }
}
