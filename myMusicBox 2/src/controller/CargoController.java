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
import library.Cargo;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class CargoController implements Initializable {

    @FXML
    private TextField idField;

	@FXML
    private TextField customerNameField;

    @FXML
    private TextField customerAddressField;

    @FXML
    private TextField usersIdField;

	@FXML
	private CheckBox cargoStatusField;

    @FXML
    private TableView<Cargo> TableView;

    @FXML
    private TableColumn<Cargo, Integer> idColumn;

    @FXML
    private TableColumn<Cargo, String> customerNameColumn;

    @FXML
    private TableColumn<Cargo, String> customerAddressColumn;

    @FXML
    private TableColumn<Cargo, String> usersIdColumn;

	@FXML
	private TableColumn<Cargo, Boolean> cargoStatusColumn;

	@FXML
	private Button loginBtn;

	@FXML
	private Button mainBtn;

    @FXML
    private void insertButton() {
    	String query = "insert into cargo values('"+idField.getText()+"','"+customerNameField.getText()+"','"+customerAddressField.getText()+"','"+usersIdField.getText()+"','"+cargoStatusField.isSelected()+"')";
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
    String query = "UPDATE cargo SET customer_name='"+customerNameField.getText()+"',customer_address='"+customerAddressField.getText()+"',users_id='"+usersIdField.getText()+"',cargo_status='"+cargoStatusField.isSelected()+"' WHERE ID='"+idField.getText()+"'";
    executeQuery(query);
	showUsers();
    }
    
    @FXML
    private void deleteButton() {
    	String query = "DELETE FROM cargo WHERE ID="+idField.getText()+"";
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
    	usersIdField.setText(String.valueOf(SessionMapUser.getSessionMap("userData").getId()));
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
    
    public ObservableList<Cargo> getCargoList(){
    	ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM cargo";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			List<Cargo> cargos = new ArrayList<>();
			Cargo cargo;
			while(rs.next()) {
				cargo = new Cargo(rs.getInt("Id"),rs.getString("customer_name"),rs.getString("customer_address"),rs.getString("users_id"),rs.getBoolean("cargo_status"));
					if (cargo.getUsersId().equals(String.valueOf(SessionMapUser.getSessionMap("userData").getId()))) {
						cargos.add(cargo);
					}
				}
			Collections.sort(cargos,(o1,o2)->o1.getId().compareTo(o2.getId()));
			cargoList.addAll(cargos);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return cargoList;
    }

    // I had to change ArrayList to ObservableList I didn't find another option to do this but this works :)
    public void showUsers() {
    	ObservableList<Cargo> list = getCargoList();

    	idColumn.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("id"));
    	customerNameColumn.setCellValueFactory(new PropertyValueFactory<Cargo,String>("customerName"));
    	customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Cargo,String>("customerAddress"));
    	usersIdColumn.setCellValueFactory(new PropertyValueFactory<Cargo,String>("usersId"));
		cargoStatusColumn.setCellValueFactory(new PropertyValueFactory<Cargo,Boolean>("cargoStatus"));

		TableView.setItems(list);
    }

}
