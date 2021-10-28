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
import library.Musics;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class MainAdminController implements Initializable {

	int n;
	String followName="";
	Boolean isPremium=false;

	@FXML
	private TextField followUserId;

	@FXML
	private TableView<Musics> TableView;

	@FXML
	private TableColumn<Musics, Integer> idColumn;

	@FXML
	private TableColumn<Musics, String> sarkiColumn;

	@FXML
	private TableColumn<Musics, String> sarkiciColumn;

	@FXML
	private TableColumn<Musics, Integer> userIdColumn;

	@FXML
	private Button logoutBtn;

	@FXML
	private Button followBtn;

	@FXML
	private Label errorLabel;

	@FXML
	private void loginButton() throws IOException {
    	//Delete Session
    	SessionMapUser.deleteAllSession();
    	//Get Stage
		Stage stageMain = (Stage) logoutBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginAdmin.fxml"));
		Scene scene = new Scene(parent, 300, 150);
		stage2.setScene(scene);
		stage2.setTitle("User Page");
		stage2.show();
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	followUserId.setText(SessionMapAdmin.getSessionMap("userData").getId()+"");
		System.out.println(SessionMapAdmin.getSessionMap("userData").getName());
		showMusics();
		n = 0;
    }

	@FXML
	private void followButton() throws IOException {
    	n++;
    	if(n%2==1) {
			if (!followUserId.getText().equals("")) {
				System.out.println(followUserId.getText());
				followBtn.setText("Un Follow");
				showMusics();
				if (isPremium) {
					errorLabel.setText(followName + " isimli Admin Takip Edilmeye Başlandı.");
					errorLabel.setStyle("-fx-background-color: #96f196");
				}else{
					errorLabel.setText(followName + " isimli Admin Premium Değil Takip Edilemez.");
					errorLabel.setStyle("-fx-background-color: #f15858");
				}
			}
		}else{
			followUserId.setText(SessionMapAdmin.getSessionMap("userData").getId()+"");
			followBtn.setText("Follow");
			errorLabel.setText("");
			errorLabel.setStyle(null);
			showMusics();
		}
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

	public ObservableList<Musics> getMusicsList(){
		ObservableList<Musics> musicsList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM admin_music";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Musics musics;


			String query2 = "SELECT * FROM users WHERE id='"+followUserId.getText()+"'";
			Statement st2;
			ResultSet rs2;

			st2 = connection.createStatement();
			rs2 = st2.executeQuery(query2);
			rs2.next();
			followName = rs2.getString("name");
			isPremium = rs2.getBoolean("Premium");
			while(rs.next()) {
				musics = new Musics(rs.getInt("Id"),rs.getString("Sarki"),rs.getString("Sarkici"),rs.getInt("Admin_id"));
				if (SessionMapAdmin.getSessionMap("userData").getId()==rs.getInt("Admin_id") || (rs2.getInt("Id")==rs.getInt("Admin_id") && rs2.getBoolean("Premium"))){
					musicsList.add(musics);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return musicsList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showMusics() {
		ObservableList<Musics> list = getMusicsList();

		idColumn.setCellValueFactory(new PropertyValueFactory<Musics,Integer>("id"));
		sarkiColumn.setCellValueFactory(new PropertyValueFactory<Musics,String>("sarki"));
		sarkiciColumn.setCellValueFactory(new PropertyValueFactory<Musics,String>("sarkici"));
		userIdColumn.setCellValueFactory(new PropertyValueFactory<Musics,Integer>("userId"));

		TableView.setItems(list);
	}

}
