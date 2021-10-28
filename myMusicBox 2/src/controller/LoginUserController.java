package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LoginUserController implements Initializable {

	@FXML
	private TextField emailField;

	@FXML
	private TextField passwordField;

	@FXML
	private Button registerBtn;

	@FXML
	private Button loginBtn;

	@FXML
	private Button mainBtn;

	@FXML
	private void registerButton() throws IOException {
		//Get Stage
		Stage stageMain = (Stage) registerBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/RegisterUser.fxml"));
		Scene scene = new Scene(parent, 920, 350);
		stage2.setScene(scene);
		stage2.setTitle("Register Admin Page");
		stage2.show();
	}

	@FXML
	private void loginButton() throws IOException {
		Users list = getUsersList();



		if (list!=null) {
			//Get Stage

			SessionMapUser.addSession("userData",list);
			Stage stageMain = (Stage) loginBtn.getScene().getWindow();
			//Close Stage
			stageMain.close();

			Stage stage2 = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("/view/MainUser.fxml"));
			Scene scene = new Scene(parent, 900, 300);
			stage2.setScene(scene);
			stage2.setTitle(SessionMapUser.getSessionMap("userData").getName()+" "+SessionMapUser.getSessionMap("userData").getSurname()+" için Sanatçı Ekleme Sayfası");
			stage2.show();
		}
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

	public Users getUsersList(){
		Connection connection = getConnection();
		String query = "SELECT * FROM users WHERE email='"+emailField.getText()+"' AND password='"+passwordField.getText()+"'";
		Statement st;
		ResultSet rs;
		Users users=null;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {
				users = null;//new Users(rs.getInt("Id"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Email"), rs.getString("Password"),rs.getString("role"),rs.getBoolean("Premium"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return users;
	}

}
