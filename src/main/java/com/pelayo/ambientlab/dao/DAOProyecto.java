package com.pelayo.ambientlab.dao;

import com.google.gson.Gson;
import com.pelayo.ambientlab.excepciones.HTTPStatusException;
import com.pelayo.ambientlab.modelo.Proyecto;
import com.pelayo.ambientlab.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase encargada de acceder a la base de datos para la clase Proyecto.
 */
public class DAOProyecto {

    public Connection con = null;

    /**
     * Conexion a la base de datos.
     * @throws SQLException
     */
    public DAOProyecto() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    /**
     * Metodo del DAO para la clase Proyecto encargado de registrar la creacion de un Proyecto en la base de datos.
     * @param proyecto objeto Proyecto que va a ser guardado en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de crear.
     */
    public void crearProyecto(Proyecto proyecto) throws SQLException {
        String sql = "INSERT INTO proyecto ( titulo, descripcion, estado, fechaInicio, fechaFin) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, proyecto.getTitulo());
        ps.setString(2, proyecto.getDescripcion());
        ps.setString(3, proyecto.getEstado());
        ps.setDate(4, new java.sql.Date(proyecto.getFechaInicio().getTime()));
        ps.setDate(5, new java.sql.Date(proyecto.getFechaFin().getTime()));

        ps.executeUpdate();
        ps.close();

    }

    /**
     * Metodo del DAO para la clase Proyecto encargado de borrar un Proyecto en la base de datos.
     * @param idProyecto id del proyecto que va a ser borrado
     * @throws SQLException Lanzada en el caso de error a la hora de borrar.
     */
    public void borrarProyecto(int idProyecto) throws SQLException {
        String sql = "DELETE FROM proyecto WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idProyecto);
        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Proyecto encargado de listar todos los Proyectos en la base de datos.
     * @return Devuelve un objeto JSON con los proyectos listados.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException  Lanzada si no se encuentra ningun Proyecto.
     */
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

    /**
     * Metodo del DAO para la clase Proyecto encargado de listar todos los Proyectos en la base de datos.
     * @param idProyecto id del proyecto que se lista.
     * @return Devuelve un objeto JSON con los proyectos listados.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException  Lanzada si no se encuentra ningun Proyecto.
     */
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

    /**
     * Metodo del DAO para la clase Proyecto encargado de listar todos los Proyectos, a los que pertenece un Usuario, en la base de datos.
     * @param idUsuario id del usuario para el que se listan los proyectos.
     * @return Devuelve un objeto JSON con los proyectos listados.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException  Lanzada si no se encuentra ningun Proyecto.
     */
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

    /**
     * Metodo del DAO para la clase Proyecto encargado de listar los usuarios que pertenecen a un proyecto desde la base de datos.
     * @param idProyecto del proyecto para el que se listan los usuarios.
     * @return Devuelve un objeto JSON con los usuarios listados.
     * @throws SQLException Lanzada en el caso de error a la hora de listar.
     * @throws HTTPStatusException  Lanzada si no se encuentra ningun Proyecto.
     */
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

    /**
     * Metodo del DAO para la clase Proyecto encargado de editar un proyecto en la base de datos.
     * @param id del proyecto.
     * @param titulo del proyecto.
     * @param descripcion del proyecto.
     * @param estado del proyecto.
     * @param fechaInicio del proyecto.
     * @param fechaFinal del proyecto.
     * @throws SQLException Lanzada en el caso de error a la hora de editar.
     */
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

    /**
     * Metodo del DAO para la clase Proyecto encargado de actualizar el estado de un proyecto en la base de datos.
     * @param id del proyecto que se actualiza
     * @param estado que se actualzia en el proyecto.
     * @throws SQLException Lanzada en el caso de error a la hora de actualizar.
     */
    public void actualizarEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE proyecto SET estado=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, estado);
        ps.setInt(2, id);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Proyecto encargado de asignar un usuario a un proyecto en la base de datos.
     * @param idProyecto id del proyecto al que se asigna el usuario.
     * @param idUsuario id del usuario que es asignado
     * @throws SQLException Lanzada en el caso de error.
     */
    public void asignarUsuario(int idProyecto, int idUsuario) throws SQLException {
        String sql = "INSERT INTO usuario_proyecto (idUsuario, idProyecto) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);
        ps.setInt(2, idProyecto);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Metodo del DAO para la clase Proyecto encargado de quitar a un usuario de un proyecto.
     * @param idProyecto del proyecto del que se quita el usuario.
     * @param idUsuario del usuario que es quitado del proyecto.
     * @throws SQLException Lanzada en el caso de error.
     */
    public void quitarUsuario(int idProyecto, int idUsuario) throws SQLException {
        String sql = "DELETE FROM usuario_proyecto WHERE idUsuario=? AND idProyecto=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idUsuario);
        ps.setInt(2, idProyecto);

        ps.executeUpdate();
        ps.close();
    }
}
