package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOSesion;
import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Sesion;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;


@WebServlet(name = "LoginUsuario", value = "/LoginUsuario")
public class LoginUsuario extends HttpServlet {

    DAOUsuario daoUsuario = new DAOUsuario();
    DAOSesion daoSesion = new DAOSesion();
    ServicioLogin servicioLogin = new ServicioLogin();

    public LoginUsuario() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int opcion = Integer.parseInt(request.getParameter("op"));

        if (opcion == 0) { // Iniciar Sesion
            String email = request.getParameter("email");
            String contrasena = request.getParameter("contrasena");

            try {
                Usuario usuario = this.daoUsuario.usuarioPorMail(email);

                // Si el usuario no es econtrado devolvemos el error.
                if (usuario == null) {
                    throw new HTTPStatusException(404);
                }
                // Si es encontrado comprobamos que la contrasena sea la correcta, si es asi, devolvemos una cookie.
                if (this.servicioLogin.chequeoContrasena(contrasena, usuario.getHash())) {
                    String cookie = UUID.randomUUID().toString();

                    Sesion sesion = new Sesion(usuario.getId(), cookie, new Date());

                    this.daoSesion.crearSesion(sesion);

                    response.addCookie(new Cookie("sesion", sesion.getCookie()));

                    if (usuario.esAdmin()) {
                        response.sendRedirect("portalAdmin.html");
                    } else {
                        response.sendRedirect("dashboard.html");
                    }
                } else {
                    throw new HTTPStatusException(404);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (HTTPStatusException e) {
                response.sendError(e.getEstatus(), e.getMessage());
            }
        } else if (opcion == 1) { // Cerra Sesion
            // Obtener la cookie de sesion del usuario.
            Cookie[] cookies = request.getCookies();

            // Verificar que la cookie existe.
            if (cookies != null) {
                for(Cookie cookie : cookies) {
                    if (cookie.getName().equals("sesion")) {
                        // Borramos el registro de la cookie de la BD.
                        try {
                            daoSesion.cerrarSesion(cookie.getValue());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        // Decimos al navegador que elimine la cookie haciendola caducar y que la sesion se cierre.
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
            response.sendRedirect("index.html");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud PUT aquí
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud DELETE aquí
    }
}