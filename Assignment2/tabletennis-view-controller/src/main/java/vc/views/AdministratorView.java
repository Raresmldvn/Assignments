package vc.views;

import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.dto.PlayerDTO;
import model.modules.PlayerModule;
import model.modules.TournamentModule;
import vc.table.PlayerObject;

public class AdministratorView {

	
	AnchorPane root;
	
	public AdministratorView() {
		
	}
	
	public Scene getScene() {
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/Administrator.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	     Scene scene = new Scene(root, 800, 500);
	     return scene;
	}
}
