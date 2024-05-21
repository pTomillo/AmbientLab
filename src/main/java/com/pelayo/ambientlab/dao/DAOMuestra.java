package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;
import com.pelayo.ambientlab.modelo.Muestra;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de acceder a la base de datos para la clase Muestra.
 */
public class DAOMuestra {

    public Connection con = null;

    /**
     * Conexion a la base de datos.
     * @throws SQLException
     */
    public DAOMuestra() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de registrar la creacion de una Muestra en la base de datos.
     * @param aRegistrar objeto Muestra que va a ser registrado en la Base de Datos.
     * @throws SQLException Lanzada en el caso de error a la hora de registrar.
     */
    public void registrarMuestra(Muestra aRegistrar) throws SQLException {
        String sql = "INSERT INTO muestra ( referencia, origen, tipo, estado, fechaRegistro, idProyecto) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aRegistrar.getReferencia());
        ps.setString(2, aRegistrar.getOrigen());
        ps.setString(3, aRegistrar.getTipo());
        ps.setString(4, aRegistrar.getEstado());
        ps.setDate(5, new java.sql.Date(aRegistrar.getFechaRegistro().getTime()));
        ps.setInt(6, aRegistrar.getIdProyecto());

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de borrar una Muestra en la base de datos.
     * @param id de la muestra que va a ser eliminada de la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de borrar.
     */
    public void eliminarMuestra(int id) throws SQLException {
        String sql = "DELETE FROM muestra WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de editar una Muestra en la base de datos.
     * @param aEditar objeto Muestra que va a ser editado en la Base de Datos.
     * @throws SQLException Lanzada en el caso de error a la hora de editar.
     */
    public void editarMuestra(Muestra aEditar) throws SQLException {
        String sql = "UPDATE muestra SET referencia = ?, tipo = ?, origen = ?, estado = ?, fechaRegistro = ?, idProyecto = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aEditar.getReferencia());
        ps.setString(2, aEditar.getTipo());
        ps.setString(3, aEditar.getOrigen());
        ps.setString(4, aEditar.getEstado());
        ps.setDate(5, new java.sql.Date(aEditar.getFechaRegistro().getTime()));
        ps.setInt(6, aEditar.getIdProyecto());
        ps.setInt(7, aEditar.getId());

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de actualizar el estado de una Muestra en la base de datos.
     * @param idMuestra id de la muestra de la que se actualiza el estado.
     * @param estado de la muestras actualizada.
     * @throws SQLException Lanzada en el caso de error a la hora de actualizar el estado.
     */
    public void actualizarEstado(int idMuestra, String estado) throws SQLException {
        String sql = "UPDATE muestra SET estado = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, idMuestra);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de listar todas las muestras en la base de datos.
     * @return Devuelve un objeto JSON con las muestras listadas.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Muestra
     */
    public String listaMuestras1() throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM muestra";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Muestra> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Muestra>();
            }
            ls.add(new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de listar una Muestra desde la base de datos.
     * @param idMuestra id de la muestra que va a ser listada.
     * @return Devuelve un objeto JSON con las muestras listadas.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Muestra
     */
    public String listarUnaMuestr1(int idMuestra) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM muestra WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idMuestra);

        ResultSet rs = ps.executeQuery();

        Muestra aListar;
        if (rs.next()) {
            aListar = new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }

    /**
     * Metodo del DAO para la clase Muestra encargado de listar todas las muestras de un proyecto desde la base de datos.
     * @param idProyecto id del proyecto al que pertenecen las muestras.
     * @return Devuelve un objeto JSON con las muestras listadas.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Muestra
     */
    public String listarMuestrasPorProyecto1(int idProyecto) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM muestra WHERE idProyecto =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idProyecto);

        ResultSet rs = ps.executeQuery();

        ArrayList<Muestra> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Muestra>();
            }
            ls.add(new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }
}
