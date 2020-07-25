package pack.MaterialesDeCon.Model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;


public class Conexion {
        Connection conectar = null;
        public Connection getConexion(){        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            
            String dbName = "matdecon";
            String dbUserName = "root";
            String dbPassword = "1234";
            String connectionString = "jdbc:mysql://127.0.0.1:3307/" + dbName + "?user=" + dbUserName + "&password=" + dbPassword + "&useUnicode=true&characterEncoding=UTF-8";
            
            conectar = DriverManager.getConnection(connectionString);
            System.out.println("conectado");
            //JOptionPane.showMessageDialog(null,"conectado");
        }catch(Exception e){
            System.out.print(e.getMessage() + " : " + e.getCause());
            System.err.println("\nNo conectado");
            //JOptionPane.showMessageDialog(null,"  No conectado");
        }
        return conectar;
    }

}
