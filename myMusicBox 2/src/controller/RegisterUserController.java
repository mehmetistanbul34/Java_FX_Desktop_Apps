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
import library.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class RegisterUserController implements Initializable {

    @FXML
    private TextField idField;

	@FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

	@FXML
	private TextField usernameField;

    @FXML
    private TextField passwordField;

	@FXML
	private TextField latitudeField;

	@FXML
	private TextField longitudeField;

	@FXML
	private TableView<Users> TableView;

	@FXML
	private TableColumn<Users, Integer> idColumn;

	@FXML
	private TableColumn<Users, String> nameColumn;

	@FXML
	private TableColumn<Users, String> surnameColumn;

	@FXML
	private TableColumn<Users, String> usernameColumn;

	@FXML
	private TableColumn<Users, String> passwordColumn;

	@FXML
	private TableColumn<Users, String> latitudeColumn;

	@FXML
	private TableColumn<Users, String> longitudeColumn;

	@FXML
	private Button loginBtn;

	@FXML
	private Button mapBtn;

    @FXML
    private void insertButton() {
    	String query = "insert into users values('"+idField.getText()+"','"+nameField.getText()+"','"+surnameField.getText()+"','"+usernameField.getText()+"','"+passwordField.getText()+"','"+latitudeField.getText()+"','"+longitudeField.getText()+"')";
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
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(parent, 435, 150);
		stage2.setScene(scene);
		stage2.setTitle("User Login Page");
		stage2.show();
	}



	@FXML
	private void setMapButton() throws IOException {
		//Get Stage
		Stage stageMain = (Stage) mapBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(parent, 435, 150);
		stage2.setScene(scene);
		stage2.setTitle("User Login Page");
		stage2.show();
	}

    @FXML 
    private void updateButton() {
		String query = "UPDATE users SET name='"+nameField.getText()+"',surname='"+surnameField.getText()+"',username='"+usernameField.getText()+"',password='"+passwordField.getText()+"',latitude='"+latitudeField.getText()+"',longitude='"+longitudeField.getText()+"' WHERE ID='"+idField.getText()+"'";
		executeQuery(query);
		showUsers();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM users WHERE ID="+idField.getText()+"";
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
    		conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cargo_db","postgres","1111");
    		return conn;
    	}
    	catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public ObservableList<Users> getUsersList(){
    	ObservableList<Users> booksList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM users";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Users users;
			while(rs.next()) {
				users = new Users(rs.getInt("Id"),rs.getString("Name"),rs.getString("Surname"),rs.getString("Username"),rs.getString("Password"),rs.getDouble("Latitude"),rs.getDouble("Longitude"));
				booksList.add(users);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return booksList;
    }

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showUsers() {
		ObservableList<Users> list = getUsersList();


		idColumn.setCellValueFactory(new PropertyValueFactory<Users,Integer>("id"));;
		nameColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("name"));
		surnameColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("surname"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("username"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("password"));
		latitudeColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("latitude"));
		longitudeColumn.setCellValueFactory(new PropertyValueFactory<Users,String>("longitude"));

		TableView.setItems(list);
	}

}
