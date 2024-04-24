package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Proyecto;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;


public class DAOProyecto {

    public Connection con = null;
    private ResultSet rs;

    public DAOProyecto() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    public void crearProyecto(Proyecto proyecto) throws SQLException {
        String sql = "INSERT INTO proyecto ( titulo, descripcion, estado, fechaInicio, fechaFin) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, proyecto.getTitulo());
        ps.setString(2, proyecto.getDescrripcion());
        ps.setString(3, proyecto.getEstado());
        ps.setDate(4, new java.sql.Date(proyecto.getFechaInicio().getTime()));
        ps.setDate(5, new java.sql.Date(proyecto.getFechaFin().getTime()));

        ps.executeUpdate();
        ps.close();

    }

    public void borrarProyecto(int idProyecto) throws SQLException {
        String sql = "DELETE FROM proyecto WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idProyecto);
        ps.executeUpdate();
        ps.close();
    }

    public String listarProyectos() throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM proyecto ";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Proyecto> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Proyecto>();
            }
            ls.add(new Proyecto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String buscarProyecto(int idProyecto) throws HTTPStatusException, SQLException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM proyecto WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idProyecto);

        ResultSet rs = ps.executeQuery();

        Proyecto aListar;
        if (rs.next()) {
            aListar = new Proyecto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }

    public String buscarProyectoPorUsuario (int idUsuario) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT proyecto.id, proyecto.titulo, proyecto.descripcion, proyecto.estado, proyecto.fechaInicio, proyecto.fechaFin FROM usuario_proyecto JOIN proyecto ON usuario_proyecto.idProyecto = proyecto.id WHERE usuario_proyecto.idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);

        ResultSet rs = ps.executeQuery();

        ArrayList<Proyecto> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Proyecto>();
            }
            ls.add(new Proyecto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String usuariosSegunProyecto(int idProyecto) throws SQLException, HTTPStatusException {

        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT usuario.id, usuario.nombre, usuario.apellidos, usuario.rol, usuario.email FROM usuario_proyecto JOIN usuario ON usuario_proyecto.idUsuario = usuario.id WHERE usuario_proyecto.idProyecto = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idProyecto);

        ResultSet rs = ps.executeQuery();

        ArrayList<Usuario> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Usuario>();
            }
            ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }


    public void editarProyecto(int id, String titulo, String descripcion, String estado, java.util.Date fechaInicio, java.util.Date fechaFinal) throws SQLException {
        String sql = "UPDATE proyecto SET titulo=?, descripcion=?, estado=?, fechaInicio=?, fechaFin=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, titulo);
        ps.setString(2, descripcion);
        ps.setString(3, estado);
        ps.setDate(4, new java.sql.Date(fechaInicio.getTime()));
        ps.setDate(5, new java.sql.Date(fechaFinal.getTime()));
        ps.setInt(6, id);

        ps.executeUpdate();
        ps.close();
    }

    public void actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE proyecto SET estado=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, id);

        ps.executeUpdate();
        ps.close();
    }

    public void asignarUsuario(int idProyecto, int idUsuario) throws SQLException {
        String sql = "INSERT INTO usuario_proyecto (idUsuario, idProyecto) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);
        ps.setInt(2, idProyecto);

        ps.executeUpdate();
        ps.close();
    }
}
