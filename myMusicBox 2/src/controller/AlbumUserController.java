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
import library.Album;
import library.Song;
import library.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class AlbumUserController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

	@FXML
	private TextField dateField;

	@FXML
	private TextField typeField;

	@FXML
	private TableView<Album> TableView;

	@FXML
	private TableColumn<Album, Integer> idColumn;

	@FXML
	private TableColumn<Album, String> nameColumn;

	@FXML
	private TableColumn<Album, Integer> singerIdColumn;

	@FXML
	private TableColumn<Album, Integer> songIDColumn;

	@FXML
	private TableColumn<Album, String> dateColumn;

	@FXML
	private TableColumn<Album, String> typeColumn;

	@FXML
	private TableColumn<Album, Void> buttonColumn;

	@FXML
	private TableColumn<Album, Void> gosterBtnColumn;

	@FXML
	private Button backToSingerBtn;

	@FXML
	private Label errorLabel;

    @FXML
    private void insertButton() {
    	String query = "insert into album values('"+idField.getText()+"','"+nameField.getText()+"','"+SessionMapSinger.getSessionMap("singerData").getId()+"','"+SessionMapSong.getSessionMap("songData").getId()+"','"+dateField.getText()+"','"+typeField.getText()+"')";
    	executeQuery(query);
    	showMusics();
		errorLabel.setText(idField.getText()+" id'li "+SessionMapSinger.getSessionMap("singerData").getName()+"'in Şarkısı başarılı bir şekilde EKLENDİ.");
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
		stage2.setTitle(SessionMapUser.getSessionMap("userData").getName()+" "+SessionMapUser.getSessionMap("userData").getSurname()+" için Sanatçı Ekleme Sayfası");
		stage2.show();
	}
    
    
    @FXML 
    private void updateButton() {
		String query = "UPDATE album SET name='"+nameField.getText()+"',date='"+dateField.getText()+"',singer_id='"+SessionMapSinger.getSessionMap("singerData").getId()+"',type='"+typeField.getText()+"' WHERE ID='"+idField.getText()+"'";
		executeQuery(query);
		showMusics();
		errorLabel.setText(idField.getText()+" id'li müzik başarılı bir şekilde GÜNCELLENDİ.");
		errorLabel.setStyle("-fx-background-color: #96f196");
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM album WHERE ID="+idField.getText()+"";
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

	public ObservableList<Album> getSongList(){
		ObservableList<Album> albumList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM album";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Album album;

			while(rs.next()) {
				album = new Album(rs.getInt("Id"),rs.getString("Name"),rs.getInt("Singer_id"),rs.getInt("Song_id"),rs.getString("Date"),rs.getString("Type"), "Button"+rs.getInt("Id"), "Button"+rs.getInt("Id"));
				albumList.add(album);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return albumList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showMusics() {
		ObservableList<Album> list = getSongList();


		idColumn.setCellValueFactory(new PropertyValueFactory<Album,Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Album,String>("Name"));
		singerIdColumn.setCellValueFactory(new PropertyValueFactory<Album,Integer>("SingerId"));
		songIDColumn.setCellValueFactory(new PropertyValueFactory<Album,Integer>("SongId"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Album,String>("Date"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Album,String>("Type"));



		Callback<TableColumn<Album, Void>, TableCell<Album, Void>> cellFactory = new Callback<TableColumn<Album, Void>, TableCell<Album, Void>>() {
			@Override
			public TableCell<Album, Void> call(final TableColumn<Album, Void> param) {
				final TableCell<Album, Void> cell = new TableCell<Album, Void>() {

					private final Button btn = new Button("Ekle");

					{
						btn.setOnAction((ActionEvent event) -> {
							Album data = getTableView().getItems().get(getIndex());
							System.out.println("Album Tipi: " + data.getType());
							System.out.println(SessionMapSong.getSessionMap("songData").getName());

							Connection connection = getConnection();
							String query0 = "SELECT * FROM song WHERE id='"+SessionMapSong.getSessionMap("songData").getId()+"'";
							Statement st;
							ResultSet rs;
							Song song=null;

							try {
								st = connection.createStatement();
								rs = st.executeQuery(query0);
								if(rs.next()) {
									song = new Song(rs.getInt("Id"),rs.getString("Name"),rs.getString("Date"),rs.getInt("Singer_id"),rs.getInt("Album_id"),rs.getString("Type"),rs.getString("Time"),rs.getInt("Count"), "Button"+rs.getInt("Id"));
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (data.getType().equals("Pop")) {
								if (song.getAlbumId()==0) {
									String query = "insert into yoresel values('" + SessionMapSinger.getSessionMap("singerData").getName() + "','" + SessionMapSong.getSessionMap("songData").getName() + "')";
									executeQuery(query);
									errorLabel.setText("Succes: " + SessionMapSong.getSessionMap("songData").getName()+" adlı şarkı eklendi.");
									errorLabel.setStyle("-fx-background-color: #0ce80c");
									String query2 = "UPDATE song SET album_id='"+data.getId()+"' WHERE ID='"+SessionMapSong.getSessionMap("songData").getId()+"'";
									executeQuery(query2);
								}else{
									errorLabel.setText("Error: Şarkı Zaten Ekli Tekrar Eklenemez.");
									errorLabel.setStyle("-fx-background-color: #e74f4f");
								}
							}

							if (data.getType().equals("Klasic")) {
								if (song.getAlbumId()==0) {
									String query = "insert into cok_calanlar values('"+ SessionMapSinger.getSessionMap("singerData").getName() + "','" + SessionMapSong.getSessionMap("songData").getName() + "')";
									executeQuery(query);
									errorLabel.setText("Succes: " + SessionMapSong.getSessionMap("songData").getName()+" adlı şarkı eklendi.");
									errorLabel.setStyle("-fx-background-color: #0ce80c");
									String query2 = "UPDATE song SET album_id='"+data.getId()+"' WHERE ID='"+SessionMapSong.getSessionMap("songData").getId()+"'";
									executeQuery(query2);
									SessionMapSong.getSessionMap("songData").setAlbumId(data.getId());
								}else{
									errorLabel.setText("Error: Şarkı Zaten Ekli Tekrar Eklenemez.");
									errorLabel.setStyle("-fx-background-color: #e74f4f");
								}
							}

							if (data.getType().equals("Jazz")) {
								if (song.getAlbumId()==0) {
									String query = "insert into favoriler values('" + SessionMapSinger.getSessionMap("singerData").getName() + "','" + SessionMapSong.getSessionMap("songData").getName() + "')";
									executeQuery(query);
									errorLabel.setText("Succes: " + SessionMapSong.getSessionMap("songData").getName()+" adlı şarkı eklendi.");
									errorLabel.setStyle("-fx-background-color: #0ce80c");
									String query2 = "UPDATE song SET album_id='"+data.getId()+"' WHERE ID='"+SessionMapSong.getSessionMap("songData").getId()+"'";
									executeQuery(query2);
									SessionMapSong.getSessionMap("songData").setAlbumId(data.getId());
								}else{
									errorLabel.setText("Error: Şarkı Zaten Ekli Tekrar Eklenemez.");
									errorLabel.setStyle("-fx-background-color: #e74f4f");
								}
							}
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

		Callback<TableColumn<Album, Void>, TableCell<Album, Void>> cellGosterFactory = new Callback<TableColumn<Album, Void>, TableCell<Album, Void>>() {
			@Override
			public TableCell<Album, Void> call(final TableColumn<Album, Void> param) {
				final TableCell<Album, Void> cell = new TableCell<Album, Void>() {

					private final Button btn = new Button("Goster");

					{
						btn.setOnAction((ActionEvent event) -> {
							Album data = getTableView().getItems().get(getIndex());
							System.out.println("Goster: " + data.getType());


							SessionMapAlbum.addSession("albumData",data);
							//System.out.println("Şarkı: " + SessionMapAlbum.getSessionMap("albumData").getName());
							//Stage stageMain = (Stage) btn.getScene().getWindow();
							//Close Stage
							//stageMain.close();

							Stage stage2 = new Stage();
							Parent parent = null;
							try {
								parent = FXMLLoader.load(getClass().getResource("/view/SongList.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							Scene scene = new Scene(parent, 330, 500);
							stage2.setScene(scene);
							stage2.setTitle(SessionMapUser.getSessionMap("userData").getName()+" "+SessionMapUser.getSessionMap("userData").getSurname()+"'in "+SessionMapAlbum.getSessionMap("albumData").getName()+" Liste");
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
		gosterBtnColumn.setCellFactory(cellGosterFactory);

		TableView.setItems(list);
	}

}
