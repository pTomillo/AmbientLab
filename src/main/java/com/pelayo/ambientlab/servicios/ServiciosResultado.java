package com.pelayo.ambientlab.servicios;


import com.pelayo.ambientlab.dao.DAOResultado;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Resultado;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.SQLException;

public class ServiciosResultado {
    DAOResultado daoResultado = new DAOResultado();

    public ServiciosResultado() throws SQLException {
    }

    /**
     *  Metodo para añadir un resultado de Analisis a la BD.
     * @param chequeo Usuario que lanza la peticion, debe de ser Admin, Supervisor o Analista.
     * @param parametro Parametro que se analiza
     * @param valor Valor obtenido
     * @param unidad Unidades del valor.
     * @param idAnalisis Id del analisis que da el resultado.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void anadirResultado(Usuario chequeo, String parametro, float valor, String unidad, int idAnalisis) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion tiene los permisos necesarios.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
            // Creamos el objeto resultado que pasar al metodo del DAOResultado.
            Resultado aAnadir = new Resultado(parametro, valor, unidad, idAnalisis);
            // Llamamos al DAOResultado para guardar el resultado en la BD.
            daoResultado.guardarResultado(aAnadir);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     *  Metodo para borrar un resultado de la BD.
     * @param chequeo Usuario que lanza la peticion, debe de ser Admin, Supervisor o Analista.
     * @param idResultado Id del resultado a borrar.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void borrarResultado(Usuario chequeo, int idResultado) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion tiene los permisos necesarios.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
            // Si tiene los permisos llamamos al DAOResultado para lanzar la peticion de borrado.
            daoResultado.borrarResultado(idResultado);
        } else {
            throw new HTTPStatusException(403);
        }
    }

    /**
     * Metodo para editar un resultado ya presente en nuestra BD.
     * @param chequeo Usuario que lanza la peticion, debe de ser Admin, Supervisor o Analista.
     * @param idResultado Id del resultado a editar.
     * @param parametro Parametro del resultado editado.
     * @param valor valor del resultado editado.
     * @param unidad unidad del resultado editado.
     * @param idAnalisis Id del analisis editado.
     * @throws HTTPStatusException
     * @throws SQLException
     */
    public void editarResultado(Usuario chequeo, int idResultado, String parametro, float valor, String unidad, int idAnalisis) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion tiene los permisos necesarios.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
            // Creamos el objeto resultado que pasar al metodo del DAOResultado.
            Resultado aEditar = new Resultado(idResultado, parametro, valor, unidad, idAnalisis);
            // Llamamos al DAOResultado para guardar el resultado en la BD.
            daoResultado.editarResultado(aEditar);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
