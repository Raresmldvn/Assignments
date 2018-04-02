package ui;

import java.util.ArrayList;

import business.dto.TournamentDTO;
import business.modules.TournamentModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UserScene {

	private Label nameTF;
	private Label emailTF;
	
	private ListView<String> tournamentList;
	
	private Button logOutButton;
	private Button openTournamentButton;
	
	private TournamentModule tournamentModule;
	private MainWindow logingWindow;
	public UserScene(String name, String email, TournamentModule upper, MainWindow parent) {
		
		this.logingWindow = parent;
		this.nameTF = new Label(name);
		nameTF.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.emailTF = new Label(email);
		emailTF.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.tournamentList = new ListView<String>();
		this.logOutButton = new Button("Log out");
		this.openTournamentButton = new Button("Open tournament");
		this.tournamentModule = upper;
		
	}
	
	 public ObservableList<String> tournaments = FXCollections.observableArrayList();
	 
	 public Scene getUserScene() {
		
		Label nameLabel = new Label(" Name: ");
		nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label emailLabel = new Label(" Email: ");
		emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		HBox nameBox = new HBox();
		nameBox.setAlignment(Pos.TOP_LEFT);
		nameBox.getChildren().addAll(nameLabel, nameTF);
		
		HBox emailBox = new HBox();
		emailBox.setAlignment(Pos.TOP_LEFT);
		emailBox.getChildren().addAll(emailLabel, emailTF);
		
		VBox infoBox = new VBox();
		infoBox.getChildren().addAll(nameBox,emailBox);
		
		
		VBox tournamentBox = new VBox();
		VBox tournamentListBox = new VBox();
		tournamentListBox.setAlignment(Pos.CENTER);
		Label tournamentLabel = new Label("              Tournament list");
		ArrayList<TournamentDTO> allTournaments = tournamentModule.getAllTournaments();
		for(TournamentDTO tournament: allTournaments) {
			
			tournaments.add(tournament.getName());
		}
		tournamentList.setItems(tournaments);
		tournamentListBox.getChildren().addAll(tournamentLabel, tournamentList);
		openTournamentButton.setAlignment(Pos.CENTER);
		openTournamentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleOpenTournament();
			}
	    });
		HBox openBox = new HBox();
		openBox.setAlignment(Pos.CENTER);
		openBox.getChildren().addAll(openTournamentButton, new Label("  "), logOutButton);
		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				logingWindow.setStageBackToLogIn();
			}
	    });
		openTournamentButton.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		tournamentBox.getChildren().addAll(new Label(" "), tournamentListBox,new Label("         "), openBox);
		
		VBox finalBox = new VBox();
		finalBox.getChildren().addAll(infoBox, tournamentBox);
		
		Scene scene = new Scene(finalBox, 500, 400);
		return scene;
	}
	 
	 	private void handleOpenTournament() {
			
			if(tournamentList.getSelectionModel().getSelectedIndex()==-1) {
				
				alertError("You need to select a tournament from the list!");
			} else {
			String tournamentName = tournamentList.getSelectionModel().getSelectedItem();
			TournamentScene tournamentScene = new TournamentScene(tournamentModule.getTournament(tournamentName));
			tournamentScene.displayTournamentScene();
			}
		}
	 	private void alertError(String error) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText(error);

			alert.showAndWait();
		}
}
