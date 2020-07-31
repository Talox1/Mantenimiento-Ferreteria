/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import conections.Conexion;
import const_system.LoginController;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Person_system;
import model.Venta_productos;
import java.io.File;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class VentasController implements Initializable {

    /**
     * Initializes the controller class.
     */      
    @FXML    private TextField productoBuscar;
    @FXML    private TextField iva;
    @FXML    private TextField subtotal;
    @FXML    private TextField total;
    @FXML    private TextField productoVender;
    @FXML    private TextField cantidadVender;    
    @FXML    private TextField precioUniVender;
    @FXML    private TextField Descripcionvender;
    @FXML    private TextField idVender;
    @FXML    private TextField cambioVenta;
    @FXML    private TextField devoluciónVenta;
    @FXML    private TextField cantidadProductoVenta;

    @FXML    private Button regresar;
    Person_system person;
    
    @FXML    private TableView ProductosVenta;
    @FXML    private TableView productoVenta;
    private Conexion cc = new Conexion();
    private Connection cn= cc.conexion();   
    private ObservableList<ObservableList> data;
    private ObservableList<ObservableList> data1;
    Venta_productos ventasGen;
    String hora;
    List<Integer> ids = new ArrayList<Integer>();    
    List<Integer> cantidadVendida = new ArrayList<Integer>();
    List<Integer> ids_delete = new ArrayList<Integer>();
    ArrayList<Venta_productos> ventasPro = new ArrayList<Venta_productos>();
    
    
    String nombre;
    String rol;
    String user;  
    @FXML    private Label Name;
    @FXML    private Label Rol;
    @FXML    private JFXButton  Usuarios;
    @FXML    private JFXButton reporteVen;
    @FXML    private Label DateTime;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DateTime.setText(Fecha());
        hora = Hora();
        visualizateData();
        visualizateData1();
    }
    boolean salir = false;
    
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
        Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
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
            Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
            stage1.close();
        person.setNombre("");
        person.setRol("");
        person.setUser("");
    }

    
    @FXML
    void regresar(ActionEvent event) throws IOException {
        if(ventasPro.size() > 0){
             Alert dialogAlert2 = new Alert(Alert.AlertType.CONFIRMATION);
            dialogAlert2.setTitle("Confirmacion");
            dialogAlert2.setHeaderText("Tienes una venta abierta");
            dialogAlert2.setContentText("¿Desea salir?");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = dialogAlert2.showAndWait();
            if(result.get() == ButtonType.OK){
                salir = true;
                fineIds();
                deleteProductos();
            }else{
            }
        }else{
            salir = true;
        }
        if(salir == true){
            Stage stage = new Stage();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Vista_principal.fxml"));
            Object carga = loader.load();
            Parent root = (Parent) carga;
            Scene scene = new Scene(root);            
            Vista_principalController controller = loader.<Vista_principalController>getController();
            controller.informacion(person);
            stage.setScene(scene);
            stage.show();                                                                   
            Stage stage1 = (Stage) ProductosVenta.getScene().getWindow();
            stage1.close();
        }        
    }
    public void fineIds(){
        String fecha = Fecha();        
        String sql = "SELECT id From farmacia.historialventas WHERE historialventas.Fecha = '"+ fecha +"' and historialventas.Hora > '"+ hora +"'";                
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);            
            while(rs.next()){
                //Iterate 
                ids_delete.add(rs.getInt(1));
            }
            //FINALLY ADDED TO TableView                                    
        } catch (Exception e) {
        }
    }
    
    public void deleteProductos(){
        for (int i = 0; i < ids_delete.size(); i++) {
            System.out.println(ids_delete.get(i));
            try {
                String query = "DELETE FROM historialventas where id = ?";
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setInt(1, ids_delete.get(i));
                preparedStmt.execute();
            } catch (Exception e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);     
            }
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
            Usuarios.setVisible(false);
            reporteVen.setVisible(false);
        }
    }
    
        
    @FXML
    void buscarProductos(ActionEvent event) {
        String sql;
        if(productoBuscar.getText().equals("")){
            System.out.println("hola perros entre aqui pero no hago nada");
            sql = "SELECT id, Nombre, Descripcion, Cantidad, Precio_unitario FROM productos";
        }else{
            sql = "SELECT id, Nombre, Descripcion, Cantidad, Precio_unitario FROM productos  WHERE productos.Nombre =  '"+ productoBuscar.getText() +"'";
        }
        productoBuscar.setText("");
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
                ProductosVenta.getItems().clear();
                ProductosVenta.setItems(data);

            } catch (SQLException e) {
                System.err.println("\nError!!!... sentencia no ejecutada");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }
         
         
        ProductosVenta.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(ProductosVenta.getSelectionModel().getSelectedItem() != null) {    
                   System.out.println(newValue);
                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                   idVender.setText(values[0]);
                   productoVender.setText(values[1]);
                   Descripcionvender.setText(values[2]);
                   precioUniVender.setText(values[4]);
                   cantidadProductoVenta.setText(values[3]);
                }
            }
        });
    }    
    
    @FXML
    void generarCompra(ActionEvent event) throws SQLException, IOException, FileNotFoundException, DocumentException {
        hora = Hora();
        if(ventasPro.size() == 0){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("No hay productos para vender");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            if(cambioVenta.getText().equals("")){
                Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert2.setTitle("Advertencia");
                dialogAlert2.setContentText("No ha introducido una cantidad de cambio");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            }else{
                if(Double.valueOf(cambioVenta.getText()) < Double.valueOf(total.getText()) ){
                    Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                    dialogAlert2.setTitle("Advertencia");
                    dialogAlert2.setContentText("La cantidad de cambio intruducido es menor al total de la compra");
                    dialogAlert2.initStyle(StageStyle.UTILITY);
                    dialogAlert2.showAndWait();
                }else{
                    if(person.getCambio() >= Double.valueOf(cambioVenta.getText())){
                        System.out.println("hols");
                        float cambio = 0;
                        for (int i = 0; i < ids.size(); i++) {
                            analizarSentencia(ids.get(i), cantidadVendida.get(i));
                        }                    
                        float valorCaja = person.getCambio();
                        if(Double.valueOf(cambioVenta.getText()).equals(Double.valueOf(total.getText()))){
                            valorCaja += Float.valueOf(total.getText());
                            System.out.println(valorCaja);
                            person.setCambio(valorCaja);
                        }else{
                            cambio = Float.valueOf(cambioVenta.getText()) - Float.valueOf(total.getText());
                            valorCaja -= cambio;
                            valorCaja += Float.valueOf(total.getText());
                            System.out.println(valorCaja);
                            person.setCambio(valorCaja);
                            devoluciónVenta.setText(String.valueOf(cambio));
                        }
                        String query = " INSERT INTO ventas (Subtotal, Iva, Total, Hora, Fecha)" +" Values (?, ?, ?, ?, ?)";
                        try {
                            Connection cn= cc.conexion();
                            
                            PreparedStatement preparedStmt = cn.prepareStatement(query);
                            preparedStmt.setString (1,subtotal.getText());
                            preparedStmt.setString (2,iva.getText());
                            preparedStmt.setString(3,total.getText());
                            preparedStmt.setString(4, Hora1());
                            preparedStmt.setString (5, Fecha());
                            preparedStmt.execute();
                        } catch (SQLException e) {
                            System.err.println("\nError!... No se pudo realizar la sentencia");
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
                        }
                        generarPdf("farmacia",iva.getText(), subtotal.getText(), total.getText(), cambio);
                        ids.clear();
                        cantidadVendida.clear();
                        iva.setText("");
                        subtotal.setText("");
                        total.setText("");                    
                        ventasPro.clear();                        
                    }else{
                        if(person.getCambio() == 0){
                            TextInputDialog dialogo = new TextInputDialog();
                            dialogo.setTitle("Mete dinero en la caja");
                            dialogo.setHeaderText("No hay cambio en caja");
                            dialogo.setContentText("Introduce dinero en la caja");
                            dialogo.initStyle(StageStyle.UTILITY);
                            Optional<String> response = dialogo.showAndWait();
                            response.ifPresent((string)->person.setCambio(Float.valueOf(string)));

                        }else{
                            TextInputDialog dialogo = new TextInputDialog();
                            dialogo.setTitle("Mete dinero en la caja");
                            dialogo.setHeaderText("No tienes el cambio suficiente");
                            dialogo.setContentText("Introduce dinero en la caja");                           
                            dialogo.initStyle(StageStyle.UTILITY);
                            Optional<String> response = dialogo.showAndWait();
                            response.ifPresent((string)->person.setCambio(Float.valueOf(string) + person.getCambio()));
                        }                    
                    }
                }
            }
        }

    }
    
    boolean hay = false;
    
    private void generarPdf(String nombre,String iva, String subtotal, String total, float cambio)throws FileNotFoundException, DocumentException, IOException{
        String sSistemaOperativo = System.getProperty("os.name");
        System.out.println(sSistemaOperativo);
        File directorio = null;
        File directorio1 = null;
        String fecha = Fecha();
        String hora = Hora1();            
        FileOutputStream archivo = null;
        if(sSistemaOperativo.equals("Windows 10") || sSistemaOperativo.equals("Windows 7") || sSistemaOperativo.equals("Windows 8") || sSistemaOperativo.equals("Windows Xp")){
            directorio = new File("C:\\pdfs");
            directorio1 = new File("C:\\pdfs\\tickets");            
        }else{
            directorio = new File("/home/pdfs");
            directorio1 = new File("/home/pdfs/tickets");            
        }        
        if (!directorio.exists()) {
            if (directorio.mkdir()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }        
        if (!directorio1.exists()) {
            if (directorio1.mkdir()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
            if(sSistemaOperativo.equals("Windows 10") || sSistemaOperativo.equals("Windows 7") || sSistemaOperativo.equals("Windows 8") || sSistemaOperativo.equals("Windows Xp")){
                archivo = new FileOutputStream(new File("C:\\pdfs\\tickets\\farmacia" + "_" + fecha + "_" +hora + ".pdf"));
            }else{
                archivo = new FileOutputStream(new File("/home/pdfs/tickets/farmacia" + "_" + fecha + "_" +hora + ".pdf"));
            } 
            Document docto = new Document();        
            PdfWriter.getInstance(docto, archivo);
            docto.open();
            Paragraph parrafo = new Paragraph("-------------- Farmacia el Fenix --------------" + "\n Fecha: " +Fecha() + "\n Hora de atencion: " + Hora() + "\n Telefono: (961)2594528" + "\n Empleado que le atendio: " + person.getNombre() + "\n Sucursal: 9a sur poniente 630");
            parrafo.setAlignment(1);
            docto.add(parrafo);
            docto.add(new Paragraph("\nProducto                             Precio Unitario                             Cantidad                            Total"));
            for (int i = 0; i < ventasPro.size(); i++) {                
                docto.add(new Paragraph(ventasPro.get(i).getNombreProducto() + "                                     " + ventasPro.get(i).getPrecioUni() + "                                     "  +ventasPro.get(i).getCantidad() + "                                     "  + ventasPro.get(i).getTotal()));
            }
            docto.add(new Paragraph("\n                     Pago con:    "+ cambioVenta.getText()));
            docto.add(new Paragraph("\n                  Se devolvio:    "+ String.valueOf(cambio)));
            docto.add(new Paragraph("\n                    Sub Total:    "+ subtotal));
            docto.add(new Paragraph("\n                          Iva:    " + iva));
            docto.add(new Paragraph("\n                        Total:    " + total));
            docto.add(new Paragraph("\n Farmacias el Fenix les agradece su preferencia vuelva pronto"));
            docto.close();           
            //System.out.println(Fecha());
            try {
                Thread.sleep(7000);
            } catch (InterruptedException ex) {
                Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            refrescar();
            this.abrir(nombre,fecha, hora);
    }
    
    public void abrir(String nombre,String fecha, String hora){
       File path = null;
        String sSistemaOperativo = System.getProperty("os.name");
        System.out.println(sSistemaOperativo);
        try {
            if(sSistemaOperativo.equals("Windows 10") || sSistemaOperativo.equals("Windows 7") || sSistemaOperativo.equals("Windows 8") || sSistemaOperativo.equals("Windows Xp")){
                path = new File("C:\\pdfs\\tickets\\" + nombre + "_" + fecha + "_" + hora + ".pdf");
            }else{
                path = new File("/home/pdfs/tickets/" + nombre + "_" + fecha + "_" + hora + ".pdf");
            }               
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
        
    private void refrescar() throws IOException{
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
        
    public static String Hora1(){
         Date fecha = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("HH-mm-ss");
         return formato.format(fecha);                  
     }    
    
    private void analizarSentencia(int id, int cantidadVendida) throws SQLException{
        int ids = 0;
        String Nombre = "";
        String Descripcion = "";
        int Cantidad = 0;
        int iva = 0;
        int Precio_Unitario = 0;
        int provedor_id = 0;
        String sql = "SELECT * FROM productos  WHERE productos.id =  '"+ id +"'";
        try {            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println(rs);
            while(rs.next()){
                ids = rs.getInt(1);
                Nombre = rs.getString(2);
                Descripcion = rs.getString(3);
                Cantidad = rs.getInt(4);
                iva = rs.getInt(5);
                Precio_Unitario = rs.getInt(6);
                provedor_id = rs.getInt(7);
            }            
        } catch (SQLException e) {
            System.err.println("\nMe llevo la ");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
        }
        Cantidad -= cantidadVendida;
        updateSentence(ids, Nombre, Descripcion, Cantidad, iva, Precio_Unitario, provedor_id);
    }
    
    private void updateSentence(int id, String Nombre, String Descripcion, int Cantidad, int iva, int Precio_Unitario, int provedor_id){
        try {
            	String query = " UPDATE productos SET Nombre = ?, Descripcion = ?, Cantidad = ?, iva = ?, Precio_unitario = ?, Proveedores_id = ? where id = ?";
                PreparedStatement preparedStmt = cn.prepareStatement(query);
                preparedStmt.setString(1, Nombre);
                preparedStmt.setString(2, Descripcion);
                preparedStmt.setString(3, String.valueOf(Cantidad));
                preparedStmt.setString(4, String.valueOf(iva));
                preparedStmt.setString(5, String.valueOf(Precio_Unitario));
                preparedStmt.setString(6, String.valueOf(provedor_id));
                preparedStmt.setInt(7, id);
                preparedStmt.execute();
                cn.close();                
            } catch (SQLException e) {
                System.err.println("\nError!... No se pudo realizar la sentencia");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }finally{
                updateInfoTable1();
            }
    }
    
    private double totales;
    private double totalGen;
    private double ivaGen;
    private double subtotalGen;
    @FXML
    void ingresarProductosVender(ActionEvent event) {
        if(cantidadVender.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Introduce una cantidad de productos");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            if(Integer.parseInt(cantidadVender.getText()) <= 0){
                Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert2.setTitle("Advertencia");
                dialogAlert2.setContentText("La cantidad de productos debe ser mayor a " +Integer.parseInt(cantidadVender.getText()) );
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            }else{
                if(Integer.parseInt(cantidadVender.getText()) <= Integer.parseInt(cantidadProductoVenta.getText().replaceAll(" ", ""))){
                    ids.add(Integer.parseInt(idVender.getText().replaceAll(" ", "")));
                    cantidadVendida.add(Integer.parseInt(cantidadVender.getText()));
                    System.out.println(Integer.parseInt(cantidadVender.getText()));
                    totales = Integer.parseInt(cantidadVender.getText()) * Integer.parseInt(precioUniVender.getText().replaceAll(" ", ""));
                    if(total.getText().equals("")){
                        totalGen = totales;
                    }else{
                        totalGen = Double.valueOf(total.getText()) + totales;
                    }

                    Venta_productos ventapro = new Venta_productos();
                    ventapro.setNombreProducto(productoVender.getText());
                    ventapro.setDescripcion(Descripcionvender.getText());
                    ventapro.setCantidad(Integer.parseInt(cantidadVender.getText()));                
                    ventapro.setPrecioUni(Integer.parseInt(precioUniVender.getText().replaceAll(" ", "")));
                    ventapro.setTotal(totales);

                    ventasPro.add(ventapro);

                    ivaGen = totalGen * .16;
                    subtotalGen = totalGen - ivaGen;
                    total.setText(String.valueOf(totalGen));
                    iva.setText(String.valueOf(ivaGen));
                    subtotal.setText(String.valueOf(subtotalGen));                
                    String query = " INSERT INTO historialventas (Nombre, Descripcion, Precio_Unitario, Cantidad, Total, Fecha, Hora)" +" Values (?, ?, ?, ?, ?, ?, ?)";
                    String fecha = Fecha();
                    String hora = Hora();
                try {
                    PreparedStatement preparedStmt = cn.prepareStatement(query);
                    preparedStmt.setString (1,productoVender.getText());
                    preparedStmt.setString (2,Descripcionvender.getText());
                    preparedStmt.setString(3,precioUniVender.getText());
                    preparedStmt.setString(4, cantidadVender.getText());
                    preparedStmt.setString (5, String.valueOf(totales));
                    preparedStmt.setString(6, fecha);
                    preparedStmt.setString(7, hora);
                    preparedStmt.execute();
                } catch (SQLException e) {
                    System.err.println("\nError!... No se pudo realizar la sentencia");
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
                }finally{
                     updateInfoTable();
                }
                }else{
                    Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                    dialogAlert2.setTitle("Advertencia");
                    dialogAlert2.setContentText("La cantidad de productos Ingresados es mayor a la que hay en stock ");
                    dialogAlert2.initStyle(StageStyle.UTILITY);
                    dialogAlert2.showAndWait();
                }
            }
        }
        productoVender.setText("");
        cantidadVender.setText("");
    }
    
    private void visualizateData(){        
        String sql = "SELECT id, Nombre, Descripcion, Cantidad, Precio_unitario FROM productos WHERE Cantidad >= 1";
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
                ProductosVenta.getColumns().addAll(col);
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
            ProductosVenta.setItems(data);            
        } catch (SQLException e) {
            System.err.println("\nError!!!... sentencia no ejecutada");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
         
         
        ProductosVenta.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(ProductosVenta.getSelectionModel().getSelectedItem() != null) {    
                   System.out.println(newValue);
                   String []values = newValue.toString().replace("[", "").replace("]", "") .split(",");
                   idVender.setText(values[0]);
                   productoVender.setText(values[1]);
                   Descripcionvender.setText(values[2]);
                   precioUniVender.setText(values[4]);
                   cantidadProductoVenta.setText(values[3]);
                }
            }
        });
    }
    
    private void visualizateData1(){
        String fecha = Fecha();        
        String sql = "SELECT Nombre, Descripcion, Precio_unitario, Cantidad, Total FROM historialventas WHERE historialventas.Fecha = '"+ fecha +"' and historialventas.Hora > '"+ hora +"'";
        data1 = FXCollections.observableArrayList();
        
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

                productoVenta.getColumns().addAll(col); 
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
                data1.add(row);
                
            }

            //FINALLY ADDED TO TableView
            
            productoVenta.setItems(data1);
            
        } catch (SQLException e) {
            System.err.println("\nError!!!... sentencia no ejecutada");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static String Fecha(){
         Date fecha = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
         return formato.format(fecha);                  
     }
    
    public static String Hora(){
        Date hora = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(hora);                  
    }
    
    private void updateInfoTable(){
        String fecha = Fecha();        
        String sql = "SELECT Nombre, Descripcion, Precio_unitario, Cantidad, Total FROM historialventas WHERE historialventas.Fecha = '"+ fecha +"' and historialventas.Hora > '"+ hora +"'";        
        data1 = FXCollections.observableArrayList();
        
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
                data1.add(row);
            }
            //FINALLY ADDED TO TableView            
            productoVenta.setItems(data1);
            productoVenta.refresh();
        } catch (Exception e) {
        }
    }
    
    private void updateInfoTable1(){
        String sql = "SELECT id, Nombre, Descripcion, Cantidad, Precio_unitario FROM productos Cantidad >= 1";
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
            
            ProductosVenta.setItems(data);
            ProductosVenta.refresh();
        } catch (Exception e) {
        }                
    }
}
