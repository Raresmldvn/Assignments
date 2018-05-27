package vc.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MatchView {
	AnchorPane root;
	Scene scene;
	
	public Scene getScene() {
		try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource(
	 	               "/MatchView.fxml"));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	     scene = new Scene(root, 400, 500);
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
