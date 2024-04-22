package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Muestra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOMuestra {

    public Connection con = null;

    public DAOMuestra() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    public void registrarMustra(Muestra aRegistrar) throws SQLException {
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


    public void eliminarMuestra(int id) throws SQLException {
        String sql = "DELETE FROM muestra WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
        ps.close();
    }
}
