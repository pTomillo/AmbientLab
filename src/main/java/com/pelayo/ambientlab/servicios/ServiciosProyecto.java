package com.pelayo.ambientlab.servicios;


import com.pelayo.ambientlab.dao.DAOProyecto;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Proyecto;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;
import java.util.Date;

public class ServiciosProyecto {

    DAOProyecto daoProyecto = new DAOProyecto();

    public ServiciosProyecto() throws SQLException {
    }

    /**
     * Metodo para crear un nuevo Proyecto y añadirlo al a base de datos.
     * @param creador Usuario que crea el proyecto, Admin o Supervisor.
     * @param titulo Titulo del proyecto.
     * @param descripcion Descripcion del proyecto.
     * @param estado Estado del proyecto.
     * @param fechaInicio Fecha de inicio del proyecto.
     * @param fechaFinal Fecha de fin del proyecto.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void crearProyecto(Usuario creador, String titulo, String descripcion, String estado, Date fechaInicio, Date fechaFinal) throws HTTPStatusException, SQLException {
        // Comprobamos que el creador de la peticion sea adminsitrador del sistema o de rango supervisor.
        if(creador.esAdmin() || creador.esSupervisor()) {
            // Creamos el proyecto.
            Proyecto proyecto = new Proyecto(titulo, descripcion, estado, fechaInicio, fechaFinal);

            // Llamamos al DAOProyecto para guardar el proyecto en la base de datos.
            daoProyecto.crearProyecto(proyecto);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Metodo para eliminar un proyecto dado de la Base de Datos.
     * @param creador Creador de la peticion de borrado, debe ser administrador o supervisor.
     * @param idProyecto Id del proyecto que va a ser borrado
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void eliminarProyecto(Usuario creador, int idProyecto) throws HTTPStatusException, SQLException {
        if (creador.esAdmin() || creador.esSupervisor()) {
            daoProyecto.borrarProyecto(idProyecto);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Funcion para editar un proyecto dado.
     * @param chequeo Usuario con la inicion iniciada y que crea la peticion.
     * @param id Id del proyecto a editar.
     * @param titulo Titulo del proyecto a editar.
     * @param descripcion Descripcion del proyecto a editar.
     * @param estado Estado del proyecto a editar.
     * @param fechaInicio Fecha de Inicio del proyecto a editar.
     * @param fechaFinal Fecha de Fin del proyecto a editar.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void editarProyecto(Usuario chequeo, int id, String titulo, String descripcion, String estado, Date fechaInicio, Date fechaFinal) throws HTTPStatusException, SQLException {
        if (chequeo.esAdmin() || chequeo.esSupervisor()) {
            daoProyecto.editarProyecto(id, titulo, descripcion, estado, fechaInicio, fechaFinal);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Funcion para actualizar solamente el estado de un proyecto.
     * @param chequeo Usuaro que crea la peticion de actualizacion.
     * @param id Id del proyecto en el que actualizar el estado.
     * @param estado Estado actualizado.
     * @throws HTTPStatusException
     * @throws SQLException
     */

    public void actualizarEstado(Usuario chequeo, int id, String estado) throws HTTPStatusException, SQLException {
        if (chequeo.esAdmin() || chequeo.esSupervisor()) {
            daoProyecto.actualizarEstado(id, estado);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Metodo para asignar un Usuario a un Proyecto
     * @param chequeo Usuario que lanza la peticion, debe de ser Admin o supervisor
     * @param idProyecto Id del proyecto al que se asigna el Usuario.
     * @param idUsuario Id del Usuario que es asignado.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void asignarUsuario(Usuario chequeo, int idProyecto, int idUsuario) throws HTTPStatusException, SQLException {
        if (chequeo.esAdmin() || chequeo.esSupervisor()){
            daoProyecto.asignarUsuario(idProyecto, idUsuario);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    public void quitarUsuario(Usuario chequeo, int idProyecto, int idUsuario) throws SQLException, HTTPStatusException {
        if (chequeo.esAdmin() || chequeo.esSupervisor()){
            daoProyecto.quitarUsuario(idProyecto, idUsuario);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
