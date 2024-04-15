package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOSesion;
import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServicioUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "GestionUsuario", value = "/GestionUsuario")
public class GestionUsuario extends HttpServlet {

    DAOUsuario daoUsuario = new DAOUsuario();
    DAOSesion daoSesion = new DAOSesion();
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioUsuario servicioUsuario = new ServicioUsuario();

    public GestionUsuario() throws SQLException {
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario chequeo;
        try {
            chequeo = servicioLogin.chequeoSesion(request, response);
        } catch (HTTPStatusException | SQLException e) {
            throw new RuntimeException(e);
        }
        if (chequeo == null) {
            return;
        }

        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        int rol = Integer.parseInt(request.getParameter("rol"));
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        try {
            servicioUsuario.crearUsuario(chequeo, nombre, apellidos, rol, email, contrasena);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud PUT aquí
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud DELETE aquí


        Usuario chequeo = null;
        try {
            chequeo = servicioLogin.chequeoSesion(request, response);
        } catch (HTTPStatusException | SQLException e) {
            throw new RuntimeException(e);
        }


        int id = Integer.parseInt(request.getParameter("id"));

        try {
            servicioUsuario.borrarUsuario(chequeo, id);
        } catch (HTTPStatusException | SQLException e) {
            throw new RuntimeException(e);
        }

        response.getWriter().write("Usuario Borrado");
    }
}