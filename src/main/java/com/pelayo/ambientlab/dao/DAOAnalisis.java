package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
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

    public String listarAnalisis() throws SQLException {
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


        json = gson.toJson(ls);
        return json;
    }

    public String listarUnAnalisis(int idAnalisis) {
        return null;
    }

    public String analisisPorProyecto(int idProyecto) {
        return null;
    }

    public String analisisPorMuestra(int idMuestra) {
        return null;
    }
}
