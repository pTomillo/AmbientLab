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
        // Opcion enviada desde el cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));

        try {
            Usuario chequeo;
            // Chequeamos que el Usuario tenga la sesion iniciada.
            chequeo = servicioLogin.chequeoSesion(request, response);
            // En funcion de la opcion enviada desde el clietne elegimios si listar un usuario o todos los usuarios.
            if (opcion == 1) { // Listar un Usuario.
                int id = Integer.parseInt(request.getParameter("id"));
                // Si se lista un solo usuario se comprueba que el cliente es admin o el usuario a listar y hay una sesion iniciada.
                if (chequeo != null && (chequeo.esAdmin() || chequeo.getId() == id)) {
                    String json = "";
                    json = this.daoUsuario.buscarUsuario(id);
                    out.print(json);
                } else {
                    throw new HTTPStatusException(403);
                }
            } else if (opcion == 0) { // Listar todos los Usuarios.
                // Si se listan todos los usuarios hace falta comprobar que la sesion sea valida y de  un administrador.
                if (chequeo != null && chequeo.esAdmin()) {
                    String json = "";
                    json = this.daoUsuario.llamarJson();
                    System.out.println(json);
                    out.print(json);
                } else {
                    // Si falla lanzamos un error 403 "Acceso no permitido".
                    throw new HTTPStatusException(403);
                }
            }
        } catch (HTTPStatusException e) {
            // Devolvemos el error.
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

        // Obtenemos la operacion a realizar del cliente.
        int opcion = Integer.parseInt(request.getParameter("op"));


        try {
            if (opcion == 0) { // Editar Usuario.
                Usuario chequeo;
                // Chequeamos que el Usuario tenga la sesion iniciada.
                chequeo = servicioLogin.chequeoSesion(request, response);

                // Obtenemos los parametros para editar el Usuario desde el cliente.
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                String email = request.getParameter("email");

                // Creamos el objeto usuario a editar.
                Usuario aEditar =new Usuario(id, nombre, apellidos, email);
                // Llamamos desde el Servlet a la capa Servicios para lanzar la fucion editarUsuario.
                servicioUsuario.editarUsuario(chequeo, aEditar);

            } else if (opcion == 1) { // Editar Usuario por Admin.
                Usuario chequeo;
                // Chequeamos que el Usuario tenga la sesion iniciada.
                chequeo = servicioLogin.chequeoSesion(request, response);

                // Obtenemos los parametros para editar el Usuario desde el cliente.
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String apellidos = request.getParameter("apellidos");
                int rol = Integer.parseInt(request.getParameter("rol"));
                String email = request.getParameter("email");

                // Creamos el objeto usuario a editar.
                Usuario aEditarAdmin =new Usuario(id, nombre, apellidos, rol, email);
                // Llamamos desde el Servlet a la capa Servicios para lanzar la fucion editarUsuario.
                servicioUsuario.editarAdmin(chequeo, aEditarAdmin);

            } else if (opcion == 2) { // Cambiar contraseña.
                Usuario chequeo;
                // Chequeo de que la sesion esta iniciada.
                chequeo = servicioLogin.chequeoSesion(request, response);

                // Obtenemos y chequeamos la antigua contraseña introducida por el Usuario en el cliente.
                String contrasena1 = request.getParameter("con1");
                String contrasena2 = request.getParameter("con2");
                String newContrasena = request.getParameter("nCon");

                // Pasamos la contrasena a la funcion
                servicioUsuario.cambiarContrasena(chequeo, contrasena1, contrasena2, newContrasena);

            } else if (opcion == 3) { // Asignar Rol.
                Usuario chequeo;
                // Chequeamos que el Usuario tenga la sesion iniciada.
                chequeo = servicioLogin.chequeoSesion(request, response);

                int id = Integer.parseInt(request.getParameter("id"));
                int rol = Integer.parseInt(request.getParameter("rol"));

                servicioUsuario.asignarRol(chequeo, id, rol);
            } else if (opcion == 4) { //Resetear Contraseña.
                Usuario chequeo;
                // Chequeamos que el Usuario tenga la sesion iniciada.
                chequeo = servicioLogin.chequeoSesion(request, response);
                int id = Integer.parseInt(request.getParameter("id"));
                String newCon = request.getParameter("nCon");

                servicioUsuario.resetContrasenaByAdmin(chequeo, id, newCon);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (HTTPStatusException e) {
            // Devolucion del error correspondiente.
            response.sendError(e.getEstatus(), e.getMessage());
        }
        response.getWriter().write("Usuario Editado");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Manejar la solicitud DELETE aquí


        Usuario chequeo = null;
        try {
            // Chequeamos que la sesion sea valida.
            chequeo = servicioLogin.chequeoSesion(request, response);

            // Obtenemos el valor id del Usuario a eliminar desde el cliente.
            int id = Integer.parseInt(request.getParameter("id"));

            try {
                // Llamamos a la capa Servicios para lanzar la funcion borrar Usuario, a la que hay que pasarle tanto la sesion como el id a editar.
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