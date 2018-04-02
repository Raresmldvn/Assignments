package ui;

import business.dto.PlayerDTO;
import business.modules.PlayerModule;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlayerForm {

	private AdminScene associatedAdminScene;
	
	private Label nameLabel;
	private TextField nameTF;
	
	private Label emailLabel;
	private TextField emailTF;
	
	private Label passwordLabel;
	private TextField passwordTF;
	
	private Button actionButton;
	
	private PlayerModule playerModule;

	public PlayerForm(PlayerModule upper, AdminScene scene) {
		
		nameLabel = new Label("Name");
		nameTF = new TextField();
		
		emailLabel = new Label("Email");
		emailTF = new TextField();
		
		passwordLabel = new Label("Password");
		passwordTF = new TextField();
		
		actionButton = new Button("Add player");
		
		playerModule = upper;
		this.associatedAdminScene = scene;
	}
	
	public PlayerForm(PlayerModule upper, String name, String email, String password, AdminScene scene) {
		
		nameLabel = new Label("Name");
		nameTF = new TextField(name);
		
		emailLabel = new Label("Email");
		emailTF = new TextField(email);
		
		passwordLabel = new Label("Password");
		passwordTF = new TextField(password);
		
		actionButton = new Button("Update");
		this.emailTF.setEditable(false);
		playerModule = upper;
		
		this.associatedAdminScene = scene;
	}
	public void displayPlayerForm() {
		
		Stage window = new Stage(); 
		GridPane finalBox = new GridPane();
		finalBox.setMaxSize(200,100);
		finalBox.setHgap(5);
		finalBox.setVgap(5);
		finalBox.setPadding(new Insets(10,10,10,10));
		finalBox.add(nameLabel, 0, 0);
		finalBox.add(nameTF, 1, 0);
		finalBox.add(passwordLabel, 0, 2);
		finalBox.add(passwordTF, 1, 2);
		finalBox.add(emailLabel, 0, 1);
		finalBox.add(emailTF, 1,1);
		finalBox.add(actionButton, 1, 3);
		actionButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleAction();
			}
	    });
		Scene finalScene = new Scene(finalBox, 300, 200);
		window.setTitle("Player form");
		window.setScene(finalScene);
		window.show();
	}
	
	private void handleAction() {
		
		if(actionButton.getText().equals("Add player")) {
			playerModule.createPlayer(nameTF.getText(), emailTF.getText(), passwordTF.getText());
			this.associatedAdminScene.addPlayerList(nameTF.getText());
		} else {
			playerModule.updatePlayer(new PlayerDTO(nameTF.getText(), emailTF.getText(), passwordTF.getText()));
			this.associatedAdminScene.updatePlayerList(nameTF.getText());
		}
	}
}
