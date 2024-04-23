package com.pelayo.ambientlab.servicios;

import com.pelayo.ambientlab.dao.DAOTarea;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Tarea;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

public class ServicioTarea {

    DAOTarea daoTarea = new DAOTarea();

    public ServicioTarea() throws SQLException {
    }

    /**
     * Metodo para agregar una tarea a la BD.
     * @param chequeo Usuario que lanza la peticion, debe de ser Admin o Supervisor.
     * @param titulo Nombre de la tarea.
     * @param observaciones Observaciones sobre la tarea.
     * @param estado Estado actual de la tarea.
     * @param idProyecto Id del proyecto al que la tarea esta asignada.
     * @param idUsuario Id del usuario al que se le asigna la tarea.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void crearTarea(Usuario chequeo, String titulo, String observaciones, String estado, int idProyecto, int idUsuario) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin o Supervisor.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
            // Creamos el objeto tarea.
            Tarea aCrear = new Tarea(titulo, observaciones, estado, idProyecto, idUsuario);
            // Llamamos al DAOTarea para guardar la tarea en a BD.
            daoTarea.crearTarea(aCrear);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    public void borrarTarea(Usuario chequeo, int idTarea) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin o Supervisor.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor())) {
            // Si el usuario es correcto llamamos al DAOTarea.
            daoTarea.borrarTarea(idTarea);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
