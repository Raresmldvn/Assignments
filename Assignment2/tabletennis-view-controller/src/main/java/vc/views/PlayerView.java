package vc.views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class PlayerView {

	AnchorPane root;
	
	public PlayerView() {
		
	}
	
	public Scene getScene() {
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Player.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	     Scene scene = new Scene(root, 680, 500);
	     return scene;
	}
}
