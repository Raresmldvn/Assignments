package vc.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vc.controllers.LogInController;

public class LogInView {

	AnchorPane root;
	private static LogInView INSTANCE;
	public LogInView() {
		
	}

	public static LogInView getInstance() {
		if(INSTANCE==null) INSTANCE = new LogInView();
		return INSTANCE;
		
	}
	public Scene getScene() {
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	     Scene scene = new Scene(root, 500, 200);
	     return scene;
	}
}
