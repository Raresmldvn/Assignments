package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ui.controller.WriterController;

public class WriterView {

	AnchorPane root;
	
	WriterController controller;
	public WriterView() {
		
		
	}
	
	public void load() {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getResource("/Writer.fxml").openStream());
			controller = (WriterController) fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Scene getScene() {
	     Scene scene = new Scene(root, 730, 600);
	     return scene;
	}
	
	public WriterController getController() {
		
		load();
		return this.controller;
	}
}