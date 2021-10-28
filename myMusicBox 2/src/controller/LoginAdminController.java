package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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


public class LoginAdminController implements Initializable {

	@FXML
	private TextField emailField;

	@FXML
	private TextField passwordField;

	@FXML
	private Button loginBtn;

	@FXML
	private Button mainBtn;

	@FXML
	private void loginButton() throws IOException {
		Users list = null;

		if (list!=null) {
			SessionMapAdmin.addSession("userData",list);
			//Get Stage
			Stage stageMain = (Stage) loginBtn.getScene().getWindow();
			//Close Stage
			stageMain.close();

			Stage stage = new Stage();
			Parent parent = FXMLLoader.load(getClass().getResource("/view/MainAdmin.fxml"));
			Scene scene = new Scene(parent, 680, 300);
			stage.setScene(scene);
			stage.setTitle("Anasayfa User");
			stage.show();
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

	public Admin getBooksList(){
		Connection connection = getConnection();
		String query = "SELECT * FROM admin WHERE email='"+emailField.getText()+"' AND password='"+passwordField.getText()+"'";
		Statement st;
		ResultSet rs;
		Admin admin=null;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			if(rs.next()) {
				admin = new Admin(rs.getInt("Id"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Email"), rs.getString("Password"),rs.getString("role"),rs.getBoolean("Premium"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

}
