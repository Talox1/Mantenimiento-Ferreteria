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
import conections.Conexion;
import static controller.VentasController.Fecha;
import static controller.VentasController.Hora;
import static controller.VentasController.Hora1;
import far_system.LoginController;
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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Person_system;
import model.Productos_Ven;
import model.Venta_productos;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class ReportesVentaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private DatePicker fechaIni;

    @FXML
    private DatePicker fechaFin;


    @FXML
    private TableView tablaReporte;

    @FXML
    private Button reporteGen;
    
    private ObservableList<ObservableList> data;
    Person_system person;
    private Conexion cc = new Conexion();
    private Connection cn= cc.conexion();
    ArrayList<Productos_Ven> productos = new ArrayList<Productos_Ven>();
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        visualizateData();
        reporteGen.setDisable(true);
    }    
    public void informacion(Person_system person){                
        this.person = person;
    }
    boolean pasa = false;
    @FXML
    void filtrar(ActionEvent event) {
        String sql = "";
        String query = " Where ";
        boolean largeQuery = false;                
        if(fechaIni.getValue().equals("") && fechaFin.getValue().equals("") ){
            sql = "SELECT id, Nombre, Cantidad, Precio_unitario, Total, Fecha, Hora FROM farmacia.historialventas";
            pasa = true;
        }else{
            if((fechaIni.getValue().equals("") && !fechaFin.getValue().equals("")) || !fechaIni.getValue().equals("") && fechaFin.getValue().equals("")){
                Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert2.setTitle("Advertencia");
                dialogAlert2.setContentText("Los campos de fecha se deben llenar obligatoriamente");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            }else {
                if(!fechaIni.getValue().equals("") && !fechaFin.getValue().equals("")){
                    String[] a1 = fechaFin.getValue().toString().split("-");
                    String[] b1 = fechaIni.getValue().toString().split("-");
                    LocalDate a = LocalDate.of(Integer.parseInt(a1[0]), Integer.parseInt(a1[1]), Integer.parseInt(a1[2]));
                    LocalDate b = LocalDate.of(Integer.parseInt(b1[0]), Integer.parseInt(b1[1]), Integer.parseInt(b1[2]));
                    if(b.isBefore(a) || b.isEqual(a)){
                        reporteGen.setDisable(false);
                        if(!largeQuery){
                            query += " historialventas.fecha >= '"+ fechaIni.getValue()+"'";
                            query += " and historialventas.fecha <= '"+fechaFin.getValue()+"'";
                        }
                        largeQuery = true;
                        pasa = true;
                        sql = "SELECT id, Nombre, Cantidad, Precio_unitario, Total, Fecha, Hora FROM farmacia.historialventas" + query;
                    }else{
                        Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                        dialogAlert2.setTitle("Advertencia");
                        dialogAlert2.setContentText("La fecha final debe ser mayor o igual a la fecha inicial");
                        dialogAlert2.initStyle(StageStyle.UTILITY);
                        dialogAlert2.showAndWait();  
                    }
                }
            }
        }
        if(pasa){        
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
                    Productos_Ven ventapro = new Productos_Ven();
                    ventapro.setNombreProducto(row.get(1));
                    ventapro.setCantidad(Integer.parseInt(row.get(2)));
                    ventapro.setPrecioUni(Integer.parseInt(row.get(3)));
                    ventapro.setTotal(Integer.parseInt(row.get(4)));
                    ventasTot += Integer.parseInt(row.get(4));
                    ventapro.setFecha(row.get(5));
                    ventapro.setHora(row.get(6));
                    productos.add(ventapro);
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                tablaReporte.getItems().clear();
                tablaReporte.setItems(data);

            } catch (SQLException e) {
                System.err.println("\nError!!!... sentencia no ejecutada");
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
            }
        }            
    }

    @FXML
    void generarReporte(ActionEvent event) throws FileNotFoundException, DocumentException {
        generarPdf();
        productos.clear();
        reporteGen.setDisable(true);
        pasa = false;
    }
     private void generarPdf()throws FileNotFoundException, DocumentException{
        String sSistemaOperativo = System.getProperty("os.name");
        System.out.println(sSistemaOperativo);
        File directorio = null;
        File directorio1 = null;
        String fecha = Fecha();
        String hora = Hora1();            
        FileOutputStream archivo = null;
        if(sSistemaOperativo.equals("Windows 10") || sSistemaOperativo.equals("Windows 7") || sSistemaOperativo.equals("Windows 8") || sSistemaOperativo.equals("Windows Xp")){
            directorio = new File("C:\\pdfs");
            directorio1 = new File("C:\\pdfs\\reportes");            
        }else{
            directorio = new File("/home/pdfs");
            directorio1 = new File("/home/pdfs/reportes");            
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
            archivo = new FileOutputStream(new File("C:\\pdfs\\reportes\\reporte" + "_" + fecha + "_" +hora + ".pdf"));
        }else{
            archivo = new FileOutputStream(new File("/home/pdfs/reportes/reporte" + "_" + fecha + "_" +hora + ".pdf"));
        }
            Document docto = new Document();        
            PdfWriter.getInstance(docto, archivo);
            docto.open();
            Paragraph parrafo = new Paragraph("-------------- Farmacia el Fenix --------------" + "\n Fecha: " +Fecha() + "\n Hora actual: " + Hora() + "\n Telefono: (961)2594528" + "\n Empleado que saco el reporte: " + person.getNombre() + "\n Rol de empleado: " + person.getRol() + "\n Sucursal: 9a sur poniente 630");
            parrafo.setAlignment(1);
            docto.add(parrafo);
            docto.add(new Paragraph("\nProducto            Precio Unitario                Cantidad               Total           Fecha           Hora"));
            for (int i = 0; i < productos.size(); i++) {                
                docto.add(new Paragraph(productos.get(i).getNombreProducto() + "                 " + productos.get(i).getPrecioUni() + "                  "  +productos.get(i).getCantidad() + "                 "  + productos.get(i).getTotal() + "          " + productos.get(i).getFecha() + "           " + productos.get(i).getHora()));
            }                        
            docto.add(new Paragraph("\n Ventas Totales:  " + ventasTot ));
            docto.close();           
            //System.out.println(Fecha());
            this.abrir("reporte",fecha, hora);
            
    }
    
    public void abrir(String nombre,String fecha, String hora){
        File path = null;
        String sSistemaOperativo = System.getProperty("os.name");
        System.out.println(sSistemaOperativo);
        try {
            if(sSistemaOperativo.equals("Windows 10") || sSistemaOperativo.equals("Windows 7") || sSistemaOperativo.equals("Windows 8") || sSistemaOperativo.equals("Windows Xp")){
                path = new File("C:\\pdfs\\reportes\\" + nombre + "_" + fecha + "_" + hora + ".pdf");
            }else{
                path = new File("/home/pdfs/reportes/" + nombre + "_" + fecha + "_" + hora + ".pdf");
            }
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
            Logger.getLogger(VentasController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
     public static String Hora1(){
         Date fecha = new Date();
         SimpleDateFormat formato = new SimpleDateFormat("HH-mm-ss");
         return formato.format(fecha);                  
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
        Stage stage1 = (Stage) fechaIni.getScene().getWindow();
        stage1.close();

    }
    
    int ventasTot = 0;
     private void visualizateData(){        
        String sql = "SELECT id, Nombre, Cantidad, Precio_unitario, Total, Fecha, Hora FROM farmacia.historialventas";
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
                tablaReporte.getColumns().addAll(col);
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
            tablaReporte.setItems(data);            
        } catch (SQLException e) {
            System.err.println("\nError!!!... sentencia no ejecutada");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }                          
    }
    
}
