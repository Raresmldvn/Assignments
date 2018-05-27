package vc.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TournamentView {

	AnchorPane root;
	Scene scene;
	
	public Scene getScene() {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(
	 	               "/Tournament.fxml"));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     scene = new Scene(root, 1000, 300);
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
