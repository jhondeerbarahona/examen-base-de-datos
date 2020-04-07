/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.sql.Connection;

public class ClsConexion {
    private static Connection conection=null;
    public Connection getConection(){
        try{
            MysqlConnectionPoolDataSource ds=new MysqlConnectionPoolDataSource();
            ds.setServerName("localhost");
            ds.setPort(3306);
            ds.setDatabaseName("musica_bd");
            conection=ds.getConnection("root","");
        }catch(Exception ex){
           ex.printStackTrace();
        }
        return conection;
    }
}
