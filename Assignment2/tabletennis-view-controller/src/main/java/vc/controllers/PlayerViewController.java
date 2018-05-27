package vc.controllers;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.PaidTournamentDTO;
import model.modules.MatchModule;
import model.modules.PlayerHelper;
import model.modules.PlayerModule;
import model.modules.TournamentModule;
import vc.table.TournamentObject;
import vc.views.LogInView;
import vc.views.TournamentView;

public class PlayerViewController implements Observer{

	private PlayerModule pModule;
	private TournamentModule tModule;
	
	public PlayerViewController() {
		
		this.pModule = PlayerModule.getInstance();
		this.tModule = TournamentModule.getInstance();
		this.pModule.addObserver(this);
		this.tModule.addObserver(this);
		MatchModule.getInstance().addObserver(this);
	}
	
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
    private ComboBox<String> comboBox;

    @FXML
    private CheckBox paidCB;

    @FXML
    private TextField nameTF;

    @FXML
    private Button searchButton;

    @FXML
    private Button openTournamentButton;

    @FXML
    private Button enrollButton;

    @FXML
    private TextField playerTF;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField moneyTF;

    @FXML
    void handleComboBox(ActionEvent event) {

    	int type = comboBox.getSelectionModel().getSelectedIndex();
    	ArrayList<PaidTournamentDTO> toBeDisplayed;
    	if(type==0)
    		toBeDisplayed = tModule.seeBy(0);
    	else
    		if(type==1) {
    		toBeDisplayed = tModule.seeBy(1);
    		} else 
    			toBeDisplayed = tModule.getEnrolled(this.playerTF.getText());
    	populateTournamentTable(toBeDisplayed);
    }

    @FXML
    void handleEnroll(ActionEvent event) {
    	
    	int tournamentIndex =  tournamentTable.getSelectionModel().getSelectedIndex();
    	if(tournamentIndex<0) 
    		alertError("You must select a tournament!");
    	else {
    	String player = this.playerTF.getText();
    	String tournament =  tournamentTable.getSelectionModel().getSelectedItem().getName();
    	boolean result = tModule.enrollPlayerInTournament(player, tournament);
    	if(result==false) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Cannot enroll!");

			alert.showAndWait();
    	} else {
    		
    		alertSuccess("Succesfully enrolled!");
    	}
    	}
    }

    @FXML
    void handleOpenTournament(ActionEvent event) {

    	int tournamentIndex =  tournamentTable.getSelectionModel().getSelectedIndex();
    	if(tournamentIndex<0) 
    		alertError("You must select a tournament!");
    	else {
    	tModule.setCurrentTournament(tournamentTable.getSelectionModel().getSelectedItem().getName());
    	TournamentView tView = new TournamentView();
    	tView.display();
    	}
    }

    @FXML
    void handleSearch(ActionEvent event) {
    		
    	int type = comboBox.getSelectionModel().getSelectedIndex();
    	ArrayList<PaidTournamentDTO> toBeDisplayed;
    	String searchName = this.nameTF.getText();
    	boolean isPaid = this.paidCB.isSelected();
    	toBeDisplayed = tModule.searchTournament(searchName, isPaid);
    	populateTournamentTable(toBeDisplayed);
    }

    @FXML
    void logOut(ActionEvent event) {

    	Stage stage = (Stage) tournamentTable.getScene().getWindow();
		stage.setScene(LogInView.getInstance().getScene());
    }
    
    @FXML
    public void initialize() {
    	
    	comboBox.getItems().add("Alphabetical");
    	comboBox.getItems().add("By type");
    	comboBox.getItems().add("Enrolled");
    	ArrayList<PaidTournamentDTO> toBeDisplayed;
    	toBeDisplayed = tModule.seeBy(0);
    	populateTournamentTable(toBeDisplayed);
    	this.playerTF.setText(PlayerHelper.currentPlayer.getName());
    	this.moneyTF.setText(Float.toString(PlayerHelper.currentPlayer.getMoney()));
    }
    
	public void populateTournamentTable(ArrayList<PaidTournamentDTO> tournaments) {
		
		tableName.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("name"));
        tableType.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("type"));
        tableFee.setCellValueFactory(new PropertyValueFactory<TournamentObject, Float>("fee"));
        tableDate.setCellValueFactory(new PropertyValueFactory<TournamentObject, String>("startDate"));
      
		List<TournamentObject> list  = new ArrayList<TournamentObject>();
		//ArrayList<PaidTournamentDTO> tournaments = tModule.getAllTournaments();
		for(PaidTournamentDTO tournament : tournaments) {
			
			String date = "";
			Calendar cal = Calendar.getInstance();
			cal.setTime(tournament.getT().getDate());
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH)+1;
			int year = cal.get(Calendar.YEAR);
			date += Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
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
		pModule.setCurrentPlayer();
		this.playerTF.setText(PlayerHelper.currentPlayer.getName());
    	this.moneyTF.setText(Float.toString(PlayerHelper.currentPlayer.getMoney()));
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