package vc.controllers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.dto.PlayerDTO;
import model.modules.MatchModule;
import model.modules.PlayerHelper;
import model.modules.PlayerModule;
import vc.table.PlayerObject;
import vc.table.TournamentObject;
import vc.views.AdministratorView;
import vc.views.LogInView;
import vc.views.MoneyForm;
import vc.views.PlayerForm;
import vc.views.TournamentForm;
import vc.views.TournamentView;
import model.modules.TournamentModule;
import model.dto.PaidTournamentDTO;
public class AdministratorController implements Observer {

	
		private PlayerModule pModule;
		private TournamentModule tModule;
		private PlayerDTO currentPlayer;
		
		public AdministratorController() {
			
			this.pModule = PlayerModule.getInstance();
			this.tModule = TournamentModule.getInstance();
			this.currentPlayer = PlayerHelper.currentPlayer;
			pModule.addObserver(this);
			tModule.addObserver(this);
			MatchModule.getInstance().addObserver(this);
		}
		
		
	 	@FXML
	    private TableView<PlayerObject> playerTable;
	 	 @FXML 
	 	 private TableColumn<PlayerObject, String> Name;
	     @FXML 
	     private TableColumn<PlayerObject, String> Email;
	     @FXML 
	     private TableColumn<PlayerObject, Float> Money;
	     
	    @FXML
	    private Button addPlayerButton;

	    @FXML
	    private Button updatePlayerButton;

	    @FXML
	    private TableView<TournamentObject> tournamentTable;
	    @FXML
	    private TableColumn<TournamentObject, String> tableName;
	    @FXML
	    private TableColumn<TournamentObject, String> tableType;
	    @FXML
	    private TableColumn<TournamentObject, String> tableDate;
	    @FXML
	    private TableColumn<TournamentObject, Float> tableFee;

	    @FXML
	    private Button addTournamentButton;

	    @FXML
	    private Button updateTournamentButton;

	    @FXML
	    private Button deleteTournamentButton;

	    @FXML
	    private Button deletePlayerButton;

	    @FXML
	    private Button enrollButton;

	    @FXML
	    private TextField adminTextField;

	    @FXML
	    private Button logOutButton;

	    @FXML
	    private Button moneyManagerButton;
	    
	    @FXML
	    private Button openTournamentButton;
	    
	    @FXML
	    void handleAddPlayer(ActionEvent event) {

	    	//PlayerDTO player = pModule.getPlayerBy("name", tournamentTable.getSelectionModel().getSelectedItem().getName());
	    	PlayerHelper.currentPlayer = null;
	    	PlayerForm playerForm = new PlayerForm();
	    	playerForm.display();
	    }

	    @FXML
	    void handleAddTournament(ActionEvent event) {
	    	
	    	tModule.setTournamentNull();
	    	TournamentForm tournamentForm = new TournamentForm();
	    	tournamentForm.display();
	    }

	    @FXML
	    void handleDeletePlayer(ActionEvent event) {

	    	if(playerTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("No player selected!");

				alert.showAndWait();
	    	} else {
	    	pModule.deletePlayer(playerTable.getSelectionModel().getSelectedItem().getName());
	    	}
	    }

	    @FXML
	    void handleDeleteTournament(ActionEvent event) {
	    	if(tournamentTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("Not tournament selected!");

				alert.showAndWait();
	    	} else {
	    	String name = tournamentTable.getSelectionModel().getSelectedItem().getName();
	    	tModule.deleteTournament(name);
	    	}
	    }

