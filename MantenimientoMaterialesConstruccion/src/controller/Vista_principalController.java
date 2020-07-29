/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import conections.Conexion;
import far_system.LoginController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Person_system;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class Vista_principalController implements Initializable {
   

    @FXML    private Label DateTime;
    @FXML    private Label Name;
    @FXML    private Label Rol;
    @FXML    private Button Productos;
    @FXML    private Button Usuarios;
    @FXML    private Button Provedores;
    @FXML    private Button Ventas;
    @FXML    private Button reporteVen;
    @FXML    private Button Corte;
    @FXML    private Button Cambio;
    @FXML    private TextField Cambio_caja;
    @FXML    private ImageView image;
    Person_system person;
    String nombre;
    String rol;
    String user;   
    float cambio;
    private ObservableList<ObservableList> data;
    @FXML    private TableView productosMenores;
    private Conexion cc = new Conexion();
    private Connection cn= cc.conexion();  

    @FXML
    void Cambio(ActionEvent event) {
        person.setCambio(Float.parseFloat(Cambio_caja.getText()));
        Cambio.setDisable(true);
        Alert dialogAlert2 = new Alert(Alert.AlertType.INFORMATION);
        dialogAlert2.setTitle("Informe");
        dialogAlert2.setContentText("Se agrego dinero a la caja");
        dialogAlert2.initStyle(StageStyle.UTILITY);
        dialogAlert2.showAndWait();
        Cambio_caja.setEditable(false);
    }

    @FXML
    void Corte_caja(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/CorteCaja.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        CorteCajaController controller = loader.<CorteCajaController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();

    }

    @FXML
    void Productos(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Productos.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        ProductosController controller = loader.<ProductosController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void Proveedores(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Proveedores.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        ProveedoresController controller = loader.<ProveedoresController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();
    }
    
    
    @FXML
    void Reporte_ventas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/ReportesVenta.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        ReportesVentaController controller = loader.<ReportesVentaController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void Usuarios(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Usuarios.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        UsuariosController controller = loader.<UsuariosController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void Ventas(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Ventas.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        VentasController controller = loader.<VentasController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) Corte.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void out(ActionEvent event) throws IOException {
        Stage stage = new Stage();       
        Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
        dialogAlert2.setTitle("Advertencia");
        dialogAlert2.setContentText("Esta saliendo de la aplciación");
        dialogAlert2.initStyle(StageStyle.UTILITY);
        dialogAlert2.showAndWait();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/far_system/Login.fxml"));
            Object carga = loader.load();
            Parent root = (Parent) carga;
            Scene scene = new Scene(root);            
            LoginController controller = loader.<LoginController>getController();            
            stage.setScene(scene);
            stage.show();                                                                   
            Stage stage1 = (Stage) Corte.getScene().getWindow();
            stage1.close();
        person.setNombre("");
        person.setRol("");
        person.setUser("");
    }

    public void informacion(Person_system person){                
        this.person = person;
        this.nombre = person.getNombre();
        this.rol = person.getRol();
        this.cambio = person.getCambio();
        Name.setText(person.getNombre());
        Rol.setText(person.getRol());
        image.setImage(new Image("/img/Portada.jpg"));
        if(rol.equals("Admin")){            
        }else{
            Usuarios.setVisible(false);
            reporteVen.setVisible(false);
        }
        if(cambio <= 50){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("No hay suficiente cambio en la caja");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
            Cambio.setDisable(false);
            Cambio_caja.setEditable(true);
        }else {
            Cambio.setDisable(true);
            Cambio_caja.setText(String.valueOf(cambio));
            Cambio_caja.setEditable(false);
        }
    }
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        DateTime.setText(Fecha());
        visualizateData();
    }
     
    public static String Fecha(){
         Date fecha = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YYYY");
         return formato.format(fecha);                  
     }
    
        private void visualizateData(){        
        String sql = "SELECT  id, Nombre, Descripcion, Cantidad,iva,  Precio_unitario FROM productos where productos.Cantidad < '" + 15 + "'";
        data = FXCollections.observableArrayList();
         try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
             /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));                
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                productosMenores.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }            
            /********************************
             * Data added to ObservableList *
             ********************************/            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);                
            }
            //FINALLY ADDED TO TableView            
            productosMenores.setItems(data);            
        } catch (SQLException e) {
            System.err.println("\nError!!!... sentencia no ejecutada");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
         
         
        
    }
}

