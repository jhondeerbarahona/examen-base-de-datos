/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedimientos;

import Entidades.ClsCancion;
import Entidades.ClsUsuario;
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
public class ClssCancion {

    private Connection connection = new conexion.ClsConexion().getConection();

    public void agregarCancion(ClsCancion Cancion) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_I_Cancion (?,?,?,?,?,?)}");
            statement.setString("pgenero", Cancion.getStrGenero());
            statement.setString("pautor", Cancion.getStrAutor());
            statement.setString("ptitulo", Cancion.getStrTitulo());
            statement.setDate ("pfecha", new java.sql.Date(Cancion.getFecha().getTime()));
            statement.setString("pduracion", Cancion.getDuracion());
            statement.setString("pUsuario_Idusuario", Cancion.getStrUsuario_Idusuario());

            statement.execute();

            JOptionPane.showMessageDialog(null, "¡Cancion Agregado con éxito!", "Mensaje del Sistema", 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void modificarCancion(String codigo, ClsCancion Cancion) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_U_Cancion (?,?,?,?,?,?)}");
            statement.setString("pidcancion", codigo);
            statement.setString("pgenero", Cancion.getStrGenero());
            statement.setString("pautor", Cancion.getStrAutor());
            statement.setString("ptitulo", Cancion.getStrTitulo());
            statement.setDate ("pfecha", new java.sql.Date(Cancion.getFecha().getTime()));
            statement.setString("pduracion", Cancion.getDuracion());
            statement.setString("pUsuario_Idusuario", Cancion.getStrUsuario_Idusuario());
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "¡Cancion Actualizado!", "Mensaje del Sistema", 1);
    }

    public ArrayList<ClsCancion> listarCancionUsuario(String ID) {
        ArrayList<ClsCancion> canciones = new ArrayList<ClsCancion>();
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_Cancion}");
            statement.setString("pUsuario", ID);
            ResultSet resultSet = statement.executeQuery();
            

            while (resultSet.next()) {
                ClsCancion cancion = new ClsCancion();
                cancion.setStrIdCancion(resultSet.getString("IdCancion"));
                cancion.setStrGenero(resultSet.getString("Genero"));
                cancion.setStrAutor(resultSet.getString("Autor"));
                cancion.setStrTitulo(resultSet.getString("Titulo"));
                cancion.setFecha(resultSet.getDate("Fecha"));
                cancion.setDuracion(resultSet.getString("Duracion"));
                cancion.setStrUsuario_Idusuario(resultSet.getString("Usuario_Idusuario"));
                
                canciones.add(cancion);
            }
            return canciones;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public ArrayList<ClsCancion> listarCancion() {
        ArrayList<ClsCancion> canciones = new ArrayList<ClsCancion>();
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_TCancion}");
            //statement.setString("pUsuario", ID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ClsCancion cancion = new ClsCancion();
                cancion.setStrIdCancion(resultSet.getString("IdCancion"));
                cancion.setStrGenero(resultSet.getString("Genero"));
                cancion.setStrAutor(resultSet.getString("Autor"));
                cancion.setStrTitulo(resultSet.getString("Titulo"));
                cancion.setFecha(resultSet.getDate("Fecha"));
                cancion.setDuracion(resultSet.getString("Duracion"));
                cancion.setStrUsuario_Idusuario(resultSet.getString("Usuario_Idusuario"));
                canciones.add(cancion);
            }
            return canciones;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarCancionPorParametro(String criterio, String busqueda) throws Exception {
        ResultSet rs = null;
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_CancionPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }
    
}
