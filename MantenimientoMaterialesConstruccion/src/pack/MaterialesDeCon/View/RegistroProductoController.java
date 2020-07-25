package pack.MaterialesDeCon.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pack.MaterialesDeCon.Main;
import pack.MaterialesDeCon.Model.Categoria;
import pack.MaterialesDeCon.Model.Conexion;
import pack.MaterialesDeCon.Model.Producto;


public class RegistroProductoController {
	Main main;
	@FXML
    private JFXComboBox<Categoria> ctg;

    @FXML
    private JFXComboBox<String> provedor;
    
    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField preUni;

    @FXML
    private JFXTextField extc;

    @FXML
    private JFXButton cancelar;

    @FXML
    private JFXButton guardar;

    static ObservableList<Categoria> categorias=FXCollections.observableArrayList();
    static ObservableList<Producto> productos= FXCollections.observableArrayList();
    Connection conn=null;
    Conexion con = new Conexion();
  
    @FXML
    public void initialize() {
    	
    	aniadirCategoria(); 
    }
    
    @FXML
    private void aniadir(ActionEvent event) throws ClassNotFoundException {
    	String consulta="INSERT INTO MaterialesDeCon.dbo.Producto(nombreProducto,idProducto,idProveerdor,idCategoria,precioUnitario,existencia)" + "values (?,?,?,?,?,?)";
    	 try {
			conn = con.getConexion();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("no se pudo");
		}
    	try {
    		PreparedStatement a = conn.prepareStatement(consulta);
    		a.setString(1, nom.getText());
    		a.setString(2, id.getText());
    		a.setString(3, "1");
    		a.setString(4, ctg.getSelectionModel().getSelectedItem().toString());
    		a.setFloat(5, Float.parseFloat(preUni.getText()));
    		a.setInt(6, Integer.parseInt(extc.getText()));
    		
    		a.execute();
    		System.out.println("si pude");
    	}catch(Exception e) {
    		System.out.println("no se puso perro");
    	}
    	Stage s = (Stage)guardar.getScene().getWindow();
    	s.close();
    }
    
    public void aniadirCategoria(){
    	
    	categorias.add(new Categoria("Acero", "AC01DECON","Materiales de Acero"));
    	categorias.add(new Categoria("Azulejo", "AZ02DECON","Azulejos de todo tipo"));
    	categorias.add(new Categoria("Cemento", "CM03DECON","Materiale de cemento y cemento"));
    	categorias.add(new Categoria("Lamina", "LM04DECON","Materiales de Lamina"));
    	categorias.add(new Categoria("Madera", "MD05DECON","Materiales de Madera"));
    	categorias.add(new Categoria("Pintura", "PN06DECON","Pinturas y liquidos"));
    	
    	ctg.setItems(categorias);
    	
    	
    }
   
    

    public void setMain(Main main) {
		this.main=main;
	}

}
