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
        if (chequeo != null && (chequeo.esAdmin()) || chequeo.esSupervisor() || chequeo.esAnalista()) {
            // Creamos el objeto Analisis que pasaremos al DAOAnalisis para su posterior insercion en la base de datos.
            Analisis aCrear = new Analisis(observaciones, tipo, fechaAnalisis, estado, idMuestra, chequeo.getId(), idProyecto); // Guardamos tambien el id del usuario que crea el analisis.
            // Llamamos a la capa de DAOAnalisis para lanzar el metodo para registrar el analisis en la BD.
            daoAnalisis.crearAnalisis(aCrear);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
