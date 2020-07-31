/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package const_system;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import conections.Conexion;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conections.Conectar;
import conections.Conection;
import controller.Vista_principalController;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Person_system;

/**
 *
 * @author Jose Pablo Sandoval
 */
public class LoginController implements Initializable {

    @FXML    private JFXTextField user;
    @FXML    private JFXPasswordField password;
    @FXML    private Button sing_in;    
    @FXML    private Button out;  
    
    private Conexion cc = new Conexion();
    private Connection cn= cc.conexion();    
    Person_system person = new Person_system();
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {                	                
    }
    
    @FXML
    void out(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void sign_in(ActionEvent event) throws IOException {
        if(password.getText().equals("") || user.getText().equals("")){
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Hay Campos Vacios");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }else{
            logear(user.getText(), password.getText());
        }
    }
    
    @FXML
    void sign_in(KeyEvent event) throws IOException {
        if(event.getCode().toString().equals("ENTER")){
            if(password.getText().equals("") || user.getText().equals("")){
                Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert2.setTitle("Advertencia");
                dialogAlert2.setContentText("Hay Campos Vacios");
                dialogAlert2.initStyle(StageStyle.UTILITY);
                dialogAlert2.showAndWait();
            }else{
                logear(user.getText(), password.getText());
            }
        }

    }
    
    int id;
    String User;
    String Nombre;
    String ApellidoP;
    String ApellidoM;
    String Log;
    String Rol;
    public void logear(String user, String password) throws IOException{
        System.out.println(password + user);
        String passDb = null;
        //select login.id, Nombre, Apellido_paterno, Apellido_materno, login.User,Rol from usuarios inner join login on usuarios.id = login.Usuarios_id where usuarios.id = 1
        String sql = "SELECT login.id, Nombre, Apellido_paterno, Apellido_materno, Login.User,Rol, login.Pasword FROM usuarios INNER JOIN login ON usuarios.id = login.Usuarios_id WHERE login.User =  '"+ user +"'";
        try {            
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            System.out.println(rs);
            while(rs.next()){
                id = rs.getInt(1);
                Nombre = rs.getString(2);
                ApellidoP = rs.getString(3);
                ApellidoM = rs.getString(4);
                User = rs.getString(5);
                Rol = rs.getString(6);
                passDb = rs.getString(7);
            }            
        } catch (SQLException e) {
            System.err.println("\nMe llevo la ");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);            
        }
        
        if(passDb.equals(password)){
            Stage stage = new Stage();            
            person.setNombre(Nombre + " " + ApellidoP + " " +ApellidoM);
            person.setUser(User);
            person.setRol(Rol);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/Vista_principal.fxml"));
            Object carga = loader.load();
            Parent root = (Parent) carga;
            Scene scene = new Scene(root);            
            Vista_principalController controller = loader.<Vista_principalController>getController();
            controller.informacion(person);
            stage.setScene(scene);
            stage.show();                                                                   
            Stage stage1 = (Stage) out.getScene().getWindow();
            stage1.close();
        }else{
            Alert dialogAlert2 = new Alert(Alert.AlertType.WARNING);
            dialogAlert2.setTitle("Advertencia");
            dialogAlert2.setContentText("Contraseña Incorrecta");
            dialogAlert2.initStyle(StageStyle.UTILITY);
            dialogAlert2.showAndWait();
        }
    }
    
}
