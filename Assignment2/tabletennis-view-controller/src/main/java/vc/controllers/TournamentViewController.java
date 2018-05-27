package vc.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.modules.MatchModule;
import model.modules.TournamentModule;
import vc.views.MatchView;

public class TournamentViewController implements Observer{

	TournamentModule tModule;
	MatchModule mModule;
	
	public TournamentViewController() {
		
		tModule = TournamentModule.getInstance();
		mModule = MatchModule.getInstance();
		mModule.addObserver(this);
		tModule.addObserver(this);
	}
	
    @FXML
    private Label nameLabel;

    @FXML
    private TextField qfM1P1Button;

    @FXML
    private TextField qfM1P2Button;

    @FXML
    private TextField qfM2P1Button;

    @FXML
    private TextField qfM2P2Button;

    @FXML
    private TextField sfM1P1Button;

    @FXML
    private TextField sfM1P2Button;

    @FXML
    private TextField fP1Button;

    @FXML
    private TextField fP2Button;

    @FXML
    private TextField sfM2P1Button;

    @FXML
    private TextField sfM2P2Button;

    @FXML
    private TextField qfM3P1Button;

    @FXML
    private TextField qfM3P2Button;

    @FXML
    private TextField qfM4P1Button;

    @FXML
    private TextField qfM4P2Button;

    @FXML
    private Button qf1Button;

    @FXML
    private Button qf2Button;

    @FXML
    private Button sf1Button;

    @FXML
    private Button fButton;

    @FXML
    private Button sf2Button;

    @FXML
    private Button qf3Button;

    @FXML
    private Button qf4Button;

    @FXML
    private Label statusLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    void handleF(ActionEvent event) {
    	if(fP1Button.getText().equals("") || fP2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch7());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleQF1(ActionEvent event) {

    	if(qfM1P1Button.getText().equals("") || qfM1P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch1());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleQF2(ActionEvent event) {

    	if(qfM2P1Button.getText().equals("") || qfM2P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch2());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleQF3(ActionEvent event) {

    	if(qfM3P1Button.getText().equals("") || qfM3P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch3());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleQF4(ActionEvent event) {

    	if(qfM4P1Button.getText().equals("") || qfM4P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch4());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleSF1(ActionEvent event) {
    	if(sfM1P1Button.getText().equals("") || sfM1P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch5());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    @FXML
    void handleSF2(ActionEvent event) {

    	if(sfM2P1Button.getText().equals("") || sfM2P2Button.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Match not ready!");

			alert.showAndWait();
    	} else {
    		this.mModule.setCurrentMatch(tModule.getCurrentTournament().getT().getMatch6());
    		MatchView mView = new MatchView();
    		mView.display();
    	}
    }

    public void blockTextFields() {
    	
    	qfM1P1Button.setEditable(false);
    	qfM1P2Button.setEditable(false);
    	qfM2P1Button.setEditable(false);
    	qfM2P2Button.setEditable(false);
    	qfM3P1Button.setEditable(false);
    	qfM3P2Button.setEditable(false);
    	qfM4P1Button.setEditable(false);
    	qfM4P2Button.setEditable(false);
    	sfM1P1Button.setEditable(false);
    	sfM1P2Button.setEditable(false);
    	sfM2P1Button.setEditable(false);
    	sfM2P2Button.setEditable(false);
    	fP1Button.setEditable(false);
    	fP2Button.setEditable(false);
    }
    @FXML
    public void initialize() {
    	
    	blockTextFields();
    	setTextFields();
    }
    
    private void setTextFields() {
    	
    	if(tModule.getCurrentTournament()!=null) {
    	this.nameLabel.setText(tModule.getCurrentTournament().getT().getName());
    	int codeOfType = tModule.getCurrentTournament().getT().classifyTournament();
		String type="";
		if(codeOfType==0)
			type = "FINISHED";
		if(codeOfType==1)
			type = "ONGOING";
		if(codeOfType==2)
			type = "UPCOMING";
		this.statusLabel.setText(type);
		if(tModule.getCurrentTournament().getT().getMatch1()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch1().getPlayer1()!=null)
				qfM1P1Button.setText(tModule.getCurrentTournament().getT().getMatch1().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch1().getPlayer2()!=null)
				qfM1P2Button.setText(tModule.getCurrentTournament().getT().getMatch1().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch2()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch2().getPlayer1()!=null)
				qfM2P1Button.setText(tModule.getCurrentTournament().getT().getMatch2().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch2().getPlayer2()!=null)
				qfM2P2Button.setText(tModule.getCurrentTournament().getT().getMatch2().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch3()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch3().getPlayer1()!=null)
				qfM3P1Button.setText(tModule.getCurrentTournament().getT().getMatch3().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch3().getPlayer2()!=null)
				qfM3P2Button.setText(tModule.getCurrentTournament().getT().getMatch3().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch4()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch4().getPlayer1()!=null)
				qfM4P1Button.setText(tModule.getCurrentTournament().getT().getMatch4().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch4().getPlayer2()!=null)
				qfM4P2Button.setText(tModule.getCurrentTournament().getT().getMatch4().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch5()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch5().getPlayer1()!=null)
				sfM1P1Button.setText(tModule.getCurrentTournament().getT().getMatch5().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch5().getPlayer2()!=null)
				sfM1P2Button.setText(tModule.getCurrentTournament().getT().getMatch5().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch6()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch6().getPlayer1()!=null)
				sfM2P1Button.setText(tModule.getCurrentTournament().getT().getMatch6().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch6().getPlayer2()!=null)
				sfM2P2Button.setText(tModule.getCurrentTournament().getT().getMatch6().getPlayer2().getName());
		}
		if(tModule.getCurrentTournament().getT().getMatch7()!=null) {
			if(tModule.getCurrentTournament().getT().getMatch7().getPlayer1()!=null)
				fP1Button.setText(tModule.getCurrentTournament().getT().getMatch7().getPlayer1().getName());
			if(tModule.getCurrentTournament().getT().getMatch7().getPlayer2()!=null)
				fP2Button.setText(tModule.getCurrentTournament().getT().getMatch7().getPlayer2().getName());
			int x = mModule.isOver(tModule.getCurrentTournament().getT().getMatch7());
			if(x==1)
				this.winnerLabel.setText(tModule.getCurrentTournament().getT().getMatch7().getPlayer1().getName());
			if(x==2)
				this.winnerLabel.setText(tModule.getCurrentTournament().getT().getMatch7().getPlayer2().getName());
	    	}
		}

    }

	public void update(Observable arg0, Object arg1) {
		setTextFields();
		
	}
}
