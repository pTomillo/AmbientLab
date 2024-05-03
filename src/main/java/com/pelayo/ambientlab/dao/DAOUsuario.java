package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Analisis;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void registrarUsuario(Usuario u) throws SQLException {

        String sql = "INSERT INTO usuario ( nombre, apellidos, rol, email, contrasena) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getApellidos());
        ps.setInt(3, 9);
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

    public void borrarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
    }

    public void editarUsuarioAdmin(Usuario usuarioUpdateAdmin) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellidos=?, rol=?, email=? WHERE id =?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usuarioUpdateAdmin.getNombre());
        ps.setString(2, usuarioUpdateAdmin.getApellidos());
        ps.setInt(3, usuarioUpdateAdmin.getRol());
        ps.setString(4, usuarioUpdateAdmin.getEmail());
        ps.setInt(5, usuarioUpdateAdmin.getId());

        ps.executeUpdate();

        ps.close();
    }

    public void editarUsuario(Usuario usuarioUpdate) throws SQLException {
        String sql = "UPDATE usuario SET nombre=?, apellidos=?, email=? WHERE id =?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, usuarioUpdate.getNombre());
        ps.setString(2, usuarioUpdate.getApellidos());
        ps.setString(3, usuarioUpdate.getEmail());
        ps.setInt(4, usuarioUpdate.getId());

        ps.executeUpdate();

        ps.close();
    }

    public void asignarRol(int idRol, Usuario usuarioUpdate) throws SQLException {
        String sql = "UPDATE usuario SET rol=? WHERE id =?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idRol);
        ps.setInt(2, usuarioUpdate.getId());

        ps.executeUpdate();

        ps.close();
    }

    public void cambiarContrasena (int id, String newHash) throws SQLException {
        String sql = "UPDATE usuario SET contrasena=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, newHash);
        ps.setInt(2, id);

        ps.executeUpdate();

        ps.close();
    }

    public void resetContrasena(int id, String newHash) throws SQLException {
        String sql = "UPDATE usuario SET contrasena=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, newHash);
        ps.setInt(2, id);

        ps.executeUpdate();

        ps.close();
    }


    public String listarUsuarios() throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM usuario";
        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ArrayList<Usuario> ls = null;

        while (rs.next()) {
            if (ls == null) {
                ls = new ArrayList<Usuario>();
            }
            ls.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
        }

        if (ls == null) {
            throw new HTTPStatusException(404);
        }


        json = gson.toJson(ls);

        ps.close();
        rs.close();

        return json;
    }

    public String buscarUsuario(int idUsuario) throws SQLException, HTTPStatusException {
        String json = "";
        Gson gson = new Gson();

        String sql = "SELECT * FROM usuario WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1 , idUsuario);

        ResultSet rs = ps.executeQuery();

        Usuario aListar;
        if (rs.next()) {
            aListar = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6));
        } else {
            throw new HTTPStatusException(404);
        }

        json = gson.toJson(aListar);
        ps.close();
        rs.close();
        return json;
    }


}
