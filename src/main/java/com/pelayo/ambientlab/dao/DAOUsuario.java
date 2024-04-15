package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUsuario {
    public Connection con = null;

    public DAOUsuario() throws SQLException {
        this.con = DBConexion.getConexion();
    }


    public void crearUsuario(Usuario u) throws SQLException {



        String sql = "INSERT INTO usuario ( nombre, apellidos, rol, email, contrasena) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getApellidos());
        ps.setInt(3, u.getRol());
        ps.setString(4, u.getEmail());
        ps.setString(5, u.getHash());

        ps.executeUpdate();
        ps.close();
    }

    public Usuario usuarioPorMail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
        }
        return null;
    }

    public Usuario usuarioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
        }
        return null;
    }




}
