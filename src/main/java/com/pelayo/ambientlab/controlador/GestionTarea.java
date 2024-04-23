package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServicioTarea;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet(name = "GestionTarea", value = "/GestionTarea")
public class GestionTarea extends HttpServlet {
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioTarea servicioTarea = new ServicioTarea();

    public GestionTarea() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doGet.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);


        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doPost.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos los parametros para crear la tarea.
            String titulo = request.getParameter("titulo");
            String observaciones = request.getParameter("observaciones");
            String estado = request.getParameter("estado");
            int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            // Llamamos a la capa ServicioTarea para lanzar el metodo.

            servicioTarea.crearTarea(chequeo, titulo, observaciones, estado, idProyecto, idUsuario);


        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doPut.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);


        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doDelete.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos del cliente el id de la tarea que borrar.
            int idTarea = Integer.parseInt(request.getParameter("idTarea"));

            // Llamamos a la capa ServiciosTarea para lanzar el metodo de borrarTarea.
            servicioTarea.borrarTarea(chequeo, idTarea);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}