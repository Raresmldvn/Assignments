package ui;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import business.dto.MatchDTO;
import business.dto.SetDTO;
import business.modules.MatchModule;
import business.modules.PlayerHelper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MatchScene {

	private Label tournamentName;
	private Label stageType;
		
	private Label namePlayer1;
	private Label namePlayer2;
	private Label matchScorePlayer1;
	private Label matchScorePlayer2;
	
	private Label set1ScoreP1;
	private Label set1ScoreP2;
	
	private Label set2ScoreP1;
	private Label set2ScoreP2;
	
	private Label set3ScoreP1;
	private Label set3ScoreP2;
	
	private Label set4ScoreP1;
	private Label set4ScoreP2;
	
	private Label set5ScoreP1;
	private Label set5ScoreP2;
	
	private Button increaseP1;
	private Button increaseP2;
	
	private MatchModule matchModule;
	
	private int idOfAllowedChanges;
	private TournamentScene tScene;
	public MatchScene(TournamentScene upper, String tournamentName, MatchDTO matchInfo) {
		
		tScene = upper;
		idOfAllowedChanges = matchInfo.getId();
		this.matchModule = new MatchModule(idOfAllowedChanges);
		
		this.tournamentName = new Label(tournamentName);
		this.tournamentName.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		setStage(matchInfo.getStage());
		this.stageType.setFont(Font.font("Verdana", FontWeight.THIN, 20));
		
		this.namePlayer1 = new Label(matchInfo.getNamePlayer1());
		this.namePlayer1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.namePlayer2 = new Label(matchInfo.getNamePlayer2());
		this.namePlayer2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.matchScorePlayer1 = new Label();
		this.matchScorePlayer1.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		this.matchScorePlayer2 = new Label();
		this.matchScorePlayer2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		
		
		this.set1ScoreP1 = new Label();
		this.set1ScoreP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.set1ScoreP2 = new Label();
		this.set1ScoreP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.set2ScoreP1 = new Label();
		this.set2ScoreP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.set2ScoreP2 = new Label();
		this.set2ScoreP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.set3ScoreP1 = new Label();
		this.set3ScoreP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.set3ScoreP2 = new Label();
		this.set3ScoreP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.set4ScoreP1 = new Label();
		this.set4ScoreP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.set4ScoreP2 = new Label();
		this.set4ScoreP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.set5ScoreP1 = new Label();
		this.set5ScoreP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.set5ScoreP2 = new Label();
		this.set5ScoreP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		this.increaseP1 = new Button("Increase");
		this.increaseP1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		this.increaseP2 = new Button("Increase");
		this.increaseP2.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		setMatchesScores(matchInfo.getSets());
		setButtonStates();
	}
	
	
	private void setStage(int type) {
		
		if(type==1)
			this.stageType = new Label("Quarterfinal");
		else if(type==2)
			this.stageType = new Label("Semifinal");
		else
			this.stageType = new Label("Final");
	}
	private void setMatchesScores(SetDTO[] sets) {
		
		setTextFieldScore(set1ScoreP1, set1ScoreP2, sets[0], 0);
		setTextFieldScore(set2ScoreP1, set2ScoreP2, sets[1], 1);
		setTextFieldScore(set3ScoreP1, set3ScoreP2, sets[2], 2);
		setTextFieldScore(set4ScoreP1, set4ScoreP2, sets[3], 3);
		setTextFieldScore(set5ScoreP1, set5ScoreP2, sets[4], 4);
		updateMatchScore();
		
	}
	
	private void updateMatchScore() {
		
		int [] matchScores = matchModule.getCurrentMatchScore();
		
		matchScorePlayer1.setText(Integer.toString(matchScores[0]));
		matchScorePlayer2.setText(Integer.toString(matchScores[1]));
	}
	
	private void setButtonStates() {
		
		if(!PlayerHelper.playerName.equals("Admin")) {
			
			if(!(namePlayer1.getText().equals(PlayerHelper.playerName))&&!(namePlayer2.getText().equals(PlayerHelper.playerName))) {
					increaseP1.setDisable(true);
					increaseP2.setDisable(true);
			}
	}
	}
	private void setTextFieldScore(Label l1, Label l2, SetDTO s, int index) {
		
		if((s.getScore1()!=0||s.getScore2()!=0)|| index == 0) {
		l1.setText(Integer.toString(s.getScore1()));
		l2.setText(Integer.toString(s.getScore2()));
		if(matchModule.checkForSetWinner(s.getScore1(), s.getScore2())!=0) {
			
			l1.setTextFill(Color.RED);
			l2.setTextFill(Color.RED);
		}
		}
	}
	private Scene getMatchScene() {
		
		//Player 1 pane
		VBox player1Pane = new VBox();
		player1Pane.getChildren().addAll(this.namePlayer1, new Label(" "), this.matchScorePlayer1, new Label(" "), 
						   this.set1ScoreP1, new Label(" "), this.set2ScoreP1, new Label(" "),
						   this.set3ScoreP1, new Label(" "), this.set4ScoreP1, new Label(" " ),
						   this.set5ScoreP1, new Label(" "), increaseP1);
		player1Pane.setMinSize(200, 400);
		player1Pane.setAlignment(Pos.TOP_CENTER);
		
		//Player 2 pane
		VBox player2Pane = new VBox();
		player2Pane.getChildren().addAll(this.namePlayer2, new Label(" "), this.matchScorePlayer2, new Label(" "), 
						   this.set1ScoreP2, new Label(" "), this.set2ScoreP2, new Label(" "),
						   this.set3ScoreP2, new Label(" "), this.set4ScoreP2, new Label(" " ),
						   this.set5ScoreP2, new Label(" "), increaseP2);
		player2Pane.setMinSize(200, 400);
		player2Pane.setAlignment(Pos.TOP_CENTER);
		
		//Middle pane
		VBox middlePane = new VBox();
		Label minusLabel = new Label("-");
		minusLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		middlePane.getChildren().addAll(new Label(),new Label(),new Label(),minusLabel, new Label(),  getLabelWithNr(1), new Label(" "),
										getLabelWithNr(2), new Label(" "), getLabelWithNr(3),
										new Label(" "), getLabelWithNr(4), new Label(" "), 
										getLabelWithNr(5));
		middlePane.setMinSize(100,300);
		middlePane.setAlignment(Pos.TOP_CENTER);
		
		//Title pane
		VBox titlePane = new VBox();
		titlePane.getChildren().addAll(tournamentName, stageType);
		titlePane.setAlignment(Pos.CENTER);
		
		//Lower part of the frame
		HBox matchInfo = new HBox();
		matchInfo.getChildren().addAll(player1Pane, middlePane, player2Pane);
		
		//Final pane
		VBox finalPane = new VBox();
		finalPane.getChildren().addAll(titlePane, matchInfo);
		finalPane.setAlignment(Pos.CENTER);
		
		increaseP1.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleIncrease1();
			}
	    });
		
		increaseP2.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				handleIncrease2();
			}
	    });
		Scene scene = new Scene(finalPane, 500,500);
		return scene;
		
	}
	
	public void displayScene() {
		
		Scene scene = getMatchScene();
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	private Label getLabelWithNr(int nr) {
		
		Label label = new Label("Set " + Integer.toString(nr));
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		return label;
		
	}
	
	private void handleIncrease1() {
		
		int result = matchModule.increase(true);
		int code_outcome = result/10;
		int set = result%10;
		if(result==-1) {
			
			alertError("This match is over!");
		} else {
			Label label = selectLabel(set,1);
			if(label.getText().equals("")) {
				label.setText(Integer.toString(1));
				selectLabel(set,2).setText(Integer.toString(0));
			}
			else
				label.setText(Integer.toString(Integer.parseInt(label.getText())+1));
			updateMatchScore();
			if(code_outcome==1||code_outcome==2) {
				alertError("Set over");
				label.setTextFill(Color.RED);
				selectLabel(set,2).setTextFill(Color.RED);
				if(matchModule.isOver()) {
					alertError("Match won by " + this.namePlayer1.getText() + "!");
					matchModule.insertIntoNextMatch(true, tournamentName.getText());
				}
			}
		}
	}
	
	
	private void handleIncrease2() {
		
		int result = matchModule.increase(false);
		int code_outcome = result/10;
		int set = result%10;
		Label label = selectLabel(set,2);
		if(result==-1) {
			
			alertError("This match is over!");
		} else {
			if(label.getText().equals("")) {
				label.setText(Integer.toString(1));
				selectLabel(set,1).setText(Integer.toString(0));
			}
			else
				label.setText(Integer.toString(Integer.parseInt(label.getText())+1));
			updateMatchScore();
			if(code_outcome==1||code_outcome==2) {
				alertError("Set over");
				label.setTextFill(Color.RED);
				selectLabel(set,1).setTextFill(Color.RED);
				if(matchModule.isOver()) {
					alertError("Match won by " + this.namePlayer2.getText() + "!");
					matchModule.insertIntoNextMatch(false, tournamentName.getText());
				}
			}
		}
	}
	
	private Label selectLabel(int set, int player) {
		
		int for_switch = set*10 + player;
		
		switch(for_switch) {
			
		case 11: return set1ScoreP1;
		case 12: return set1ScoreP2;
		case 21: return set2ScoreP1;
		case 22: return set2ScoreP2;
		case 31: return set3ScoreP1;
		case 32: return set3ScoreP2;
		case 41: return set4ScoreP1;
		case 42: return set4ScoreP2;
		case 51: return set5ScoreP1;
		case 52: return set5ScoreP2;
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
}
