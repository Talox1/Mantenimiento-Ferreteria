/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Person_system;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import conections.Conexion;

import java.sql.Connection;

import conections.Conexion;
import far_system.LoginController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Person_system;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;


/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class ProductosController implements Initializable {

    /**
     * Initializes the controller class.
     */
	private Conexion cc = new Conexion();
    private Connection cn = cc.conexion();
    @FXML    
    private Button regresar;
    Person_system person;
    private ObservableList<ObservableList> data;
    @FXML
    private TableView verTabla;
    @FXML
    private Button botonNuevo;
    @FXML
    private TextField nuevoNombrePro;
    @FXML
    private TextField nuevoDescripcionPro;
    @FXML
    private TextField nuevoCantidadPro;
    @FXML
    private TextField editarNombrePro;
    @FXML
    private TextField editarDescripcionPro;
    @FXML
    private TextField editarCantidadPro;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonEliminar;
    @FXML
    private TextField editarIDProv;
    @FXML
    private TextField nuevoIVAPro;
    @FXML
    private TextField nuevoPrecioPro;
    @FXML
    private TextField nuevoIDProvPro;
    @FXML
    private TextField editarIVAProv;
    @FXML
    private TextField editarPrecioPro;
    @FXML
    private TextField editarIDProvePro;
    @FXML
    private Button botonBuscar;
    @FXML
    private TextField buscarIDPro;
    @FXML
    private Button botonMostrar;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	visualizateData();
    }
    
    @FXML
    void regresar(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Vista_principal.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        Vista_principalController controller = loader.<Vista_principalController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) regresar.getScene().getWindow();
        stage1.close();
    }
    @FXML
    private void nuevo(ActionEvent event) {
    	Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        
    	if(nuevoNombrePro.getText().equals("") || nuevoDescripcionPro.getText().equals("") || nuevoCantidadPro.getText().equals("") || nuevoIVAPro.getText().equals("") || nuevoPrecioPro.getText().equals("") || nuevoIDProvPro.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Hay Campos Vacios");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            String nombre = nuevoNombrePro.getText();
            String descripcion = nuevoDescripcionPro.getText();
            String cantidad = nuevoCantidadPro.getText();
            String iva = nuevoIVAPro.getText();
            String precio = nuevoPrecioPro.getText();
            String idProv = nuevoIDProvPro.getText();
            String query = " INSERT INTO productos (Nombre, Descripcion, Cantidad, iva, Precio_unitario, Proveedores_id)" +" Values (?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setString(1, nombre);
                preparedStmt.setString(2, descripcion);
                preparedStmt.setString(3, cantidad);
                preparedStmt.setString(4, iva);
                preparedStmt.setString(5, precio);
                preparedStmt.setString(6, idProv);
                preparedStmt.execute();
                cn.close();

                Alert dialogAlert2 = new Alert(Alert.AlertType.INFORMATION);
                dialogAlert2.setTitle("Exito");
                dialogAlert2.setContentText("Se guardo con exito");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }finally {
            	updateInfoTable();
            }
        }
    }

    @FXML
    private void editar(ActionEvent event) {
    	Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        
    	if(editarNombrePro.getText().equals("") || editarDescripcionPro.getText().equals("") || editarCantidadPro.getText().equals("") || editarIVAProv.getText().equals("") || editarPrecioPro.getText().equals("") || editarIDProvePro.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Hay Campos Vacios");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
        	int id =Integer.parseInt(editarIDProv.getText());
            String nombre = editarNombrePro.getText();
            String descripcion = editarDescripcionPro.getText();
            String cantidad = editarCantidadPro.getText();
            String iva = editarIVAProv.getText();
            String precio = editarPrecioPro.getText();
            String idProv = editarIDProvePro.getText();
            try {
            	String query = " UPDATE productos SET Nombre = ?, Descripcion = ?, Cantidad = ?, iva = ?, Precio_unitario = ?, Proveedores_id = ? where id = ?";
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setString(1, nombre);
                preparedStmt.setString(2, descripcion);
                preparedStmt.setString(3, cantidad);
                preparedStmt.setString(4, iva);
                preparedStmt.setString(5, precio);
                preparedStmt.setString(6, idProv);
                preparedStmt.setInt(7, id);
                preparedStmt.execute();
                cn.close();

                Alert dialogAlert2 = new Alert(Alert.AlertType.INFORMATION);
                dialogAlert2.setTitle("Exito");
                dialogAlert2.setContentText("Se edito con exito");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }finally {
            	updateInfoTable();
            }
        }
    }
    
    boolean delete = false;
    @FXML
    private void eliminar(ActionEvent event) {
    	Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        
    	if(editarNombrePro.getText().equals("") || editarDescripcionPro.getText().equals("") || editarCantidadPro.getText().equals("") || editarIVAProv.getText().equals("") || editarPrecioPro.getText().equals("") || editarIDProvePro.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Hay Campos Vacios");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            int id =Integer.parseInt(editarIDProv.getText());
            Alert dialogAlert2 = new Alert(Alert.AlertType.CONFIRMATION);
            dialogAlert2.setTitle("Confirmacion");
            dialogAlert2.setHeaderText(null);
            dialogAlert2.setContentText("¿Desea eliminar este Producto?");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = dialogAlert2.showAndWait();
            if(result.get() == ButtonType.OK){
                delete = true;                
            }else{
            }
            if(delete == true){
                try {
                    String query = " DELETE FROM productos where id = ?";
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setInt(1, id);
                    preparedStmt.execute();
                    cn.close();

                    Alert dialogAlert1 = new Alert(Alert.AlertType.INFORMATION);
                    dialogAlert1.setTitle("Exito");
                    dialogAlert1.setContentText("Se elimino con exito");
                    dialogAlert1.initStyle(StageStyle.UTILITY);
                    dialogAlert1.showAndWait();
                } catch (SQLException e) {
                    System.err.println("\nError!... No se pudo realizar la sentencia");
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
                }finally {
                    updateInfoTable();
                }
            }
        }
    }

    @FXML
    private void buscar(ActionEvent event) {
    	Conexion cc = new Conexion();
        Connection cn = cc.conexion();
        String query = "";
        boolean largeQuery = false;

    	//System.out.print("This: "+!nuevoNombrePro.getText().equals(""));
        	if(!buscarIDPro.getText().equals("")){
                if(!largeQuery) {
                	query = " id = "+buscarIDPro.getText()+"";
                }else{
                    query = " and id = "+buscarIDPro.getText()+"";
                }
                largeQuery = true;
            }else
        	if(!nuevoNombrePro.getText().equals("")){
                if(!largeQuery){
                	query = " Nombre = '"+nuevoNombrePro.getText()+"'";
                }else{
                    query = " and Nombre = '"+nuevoNombrePro.getText()+"'";
                }
                largeQuery = true;
            }else
        	if(!nuevoDescripcionPro.getText().equals("")){
                if(!largeQuery) {
                    query = " Descripcion = '"+nuevoDescripcionPro.getText()+"'";
                }else{
                    query = " and Descripcion = '"+nuevoDescripcionPro.getText()+"'";
                }
                largeQuery = true;
            }else
        	if(!nuevoCantidadPro.getText().equals("")){
                if(!largeQuery) {
                	query = " Cantidad = "+nuevoCantidadPro.getText()+"";
                }
                    
                else{
                    query = " and Cantidad = "+nuevoCantidadPro.getText()+"";
                }
                largeQuery = true;
            }else
        	if(!nuevoIVAPro.getText().equals("")){
                if(!largeQuery)
                {
                	query = " iva = "+nuevoIVAPro.getText()+"";
                }
                else{
                    query = " and iva = "+nuevoIVAPro.getText()+"";
                }
                largeQuery = true;
            }else
        	if(!nuevoPrecioPro.getText().equals("")){
                if(!largeQuery)
                {
                	query = " Precio_unitario = "+nuevoPrecioPro.getText()+"";
                }
                else{
                    query = " and Precio_unitario = "+nuevoPrecioPro.getText()+"";
                }
                largeQuery = true;
            }else
        	if(!nuevoIDProvPro.getText().equals("")){
                if(!largeQuery)
                {
                	query = " Proveedores_id = "+nuevoIDProvPro.getText()+"";
                }
                else{
                    query = " and Proveedores_id = "+nuevoIDProvPro.getText()+"";
                }
                largeQuery = true;
            }
        	
            String sql = "SELECT * FROM productos WHERE"+query;
            data = FXCollections.observableArrayList();
            try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                
                
                
                data.clear();
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    //System.out.println("Row [1] added "+row );
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                verTabla.getItems().clear();
                verTabla.setItems(data);

            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }
            
            verTabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    //Check whether item is selected and set value of selected item to Label
                    if(verTabla.getSelectionModel().getSelectedItem() != null) {    
                       //System.out.println(newValue);
                       String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                       editarIDProv.setText(values[0]);
                       editarNombrePro.setText(values[1]);
                       editarDescripcionPro.setText(values[2]);
                       editarCantidadPro.setText(values[3]);
                       editarIVAProv.setText(values[4]);
                       editarPrecioPro.setText(values[5]);
                       editarIDProvePro.setText(values[6]);
                    }
                }
            });
            
        
    }

    @FXML
    private void OnMouseClcikedMostrarTodo(MouseEvent event) {
    }

    @FXML
    private void mostrar(ActionEvent event) {
    	updateInfoTable();
    }
    
    private void updateInfoTable(){
        String sql = "SELECT * FROM productos";
        data = FXCollections.observableArrayList();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added "+row );
                data.add(row);
            }
            
            //FINALLY ADDED TO TableView
            
            verTabla.setItems(data);
            verTabla.refresh();
        } catch (Exception e) {

            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void visualizateData(){
        String sql = "SELECT * FROM productos";
        data = FXCollections.observableArrayList();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    
                	public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                verTabla.getColumns().addAll(col); 
                //System.out.println("Column ["+i+"] ");
            }
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added "+row );
                data.add(row);
                
            }

            //FINALLY ADDED TO TableView
            
            verTabla.setItems(data);
            //verTabla.refresh();
        } catch (Exception e) {

            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
        
        verTabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(verTabla.getSelectionModel().getSelectedItem() != null) {    
                   //System.out.println(newValue);
                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                   editarIDProv.setText(values[0]);
                   editarNombrePro.setText(values[1]);
                   editarDescripcionPro.setText(values[2]);
                   editarCantidadPro.setText(values[3]);
                   editarIVAProv.setText(values[4]);
                   editarPrecioPro.setText(values[5]);
                   editarIDProvePro.setText(values[6]);
                }
            }
        });
    }
    
    public void informacion(Person_system person){                
        this.person = person;
    }       
}