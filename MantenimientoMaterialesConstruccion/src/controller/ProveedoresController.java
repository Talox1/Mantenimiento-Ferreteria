/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import conections.Conexion;
import const_system.LoginController;
import static controller.Vista_principalController.Fecha;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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
import javafx.scene.control.Label;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class ProveedoresController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Conexion cc = new Conexion();
    private Connection cn= cc.conexion();    
    Person_system person;
        
    @FXML    private Button regresar;
    
    private ObservableList<ObservableList> data;
    @FXML private TableView tablaProveedores;
    
    @FXML private TextField nombreNuevoProv;

    @FXML private TextField telefonoNuevoProv;

    @FXML private TextField rfcNuevoProv;

    @FXML private TextField verNombreProv;

    @FXML private TextField verTelefonoProv;

    @FXML private TextField verRFCProv;
    
    @FXML private TextField verIdProv;
    
    @FXML private TextField filtroId;
    
    @FXML private TextField filtroNombre;

    @FXML private TextField fltroTelefono;

    @FXML private TextField filtroRfc;

    
    String nombre;
    String rol;
    String user;  
    @FXML    private Label Name;
    @FXML    private Label Rol;
    @FXML    private JFXButton Usuarios;
    @FXML    private JFXButton reporteVen;
    @FXML    private Label DateTime;
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        DateTime.setText(Fecha());
        visualizateData();
    }
    public static String Fecha(){
         Date fecha = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("dd-MM-YYYY");
         return formato.format(fecha);                  
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
        stage1.close();

    }

    @FXML
    void Productos(ActionEvent event) throws IOException {
        System.out.println("Productos");
        Stage stage = new Stage();
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Productos.fxml"));
        Object carga = loader.load();
        Parent root = (Parent) carga;
        Scene scene = new Scene(root);            
        ProductosController controller = loader.<ProductosController>getController();
        controller.informacion(person);
        stage.setScene(scene);
        stage.show();                                                                   
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
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
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/const_system/Login.fxml"));
            Object carga = loader.load();
            Parent root = (Parent) carga;
            Scene scene = new Scene(root);            
            LoginController controller = loader.<LoginController>getController();            
            stage.setScene(scene);
            stage.show();                                                                   
            Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
            stage1.close();
        person.setNombre("");
        person.setRol("");
        person.setUser("");
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
        Stage stage1 = (Stage) tablaProveedores.getScene().getWindow();
        stage1.close();
    }
    
    @FXML
    void OnMouseClcikedMostrarTodo(MouseEvent event) {
        updateInfoTable();
    }
    
    @FXML
    void OnMouseClickedEditar(MouseEvent event) {
        if(verIdProv.getText().equals("") || verNombreProv.getText().equals("") || verRFCProv.getText().equals("") || verTelefonoProv.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Debe seleccionar un elemento de la lista");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            int id =Integer.parseInt(verIdProv.getText());
            String nombre = verNombreProv.getText();
            String telefono = verTelefonoProv.getText();
            String rfc = verRFCProv.getText();
            //String query = " UPDATE proveedores (id, Nombre, Rfc, Telefono)" +" Values (?, ?, ?)";
            //st.execute("UPDATE ciudades SET id = '"+id+"' where idciudad ="+idCiudad);
            
            try {
                String query = "UPDATE proveedores SET nombre = ?, Telefono = ?, Rfc = ? where id = ?";
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setString  (1, nombre);
                preparedStmt.setString(2, telefono);
                preparedStmt.setString(3, rfc);
                preparedStmt.setInt(4, id);

                // execute the java preparedstatement
                preparedStmt.executeUpdate();
                                                
                Alert dialogAlert2 = new Alert(Alert.AlertType.INFORMATION);
                dialogAlert2.setTitle("Exito");
                dialogAlert2.setContentText("Se guardo con exito");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
                
                
            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }finally{
                updateInfoTable();
            }
        }
    }
    
    boolean delete = false;
    @FXML
    void OnMouseClickedEliminar(MouseEvent event) {
        if(verIdProv.getText().equals("") ){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Debe seleccionar un elemento de la lista");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            int id =Integer.parseInt(verIdProv.getText());            
            Alert dialogAlert2 = new Alert(Alert.AlertType.CONFIRMATION);
            dialogAlert2.setTitle("Confirmacion");
            dialogAlert2.setHeaderText(null);
            dialogAlert2.setContentText("¿Desea eliminar este Proveedor?");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = dialogAlert2.showAndWait();
            if(result.get() == ButtonType.OK){
                delete = true;                
            }else{
            }
            if(delete == true){
                try {
                String query = "DELETE FROM proveedores where id = ?";
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setInt(1, id);
                preparedStmt.execute();                                      
                
                Alert dialogAlert1 = new Alert(Alert.AlertType.INFORMATION);
                dialogAlert1.setTitle("Exito");
                dialogAlert1.setContentText("Se elimino con exito");
                dialogAlert1.initStyle(StageStyle.UTILITY);
                dialogAlert1.showAndWait();
                
            } catch (Exception e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);     
            }finally{
                updateInfoTable();
            }
            }            
        }
    }

    @FXML
    void OnMouseClickedNuevoProveedor(MouseEvent event) {
        if(nombreNuevoProv.getText().equals("") || telefonoNuevoProv.getText().equals("") || rfcNuevoProv.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Hay Campos Vacios");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            String nombre = nombreNuevoProv.getText();
            String telefono = telefonoNuevoProv.getText();
            String rfc = rfcNuevoProv.getText();
            String query = " INSERT INTO proveedores (Nombre, Rfc, Telefono)" +" Values (?, ?, ?)";
            try {
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setString (1, nombre);
                preparedStmt.setString (2, rfc);
                preparedStmt.setString(3, telefono);
                preparedStmt.execute();
                
               
                
                Alert dialogAlert2 = new Alert(Alert.AlertType.INFORMATION);
                dialogAlert2.setTitle("Exito");
                dialogAlert2.setContentText("Se guardo con exito");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
                
                
            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }finally{
                 updateInfoTable();
            }
        }
    }

    @FXML
    void OnMouseClickedBuscarProveedor(MouseEvent event) {
        String query = "Where";
        boolean largeQuery = false;
        if(filtroId.getText().equals("") && filtroNombre.getText().equals("") && fltroTelefono.getText().equals("") && filtroRfc.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Debe llenar al menos campo");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
            largeQuery = false;
        }else{
            if(!filtroId.getText().equals("")){
                if(!largeQuery)
                    query = " id = "+filtroId.getText()+"";
                else{
                    query = " and id = "+filtroId.getText()+"";
                }
                largeQuery = true;
            }
            if(!filtroNombre.getText().equals("")){
                if(!largeQuery)
                    query = " Nombre = '"+filtroNombre.getText()+"' ";
                else{
                    query += " and Nombre = '"+filtroNombre.getText()+"' ";
                }
                largeQuery = true;
            }
            if(!fltroTelefono.getText().equals("")){
                if(!largeQuery)
                    query = " Telefono = '"+fltroTelefono.getText()+"' ";
                else{
                    query += " and Telefono = '"+fltroTelefono.getText()+"' ";
                }
                largeQuery = true;
            }
            if(!filtroRfc.getText().equals("")){
                if(!largeQuery)
                    query = " Rfc = '"+rfcNuevoProv.getText()+"' ";
                else{
                    query += " and Rfc = '"+rfcNuevoProv.getText()+"' ";
                }
                largeQuery = true;
            }
            String sql = "SELECT * FROM proveedores WHERE "+query;
            System.out.println(sql);
            data = FXCollections.observableArrayList();

             try {            
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql);


                /********************************
                 * Data added to ObservableList *
                 ********************************/
                data.clear();
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
                tablaProveedores.getItems().clear();
                tablaProveedores.setItems(data);

            } catch (SQLException e) {
                System.err.println("\nError!!!... sentencia no ejecutada");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }
         
         
        tablaProveedores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tablaProveedores.getSelectionModel().getSelectedItem() != null) {    
                   System.out.println(newValue);
                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                   verIdProv.setText(values[0]);
                   verNombreProv.setText(values[1]);
                   verRFCProv.setText(values[2]);
                   verTelefonoProv.setText(values[3]);
                }
            }
        });
        }
    }
    
        
    public void informacion(Person_system person){                
        this.person = person;
        
        this.nombre = person.getNombre();
        this.rol = person.getRol();
        
        Name.setText(person.getNombre());
        Rol.setText(person.getRol());
        //image.setImage(new Image("/img/Portada.jpg"));
        if(rol.equals("Admin")){            
        }else{
            System.out.println("rol"+rol);
            Usuarios.setVisible(false);
            reporteVen.setVisible(false);
        }
    }
    
    
    private void visualizateData(){
        
        String sql = "SELECT * FROM proveedores";
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
                
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });

                tablaProveedores.getColumns().addAll(col); 
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
            
            tablaProveedores.setItems(data);
            
        } catch (SQLException e) {
            System.err.println("\nError!!!... sentencia no ejecutada");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
        }
         
         
        tablaProveedores.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tablaProveedores.getSelectionModel().getSelectedItem() != null) {    
                   System.out.println(newValue);
                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                   verIdProv.setText(values[0]);
                   verNombreProv.setText(values[1]);
                   verRFCProv.setText(values[2]);
                   verTelefonoProv.setText(values[3]);
                }
            }
        });
    }
    
    
    private void updateInfoTable(){
        String sql = "SELECT * FROM proveedores";
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
                System.out.println("Row [1] added "+row );
                data.add(row);
                
            }

            //FINALLY ADDED TO TableView
            
            tablaProveedores.setItems(data);
            tablaProveedores.refresh();
        } catch (Exception e) {
        }
        
        
    }
}