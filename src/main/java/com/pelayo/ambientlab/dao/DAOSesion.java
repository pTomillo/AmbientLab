package com.pelayo.ambientlab.dao;

import com.pelayo.ambientlab.modelo.Sesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de acceder a la base de datos para la sesion.
 */
public class DAOSesion {
    public Connection con = null;

    /**
     * Conexion a la base de datos.
     * @throws SQLException
     */
    public DAOSesion() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    /**
     * Metodo del DAO para la clase Sersion encargado de registrar la creacion de una Sesion en la base de datos.
     * @param s Objeto sesion que se inserta en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de insertar.
     */
    public void crearSesion(Sesion s) throws SQLException {
        String sql = "INSERT INTO sesion ( idUsuario, cookie, fechaCreacion) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, s.getIdUsuario());
        ps.setString(2, s.getCookie());
        ps.setDate(3, new java.sql.Date(s.getFechaCreacion().getTime()));

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Sersion encargado de borrar una sesion de la base de datos mediante el borrado de la cookie.
     * @param cookie cookie que se va a borrar de la base de datos para poder cerrar la sesion.
     * @throws SQLException Lanzada en el caso de error a la hora de borrar.
     */
    public void cerrarSesion(String cookie) throws SQLException {
        String sql = "DELETE FROM sesion WHERE cookie = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, cookie);
        ps.executeUpdate();
    }

    /**
     * Metodo del DAO para la clase Sesion encargado de registrar la creacion de una Sesion en la base de datos.
     * @param cookie cookie por la que se busca la sesion en la base de datos.
     * @return Devuelve un objeto Sesion.
     * @throws SQLException Lanzada en el caso de error a la hora de buscar.
     */
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
