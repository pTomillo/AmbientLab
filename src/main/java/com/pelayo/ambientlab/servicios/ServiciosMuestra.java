package com.pelayo.ambientlab.servicios;

import com.pelayo.ambientlab.dao.DAOMuestra;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Muestra;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;
import java.util.Date;

public class ServiciosMuestra {
    DAOMuestra daoMuestra = new DAOMuestra();

    public ServiciosMuestra() throws SQLException {
    }

    /**
     * Metodo para registrar una muestra llamando al DAOMuestra para que la guarde en la BD.
     *
     * @param chequeo       Usuario que crea la peticion, debe ser Admin, Supervisor o Registro.
     * @param referencia    Referencia de la muestra.
     * @param tipo          Tipo de muestra.
     * @param origen        Origen de la muestra.
     * @param estado        Estado actual de la muestra.
     * @param fecharegistro Fecha en la que se registro la muestra.
     * @param idProyecto    Id del proyecto al que pertenece la muestra.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void registrarMuestra(Usuario chequeo, String referencia, String tipo, String origen, String estado, Date fecharegistro, int idProyecto) throws HTTPStatusException, SQLException {
        if (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esRegistro()) {
            // Creamos el objeto Muestra para pasarselo a la fucion de registro en la BD.
            Muestra aRegistrar = new Muestra(referencia, tipo, origen, estado, fecharegistro, idProyecto);
            // Llamamos al DAOMuestra para guardarla en la BD.
            daoMuestra.registrarMuestra(aRegistrar);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Metodo para eliminar una muestra de la BD.
     *
     * @param chequeo Usuario que lanza la peticion para eliminar la muestra.
     * @param id      Identificador de la muestra a eliminar.
     */
    public void borrarMuestra(Usuario chequeo, int id) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supevisor o Registro.
        if (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esRegistro()) {
            daoMuestra.eliminarMuestra(id);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    public void editarMuestra(Usuario chequeo, int idMuestra, String referencia, String tipo, String origen, String estado, Date fecharegistro, int idProyecto) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supevisor o Registro.
        if (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esRegistro()) {
            // Creamos el objeto Muestra para pasar al DAOMuestra.
            Muestra aEditar = new Muestra(idMuestra, referencia, tipo, origen, estado, fecharegistro, idProyecto);
            // Llamamos al DAOMuestra para actualizar la muestra.
            daoMuestra.editarMuestra(aEditar);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    public void actualizarEstadoMuestra(Usuario chequeo, int idMuestra, String estado) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion sea Admin, Supevisor o Registro.
        if (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esRegistro()) {
            // Llamamos al DAOMuestra para actualizar la muestra.
            daoMuestra.actualizarEstado(idMuestra, estado);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
