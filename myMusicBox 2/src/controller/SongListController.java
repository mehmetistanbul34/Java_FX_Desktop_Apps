package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Liste;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SongListController implements Initializable {

	@FXML
	private TableView<Liste> TableView;

	@FXML
	private TableColumn<Liste, String> singerNameColumn;

	@FXML
	private TableColumn<Liste, String> songNameColumn;

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

	public ObservableList<Liste> getSingerList(){
		ObservableList<Liste> songList = FXCollections.observableArrayList();
		Connection connection = getConnection();

		System.out.println("Şarkım: " + SessionMapAlbum.getSessionMap("albumData").getName());
		String query=null;
		if (SessionMapAlbum.getSessionMap("albumData").getName().equals("Favoriler")) {
			query = "SELECT * FROM favoriler";
		}else if (SessionMapAlbum.getSessionMap("albumData").getName().equals("Çok Çalanlar")) {
			query = "SELECT * FROM cok_calanlar";
		}else if (SessionMapAlbum.getSessionMap("albumData").getName().equals("Yöresel")) {
			query = "SELECT * FROM yoresel";
		}

		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Liste liste;

			while(rs.next()) {
				liste = new Liste(rs.getString("Singer_name"),rs.getString("Song_name"));
				songList.add(liste);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return songList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
	public void showMusics() {
		ObservableList<Liste> list = getSingerList();

		singerNameColumn.setCellValueFactory(new PropertyValueFactory<Liste,String>("SingerName"));
		songNameColumn.setCellValueFactory(new PropertyValueFactory<Liste,String>("SongName"));

		TableView.setItems(list);
	}

}
