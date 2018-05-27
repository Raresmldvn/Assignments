package vc.controllers;

import model.modules.PlayerModule;
import vc.views.AdministratorView;
import vc.views.LogInView;
import vc.views.PlayerView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

	private PlayerModule pModule;
	
	
	public LogInController() {
		
		pModule = PlayerModule.getInstance();
	}

	@FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button logInButton;

    @FXML
    private Button clearButton;

    @FXML
    void handleClear(ActionEvent event) {

    	emailTF.clear();
    	passwordTF.clear();
    }

    @FXML
    void handleLogIn(ActionEvent event) {
    	String username = emailTF.getText();
		String password = passwordTF.getText();
		int flag = pModule.logIn(username, password);
		switch(flag) {
		case 0: {
				AdministratorView  c = new AdministratorView();
				Stage stage = (Stage) emailTF.getScene().getWindow();
				stage.setScene(c.getScene());
				break;
				}
		case 1: {
					PlayerView p = new PlayerView();
					Stage stage = (Stage) emailTF.getScene().getWindow();
					stage.setScene(p.getScene());
					break;
				}
		case -1: {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("Incorrect username!");

				alert.showAndWait();
				break;
				}
		case -2: {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("Incorrect password!");

				alert.showAndWait();
				break;
				}
		
		}
			
	}
   
}
    
	
