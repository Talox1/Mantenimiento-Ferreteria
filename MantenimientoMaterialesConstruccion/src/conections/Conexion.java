/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conections;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose Pablo Sandoval
 */
public class Conexion {
    Connection conectar = null;
    public Connection conexion(){        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost/farmacia","root","");
            System.out.println("conectado");
            //JOptionPane.showMessageDialog(null,"conectado");
        }catch(Exception e){
            System.out.print(e.getMessage() + " : " + e.getCause());
            System.err.println("\nNo conectado");
            //JOptionPane.showMessageDialog(null,"  No conectado");
        }
        return conectar;
    }
    public static void main(String[] args){
        Conexion cc = new Conexion();
        Connection cn = cc.conexion();
    }
}   
