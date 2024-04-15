package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Sesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOSesion {
    public Connection con = null;

    public DAOSesion() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    public void crearSesion(Sesion s) throws SQLException {
        String sql = "INSERT INTO sesion ( idUsuario, cookie, fechaCreacion) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, s.getIdUsuario());
        ps.setString(2, s.getCookie());
        ps.setDate(3, new java.sql.Date(s.getFechaCreacion().getTime()));

        ps.executeUpdate();
        ps.close();
    }

    public Sesion sesionPorCookie(String cookie) throws SQLException {
        String sql = "SELECT * FROM SESION WHERE cookie = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, cookie);
        ResultSet res = ps.executeQuery();

        if (res.next()) {
            return new Sesion(res.getInt(1), res.getString(2), new java.util.Date(res.getDate(3).getTime()));
        }
        return null;
    }

}
