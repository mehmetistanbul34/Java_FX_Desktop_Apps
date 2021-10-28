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
import library.Song;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class SongUserController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

	@FXML
	private TextField dateField;

	@FXML
	private TextField typeField;

	@FXML
	private TextField timeField;

	@FXML
	private TableView<Song> TableView;

	@FXML
	private TableColumn<Song, Integer> idColumn;

	@FXML
	private TableColumn<Song, String> nameColumn;

	@FXML
	private TableColumn<Song, String> dateColumn;

	@FXML
	private TableColumn<Song, Integer> singerIdColumn;

	@FXML
	private TableColumn<Song, Integer> albumIDColumn;

	@FXML
	private TableColumn<Song, String> typeColumn;

	@FXML
	private TableColumn<Song, String> timeColumn;

	@FXML
	private TableColumn<Song, Integer> countColumn;

	@FXML
	private TableColumn<Song, Void> buttonColumn;

	@FXML
	private Button backToSingerBtn;

	@FXML
	private Label errorLabel;

    @FXML
    private void insertButton() {
    	String query = "insert into song values('"+idField.getText()+"','"+nameField.getText()+"','"+dateField.getText()+"','"+SessionMapSinger.getSessionMap("singerData").getId()+"',"+null+",'"+typeField.getText()+"','"+timeField.getText()+"')";
    	executeQuery(query);
    	showMusics();
		errorLabel.setText(SessionMapSinger.getSessionMap("singerData").getName()+"'in "+idField.getText()+" id'li"+" Şarkısı başarılı bir şekilde EKLENDİ.");
		errorLabel.setStyle("-fx-background-color: #96f196");
    }

	@FXML
	private void loginButton() throws IOException {
    	//Delete Session
    	SessionMapSinger.deleteAllSession();
    	//Get Stage
		Stage stageMain = (Stage) backToSingerBtn.getScene().getWindow();
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
    private void updateButton() {
		String query = "UPDATE song SET name='"+nameField.getText()+"',date='"+dateField.getText()+"',singer_id='"+SessionMapSinger.getSessionMap("singerData").getId()+"',type='"+typeField.getText()+"',time='"+timeField.getText()+"' WHERE ID='"+idField.getText()+"'";
		executeQuery(query);
		showMusics();
		errorLabel.setText(idField.getText()+" id'li müzik başarılı bir şekilde GÜNCELLENDİ.");
		errorLabel.setStyle("-fx-background-color: #96f196");
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM Song WHERE ID="+idField.getText()+"";
    	executeQuery(query);
		showMusics();
		errorLabel.setText(idField.getText()+" id'li müzik başarılı bir şekilde SİLİNDİ.");
		errorLabel.setStyle("-fx-background-color: #96f196");
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
		System.out.println(SessionMapSinger.getSessionMap("singerData").getId());
		System.out.println(SessionMapSinger.getSessionMap("singerData").getName());
		System.out.println(SessionMapSinger.getSessionMap("singerData").getName());
		showMusics();
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

	public ObservableList<Song> getSingerList(){
		ObservableList<Song> songList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM song";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Song song;

			while(rs.next()) {
				song = new Song(rs.getInt("Id"),rs.getString("Name"),rs.getString("Date"),rs.getInt("Singer_id"),rs.getInt("Album_id"),rs.getString("Type"),rs.getString("Time"),rs.getInt("Count"), "Button"+rs.getInt("Id"));
				if (SessionMapSinger.getSessionMap("singerData").getId()==rs.getInt("singer_id")){
					songList.add(song);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return songList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showMusics() {
		ObservableList<Song> list = getSingerList();


		idColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("Name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("Date"));
		singerIdColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("SingerId"));
		albumIDColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("AlbumId"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("Type"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("Time"));
		countColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("Count"));



		Callback<TableColumn<Song, Void>, TableCell<Song, Void>> cellFactory = new Callback<TableColumn<Song, Void>, TableCell<Song, Void>>() {
			@Override
			public TableCell<Song, Void> call(final TableColumn<Song, Void> param) {
				final TableCell<Song, Void> cell = new TableCell<Song, Void>() {

					private final Button btn = new Button("Album Ekle");

					{
						btn.setOnAction((ActionEvent event) -> {
							Song data = getTableView().getItems().get(getIndex());
							//System.out.println("selectedData: " + data.getName()+","+data.getId());

							SessionMapSong.addSession("songData",data);
							System.out.println("Song: " + SessionMapSong.getSessionMap("songData").getName());
							Stage stageMain = (Stage) btn.getScene().getWindow();
							//Close Stage
							stageMain.close();

							Stage stage2 = new Stage();
							Parent parent = null;
							try {
								parent = FXMLLoader.load(getClass().getResource("/view/AlbumUser.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							Scene scene = new Scene(parent, 1400, 700);
							stage2.setScene(scene);
							stage2.setTitle(SessionMapSinger.getSessionMap("singerData").getName()+"'nin "+SessionMapSong.getSessionMap("songData").getName()+" Adlı Şarkısı için Album Ekleme");
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
