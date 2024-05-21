package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOTarea;
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
import java.io.PrintWriter;
import java.sql.SQLException;


/**
 * Este servlet maneja la peticiones a la clase Tarea. Incluyendo las peticiones de creacion, listado, edicion y borrado.
 */
@WebServlet(name = "GestionTarea", value = "/GestionTarea")
public class GestionTarea extends HttpServlet {
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioTarea servicioTarea = new ServicioTarea();
    DAOTarea daoTarea = new DAOTarea();

    public GestionTarea() throws SQLException {
    }

    /**
     * Maneja el metodo GET del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doGet.
        PrintWriter out = response.getWriter();
        // Obntenemos la opcion seleccionada desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Listar todas las tareas.
                // Comprobamos que el usuario sea Admin o Supervisor.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
                    String json = "";
                    // Llamos al DAOTareas para listar todas las tareas.
                    json = this.daoTarea.listarTareas();
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 1) { // Listar una tarea.
                if (chequeo != null) {
                    // Recogemos el idProyecto desde el cliente.
                    int idTarea = Integer.parseInt(request.getParameter("idTarea"));
                    String json = "";
                    // Llamos al DAOTareas para listar todas las tareas.
                    json = this.daoTarea.listarUnaTarea(idTarea);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 2) { // Listar tareas por Proyecto.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
                    // Recogemos el idProyecto desde el cliente.
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    // Llamos al DAOTareas para listar todas las tareas.
                    json = this.daoTarea.tareasPorProyecto(idProyecto);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 3) { // Listar tareas por Usuario.
                if (chequeo != null) {
                    // Recogemos el idUsuario
                    int idUsuario = chequeo.getId();
                    String json = "";
                    // Llamos al DAOTareas para listar todas las tareas del Usuario.
                    json = this.daoTarea.tareasPorUsuario(idUsuario);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 4) { // Listar Tareas de un Proyecto por Usuario.
                if (chequeo != null) {
                    // Recogemos el idUsuario
                    int idUsuario = chequeo.getId();
                    // Recogemos el idProyecto desde el cliente.
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    // Llamos al DAOTareas para listar todas las tareas del Usuario.
                    json = this.daoTarea.tareasPorProyectoXUsuario(idProyecto, idUsuario);
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

    /**
     * Maneja el metodo POST del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
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

    /**
     * Maneja el metodo PUT del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo solicutd doPut.
        // Recogemos la opcion desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Editar tarea.
                // Recogemos los parametros para crear la tarea.
                String titulo = request.getParameter("titulo");
                String observaciones = request.getParameter("observaciones");
                String estado = request.getParameter("estado");
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

                // Recogemos el id de la tarea a editar.
                int idTarea = Integer.parseInt(request.getParameter("idTarea"));

                // Llamamos a la capa servicios para lanzar el metodo editarTarea.
                servicioTarea.editarTarea(chequeo, titulo, observaciones, estado, idProyecto, idUsuario, idTarea);

            } else if (opcion == 1) { // Actualizar estado de tarea.
                // Reccogemos el estado actualizado desde el cliente.
                String estado = request.getParameter("estado");
                // Recogemos el Id de la tarea a actualizar.
                int idTarea = Integer.parseInt(request.getParameter("idTarea"));

                // Llamamos a la capa servicosTarea para lanzar el metodo actualizarEstadoTarea.
                servicioTarea.actualizarEstadoTarea(chequeo, estado, idTarea);
            }

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Maneja el metodo DELETE del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
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