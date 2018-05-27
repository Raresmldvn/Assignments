package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ui.controller.AdminController;
import ui.controller.ArticleController;

public class AdminView {

AnchorPane root;
	
	AdminController controller;
	public AdminView() {
		
		
	}
	
	public void load() {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getResource("/Admin.fxml").openStream());
			controller = (AdminController) fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Scene getScene() {
	     Scene scene = new Scene(root, 650, 400);
	     return scene;
	}
	
	public AdminController getController() {
		
		load();
		return this.controller;
	}
}
