/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import conections.Conexion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Person_system;
import model.Usuarios;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Jose Pablo Sandoval
 */
public class UsuariosController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@FXML
	private Button regresar;

	@FXML
	private Button guardar;

	Person_system person;    

    Usuarios user;

	@FXML
	private TextField nombre;

	@FXML
	private TextField edad;

	@FXML
	private TextField apellidoPaterno;

	@FXML
	private TextField apellidoMaterno;

	@FXML
	private TextField sexo;

	@FXML
	private TextField usuario;

	@FXML
	private PasswordField password;

	@FXML
	private TextField rol;              
        
	@FXML
	private TableView<Usuarios> tableUsers;

	@FXML
	private TableColumn<?, ?> value0;

	@FXML
	private TableColumn<?, ?> value1;

	@FXML
	private TableColumn<?, ?> value2;

	@FXML
	private TableColumn<?, ?> value3;

	@FXML
	private TableColumn<?, ?> value4;

	@FXML
	private TableColumn<?, ?> value5;

	@FXML
	private TableColumn<?, ?> value6;

	@FXML
	private TableColumn<?, ?> value7;

	@FXML
	private TextField search;

	@FXML
	private TextField idOculto;

	@FXML
	private Button borrar;

	@FXML
	private Button editar;

	@FXML
	private Button cancelar;

	@FXML
	private Label title;

	private ObservableList listaUsuarios = FXCollections.observableArrayList();

	@FXML
	public void initialize(URL url, ResourceBundle rb) {
		borrar.setDisable(true);
		editar.setDisable(true);
		cancelar.setDisable(true);
		idOculto.setDisable(true);
		mostrarUsuarios();
	}

	private Conexion cc = new Conexion();
	private Connection cn = cc.conexion();

	@FXML
	void regresar(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Vista_principal.fxml"));
		Object carga = loader.load();
		Parent root = (Parent) carga;
		Scene scene = new Scene(root);
		Vista_principalController controller = loader.<Vista_principalController>getController();
		System.out.println(controller);
		controller.informacion(person);
		stage.setScene(scene);
		stage.show();
		Stage stage1 = (Stage) regresar.getScene().getWindow();
		stage1.close();
	}

	boolean bandData = false;
	boolean band = false;
	int id;
	String name;
	String apPat;
	String apMat;
	int year;
	String sex;
	String username;
	String pass;
	String rl;
	int Usuarios_id;

	@FXML
	void guardar(ActionEvent event) throws Exception {
		cc = new Conexion();
		cn = cc.conexion();
		if (acceso) {
			acceso = false;
			System.out.println(idOculto.getText());
			Statement st = cn.createStatement();
			String query = "SELECT * FROM Login WHERE Usuarios_id = '" + idOculto.getText() + "'";
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt(1);
				pass = rs.getString(3);
				Usuarios_id = rs.getInt(5);
			}
			System.out.println(id);
			if (validateDataInput()) {
				if (validateUser()) {
					try {
						if (Integer.parseInt(edad.getText()) < 0) {
							System.err.println("No se puede");
						} else {
							String sql = "UPDATE Usuarios SET Nombre=?, Apellido_paterno=?, Apellido_materno=?, Edad=?, Sexo=? WHERE id=?";

							PreparedStatement statement = cn.prepareStatement(sql);
							statement.setString(1, nombre.getText());
							statement.setString(2, apellidoPaterno.getText());
							statement.setString(3, apellidoMaterno.getText());
							statement.setString(4, edad.getText());
							statement.setString(5, sexo.getText());
							statement.setInt(6, Usuarios_id);
							int rowsUpdated = statement.executeUpdate();
							if (rowsUpdated > 0) {
								System.out.println("An existing user was updated successfully!");
								String sql2 = "UPDATE Login SET User=?, Pasword=?, Rol=?, Usuarios_id=? WHERE id=?";
								statement = cn.prepareStatement(sql2);
								statement.setString(1, usuario.getText());
								statement.setString(2, password.getText());
								statement.setString(3, rol.getText());
								statement.setInt(4, Usuarios_id);
								statement.setInt(5, id);
								statement.executeUpdate();
								alertSucces();
								clearInformation();
								cancelar.setDisable(true);
								title.setText("Registro de usuarios");
								mostrarUsuarios();
							} else {
								alertError();
								cn.close();
							}

						}
					} catch (Exception e) {
						acceso = true;
						alertErrorYear();
						cn.close();
					}
				} else {
					acceso = true;
					alertTypeUser();
					cn.close();
				}
			} else {
				acceso = true;
				alertEmptyInput();
				cn.close();
			}

		} else if (validateDataInput()) {
			if (validateUser()) {
				try {
					if (Integer.parseInt(edad.getText()) < 0) {
						System.err.println("No se puede");
					} else {
						String sql = "INSERT INTO Usuarios (Nombre,Apellido_Paterno,Apellido_materno,Edad,Sexo) VALUES ('"
								+ nombre.getText() + "','" + apellidoPaterno.getText() + "','"
								+ apellidoMaterno.getText() + "','" + edad.getText() + "','" + sexo.getText() + "')";
						try {
							Statement st = cn.createStatement();
							st.executeUpdate(sql);
							String query = "SELECT * FROM Usuarios WHERE Usuarios.Nombre = '" + nombre.getText() + "'";
							st = cn.createStatement();
							ResultSet rs = st.executeQuery(query);
							while (rs.next()) {
								id = rs.getInt(1);
								name = rs.getString(2);
								apPat = rs.getString(3);
								apMat = rs.getString(4);
								year = rs.getInt(5);
								sex = rs.getString(6);
								band = true;
							}
							if (band = true) {
								String sql2 = "INSERT INTO Login (User,Pasword,Rol,Usuarios_id) VALUES ('"
										+ usuario.getText() + "','" + password.getText() + "','" + rol.getText() + "','"
										+ id + "')";
								st.executeUpdate(sql2);
								String query2 = "SELECT * FROM Login";
								st = cn.createStatement();
								rs = st.executeQuery(query2);
								band = false;
								clearInformation();
								alertSucces();
								mostrarUsuarios();
							} else {
								alertError();
								cn.close();
							}
						} catch (SQLException e) {
							alertError();
							cn.close();
						}
					}
				} catch (Exception e) {
					alertErrorYear();
					cn.close();
				}
			} else {
				alertTypeUser();
				cn.close();
			}

		} else {
			alertEmptyInput();
			cn.close();
		}

	}

	@SuppressWarnings("unchecked")
	public void mostrarUsuarios() {
		listaUsuarios.clear();
		try {                                        
			String sql = "SELECT Login.id, Login.User, Nombre, Apellido_paterno, Apellido_materno, Edad, Sexo, Rol FROM Usuarios INNER JOIN Login ON Usuarios.id = Login.Usuarios_id";
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			System.out.println(rs);
			while (rs.next()) {
				id = rs.getInt(1);
				username = rs.getString(2);
				name = rs.getString(3);
				apPat = rs.getString(4);
				apMat = rs.getString(5);
				year = rs.getInt(6);
				sex = rs.getString(7);
				rl = rs.getString(8);
				listaUsuarios.addAll(new Usuarios(id, username, name, apPat, apMat, year, sex, rl));
			}
			tableUsers.setColumnResizePolicy(tableUsers.CONSTRAINED_RESIZE_POLICY);

			TableColumn<Usuarios, Number> value0 = new TableColumn<>("Id");
			value0.setCellValueFactory(c -> c.getValue().getId());

			TableColumn<Usuarios, String> value1 = new TableColumn<>("Usuario");
			value1.setCellValueFactory(c -> c.getValue().getUsuario());

			TableColumn<Usuarios, String> value2 = new TableColumn<>("Nombre");
			value2.setCellValueFactory(c -> c.getValue().getNameProperty());

			TableColumn<Usuarios, String> value3 = new TableColumn<>("Apellido Paterno");
			value3.setCellValueFactory(c -> c.getValue().getApellidoPaterno());

			TableColumn<Usuarios, String> value4 = new TableColumn<>("Apellido Materno");
			value4.setCellValueFactory(c -> c.getValue().getApellidoMaterno());

			TableColumn<Usuarios, Number> value5 = new TableColumn<>("Edad");
			value5.setCellValueFactory(c -> c.getValue().getEdad());

			TableColumn<Usuarios, String> value6 = new TableColumn<>("Sexo");
			value6.setCellValueFactory(c -> c.getValue().getSexo());

			TableColumn<Usuarios, String> value7 = new TableColumn<>("Rol");
			value7.setCellValueFactory(c -> c.getValue().getRole());

			tableUsers.getColumns().clear();
			tableUsers.getColumns().add(value0);
			tableUsers.getColumns().add(value1);
			tableUsers.getColumns().add(value2);
			tableUsers.getColumns().add(value3);
			tableUsers.getColumns().add(value4);
			tableUsers.getColumns().add(value5);
			tableUsers.getColumns().add(value6);
			tableUsers.getColumns().add(value7);

			FilteredList<Usuarios> filteredData = new FilteredList<>(listaUsuarios, p -> true);

			SortedList<Usuarios> sortedData = new SortedList<>(filteredData);
			sortedData.comparatorProperty().bind(tableUsers.comparatorProperty());

			tableUsers.setItems(sortedData);

			search.textProperty().addListener((prop, old, text) -> {
				filteredData.setPredicate(user -> {
					if (text == null || text.isEmpty()) {
						return true;
					}
					String name = user.getName().toLowerCase();
					return name.contains(text.toLowerCase());
				});
			});
			cn.close();
		} catch (Exception e) {
			System.err.println("\nMe llevo la ");
			Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	boolean band2 = false;

	@FXML
	void borrar(ActionEvent event) throws SQLException {
		cc = new Conexion();
		cn = cc.conexion();
		try {
			String auxUser = person.getUser();
			Usuarios user = (Usuarios) this.tableUsers.getSelectionModel().getSelectedItem();
			StringProperty valorUsuario = user.getUsuario();
			String auxValorUsuario = valorUsuario.get();
			if (auxUser.equals(auxValorUsuario)) {
				Alert alerta = new Alert(Alert.AlertType.INFORMATION);
				alerta.setTitle("Advertencia");
				alerta.setContentText("No se puede eliminar uno mismo");
				alerta.initStyle(StageStyle.UTILITY);
				alerta.setHeaderText(null);
				alerta.showAndWait();

			} else {
				try {
					IntegerProperty id = user.getId();
					int idAux = id.get();
					String sql = "SELECT * FROM Login WHERE id=" + "'" + idAux + "'";
					String sql2 = "DELETE FROM Login WHERE id=" + "'" + idAux + "'";
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery(sql);
					while (rs.next()) {
						idAux = rs.getInt(1);
						username = rs.getString(2);
						pass = rs.getString(3);
						rl = rs.getString(4);
						Usuarios_id = rs.getInt(5);
						band2 = true;
					}
					if (band2) {
						PreparedStatement preparedStmt = cn.prepareStatement(sql2);
						preparedStmt.execute();
						String sql3 = "DELETE FROM Usuarios WHERE Usuarios.id=" + "'" + Usuarios_id + "'";
						preparedStmt = cn.prepareStatement(sql3);
						preparedStmt.execute();
						alertSucces();
						borrar.setDisable(true);
						editar.setDisable(true);
						cancelar.setDisable(true);
						guardar.setDisable(false);
						band2 = false;
						clearInformation();
						mostrarUsuarios();
					} else {
						alertError();
						cn.close();
					}
				} catch (Exception e) {
					cn.close();
					alertError();
				}
			}
		} catch (Exception e) {
			cn.close();
			alertNoSelectUser();
		}
	}

	boolean acceso = false;

	@FXML
	void editar(ActionEvent event) throws Exception {
		guardar.setDisable(false);
		title.setText("Editar Usuario");
		try {
			Usuarios user = (Usuarios) this.tableUsers.getSelectionModel().getSelectedItem();
			acceso = true;
			int idFactory = user.getId().get();
			String id = "" + idFactory;
			System.out.println(id);
			idOculto.setText((id));
			nombre.setText(user.getName());
			apellidoPaterno.setText(user.getApellidoPaterno().get());
			apellidoMaterno.setText(user.getApellidoMaterno().get());
			edad.setText("" + user.getEdad().get());
			sexo.setText(user.getSexo().get());
			usuario.setText(user.getUsuario().get());
			rol.setText(user.getRole().get());
			borrar.setDisable(true);
			editar.setDisable(true);
		} catch (Exception e) {
			guardar.setDisable(true);
			title.setText("Registro de usuarios");
			alertNoSelectUser();
		}

	}

	@FXML
	void presionado(MouseEvent event) {
		guardar.setDisable(true);
		borrar.setDisable(false);
		editar.setDisable(false);
		cancelar.setDisable(false);
	}

	@FXML
	void cancelar(ActionEvent event) {
		title.setText("Registro de usuarios");
		acceso = false;
		guardar.setDisable(false);
		borrar.setDisable(true);
		editar.setDisable(true);
		cancelar.setDisable(true);
		tableUsers.getSelectionModel().clearSelection();
		clearInformation();
	}

	public void clearInformation() {
		nombre.clear();
		edad.clear();
		apellidoPaterno.clear();
		apellidoMaterno.clear();
		sexo.clear();
		usuario.clear();
		password.clear();
		rol.clear();
	}

	public void alertNoSelectUser() {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		alerta.setTitle("WARNING");
		alerta.setContentText("Parece que no selecciono un usuario");
		alerta.initStyle(StageStyle.UTILITY);
		alerta.setHeaderText(null);
		alerta.showAndWait();
	}

	public void alertError() {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle("ERROR");
		alerta.setContentText("Ocurrio un error en la operacion");
		alerta.initStyle(StageStyle.UTILITY);
		alerta.setHeaderText(null);
		alerta.showAndWait();
	}

	public void alertErrorYear() {
		Alert dialogAlert2 = new Alert(Alert.AlertType.ERROR);
		dialogAlert2.setTitle("ERROR");
		dialogAlert2.setContentText("La edad no puede incluir caracteres");
		dialogAlert2.initStyle(StageStyle.UTILITY);
		dialogAlert2.showAndWait();
	}

	public void alertSucces() {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle("Completado");
		alerta.setContentText("Operacion completada con exito");
		alerta.initStyle(StageStyle.UTILITY);
		alerta.setHeaderText(null);
		alerta.showAndWait();
	}

	public void alertEmptyInput() {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		alerta.setTitle("WARNING");
		alerta.setContentText("Hay campos vacios vuelva a verificar");
		alerta.initStyle(StageStyle.UTILITY);
		alerta.setHeaderText(null);
		alerta.showAndWait();
	}

	public void alertTypeUser() {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle("ERROR");
		alerta.setContentText("El rol solo puede ser Admin o Cajero");
		alerta.initStyle(StageStyle.UTILITY);
		alerta.setHeaderText(null);
		alerta.showAndWait();
	}

	public boolean validateUser() {
		System.out.println(usuario.getText());
		if (rol.getText().equals("Admin") || rol.getText().equals("Cajero")) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}

	public boolean validateDataInput() {
		if (!nombre.getText().equals("") && !apellidoPaterno.getText().equals("")
				&& !apellidoMaterno.getText().equals("") && !edad.getText().equals("") && !sexo.getText().equals("")
				&& !usuario.getText().equals("") && !password.getText().equals("") && !rol.getText().equals("")) {
			bandData = true;
		} else {
			bandData = false;
		}
		System.out.println(bandData);
		return bandData;
	}
    
    public void informacion(Person_system person) {
        this.person = person;
    }

}