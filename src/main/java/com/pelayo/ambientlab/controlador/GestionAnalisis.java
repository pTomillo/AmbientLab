package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOAnalisis;
import com.pelayo.ambientlab.dao.DAOMuestra;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServiciosAnalisis;
import com.pelayo.ambientlab.servicios.ServiciosMuestra;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "GestionAnalisis", value = "/GestionAnalisis")
public class GestionAnalisis extends HttpServlet {

    ServicioLogin servicioLogin = new ServicioLogin();
    ServiciosAnalisis serviciosAnalisis = new ServiciosAnalisis();
    DAOAnalisis daoAnalisis = new DAOAnalisis();
    public GestionAnalisis() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo de la peticion doGet

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
        // Manejo de la peticion doPost

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos los parametros desde el cliente para crear el Analisis.
            String observaciones = request.getParameter("observaciones");
            String tipo = request.getParameter("tipo");

            // La fecha tiene que ser correctamente parseada a un objeto Date.
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
            Date fecharegistro = formatoFecha.parse(request.getParameter("fechaAnalisis"));

            String estado = request.getParameter("estado");

            int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));
            int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));

            // Pasamos los parametros junto con el usuario que lanza la peticion a la funcion de la capa servicios crearAnalisis.

            serviciosAnalisis.crearAnalisis(chequeo, observaciones, tipo, fecharegistro, estado, idMuestra, idProyecto);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo de la peticion doPut

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
        // Manejo de la peticion doDelete

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos el Id del analisis a eliminar.
            int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));

            // Llamamos a la capa servicios para lanzar la funcion eliminarAnalisis.
            serviciosAnalisis.eliminarAnalisis(chequeo, idAnalisis);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}