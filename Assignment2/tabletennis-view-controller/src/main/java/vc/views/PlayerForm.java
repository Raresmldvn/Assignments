package vc.views;

import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.dto.PlayerDTO;
import model.modules.PlayerModule;
import vc.controllers.LogInController;
import vc.controllers.PlayerFormController;

public class PlayerForm {

	
	public PlayerForm() {
		
	}
	
	AnchorPane root;
	Scene scene;
	
	public Scene getScene() {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(
	 	               "/PlayerForm.fxml"));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     scene = new Scene(root, 300, 150);
	     return scene;
	}
	
	public void display() {
		
		getScene();
		Stage window = new Stage(); 
		window.setTitle("Player form");
		window.setScene(scene);
		window.show();
	}
}
