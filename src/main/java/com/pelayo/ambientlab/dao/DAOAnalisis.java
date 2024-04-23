package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;

import java.sql.*;
import java.util.ArrayList;

public class DAOAnalisis {
    public Connection con = null;

    public DAOAnalisis() throws SQLException {
        this.con = DBConexion.getConexion();
    }


    public void crearAnalisis(Analisis aCrear) throws SQLException {
        String sql = "INSERT INTO analisis (observaciones, tipo, fecha, estado, idMuestra, idUsuario, idProyecto) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aCrear.getObservaciones());
        ps.setString(2, aCrear.getTipo());
        ps.setDate(3, new Date(aCrear.getFechaAnalisis().getTime()));
        ps.setString(4, aCrear.getEstado());
        ps.setInt(5, aCrear.getIdMuestra());
        ps.setInt(6, aCrear.getIdUsuario());
        ps.setInt(7, aCrear.getIdProyecto());

        ps.executeUpdate();
        ps.close();

    }

    public void borrarAnalisis(int idAnalisis) throws SQLException {
        String sql = "DELETE FROM analisis WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idAnalisis);

        ps.executeUpdate();
        ps.close();
    }

    public void editarAnalisis(Analisis aEditar) throws SQLException {
        String sql = "UPDATE analisis SET observaciones=?, tipo=?, fecha=?, estado=?, idMuestra = ?, idUsuario=?, idProyecto=? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aEditar.getObservaciones());
        ps.setString(2, aEditar.getTipo());
        ps.setDate(3, new Date(aEditar.getFechaAnalisis().getTime()));
        ps.setString(4, aEditar.getEstado());
        ps.setInt(5, aEditar.getIdMuestra());
        ps.setInt(6, aEditar.getIdUsuario());
        ps.setInt(7, aEditar.getIdProyecto());
        ps.setInt(8, aEditar.getId());

        ps.executeUpdate();
        ps.close();
    }

    public void actualizarEstado(String estado, int idAnalisis) throws SQLException {
        String sql = "UPDATE analisis SET estado = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, idAnalisis);

        ps.executeUpdate();
        ps.close();
    }

    public String listarAnalisis() throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM analisis";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Analisis> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Analisis>();
            }
            ls.add(new Analisis(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String listarUnAnalisis(int idAnalisis) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM analisis WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idAnalisis);

        ResultSet rs = ps.executeQuery();

        Analisis aListar;
        if (rs.next()) {
            aListar = new Analisis(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }

    public String analisisPorProyecto(int idProyecto) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM analisis WHERE idProyecto = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idProyecto);

        ResultSet rs = ps.executeQuery();

        ArrayList<Analisis> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Analisis>();
            }
            ls.add(new Analisis(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String analisisPorMuestra(int idMuestra) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM analisis WHERE idMuestra = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idMuestra);

        ResultSet rs = ps.executeQuery();

        ArrayList<Analisis> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Analisis>();
            }
            ls.add(new Analisis(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String analisisPorUsuario(int idUsuario) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM analisis WHERE idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idUsuario);

        ResultSet rs = ps.executeQuery();

        ArrayList<Analisis> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Analisis>();
            }
            ls.add(new Analisis(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
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
