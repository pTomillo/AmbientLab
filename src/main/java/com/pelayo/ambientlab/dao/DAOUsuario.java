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

/**
 * Clase encargada de acceder a la base de datos para la clase Usuario.
 */
public class DAOUsuario {
    public Connection con = null;

    /**
     * Conexion a la base de datos.
     * @throws SQLException
     */
    public DAOUsuario() throws SQLException {
        this.con = DBConexion.getConexion();
    }

    /**
     * Metodo del DAO para la clase Usuario encargado de registrar la creacion de una Usuario en la base de datos. Para el admin.
     * @param u Objeto Usuario que se insertara en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de crear.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de registrar la creacion de una Usuario en la base de datos. Para el usuario sin registrar.
     * @param u Objeto Usuario que se registrara en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de registrar.
     */
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

    /**
     *  Metodo del DAO para la clase Usuario encargado de buscar un usuario por su email.
     * @param email email del usuario que se busca.
     * @return Devuelve un objeto JSON con el Usuario buscado.
     * @throws SQLException Lanzada en el caso de error a la hora de buscar el usuario.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de buscar un usuario por su id.
     * @param id id del usuario que se busca.
     * @return Devuelve un objeto JSON con el Usuario buscado.
     * @throws SQLException Lanzada en el caso de error a la hora de buscar el usuario.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de borrar un Usuario de la base de datos.
     * @param id del Usuario que va a ser borrado.
     * @throws SQLException Lanzada en el caso de error a la hora de borrar el usuario.
     */
    public void borrarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();

        ps.close();
    }

    /**
     * Metodo del DAO para la clase Usuario encargado de editar un Usuario en la base de datos. Para el Admin.
     * @param usuarioUpdateAdmin Objeto Usuario que va a ser actualizado.
     * @throws SQLException Lanzada en el caso de error a la hora de editar el Usuario.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de editar un Usuario en la base de datos. Para el usuario.
     * @param usuarioUpdate Objeto Usuario que va a ser actualizado en la base de datos.
     * @throws SQLException Lanzada en el caso de error a la hora de editar el Usuario.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de asignar un rol a un Usuario en la base de datos.
     * @param idRol id del rol que se va a asignar.
     * @param usuarioUpdate Objeto Usuario al que se le va asignar el rol.
     * @throws SQLException Lanzada en el caso de error a la hora de asignar al rol del Usuario.
     */
    public void asignarRol(int idRol, Usuario usuarioUpdate) throws SQLException {
        String sql = "UPDATE usuario SET rol=? WHERE id =?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, idRol);
        ps.setInt(2, usuarioUpdate.getId());

        ps.executeUpdate();

        ps.close();
    }

    /**
     * Metodo del DAO para la clase Usuario encargado de cambiar la contraseña de un Usuario en la base de datos. Metodo empleado por el usuario.
     * @param id del usuario que va a cambiar la contraseña.
     * @param newHash nueva contraseña.
     * @throws SQLException Lanzada en el caso de error a la hora de cambiar la contraseña del Usuario.
     */
    public void cambiarContrasena (int id, String newHash) throws SQLException {
        String sql = "UPDATE usuario SET contrasena=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, newHash);
        ps.setInt(2, id);

        ps.executeUpdate();

        ps.close();
    }

    /**
     * Metodo del DAO para la clase Usuario encargado de resetear la contraseña de un Usuario en la base de datos. Metodo empleado por el Administrador.
     * @param id del usuario al que se le resetea la contraseña.
     * @param newHash nueva contraseña asignada por el administrador.
     * @throws SQLException Lanzada en el caso de error a la hora de cambiar la contraseña del Usuario.
     */
    public void resetContrasena(int id, String newHash) throws SQLException {
        String sql = "UPDATE usuario SET contrasena=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, newHash);
        ps.setInt(2, id);

        ps.executeUpdate();

        ps.close();
    }

    /**
     * Metodo del DAO para la clase Usuario encargado de listar todos los usuarios desde la base de datos.
     * @return Devuelve un objeto JSON con los Usuarios listados.
     * @throws SQLException Lanzada en el caso de error a la hora de listar los Usuarios.
     * @throws HTTPStatusException Lanzada en el caso de eno encontrar los usuarios.
     */
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

    /**
     * Metodo del DAO para la clase Usuario encargado de listar un usuario desde la base de datos.
     * @return Devuelve un objeto JSON con el Usuario listado.
     * @throws SQLException Lanzada en el caso de error a la hora de listar el Usuario.
     * @throws HTTPStatusException Lanzada en el caso de eno encontrar al usuario.
     */
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
