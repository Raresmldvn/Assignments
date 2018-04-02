package ui;

import business.modules.PlayerModule;
import business.modules.TournamentModule;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainWindow extends Application{

	private Button logInButton;
	private Button clearButton;
	private TextField emailField;
	private TextField passwordField;
	
	private Scene logInScene;
	
	private AdminScene adminScene;
	private UserScene userScene;
	
	private PlayerModule playerModule;
	
	private Stage mainStage;
	public static void main(String args[]) {
		
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		mainStage = primaryStage;
		adminScene = new AdminScene(this);
		playerModule = new PlayerModule();
		primaryStage.setTitle("Table tennis application");
		GridPane grid = new GridPane();
		grid.setMinSize(500,300);
	    grid.setHgap(5);
	    grid.setVgap(5);
	    grid.setPadding(new Insets(10,10,10,10)); 
	    //Title information
	    Label titleLabel = new Label("Table tennis application");
	    titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
	    grid.add(titleLabel, 1, 0);
	    
	    //Email information
	    Label emailLabel = new Label("Username");
	    emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
	    grid.add(emailLabel, 0, 4);
	    
	    emailField = new TextField();
	    grid.add(emailField, 1, 4);
	    
	    //Password information
	    Label passwordLabel = new Label("Password");
	    passwordLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 17));
	    grid.add(passwordLabel, 0, 5);
	    
	    passwordField = new TextField();
	    grid.add(passwordField, 1, 5);
	    
	    //Buttons
	    logInButton = new Button("Log in");
	    logInButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	    logInButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleLogIn();
			}
	    });
	    
	    
	    clearButton = new Button("Clear");
	    clearButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				emailField.clear();
	            passwordField.clear();
			}
	    });
	    clearButton.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	    grid.add(logInButton, 0, 6);
	    grid.add(clearButton, 1, 6);
	    
	    logInScene = new Scene(grid, 500,200);
	    primaryStage.setScene(logInScene);
	    primaryStage.show();
	}
	
	public void setStageBackToLogIn() {
		
		emailField.clear();
		passwordField.clear();
		this.mainStage.setScene(logInScene);
	}
	
	public void handleLogIn() {
		String username = emailField.getText();
		String password = passwordField.getText();
		int flag = playerModule.logIn(username, password);
		switch(flag) {
		case 0: {
					mainStage.setScene(adminScene.getAdminScene());
					break;
				}
		case 1: {
					userScene = new UserScene(playerModule.getPlayerBy("email", emailField.getText()).getName(),emailField.getText(), new TournamentModule(), this);
					mainStage.setScene(userScene.getUserScene());
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

