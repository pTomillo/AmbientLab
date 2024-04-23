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

    public void borrarResultado(Usuario chequeo, int idResultado) throws HTTPStatusException, SQLException {
        // Comprobamos que el usuario que lanza la peticion tiene los permisos necesarios.
        if (chequeo != null && (chequeo.esAdmin() || chequeo.esSupervisor() || chequeo.esAnalista())) {
            // Si tiene los permisos llamamos al DAOResultado para lanzar la peticion de borrado.
            daoResultado.borrarResultado(idResultado);
        } else {
            throw new HTTPStatusException(403);
        }
    }
}
