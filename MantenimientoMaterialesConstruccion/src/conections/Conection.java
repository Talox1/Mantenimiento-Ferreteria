/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conections;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jose Pablo
 */
public class Conection {
    private Connection connection;
	//private String url = "jdbc:mysql://localhost/db_alumnos";
	//private String usuario = "root";
	//private String contrasena = "";
	
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public void establecerConexion(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmacia","root","");
                        System.out.println("Coneccion exitosa");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarConexion(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
