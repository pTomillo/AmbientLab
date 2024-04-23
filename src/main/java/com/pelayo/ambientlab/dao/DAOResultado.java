package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;
import com.pelayo.ambientlab.modelo.Resultado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public void editarResultado(Resultado aEditar) throws SQLException {
        String sql = "UPDATE resultado SET parametro = ?, valor = ?, unidad = ?, idAnalisis = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, aEditar.getParametro());
        ps.setFloat(2, aEditar.getValor());
        ps.setString(3, aEditar.getUnidad());
        ps.setInt(4, aEditar.getIdAnalisis());
        ps.setInt(5, aEditar.getId());

        ps.executeUpdate();
        ps.close();

    }

    public String listarResultados() throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM resultado";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Resultado> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Resultado>();
            }
            ls.add(new Resultado(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String listarResultado(int idResultado) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM resultado WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idResultado);

        ResultSet rs = ps.executeQuery();

        Resultado aListar;
        if (rs.next()) {
            aListar = new Resultado(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }

    public String listarResultadoPorAnalisis(int idAnalisis) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM resultado WHERE idAnalisis = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idAnalisis);

        ResultSet rs = ps.executeQuery();

        ArrayList<Resultado> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Resultado>();
            }
            ls.add(new Resultado(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4), rs.getInt(5)));
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
