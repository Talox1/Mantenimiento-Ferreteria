/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conections;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jose Pablo Sandoval
 */
public class Conectar {
    Connection conectar = null;
    public Connection conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia","root","");
            System.out.println("conexion exitosa");
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return conectar;
    }
    
}

