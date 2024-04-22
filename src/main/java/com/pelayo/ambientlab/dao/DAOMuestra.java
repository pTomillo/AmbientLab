package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Muestra;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOMuestra {

    public Connection con = null;

    public DAOMuestra() throws SQLException {
        this.con = DBConexion.getConexion();
    }

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


    public void eliminarMuestra(int id) throws SQLException {
        String sql = "DELETE FROM muestra WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ps.executeUpdate();
        ps.close();
    }


    public ArrayList<Muestra> muestrasPorProyecto(int id) throws SQLException {
        String sql = "SELECT * FROM muestra WHERE idProyecto = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        ArrayList<Muestra> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Muestra>();
            }
            ls.add(new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)));
        }
        return ls;
    }

    public ArrayList<Muestra> listarMuestras() throws SQLException {
        String sql = "SELECT * FROM muestra ";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Muestra> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Muestra>();
            }
            ls.add(new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7)));
        }
        return ls;
    }

    public String listarMuestrasPorProyecto(int id) throws SQLException {
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(this.muestrasPorProyecto(id));
        return json;
    }

    public String listaMuestras() throws SQLException {
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(this.listarMuestras());
        return json;
    }

    public String listarUnaMuestra(int idMuestra) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(this.buscarMuestra(idMuestra));
        return json;
    }

    public Muestra buscarMuestra(int idMuestra) throws SQLException, HTTPStatusException {
        String sql = "SELECT * FROM muestra WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idMuestra);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Muestra(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6), rs.getInt(7));
        } else {
            throw new HTTPStatusException(404);
        }
    }
}
