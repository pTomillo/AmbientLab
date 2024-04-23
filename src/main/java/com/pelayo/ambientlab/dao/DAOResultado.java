package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOResultado {

    public Connection con = null;
    public DAOResultado() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    public void guardarResultado(Resultado aAnadir) throws SQLException {
        String sql = "INSERT INTO resultado (parametro, valor, unidad, idAnalisis) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aAnadir.getParametro());
        ps.setFloat(2, aAnadir.getValor());
        ps.setString(3, aAnadir.getUnidad());
        ps.setInt(4, aAnadir.getIdAnalisis());

        ps.executeUpdate();
        ps.close();
    }

    public void borrarResultado(int idResultado) throws SQLException {
        String sql = "DELETE FROM resultado WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idResultado);

        ps.executeUpdate();
        ps.close();
    }
}
