package com.pelayo.ambientlab.servicios;

import com.pelayo.ambientlab.dao.DAOAnalisis;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;
import java.util.Date;

public class ServiciosAnalisis {

    DAOAnalisis daoAnalisis = new DAOAnalisis();

    public ServiciosAnalisis() throws SQLException {
    }

    /**
     * Metodo para crear un Analisis y guardarlo en la BD.
     * @param chequeo Usuario que crea la peticion, debe ser Admin, Supervisor o Analista. El Usuario que crea la peticion sera guardado en la BD junto con el resto de la informacion.
     * @param observaciones Informacion sobre el analisis.
     * @param tipo Tipo de Analisis realizado, debe ser ICP-MS, GC-MS o HgTotal.
     * @param fechaAnalisis Fecha de realizacion del Analisis.
     * @param estado Estado en el que se encuentra la peticion.
     * @param idMuestra Id de la muestra que es esta analizando
     * @param idProyecto Id del proyecto del que la muestra y el analisis forman parte.
     * @throws HTTPStatusException
     */
    public void crearAnalisis(Usuario chequeo, String observaciones, String tipo, Date fechaAnalisis, String estado, int idMuestra, int idProyecto) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supervisor o Analista.
        if (chequeo != null && (chequeo.esAdmin()) || chequeo.esSupervisor() || chequeo.esAnalista()) {
            // Creamos el objeto Analisis que pasaremos al DAOAnalisis para su posterior insercion en la base de datos.
            Analisis aCrear = new Analisis(observaciones, tipo, fechaAnalisis, estado, idMuestra, chequeo.getId(), idProyecto); // Guardamos tambien el id del usuario que crea el analisis.
            // Llamamos a la capa de DAOAnalisis para lanzar el metodo para registrar el analisis en la BD.
            daoAnalisis.crearAnalisis(aCrear);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Funcion para eliminar el registro de un analisis de la BD
     * @param chequeo Usuario que lanza la peticion de eliminar.
     * @param idAnalisis Id del analisis que se elimina.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void eliminarAnalisis(Usuario chequeo, int idAnalisis) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supervisor o Analista.
        if (chequeo != null && (chequeo.esAdmin()) || chequeo.esSupervisor() || chequeo.esAnalista()) {
            // Si es correcto lanzamos la peticion de elminar al DAOAnalisis.
            daoAnalisis.borrarAnalisis(idAnalisis);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Funcion para editar un analisis presente en la BD. Se le ha de pasar todos los parametros mas el usuario que crea la peticion.
     * @param chequeo Usuario que crea la peticion, debe de ser Admin, Supervisor o Analista.
     * @param observaciones Observaciones pertinentes al analisis a ser editardo.
     * @param tipo Tipo del analisis que va a ser editado.
     * @param fechaAnalisis Fecha en la que el analisis editado ha sido realizado.
     * @param estado Estado en el que se encuentra el analisis a editar.
     * @param idMuestra Id de la muestra que esta asiendo analizada.
     * @param idProyecto Id del proyecto al que pertenece el analisis.
     * @param idUsuario Id del Usuario que lleva a cabo el analisis.
     * @param idAnalisis Id el analisis que va a ser editado.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void editarAnalisis(Usuario chequeo, String observaciones, String tipo, Date fechaAnalisis, String estado, int idMuestra, int idProyecto, int idUsuario, int idAnalisis) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supervisor o Analista.
        if (chequeo != null && (chequeo.esAdmin()) || chequeo.esSupervisor() || chequeo.esAnalista()) {
            // Creamos el objeto Analisis que pasaremos al DAOAnalisis para su posterior edicion en la base de datos.
            Analisis aEditar = new Analisis(idAnalisis, observaciones, tipo, fechaAnalisis, estado, idMuestra, idUsuario, idProyecto); // Guardamos tambien el id del usuario que crea el analisis.
            // Llamamos a la capa de DAOAnalisis para lanzar el metodo para editar el analisis en la BD.
            daoAnalisis.editarAnalisis(aEditar);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Funcion para actualizar el estado en que se encuentra un analisis.
     * @param chequeo Usuario que lanza la peticion.
     * @param estado Estado actualizado
     * @param idAnalisis Id del Analisis a actualizar.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void actualizarEstado(Usuario chequeo, String estado, int idAnalisis) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supervisor o Analista.
        if (chequeo != null && (chequeo.esAdmin()) || chequeo.esSupervisor() || chequeo.esAnalista()) {
            // Llamamos al DAOAnalisis para actualizar el estado en la BD.
            daoAnalisis.actualizarEstado(estado, idAnalisis);

        } else {
            throw new HTTPStatusException(403);
        }
    }
}
