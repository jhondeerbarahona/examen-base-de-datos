/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procedimientos;

import Entidades.ClsListadeReproduccion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import presentacion.FrmUsuario;

/**
 *
 * @author Usuario
 */
public class ClssListadeReproduccion {

    private Connection connection = new conexion.ClsConexion().getConection();

    public ClssListadeReproduccion() throws SQLException {
        this.usuario = new FrmUsuario();
    }

    public void agregarListadeReproduccion(ClsListadeReproduccion Lista) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_I_ListadeReproduccion (?,?)}");
            statement.setString("pnombreLista", Lista.getStrnombreLista());
            statement.setString("pUsuario_Idusuario", Lista.getStrUsuario_Idusuario());

            statement.execute();

            JOptionPane.showMessageDialog(null, "¡Lista de Reproduccion Agregado con éxito!", "Mensaje del Sistema", 1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void modificarListadeReproduccion(String codigo, ClsListadeReproduccion Lista) {
        try {
            CallableStatement statement = connection.prepareCall("{call SP_U_ListadeReproduccion (?,?)}");
            statement.setString("pidListadeReproduccion", codigo);
            statement.setString("pnombreLista", Lista.getStrnombreLista());

            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "¡Cancion Actualizado!", "Mensaje del Sistema", 1);
    }
    FrmUsuario usuario;

    public ArrayList<ClsListadeReproduccion> listarListadeReproduccion() throws SQLException {
        ArrayList<ClsListadeReproduccion> listas = new ArrayList<ClsListadeReproduccion>();
        //String ID = null;
        //ID=usuario.UsuarioId;
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_Lista}");
            //statement.setString("pUsuario", ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClsListadeReproduccion lista = new ClsListadeReproduccion();
                lista.setStridListadeReproduccion(resultSet.getString("IdListadeReproduccion"));
                lista.setStrnombreLista(resultSet.getString("NombreLista"));
                lista.setStrUsuario_Idusuario(resultSet.getString("Usuario_Idusuario"));

                listas.add(lista);
            }
            return listas;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ResultSet listarListadeReproduccionPorParametro(String criterio, String busqueda) throws Exception {
        ResultSet rs = null;
        try {
            CallableStatement statement = connection.prepareCall("{call SP_S_ListaPorParametro(?,?)}");
            statement.setString("pcriterio", criterio);
            statement.setString("pbusqueda", busqueda);
            rs = statement.executeQuery();
            return rs;
        } catch (SQLException SQLex) {
            throw SQLex;
        }
    }

}
