package com.pelayo.ambientlab.dao;


import com.pelayo.ambientlab.modelo.Tarea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOTarea {
    public Connection con = null;

    public DAOTarea() throws SQLException {
        this.con = DBConexion.getConexion();
    }


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

    public void borrarTarea(int idTarea) throws SQLException {
        String sql = "DELETE FROM tarea WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idTarea);

        ps.executeUpdate();
        ps.close();

    }
}
