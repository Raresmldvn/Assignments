package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MainView {

	AnchorPane root;
	private static MainView INSTANCE;
	public MainView() {
		
		
	}
	
	public static MainView getInstance() {
		
		if(INSTANCE==null) INSTANCE = new MainView();
		return INSTANCE;
	}
	
	Scene scene;
	public void load() {
		
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Main.fxml"));
			scene = new Scene(root, 650, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Scene getScene() {
	     return scene;
	}
}
