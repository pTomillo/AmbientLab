package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Analisis;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
