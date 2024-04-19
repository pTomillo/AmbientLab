package com.pelayo.ambientlab.servicios;


import com.pelayo.ambientlab.dao.DAOProyecto;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Proyecto;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;
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

}
