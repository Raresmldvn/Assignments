package ui;

import java.util.ArrayList;

import business.dto.PlayerDTO;
import business.dto.TournamentDTO;
import business.modules.TournamentModule;
import business.modules.PlayerModule;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminScene {


	//Log out button
	private Button logOut;
	
	//Player and Tournament lists
	private ListView<String> tournamentList;
	private ListView<String> playerList;
	
	//Buttons for players
	private Button deletePlayerButton;
	private Button addPlayerButton;
	private Button updatePlayerButton;
	
	//Buttons for tournaments
	private Button addTournamentButton;
	private TextField addTournamentTextField;
	private Button updateTournamentButton;
	private Button deleteTournamentButton;
	private Button openTournamentButton;
	
	//Button for enrolling player in tournament
	private Button enrollPlayerButton;
	
	private PlayerModule playerModule;
	private ArrayList<PlayerDTO> allPlayers;
	
	private TournamentModule tournamentModule;
	private ArrayList<TournamentDTO> allTournaments;
	
	private int playerIndex;
	
	private MainWindow upperStage;
	public AdminScene(MainWindow logInStage) {
		
		this.upperStage = logInStage;
		playerModule = new PlayerModule();
		tournamentModule = new TournamentModule();
		logOut = new Button("Log out");
		
		tournamentList = new ListView<String>();
		tournamentList.setMinSize(300, 300);
		playerList = new ListView<String>();
		playerList.setMinSize(300, 300);
		
		enrollPlayerButton = new Button("Enroll player");
		addPlayerButton = new Button("Add");
		updatePlayerButton = new Button("Update");
		
		
		deleteTournamentButton = new Button("Delete");
		openTournamentButton = new Button("Open");
		addTournamentButton = new Button("New");
		addTournamentTextField = new TextField();
		updateTournamentButton = new Button("Update tournament");
	}
	
	 public static final ObservableList<String> players = FXCollections.observableArrayList();
	 public static final ObservableList<String> tournaments = FXCollections.observableArrayList();
	 public Scene getAdminScene() {
		
		
		//Title
		Label titleLabel = new Label("Administrator panel");
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
		
		HBox titlePane = new HBox();
		titlePane.getChildren().add(titleLabel);
		
		BorderPane finalPane = new BorderPane();
		finalPane.setTop(titlePane);
		titlePane.setAlignment(Pos.CENTER);
		
		
		//Players
		Label playerLabel = new Label("             Player list");
		//fill list data
		allPlayers = playerModule.getAllPlayers();
		for(PlayerDTO player : allPlayers) {
			players.add(player.getName());

		}
		playerList.setItems(players);
		VBox playerPane = new VBox();
		HBox playerListPane = new HBox();
		playerListPane.getChildren().addAll(new Label("   "), playerList);
		playerPane.getChildren().addAll(playerLabel,playerListPane);
		finalPane.setLeft(playerPane);
		
		//Tournaments
		Label tournamentLabel = new Label("              Tournament list");
		allTournaments = tournamentModule.getAllTournaments();
		for(TournamentDTO tournament: allTournaments) {
			
			tournaments.add(tournament.getName());
		}
		tournamentList.setItems(tournaments);
		VBox tournamentPane = new VBox();
		HBox tournamentListPane = new HBox();
		tournamentListPane.getChildren().addAll(tournamentList, new Label("  "));
		tournamentPane.getChildren().addAll(tournamentLabel, tournamentListPane);
		finalPane.setRight(tournamentPane);
		
		//Center pane with buttons
		VBox buttonsPane = new VBox();
		buttonsPane.setAlignment(Pos.CENTER);
		addPlayerButton = new Button("    Add player    ");
		addPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleAddPlayer();
			}
	    });
		deletePlayerButton = new Button("  Delete player   ");
		deletePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleDeletePlayer();
			}
	    });
		
		updatePlayerButton = new Button("  Update player  ");
		updatePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleUpdatePlayer();
			}
	    });
		
		enrollPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleEnrollPlayer();
			}
	    });
		Label separationLabel1 = new Label("");
		Label separationLabel2 = new Label("");
		Label separationLabel3 = new Label("");
		addTournamentButton = new Button(" Add tournament  ");
		addTournamentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleAddTournament();
			}
	    });
		deleteTournamentButton = new Button("Delete tournament");
		deleteTournamentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleDeleteTournament();
			}
	    });
		openTournamentButton = new Button("Open tournament  ");
		openTournamentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleOpenTournament();
			}
	    });
		buttonsPane.getChildren().addAll(separationLabel1, separationLabel2, addPlayerButton, deletePlayerButton, updatePlayerButton, 
										separationLabel3,enrollPlayerButton , new Label(" "), addTournamentButton, addTournamentTextField, deleteTournamentButton,
										openTournamentButton, updateTournamentButton);
		
		updateTournamentButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleUpdateTournament();
			}
	    });
		buttonsPane.setPadding(new Insets(5, 5, 7,8));
		buttonsPane.setMinSize(50, 400);
		finalPane.setCenter(buttonsPane);
		
		//Log out on button
		VBox logOutPane = new VBox();
		//logOut.setFont(new Font("Verdana"));
		logOut.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				upperStage.setStageBackToLogIn();
			}
	    });
		logOutPane.getChildren().addAll(logOut,  new Label("  "), new Label(" "));
		logOutPane.setAlignment(Pos.CENTER);
		finalPane.setBottom(logOutPane);
		finalPane.setPadding(new Insets(10, 10, 10, 10));
		
		Scene finalScene = new Scene(finalPane, 800,500);
		return finalScene;
	}
	
	private void handleAddPlayer() {
		
		PlayerForm form = new PlayerForm(playerModule,this);
		form.displayPlayerForm();
	}
	
	private void handleUpdatePlayer() {
		
		if(playerList.getSelectionModel().getSelectedIndex()==-1) {
			
			alertError("You need to select a player!");
		} else {
			String selectedItem = playerList.getSelectionModel().getSelectedItem();
			playerIndex = playerList.getSelectionModel().getSelectedIndex();
			PlayerDTO result = playerModule.getPlayerBy("name", selectedItem);
			PlayerForm form = new PlayerForm(playerModule, result.getName(), result.getEmail(), result.getPassword(),this);
			form.displayPlayerForm();
		}
	}
	
	private void handleDeletePlayer() {
		if(playerList.getSelectionModel().getSelectedIndex()==-1) {
			
			alertError("You need to select a player!");
		} else {
		String selectedItem = playerList.getSelectionModel().getSelectedItem();
		playerList.getItems().remove(playerList.getSelectionModel().getSelectedIndex());
		playerModule.deletePlayer(selectedItem);
		}
	}
	
	private void handleAddTournament() {
		
		if( addTournamentTextField.getText().equals("")) {
			
			alertError("You need to input a name!");
		} else {
			String newTournament = addTournamentTextField.getText();
			tournamentList.getItems().add(newTournament);
			tournamentModule.createNewTournament(newTournament);
		}
	}
	
	private void handleUpdateTournament() {
		
		if(tournamentList.getSelectionModel().getSelectedIndex()==-1|| addTournamentTextField.getText().equals("")) {
			
			alertError("You need to select a tournament and input a name!");
		} else {
			String toBeUpdated = tournamentList.getSelectionModel().getSelectedItem();
			int index = tournamentList.getSelectionModel().getSelectedIndex();
			String updatedName = addTournamentTextField.getText();
			tournamentList.getItems().remove(index);
			tournamentList.getItems().add(index, updatedName);
			tournamentModule.updateTournament(toBeUpdated, updatedName);
		}
	}
	
	private void handleDeleteTournament() {
		
		if(tournamentList.getSelectionModel().getSelectedIndex()==-1) {
			
			alertError("You need to select a tournament!");
		} else {
			String toBeDeleted = tournamentList.getSelectionModel().getSelectedItem();
			tournamentList.getItems().remove(tournamentList.getSelectionModel().getSelectedIndex());
			tournamentModule.deleteTournament(toBeDeleted);
		}
	}
	
	private void handleEnrollPlayer() {
		int playerIndex =  playerList.getSelectionModel().getSelectedIndex();
		int tournamentIndex =  playerList.getSelectionModel().getSelectedIndex();
		
		if(playerIndex==-1||tournamentIndex==-1) {
			
			alertError("You need to select a player and a tournament!");
		} else {
		
		String playerName = playerList.getSelectionModel().getSelectedItem();
		String tournamentName = tournamentList.getSelectionModel().getSelectedItem();
		boolean success = tournamentModule.enrollPlayerInTournament(playerName, tournamentName);
			if(success==true) {
				
				alertSuccess("Succesfully enrolled!");
			} else {
				
				alertError("Cannot enroll player!");
			}
		}
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
	
	public void addPlayerList(String newPlayer) {
		
		this.playerList.getItems().add(newPlayer);
	}
	
	public void updatePlayerList(String updated) {
		
		this.playerList.getItems().remove(playerIndex);
		this.playerList.getItems().add(playerIndex, updated);
	}
	
	private void alertError(String error) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("ERROR");
		alert.setContentText(error);

		alert.showAndWait();
	}
	
	private void alertSuccess(String success) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("SUCCESS");
		alert.setContentText(success);

		alert.showAndWait();
	}
}
