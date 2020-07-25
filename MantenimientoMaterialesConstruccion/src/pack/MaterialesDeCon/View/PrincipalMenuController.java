package pack.MaterialesDeCon.View;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pack.MaterialesDeCon.Main;

public class PrincipalMenuController {
	Main main;
	@FXML
    private JFXButton productos;

    @FXML
    private JFXButton usuario;

    @FXML
    private JFXButton regisUser;

    @FXML
    private JFXButton infoUser;

    @FXML
    private JFXButton cemento;

    @FXML
    private JFXButton madera;

    @FXML
    private JFXButton acero;

    @FXML
    private JFXButton azulejo;

    @FXML
    private JFXButton pintura;

    @FXML
    private JFXButton lamina;
    @FXML
    private JFXButton registroProducto;
    @FXML
    private JFXButton compra;
    @FXML
    private JFXButton caja;
    @FXML
    private JFXButton seeSuppiler;
    @FXML
    private JFXButton regSuppiler;
   
 
    static float cantidadInicial= Float.parseFloat(JOptionPane.showInputDialog(null, "Inserte el ingreso del dia: "));
	
	public void cagarMenuUsuario(ActionEvent event) {
		verficarBotones();
		regisUser.setVisible(true);
		infoUser.setVisible(true);
	}
	
	public void cagarProductos(ActionEvent event) {
		verficarBotones();
		registroProducto.setVisible(true);
		acero.setVisible(true);
		madera.setVisible(true);
		azulejo.setVisible(true);
		cemento.setVisible(true);
		pintura.setVisible(true);
		lamina.setVisible(true);
	}
	
	public void cagarCaja(ActionEvent event) {
		verficarBotones();
		compra.setVisible(true);
		caja.setVisible(true);
	}
	
	public void cagarProveedores(ActionEvent event) {
		verficarBotones();
		regSuppiler.setVisible(true);
		seeSuppiler.setVisible(true);
	}
	
	@FXML
	void cagarRegisProduc(ActionEvent event) {
		main.cagarRegistroProducto();
	}
	
	@FXML
	void cagarListaMateriales(ActionEvent event) {
		main.cargarListaMateriales();
	}
	
	@FXML
	void cargarVenta(ActionEvent event) {
		Stage s = (Stage)compra.getScene().getWindow();
    	s.close();
		main.cargarVenta();
	}
	
	@FXML
	void cargarCaja(ActionEvent event) {
		main.cagarCorteCaja();
	}
	
	
	public void verficarBotones() {
		regisUser.setVisible(false);
		infoUser.setVisible(false);
		acero.setVisible(false);
		madera.setVisible(false);
		azulejo.setVisible(false);
		cemento.setVisible(false);
		pintura.setVisible(false);
		lamina.setVisible(false);
		registroProducto.setVisible(false);
		seeSuppiler.setVisible(false);
		regSuppiler.setVisible(false);
		caja.setVisible(false);
		compra.setVisible(false);
	}
	
	public Float ingresoInicial() {
		return cantidadInicial;
	}

	public void setMain(Main main) {
		this.main= main;
	}
}
