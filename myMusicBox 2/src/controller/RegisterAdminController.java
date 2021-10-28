package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.Admin;
import library.Album;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterAdminController implements Initializable {

    @FXML
    private TextField idField;

	@FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

	@FXML
	private CheckBox premiumField;

    @FXML
    private TableView<Admin> TableView;

    @FXML
    private TableColumn<Admin, Integer> idColumn;

    @FXML
    private TableColumn<Admin, String> nameColumn;

    @FXML
    private TableColumn<Admin, String> surnameColumn;

    @FXML
    private TableColumn<Admin, String> emailColumn;

    @FXML
    private TableColumn<Admin, String> passwordColumn;

	@FXML
	private TableColumn<Admin, String> roleColumn;

	@FXML
	private TableColumn<Admin, Boolean> premiumColumn;

	@FXML
	private Button loginBtn;

	@FXML
	private Button mainBtn;

    @FXML
    private void insertButton() {
    	String query = "insert into admin values('"+idField.getText()+"','"+nameField.getText()+"','"+surnameField.getText()+"','"+emailField.getText()+"','"+passwordField.getText()+"','admin','"+premiumField.isSelected()+"')";
    	executeQuery(query);
    	showUsers();
    }

	@FXML
	private void loginButton() throws IOException {
    	//Get Stage
		Stage stageMain = (Stage) loginBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginAdmin.fxml"));
		Scene scene = new Scene(parent, 300, 150);
		stage2.setScene(scene);
		stage2.setTitle("Login Admin Page");
		stage2.show();
	}

	@FXML
	private void mainButton() throws IOException {
		//Get Stage
		Stage stageMain = (Stage) mainBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(parent, 400, 100);
		stage.setScene(scene);
		stage.setTitle("Select Page");
		stage.show();
	}
    
    
    @FXML 
    private void updateButton() {
    String query = "UPDATE admin SET name='"+nameField.getText()+"',surname='"+surnameField.getText()+"',email='"+emailField.getText()+"',password='"+passwordField.getText()+"',premium='"+premiumField.isSelected()+"' WHERE ID='"+idField.getText()+"'";
    executeQuery(query);
	showUsers();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM admin WHERE ID="+idField.getText()+"";
    	executeQuery(query);
    	showUsers();
    }
    
    public void executeQuery(String query) {
    	Connection conn = getConnection();
    	Statement st;
    	try {
			st = conn.createStatement();
			st.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	showUsers();
    }
    
    public Connection getConnection() {
    	Connection conn;
    	try {
    		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test_db","postgres","1111");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ObservableList<Admin> getUsersList(){
    	ObservableList<Admin> adminList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM admin";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Admin admin;
			while(rs.next()) {
				admin = new Admin(rs.getInt("Id"),rs.getString("Name"),rs.getString("Surname"),rs.getString("Email"),rs.getString("Password"),rs.getString("role"),rs.getBoolean("premium"));
				adminList.add(admin);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return adminList;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showUsers() {
    	ObservableList<Admin> list = getUsersList();

    	idColumn.setCellValueFactory(new PropertyValueFactory<Admin,Integer>("id"));
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Admin,String>("name"));
    	surnameColumn.setCellValueFactory(new PropertyValueFactory<Admin,String>("surname"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<Admin,String>("email"));
    	passwordColumn.setCellValueFactory(new PropertyValueFactory<Admin,String>("password"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<Admin,String>("role"));
		premiumColumn.setCellValueFactory(new PropertyValueFactory<Admin,Boolean>("premium"));

		TableView.setItems(list);
    }

}
