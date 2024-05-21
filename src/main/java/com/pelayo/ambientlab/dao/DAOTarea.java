package com.pelayo.ambientlab.dao;


import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;
import com.pelayo.ambientlab.modelo.Tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de acceder a la base de datos para la clase Tarea.
 */
public class DAOTarea {
    public Connection con = null;

    /**
     * Conexion a la base de datos.
     * @throws SQLException
     */
    public DAOTarea() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    /**
     * Metodo del DAO para la clase Tarea encargado de registrar la creacion de una Tarea en la base de datos.
     * @param aCrear Objeto Tarea que se pasa para ser guardado en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de crear.
     */
    public void crearTarea(Tarea aCrear) throws SQLException {
        String sql = "INSERT INTO tarea (titulo, observaciones, estado, idProyecto, idUsuario) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aCrear.getTitulo());
        ps.setString(2, aCrear.getObservaciones());
        ps.setString(3, aCrear.getEstado());
        ps.setInt(4, aCrear.getIdProyecto());
        ps.setInt(5, aCrear.getIdUsuario());

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Tarea encargado de borrar una Tarea en la base de datos.
     * @param idTarea de la tarea que va ser borrada.
     * @throws SQLException Lanzada en el caso de error a la hora de borrar.
     */
    public void borrarTarea(int idTarea) throws SQLException {
        String sql = "DELETE FROM tarea WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idTarea);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Tarea encargado de editar una Tarea en la base de datos.
     * @param aEditar objeto Tarea que es editado.
     * @param idTarea id de la Tarea a editar.
     * @throws SQLException Lanzada en el caso de error a la hora de editar.
     */
    public void editarTarea(Tarea aEditar, int idTarea) throws SQLException {
        String sql = "UPDATE tarea SET titulo=?, observaciones=?, estado=?, idProyecto=?, idUsuario = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aEditar.getTitulo());
        ps.setString(2, aEditar.getObservaciones());
        ps.setString(3, aEditar.getEstado());
        ps.setInt(4, aEditar.getIdProyecto());
        ps.setInt(5, aEditar.getIdUsuario());
        ps.setInt(6, idTarea);

        ps.executeUpdate();
        ps.close();

    }

    /**
     * Metodo del DAO para la clase Tarea encargado de actualizar el estado de una Tarea en la base de datos.
     * @param estado estado que es actualizado
     * @param idTarea para la que se actualiza el estado.
     * @throws SQLException Lanzada en el caso de error a la hora de actualizar el estado.
     */
    public void actualizarEstado(String estado, int idTarea) throws SQLException {
        String sql = "UPDATE tarea SET estado =? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, idTarea);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Tarea encargado de listar todas las Tareas en la base de datos.
     * @return Devuelve un objeto JSON con las Tareas listadas.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Tarea.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     */
    public String listarTareas() throws HTTPStatusException, SQLException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM tarea";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Tarea> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Tarea>();
            }
            ls.add(new Tarea(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
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
     * Metodo del DAO para la clase Tarea encargado de listar una Tarea desde la base de datos.
     * @param idTarea id de la Tarea listada.
     * @return Devuelve un objeto JSON con las Tareas listadas.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Tarea.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     */
    public String listarUnaTarea(int idTarea) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM tarea WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idTarea);

        ResultSet rs = ps.executeQuery();

        Tarea aListar;
        if (rs.next()) {
            aListar = new Tarea(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }

    /**
     * Metodo del DAO para la clase Tarea encargado de listar todas las Tareas de un Proyecto desde la base de datos.
     * @param idProyecto id del proyecto para el que se listan las tareas.
     * @return Devuelve un objeto JSON con las Tareas listadas.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Tarea.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     */
    public String tareasPorProyecto(int idProyecto) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM tarea WHERE idProyecto = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idProyecto);

        ResultSet rs = ps.executeQuery();

        ArrayList<Tarea> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Tarea>();
            }
            ls.add(new Tarea(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
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
     * Metodo del DAO para la clase Tarea encargado de listar todas las Tareas de un Usuario desde la base de datos.
     * @param idUsuario id del usuario para el que se listan las tareas.
     * @return Devuelve un objeto JSON con las Tareas listadas.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Tarea.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     */
    public String tareasPorUsuario(int idUsuario) throws HTTPStatusException, SQLException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM tarea WHERE idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);

        ResultSet rs = ps.executeQuery();

        ArrayList<Tarea> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Tarea>();
            }
            ls.add(new Tarea(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
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
     * Metodo del DAO para la clase Tarea encargado de listar todas las Tareas de un Usuario para un proyecto dado desde la base de datos.
     * @param idProyecto id del proyecto en el que esta el Usuario
     * @param idUsuario id del usuario para el que se van a listar las tareas por proyecto.
     * @return Devuelve un objeto JSON con las Tareas listadas.
     * @throws HTTPStatusException Lanzada si no se encuentra ninguna Tarea.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     */
    public String tareasPorProyectoXUsuario(int idProyecto, int idUsuario) throws HTTPStatusException, SQLException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM tarea WHERE idUsuario = ? AND idProyecto = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);
        ps.setInt(2, idProyecto);

        ResultSet rs = ps.executeQuery();

        ArrayList<Tarea> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Tarea>();
            }
            ls.add(new Tarea(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
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
