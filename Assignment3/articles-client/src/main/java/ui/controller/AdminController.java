package ui.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import listener.ServerListener;
import model.User;
import model.UserList;
import ui.MainView;

public class AdminController {

	private User admin;
	private ServerListener listener;
	private UserList userList;
	public AdminController() {
		
		listener = ServerListener.getInstance();
		userList = UserList.getInstance();
	}
	
    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField adminTF;
    
    @FXML
    void handleAdd(ActionEvent event) {

    	if(nameTF.getText().length()==0 || emailTF.getText().length()==0 || passwordTF.getText().length()==0) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You must complete all fields!");

			alert.showAndWait();
    	} else {
    		
    		User user = new User();
    		user.setName(nameTF.getText());
    		user.setEmail(emailTF.getText());
    		user.setPassword(passwordTF.getText());
    		listener.addUser(user);
    		userList.addUser(user);
    		userTable.getItems().add(user);
    		
    	}
    }

    @FXML
    void handleDelete(ActionEvent event) {

    	if(userTable.getSelectionModel().getSelectedItem()==null) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You must select a user!");

			alert.showAndWait();
    	} else {
    		
    		User user = userTable.getSelectionModel().getSelectedItem();
    		User u = listener.deleteUser(user);
    		userList.getUsers().remove(user);
    		userTable.getItems().clear();
        	List<User> users = userList.getUsers();
        	userTable.getItems().addAll(users);
    	}
    }

    @FXML
    void handleLogOut(ActionEvent event) {
    	Stage stage = (Stage)nameTF.getScene().getWindow();
    	stage.setScene(MainView.getInstance().getScene());
    }

    @FXML
    void handleUpdate(ActionEvent event) {
    	
    	if(nameTF.getText().length()==0 || passwordTF.getText().length()==0 ||userTable.getSelectionModel().getSelectedItem()==null) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You must complete name and password fields and select a user!");

			alert.showAndWait();
    	} else {
    		
    		User user = userTable.getSelectionModel().getSelectedItem();
    		userList.getUsers().remove(user);
    		user.setName(nameTF.getText());
    		user.setPassword(passwordTF.getText());
    		listener.updateUser(user);
    		userList.addUser(user);
    		userTable.getItems().clear();
    		userTable.getItems().addAll(userList.getUsers());
    		
    	}
    }

    public void setAdmin(User A) {
    	
    	admin = A;
    }
    
    @FXML
    public void initialize() {
    	
    	listener.getUsersCommand();
    	populateTable();
    }
    
    private void populateTable() {
    	
    	nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
    	emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
    	List<User> users = userList.getUsers();
    	userTable.getItems().addAll(users);
    }

}
