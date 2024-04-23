package com.pelayo.ambientlab.controlador;

import com.pelayo.ambientlab.dao.DAOResultado;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Usuario;
import com.pelayo.ambientlab.servicios.ServicioLogin;
import com.pelayo.ambientlab.servicios.ServiciosResultado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


@WebServlet(name = "GestionResultado", value = "/GestionResultado")
public class GestionResultado extends HttpServlet {
    ServicioLogin servicioLogin = new ServicioLogin();
    ServiciosResultado serviciosResultado = new ServiciosResultado();
    DAOResultado daoResultado = new DAOResultado();
    public GestionResultado() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo de la peticion doGet.
        PrintWriter out = response.getWriter();
        // Obtenemos la opcion escogida desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            if (opcion == 0) { // Listar todos los resultado.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
                    String json = "";
                    json = this.daoResultado.listarResultados();
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 1) { // Listar un resultado.
                if (chequeo != null) {
                    int idResultado = Integer.parseInt(request.getParameter("idResultado"));
                    String json = "";
                    json = this.daoResultado.listarResultado(idResultado);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(401);
                }

            } else if (opcion == 2) { // Listar los resultados de un Analisis.
                if (chequeo != null) {
                    int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));
                    String json = "";
                    json = this.daoResultado.listarResultadoPorAnalisis(idAnalisis);
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
        // Manejo de la peticion doPost.
        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos los parametros para añadir un resultado
            String parametro = request.getParameter("parametro");
            float valor = Float.parseFloat(request.getParameter("valor"));
            String unidad = request.getParameter("unidad");
            int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));

            // Llamamos a la capa serviciosResultado para lanzar el metodo crearResultado.
            serviciosResultado.anadirResultado(chequeo, parametro, valor, unidad, idAnalisis);


        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejo de la peticion doPut.

        try {
            Usuario chequeo;
            // Comprobamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Recogemos los parametros para editar el resultado desde el cliente.
            String parametro = request.getParameter("parametro");
            float valor = Float.parseFloat(request.getParameter("valor"));
            String unidad = request.getParameter("unidad");
            int idAnalisis = Integer.parseInt(request.getParameter("idAnalisis"));

            // Recogemos el id del resultado a editar.
            int idResultado = Integer.parseInt(request.getParameter("idResultado"));

            // Llamamos a la capa serviciosResultado para lanzar el metodo editar resultado.

            serviciosResultado.editarResultado(chequeo, idResultado, parametro, valor, unidad, idAnalisis);

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

            // Recogemos el id del resultado a borrar desde el cliente.
            int idResultado = Integer.parseInt(request.getParameter("idResultado"));

            // Llamamos a la capa servicios para lanzar el metodo borrarResultado.
            serviciosResultado.borrarResultado(chequeo, idResultado);

        } catch (HTTPStatusException e) {
            response.sendError(e.getEstatus(), e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}