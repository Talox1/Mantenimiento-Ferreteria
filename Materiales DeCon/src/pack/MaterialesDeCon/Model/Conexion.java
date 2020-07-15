package pack.MaterialesDeCon.Model;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;


public class Conexion {
	Connection referencia = null;
	
	public Connection getConexion() throws SQLException {
		String url= "jdbc:sqlserver://localhost:1433;datebaseName=MaterialesDeCon";
		
		try {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			referencia= (Connection) DriverManager.getConnection(url, "sa", "1234");
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "No se lo lorgro establecer la conexcion");
		}
		
		return referencia;
		
		
		
	}

}
