package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOMuestra;
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

/**
 * Este servlet maneja la peticiones a la clase Muestra
 */
@WebServlet(name = "GestionMuestra", value = "/GestionMuestra")
public class GestionMuestra extends HttpServlet {

    DAOUsuario daoUsuario = new DAOUsuario();

    DAOMuestra daoMuestra = new DAOMuestra();
    ServicioLogin servicioLogin = new ServicioLogin();
    ServicioUsuario servicioUsuario = new ServicioUsuario();

    ServiciosMuestra serviciosMuestra = new ServiciosMuestra();

    public GestionMuestra() throws SQLException {
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
        // Manejo peticion GET
        PrintWriter out = response.getWriter();
        // Recogemos la opcion enviada desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));
        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);
            if (opcion == 0) { // Listar todas las muestras.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esRegistro())) {
                    String json = "";
                    json = this.daoMuestra.listaMuestras1();
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 1) { // Listar una muestra
                if (chequeo != null) {
                    int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));
                    String json = "";
                    json = this.daoMuestra.listarUnaMuestr1(idMuestra);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }
            } else if (opcion == 2) { // Listar muestras por Proyecto
                if (chequeo != null) {
                    int id = Integer.parseInt(request.getParameter("idProyecto"));
                    String json = "";
                    json = this.daoMuestra.listarMuestrasPorProyecto1(id);
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

    /**
     * Maneja el metodo POST del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
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
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
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

    /**
     * Maneja el metodo PUT del servlet.
     * @param request objeto que contiene  la solicitud realizada por el cliente.
     * @param response objeto que contiene la respuesta que el servlet envia al cliente.
     * @throws ServletException Lanza la excepcion si el metodo GET no puede ser ejecutado.
     * @throws IOException Lanza la excepcion si el metodo GET detecta un error en el input o output del metodo.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo peticion PUT

        // Obtenemos la opcion desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            
            if (opcion == 0) { // Editar Muestra

                // Recogemos los parametros para editar la muestra.
                int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));

                String referencia = request.getParameter("referencia");
                String tipo = request.getParameter("tipo");
                String origen = request.getParameter("origen");
                String estado = request.getParameter("estado");
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                String fechaReg = request.getParameter("fechaRegistro");

                // La fecha de registro es recogida y adecuadamente parseada.
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date fecharegistro = formatoFecha.parse(fechaReg);

                // Llamamos al metodo editarMuestra.

                serviciosMuestra.editarMuestra(chequeo, idMuestra, referencia, tipo, origen, estado, fecharegistro, idProyecto);

                
            } else if (opcion == 1) { // Actualizar estado de la muestra.
                // Recogemos los parametros para actualizar el estado de la muestra.
                int idMuestra = Integer.parseInt(request.getParameter("idMuestra"));
                String estado = request.getParameter("estado");

                // Llamamos a la capa servicios para lanzar el metodo de actualizar estado.
                serviciosMuestra.actualizarEstadoMuestra(chequeo, idMuestra, estado);
                
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