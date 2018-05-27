package vc.controllers;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.modules.MatchModule;
import model.modules.PlayerHelper;
import model.modules.TournamentModule;

public class MatchViewController implements Observer{

	
	private MatchModule mModule;
	private TournamentModule tModule;
	
	public MatchViewController() {
		
		this.mModule = MatchModule.getInstance();
		this.tModule = TournamentModule.getInstance();
	}
    @FXML
    private Label stageLabel;

    @FXML
    private Label tournamentLabel;

    @FXML
    private Label p1Label;

    @FXML
    private Label p2Label;

    @FXML
    private Label m2Label;

    @FXML
    private Label m1Label;

    @FXML
    private Label set12Label;

    @FXML
    private Label set11Label;

    @FXML
    private Label set21Label;

    @FXML
    private Label set22Label;

    @FXML
    private Label set31Label;

    @FXML
    private Label set32Label;

    @FXML
    private Label set41Label;

    @FXML
    private Label set42Label;

    @FXML
    private Label set51Label;

    @FXML
    private Label set52Label;

    @FXML
    private Label winnerLabel;

    @FXML
    private Button p1Button;

    @FXML
    private Button p2Button;

    @FXML
    void handleIncrease1(ActionEvent event) {
    	
    	int result = mModule.increase(true);
		int code_outcome = result/10;
		int set = result%10;
		if(result==-1) {
			
			alertError("This match is over!");
		} else {
			Label label = selectLabel(set,1);
				label.setText(Integer.toString(Integer.parseInt(label.getText())+1));
			if(code_outcome==1||code_outcome==2) {
				alertError("Set over");
				updateMatchScore();
				label.setTextFill(Color.RED);
				selectLabel(set,2).setTextFill(Color.RED);
				if(mModule.isOver()) {
					alertError("Match won by " + this.p1Label.getText() + "!");
					updateMatchWinner();
					mModule.insertIntoNextMatch(true, this.tournamentLabel.getText());
				}
			}
		}
		
		
    }

    @FXML
    void handleIncrease2(ActionEvent event) {
    	int result = mModule.increase(false);
		int code_outcome = result/10;
		int set = result%10;
		if(result==-1) {
			
			alertError("This match is over!");
		} else {
			Label label = selectLabel(set,2);
				label.setText(Integer.toString(Integer.parseInt(label.getText())+1));
			if(code_outcome==1||code_outcome==2) {
				alertError("Set over");
				updateMatchScore();
				label.setTextFill(Color.RED);
				selectLabel(set,2).setTextFill(Color.RED);
				if(mModule.isOver()) {
					alertError("Match won by " + this.p2Label.getText() + "!");
					updateMatchWinner();
					mModule.insertIntoNextMatch(false, this.tournamentLabel.getText());
				}
			}
		}
    }
    
private Label selectLabel(int set, int player) {
		
		int for_switch = set*10 + player;
		
		switch(for_switch) {
			
		case 11: return set11Label;
		case 12: return set12Label;
		case 21: return set21Label;
		case 22: return set22Label;
		case 31: return set31Label;
		case 32: return set32Label;
		case 41: return set41Label;
		case 42: return set42Label;
		case 51: return set51Label;
		case 52: return set52Label;
		default: return new Label();
		}
	}
	
	private void alertError(String error) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("DONE");
		alert.setContentText(error);

		alert.showAndWait();
	}
    @FXML
    void initialize() {
   
    	updateMatchScore();
    	if(mModule.isOver()==true) {
			
			if(mModule.getCurrentMatchScore()[0]>mModule.getCurrentMatchScore()[1])
				this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer1().getName());
			else
				this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer2().getName());
		}
    	this.tournamentLabel.setText(tModule.getCurrentTournament().getT().getName());
    	this.p1Label.setText(mModule.getCurrentMatch().getPlayer1().getName());
    	this.p2Label.setText(mModule.getCurrentMatch().getPlayer2().getName());
    	this.set11Label.setText(Integer.toString(mModule.getCurrentMatch().getSet1().getScore1()));
    	this.set12Label.setText(Integer.toString(mModule.getCurrentMatch().getSet1().getScore2()));
    	this.set21Label.setText(Integer.toString(mModule.getCurrentMatch().getSet2().getScore1()));
    	this.set22Label.setText(Integer.toString(mModule.getCurrentMatch().getSet2().getScore2()));
    	this.set31Label.setText(Integer.toString(mModule.getCurrentMatch().getSet3().getScore1()));
    	this.set32Label.setText(Integer.toString(mModule.getCurrentMatch().getSet3().getScore2()));
    	this.set41Label.setText(Integer.toString(mModule.getCurrentMatch().getSet4().getScore1()));
    	this.set42Label.setText(Integer.toString(mModule.getCurrentMatch().getSet4().getScore2()));
    	this.set51Label.setText(Integer.toString(mModule.getCurrentMatch().getSet5().getScore1()));
    	this.set52Label.setText(Integer.toString(mModule.getCurrentMatch().getSet5().getScore2()));
    	if(tModule.getCurrentTournament().getT().classifyTournament()==2) {
    		System.out.println("Aici");
    		this.p1Button.setDisable(true);
    		this.p2Button.setDisable(true);
    	}
    	if(PlayerHelper.currentPlayer.getIsAdmin()==false&&PlayerHelper.currentPlayer.getName().equals(p1Label.getText())==false&&PlayerHelper.currentPlayer.getName().equals(p2Label.getText())==false) {
    		System.out.println("SauAici");
    		this.p1Button.setDisable(true);
    		this.p2Button.setDisable(true);
    	}
    	
    }

    private void updateMatchScore() {
		
		int [] matchScores = mModule.getCurrentMatchScore();
		
		m1Label.setText(Integer.toString(matchScores[0]));
		m2Label.setText(Integer.toString(matchScores[1]));
	}

    private void updateMatchWinner() {
    	
    	if(mModule.getCurrentMatchScore()[0]>mModule.getCurrentMatchScore()[1])
			this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer1().getName());
		else
			this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer2().getName());
    }
	public void update(Observable o, Object arg) {
		updateMatchScore();
		if(mModule.isOver()==true) {
			
			if(mModule.getCurrentMatchScore()[0]>mModule.getCurrentMatchScore()[1])
				this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer1().getName());
			else
				this.winnerLabel.setText(mModule.getCurrentMatch().getPlayer2().getName());
		}
		this.set11Label.setText(Integer.toString(mModule.getCurrentMatch().getSet1().getScore1()));
    	this.set12Label.setText(Integer.toString(mModule.getCurrentMatch().getSet1().getScore2()));
    	this.set21Label.setText(Integer.toString(mModule.getCurrentMatch().getSet2().getScore1()));
    	this.set22Label.setText(Integer.toString(mModule.getCurrentMatch().getSet2().getScore2()));
    	this.set31Label.setText(Integer.toString(mModule.getCurrentMatch().getSet3().getScore1()));
    	this.set32Label.setText(Integer.toString(mModule.getCurrentMatch().getSet3().getScore2()));
    	this.set41Label.setText(Integer.toString(mModule.getCurrentMatch().getSet4().getScore1()));
    	this.set42Label.setText(Integer.toString(mModule.getCurrentMatch().getSet4().getScore2()));
    	this.set51Label.setText(Integer.toString(mModule.getCurrentMatch().getSet5().getScore1()));
    	this.set52Label.setText(Integer.toString(mModule.getCurrentMatch().getSet5().getScore2()));
		
	}
}

