package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Muestra;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServiciosMuestra;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServicioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet(name = "GestionMuestra", value = "/GestionMuestra")
public class GestionMuestra extends HttpServlet {

    DAOUsuario daoUsuario = new DAOUsuario();
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioUsuario servicioUsuario = new ServicioUsuario();

    ServiciosMuestra serviciosMuestra = new ServiciosMuestra();

    public GestionMuestra() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo peticion GET
        PrintWriter out = response.getWriter();
        // Recogemos la opcion enviada desde el cliente.


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
        // Manejo peticion POST

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);


            // Recogemos los datos para registrar la muestra.
            String referencia = request.getParameter("referencia");
            String tipo = request.getParameter("tipo");
            String origen = request.getParameter("origen");
            String estado = request.getParameter("estado");
            int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));

            // La fecha de registro es recogida y adecuadamente parseada.
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
            Date fecharegistro = formatoFecha.parse(request.getParameter("fechaRegistro"));

            // Llamamos a la capa ServiciosMuestra, concretamente a la funcion para registar una muestra.

            serviciosMuestra.registrarMuestra(chequeo, referencia, tipo, origen, estado, fecharegistro, idProyecto);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo peticion PUT

        // Obtenemos la opcion desde el cliente.


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
        // Manejo peticion DELETE

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos desde el cliente el Id de la muestra a eliminar.
            int id = Integer.parseInt(request.getParameter("id"));

            // Llamos a la capa de servicios muestra para lanzar el metodo borrarMuestra.
            serviciosMuestra.borrarMuestra(chequeo, id);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}