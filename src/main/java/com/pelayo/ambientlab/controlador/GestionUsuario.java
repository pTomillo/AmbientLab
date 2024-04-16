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
import java.io.PrintWriter;
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

        PrintWriter out = response.getWriter();
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;

            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 1) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (chequeo.esAdmin() || chequeo.getId() == id) {
                    String json = "";
                    json = this.daoUsuario.buscarUsuario(id);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 0) {
                if (chequeo != null && chequeo.esAdmin()) {
                    String json = "";
                    json = this.daoUsuario.llamarJson();
                    System.out.println(json);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            }
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario chequeo;
            try {
                chequeo = servicioLogin.chequeoSesion(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud PUT aquí

        PrintWriter out = response.getWriter();

        try {
            Usuario chequeo;
            chequeo = servicioLogin.chequeoSesion(request, response);

            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            int rol = Integer.parseInt(request.getParameter("rol"));
            String email = request.getParameter("email");
            String contrasena = request.getParameter("contrasena");

            String hash = servicioLogin.hashContrasena(contrasena);

            Usuario aEditar =new Usuario(id, nombre, apellidos, rol, email, hash);

            servicioUsuario.editarUsuario(chequeo, aEditar);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        }
        response.getWriter().write("Usuario Editado");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud DELETE aquí


        Usuario chequeo = null;
        try {
            chequeo = servicioLogin.chequeoSesion(request, response);

            int id = Integer.parseInt(request.getParameter("id"));

            try {
                servicioUsuario.borrarUsuario(chequeo, id);
            } catch (HTTPStatusException | SQLException e) {
                throw new RuntimeException(e);
            }

            response.getWriter().write("Usuario Borrado");
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}