	    @FXML
	    void handleEnroll(ActionEvent event) {
	    	
	    	if(playerTable.getSelectionModel().isEmpty()==true || tournamentTable.getSelectionModel().isEmpty()==true) {
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("You must select a player and a tournament!");

	    		
	    	} else {
	    	String player =  playerTable.getSelectionModel().getSelectedItem().getName();
	    	String tournament =  tournamentTable.getSelectionModel().getSelectedItem().getName();
	    	boolean result = tModule.enrollPlayerInTournament(player, tournament);
	    	if(result==false) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("Cannot enroll!");

				alert.showAndWait();
	    	} else {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("SUCCESS");
				alert.setContentText("Succesfully enrolled");
				alert.showAndWait();
	    	}
	    	}
	    }

	    @FXML
	    void handleLogOut(ActionEvent event) {

	    	Stage stage = (Stage) playerTable.getScene().getWindow();
			stage.setScene(LogInView.getInstance().getScene());
	    	
	    }

	    @FXML
	    void handleManageMoney(ActionEvent event) {

	    	if(playerTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("No player selected!");

				alert.showAndWait();
	    	} else {
	    	String name = playerTable.getSelectionModel().getSelectedItem().getName();
	    	PlayerDTO player = pModule.getPlayerBy("name", name);
	    	PlayerHelper.currentPlayer = player;
	    	MoneyForm moneyForm = new MoneyForm();
	    	moneyForm.display();
	    	}
	    	
	    }

	    @FXML
	    void handleUpdatePlayer(ActionEvent event) {

	    	if(playerTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("No player selected!");

				alert.showAndWait();
	    	} else {
	    	String name = playerTable.getSelectionModel().getSelectedItem().getName();
	    	PlayerDTO player = pModule.getPlayerBy("name", name);
	    	PlayerHelper.currentPlayer = player;
	    	PlayerForm playerForm = new PlayerForm();
	    	playerForm.display();
	    	}
	    	
	    }

	    @FXML
	    void handleUpdateTournament(ActionEvent event) {
	    	
	    	if(tournamentTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("Not tournament selected!");

				alert.showAndWait();
	    	} else {
	    	tModule.setCurrentTournament(tournamentTable.getSelectionModel().getSelectedItem().getName());
	    	TournamentForm tForm = new TournamentForm();
	    	tForm.display();
	    	}
	    }

	    
	    @FXML
	    void handleOpenTournament(ActionEvent event) {
	    	
	    	if(tournamentTable.getSelectionModel().isEmpty()==true) {
	    		
	    		Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ERROR");
				alert.setContentText("No tournament selected!");

				alert.showAndWait();
	    	} else {
	    		tModule.setCurrentTournament(tournamentTable.getSelectionModel().getSelectedItem().getName());
	    		TournamentView tView = new TournamentView();
	    		tView.display();
	    	}
	    }
	    @FXML
	    public void initialize() {
	    	
	    	this.adminTextField.setText(currentPlayer.getName());
	    	populatePlayerTable();
	    	populateTournamentTable();
	    }
	    
		public void populatePlayerTable() {
			
			Name.setCellValueFactory(new PropertyValueFactory<PlayerObject, String>("name"));
	        Email.setCellValueFactory(new PropertyValueFactory<PlayerObject, String>("email"));
	        Money.setCellValueFactory(new PropertyValueFactory<PlayerObject, Float>("money"));
	        
			List<PlayerObject> list  = new ArrayList<PlayerObject>();
			ObservableList<PlayerDTO> players = pModule.getPlayers();
			
			for(PlayerDTO player : players) {
				
				list.add(new PlayerObject(player.getName(), player.getEmail(), player.getMoney()));
			}
			playerTable.getItems().setAll(list);
		}
		
		public void populateTournamentTable() {
			
			tableName.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("name"));
	        tableType.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("type"));
	        tableFee.setCellValueFactory(new PropertyValueFactory<TournamentObject, Float>("fee"));
	        tableDate.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("startDate"));
	      
			List<TournamentObject> list  = new ArrayList<TournamentObject>();
			ArrayList<PaidTournamentDTO> tournaments = tModule.getAllTournaments();
			for(PaidTournamentDTO tournament : tournaments) {
				
				String date = "";
				Calendar cal = Calendar.getInstance();
				cal.setTime(tournament.getT().getDate());
				System.out.println(tournament.getT().getDate());
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int month = cal.get(Calendar.MONTH)+1;
				int year = cal.get(Calendar.YEAR);
				System.out.println(day + " " + month + " " + year);
				if(day<10)
					date += "0" + Integer.toString(day) + "/";
				else
					date +=  Integer.toString(day) + "/";
				if(month<10)
					date += "0" + Integer.toString(month) + "/";
				else
					date += Integer.toString(month) + "/";
				date+=year;
				int codeOfType = tournament.getT().classifyTournament();
				String type="";
				if(codeOfType==0)
					type = "FINISHED";
				if(codeOfType==1)
					type = "ONGOING";
				if(codeOfType==2)
					type = "UPCOMING";
				
				list.add(new TournamentObject(tournament.getT().getName(), type, tournament.getFee(), date));
			}

			tournamentTable.getItems().setAll(list);
		}

		public void update(Observable arg0, Object arg1) {
			populatePlayerTable();
			populateTournamentTable();
		}
}
