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
import java.io.PrintWriter;
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
        PrintWriter out = response.getWriter();
        // Recogemos la opcion enviada desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Listar todos los Analisis.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
                    String json = "";
                    json = this.daoAnalisis.listarAnalisis();
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 1) { // Listar un Analisis.
                if (chequeo != null) {
                    int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));
                    String json = "";
                    json = this.daoAnalisis.listarUnAnalisis(idAnalisis);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }
            } else if (opcion == 2) { // Listar Analisis por proyecto
                if (chequeo != null) {
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    json = this.daoAnalisis.analisisPorProyecto(idProyecto);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }
            } else if (opcion == 3) { // Listar Analisis por Muestra
                if (chequeo != null) {
                    int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));
                    String json = "";
                    json = this.daoAnalisis.analisisPorMuestra(idMuestra);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }
            } else if (opcion == 4) { // Listar Analisis por Usuario
                if (chequeo != null) {
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    String json = "";
                    json = this.daoAnalisis.analisisPorUsuario(idUsuario);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
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
        // Recogemos la opcion enviada desde el cliente
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Editar un analisis.
                // Recogemos los parametros desde el cliente para editar el Analisis.
                String observaciones = request.getParameter("observaciones");
                String tipo = request.getParameter("tipo");

                // La fecha tiene que ser correctamente parseada a un objeto Date.
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd");
                Date fecharAnalisis = formatoFecha.parse(request.getParameter("fechaAnalisis"));

                String estado = request.getParameter("estado");

                int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));


                serviciosAnalisis.editarAnalisis(chequeo, observaciones, tipo, fecharAnalisis, estado, idMuestra, idProyecto, idUsuario, idAnalisis);

            } else if (opcion == 1) { // Actualizar estado de un analisis.
                // Recogemos los parametros desde el cliente para actualizar el estado del Analisis.
                String estado = request.getParameter("estado");
                int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));

                // Llamamos a la capa de serviciosAnalisis para lanzar el metodo actualiarEstado.
                serviciosAnalisis.actualizarEstado(chequeo, estado, idAnalisis);
            }
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException | ParseException e) {
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