/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedimientos;

import Entidades.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class ClssUsuario {

    private Connection connection = new conexion.ClsConexion().getConection();

    public void agregarUsuario(ClsUsuario Usuario) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_I_Usuario (?,?,?,?)}");
            statement.setString("pnombre", Usuario.getStrNombreUsuario());
            statement.setString("papellido", Usuario.getStrApellidoUsuario());
            statement.setString("pcorreo", Usuario.getStrCorreoUsuario());
            statement.setString("ppasword", Usuario.getStrPaswordUsuario());
            statement.execute();

            JOptionPane.showMessageDialog(null, "¡Usuario Agregado con éxito!", "Mensaje del Sistema", 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void modificarUsuario(String strCodigo, ClsUsuario Usuario) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_U_Usuario(?,?,?,?,?)}");
            statement.setString("pid", strCodigo);
            statement.setString("pnombre", Usuario.getStrNombreUsuario());
            statement.setString("papellido", Usuario.getStrApellidoUsuario());
            statement.setString("pcorreo", Usuario.getStrCorreoUsuario());
            statement.setString("ppasword", Usuario.getStrPaswordUsuario());
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "¡Usuario Actualizado!", "Mensaje del Sistema", 1);
    }

    public ArrayList<ClsUsuario> listarUsuario() {
        ArrayList<ClsUsuario> usuarios = new ArrayList<ClsUsuario>();
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_Usuario}");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ClsUsuario usuario = new ClsUsuario();
                usuario.setStrIdusuario(resultSet.getString("IdUsuario"));
                usuario.setStrNombreUsuario(resultSet.getString("Nombre"));
                usuario.setStrApellidoUsuario(resultSet.getString("Apellido"));
                usuario.setStrCorreoUsuario(resultSet.getString("Correo"));
                usuario.setStrPaswordUsuario(resultSet.getString("Pasword"));
                usuarios.add(usuario);
            }
            return usuarios;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarUsuarioPorParametro(String criterio, String busqueda) throws Exception {
        ResultSet rs = null;
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_UsuarioPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }

}
