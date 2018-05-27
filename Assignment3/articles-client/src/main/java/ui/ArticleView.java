package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import ui.controller.ArticleController;

public class ArticleView {

	AnchorPane root;
	
	ArticleController controller;
	public ArticleView() {
		
		
	}
	
	public void load() {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getResource("/Article.fxml").openStream());
			controller = (ArticleController) fxmlLoader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Scene getScene() {
	     Scene scene = new Scene(root, 550, 650);
	     return scene;
	}
	
	public ArticleController getController() {
		
		load();
		return this.controller;
	}
}
