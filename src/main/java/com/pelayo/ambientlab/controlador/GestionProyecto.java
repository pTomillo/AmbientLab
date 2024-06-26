package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOProyecto;
import com.pelayo.ambientlab.dao.DAOSesion;
import com.pelayo.ambientlab.dao.DAOUsuario;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServicioUsuario;
import com.pelayo.ambientlab.servicios.ServiciosProyecto;
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

/**
 * Este servlet maneja la peticiones a la clase Proyecto. Incluyendo las peticiones de creacion, listado, edicion y borrado.
 */
@WebServlet(name = "GestionProyecto", value = "/GestionProyecto")
public class GestionProyecto extends HttpServlet {

    DAOUsuario daoUsuario = new DAOUsuario();
    DAOSesion daoSesion = new DAOSesion();
    DAOProyecto daoProyecto = new DAOProyecto();
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    ServiciosProyecto serviciosProyecto = new ServiciosProyecto();

    private Usuario chequeo;

    public GestionProyecto() throws SQLException {
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
        PrintWriter out = response.getWriter();
        // Opcion enviada desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));
        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Listar todos los proyectos.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
                    String json = "";
                    json = this.daoProyecto.listarProyectos();
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 1) { // Listar un proyecto.
                if (chequeo != null) {
                    int id = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    json = this.daoProyecto.buscarProyecto(id);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 3) { // Listar proyectos segun Usuario.
                if (chequeo != null) {
                    int id = chequeo.getId();
                    String json = "";
                    json = this.daoProyecto.buscarProyectoPorUsuario(id);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }
            } else if (opcion == 4 ) { // Listatr Usuarios segun Proyecto
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
                    int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    json = this.daoProyecto.usuariosSegunProyecto(idProyecto);
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
        // Manejo solicitud doPost.
        // Recogemos la opcion desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) {
                // Recogemos los parametros para crear el proyecto.
                String titulo = request.getParameter("titulo");
                String descripcion = request.getParameter("descripcion");
                String estado = request.getParameter("estado");
                String fechaIni = request.getParameter("fechaInicio");
                String fechaFin = request.getParameter("fechaFin");

                // Dado que ambas fechas vienen en formato String tenemos que parsear al objeto Date.

                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaInicio = formatoFecha.parse(fechaIni);
                Date fechaFinal = formatoFecha.parse(fechaFin);


                // Pasamos todos los parametros recogidos y formateados, junto al usuario, al metodo crearProyecto.
                serviciosProyecto.crearProyecto(chequeo, titulo, descripcion, estado, fechaInicio, fechaFinal);
            } else if (opcion == 1) {
                // Recogemos los parametros para añadir un usuario al proyecto.
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

                // Llamamos a ServiciosProyecto para lanzar el metodo de asignarUsuario a proyecto.
                serviciosProyecto.asignarUsuario(chequeo, idProyecto, idUsuario);
            } else if (opcion == 2) {
                // Recogemos los parametros para añadir un usuario al proyecto.
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

                // Llamamos a ServiciosProyecto para lanzar el metodo de asignarUsuario a proyecto.
                serviciosProyecto.quitarUsuario(chequeo, idProyecto, idUsuario);
            }
        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException | ParseException e) {
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
        // Manejo solicitud doPut.

        // Obtenemos la opcion desde el cliente
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Editar Proyecto.

                // Recogemos los parametros para editar el proyecto.
                int id = Integer.parseInt(request.getParameter("idProyecto"));
                String titulo = request.getParameter("titulo");
                String descripcion = request.getParameter("descripcion");
                String estado = request.getParameter("estado");
                String fechaIni = request.getParameter("fechaInicio");
                String fechaFin = request.getParameter("fechaFin");

                // Dado que ambas fechas vienen en formato String tenemos que parsear al objeto Date.

                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaInicio = formatoFecha.parse(fechaIni);
                Date fechaFinal = formatoFecha.parse(fechaFin);

                // Pasamos todos los parametros recogidos y formateados, junto al usuario, al metodo crearProyecto.
                serviciosProyecto.editarProyecto(chequeo, id, titulo, descripcion, estado, fechaInicio, fechaFinal);

            } else if (opcion == 1) { // Actualizar estado de proyecto.
                // Obtenemos los parametros necesarios para actualizar el estado del proyecto.
                int id = Integer.parseInt(request.getParameter("idProyecto"));
                String estado = request.getParameter("estado");

                // Pasamos los parametros, junto con el usuario de la peticion, a la funcion de la capa de servicios.
                serviciosProyecto.actualizarEstado(chequeo, id, estado);
            }

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException | ParseException e) {
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
        // Manejo solicitud doDelete.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos el id del proyecto a eliminar.
            int id = Integer.parseInt(request.getParameter("idProyecto"));

            // Llamamos a la capa de servicios para lanzar el metodo borrarProyecto.
            serviciosProyecto.eliminarProyecto(chequeo, id);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}