package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import library.Singer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ListeleController implements Initializable {

	int n;
	String followName="";
	Boolean isPremium=false;

	@FXML
	private Button sarkiBtn;

	@FXML
	private TextField followUserId;

	@FXML
	private TableView<Singer> TableView;

	@FXML
	private TableColumn<Singer, Integer> idColumn;

	@FXML
	private TableColumn<Singer, String> nameColumn;

	@FXML
	private TableColumn<Singer, String> countryColumn;

	@FXML
	private TableColumn<Singer, Integer> userIdColumn;

	@FXML
	private TableColumn<Singer, Void> buttonColumn;

	@FXML
	private Button logoutBtn;

	@FXML
	private Button listeBtn;

	@FXML
	private Button followBtn;

	@FXML
	private Label errorLabel;

	@FXML
	private void loginButton() throws IOException {
		//Get Stage

		Stage stageMain = (Stage) logoutBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/MainUser.fxml"));
		Scene scene = new Scene(parent, 900, 300);
		stage2.setScene(scene);
		stage2.setTitle("Singer Page");
		stage2.show();
	}

	@FXML
	private void listeleButton() throws IOException {
		//Get Stage
		Stage stageMain = (Stage) listeBtn.getScene().getWindow();
		//Close Stage
		stageMain.close();

		Stage stage2 = new Stage();
		Parent parent = FXMLLoader.load(getClass().getResource("/view/MainUser.fxml"));
		Scene scene = new Scene(parent, 900, 300);
		stage2.setScene(scene);
		stage2.setTitle("Listele Page");
		stage2.show();
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
		followUserId.setText(SessionMapUser.getSessionMap("userData").getId()+"");
		System.out.println(SessionMapUser.getSessionMap("userData").getName());
		System.out.println(SessionMapUser.getSessionMap("userData").getName());
		showMusics();
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
			followUserId.setText(SessionMapUser.getSessionMap("userData").getId()+"");
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

	public ObservableList<Singer> getSingerList(){
		ObservableList<Singer> singerList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM singer";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Singer singer;


			String query2 = "SELECT * FROM users WHERE id='"+followUserId.getText()+"'";
			Statement st2;
			ResultSet rs2;

			st2 = connection.createStatement();
			rs2 = st2.executeQuery(query2);
			rs2.next();
			followName = rs2.getString("name");
			isPremium = rs2.getBoolean("Premium");

			while(rs.next()) {
				singer = new Singer(rs.getInt("Id"),rs.getString("Name"),rs.getString("Country"),rs.getInt("User_id"), "Button"+rs.getInt("Id"));
				if (SessionMapUser.getSessionMap("userData").getId()==rs.getInt("User_id") /*|| (rs2.getInt("Id")==rs.getInt("User_id") && rs2.getBoolean("Premium"))*/){
					singerList.add(singer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singerList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showMusics() {
		ObservableList<Singer> list = getSingerList();


		idColumn.setCellValueFactory(new PropertyValueFactory<Singer,Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Singer,String>("Name"));
		countryColumn.setCellValueFactory(new PropertyValueFactory<Singer,String>("Country"));
		userIdColumn.setCellValueFactory(new PropertyValueFactory<Singer,Integer>("UserId"));



		Callback<TableColumn<Singer, Void>, TableCell<Singer, Void>> cellFactory = new Callback<TableColumn<Singer, Void>, TableCell<Singer, Void>>() {
			@Override
			public TableCell<Singer, Void> call(final TableColumn<Singer, Void> param) {
				final TableCell<Singer, Void> cell = new TableCell<Singer, Void>() {

					private final Button btn = new Button("Şarkı Ekle");

					{
						btn.setOnAction((ActionEvent event) -> {
							Singer data = getTableView().getItems().get(getIndex());
							System.out.println("selectedData: " + data.getName()+","+data.getId());

							//Get Stage
							//SessionMapUser.addSession("userData",list);
							SessionMapSinger.addSession("singerData",data);
							System.out.println("singer: " + SessionMapSinger.getSessionMap("singerData").getName());
							Stage stageMain = (Stage) btn.getScene().getWindow();
							//Close Stage
							stageMain.close();

							Stage stage2 = new Stage();
							Parent parent = null;
							try {
								parent = FXMLLoader.load(getClass().getResource("/view/SongUser.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							Scene scene = new Scene(parent, 1400, 700);
							stage2.setScene(scene);
							stage2.setTitle("Song Page");
							stage2.show();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		buttonColumn.setCellFactory(cellFactory);

		TableView.setItems(list);
	}

}
