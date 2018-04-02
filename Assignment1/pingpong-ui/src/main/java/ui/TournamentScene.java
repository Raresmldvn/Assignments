package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import business.dto.MatchDTO;
import business.dto.TournamentDTO;
import business.modules.TournamentModule;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
public class TournamentScene {

	//TextFields for matches
	private TextField qfMatch1Player1;
	private TextField qfMatch1Player2;
	
	private TextField qfMatch2Player1;
	private TextField qfMatch2Player2;
	
	private TextField qfMatch3Player1;
	private TextField qfMatch3Player2;
	
	private TextField qfMatch4Player1;
	private TextField qfMatch4Player2;
	
    private TextField sfMatch1Player1;
    private TextField sfMatch1Player2;
    
    private TextField sfMatch2Player1;
    private TextField sfMatch2Player2;
    
    private TextField fMatchPlayer1;
    private TextField fMatchPlayer2;
    
    //Title label
    private Label tournamentName;
    
    //Buttons
    private Button qfMatch1Button;
    private Button qfMatch2Button;
    private Button qfMatch3Button;
    private Button qfMatch4Button;
    private Button sfMatch1Button;
    private Button sfMatch2Button;
    private Button fMatchButton;
    
    TournamentDTO tournamentInfo;
    
    private TournamentModule tModule;
    public TournamentScene(TournamentDTO tournamentInfo) {
    	
    	this.tournamentInfo = tournamentInfo;
    	tModule = new TournamentModule();
    	initializeAll();
        tournamentName.setText(tournamentInfo.getName());
        
        setMatches();
    }
    
    private void setMatches() {
    	
    	 MatchDTO[] matchInfo = tournamentInfo.getMatches();
    	 
         setMatchProperly(matchInfo[0], qfMatch1Player1, qfMatch1Player2);
         setMatchProperly(matchInfo[1], qfMatch2Player1, qfMatch2Player2);
         setMatchProperly(matchInfo[2], qfMatch3Player1, qfMatch3Player2);
         setMatchProperly(matchInfo[3], qfMatch4Player1, qfMatch4Player2);
         setMatchProperly(matchInfo[4], sfMatch1Player1, sfMatch1Player2);
         setMatchProperly(matchInfo[5], sfMatch2Player1, sfMatch2Player2);
         setMatchProperly(matchInfo[6], fMatchPlayer1, fMatchPlayer2);
    }
    
    public void setTournamentInfo() {
    	
    	
    	setMatches();
    }
    private void setMatchProperly(MatchDTO match, TextField tf1, TextField tf2) {
    	
    	if(match!=null) {
    		tf1.setText(match.getNamePlayer1());
    		if(match.getNamePlayer2()!=null)
    			tf2.setText(match.getNamePlayer2());
    	}
    }
    private void initializeAll() {
    	
    	qfMatch1Player1 = new TextField();
    	qfMatch1Player1.setEditable(false);
    	qfMatch1Player2 = new TextField();
    	qfMatch1Player2.setEditable(false);
    	qfMatch2Player1 = new TextField();
    	qfMatch2Player1.setEditable(false);
    	qfMatch2Player2 = new TextField();
    	qfMatch2Player2.setEditable(false);
    	qfMatch3Player1 = new TextField();
    	qfMatch3Player1.setEditable(false);
    	qfMatch3Player2 = new TextField();
    	qfMatch3Player2.setEditable(false);
    	qfMatch4Player1 = new TextField();
    	qfMatch4Player1.setEditable(false);
    	qfMatch4Player2 = new TextField();
    	qfMatch4Player2.setEditable(false);
    	sfMatch1Player1 = new TextField();
    	sfMatch1Player1.setEditable(false);
    	sfMatch1Player2 = new TextField();
    	sfMatch1Player2.setEditable(false);
    	sfMatch2Player1 = new TextField();
    	sfMatch2Player1.setEditable(false);
    	sfMatch2Player2 = new TextField();
    	sfMatch2Player2.setEditable(false);
    	fMatchPlayer1 = new TextField();
    	fMatchPlayer1.setEditable(false);
    	fMatchPlayer2 = new TextField();
    	fMatchPlayer2.setEditable(false);
    	
    	tournamentName = new Label("Tournament");
    	tournamentName.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
    	qfMatch1Button = new Button("UPDATE");
    	qfMatch2Button = new Button("UPDATE");
    	qfMatch3Button = new Button("UPDATE");
    	qfMatch4Button = new Button("UPDATE");
    	sfMatch1Button = new Button("UPDATE");
    	sfMatch2Button = new Button("UPDATE");
    	fMatchButton = new Button("UPDATE");
    }
    private Scene getTournamentScene() {
    	
    	VBox qfPanel1 = new VBox();
    	
    	qfPanel1.getChildren().addAll(new Label("Quarterfinal 1"), qfMatch1Player1, qfMatch1Player2, qfMatch1Button,
    								  new Label(), new Label(), new Label(),
    								  new Label("Quarterfinal 2"), qfMatch2Player1, qfMatch2Player2, qfMatch2Button);
    	
    	VBox sfPanel1 = new VBox();
    	sfPanel1.getChildren().addAll(new Label(), new Label(), new Label(), new Label(),
    								  new Label("Semifinal 1"), sfMatch1Player1, sfMatch1Player2,
    								  sfMatch1Button);
    	
    	VBox fPanel = new VBox();
    	fPanel.getChildren().addAll(new Label(), new Label(), new Label(), new Label(),
    								new Label("        FINAL"), fMatchPlayer1, fMatchPlayer2,
    								fMatchButton);
    	
    	VBox sfPanel2 = new VBox();
    	sfPanel2.getChildren().addAll(new Label(), new Label(), new Label(), new Label(),
    								  new Label("Semifinal 2"), sfMatch2Player1, sfMatch2Player2,
    								  sfMatch2Button);
    	
    	VBox qfPanel2 = new VBox();
    	qfPanel2.getChildren().addAll(new Label("Quarterfinal 3"), qfMatch3Player1, qfMatch3Player2, qfMatch3Button,
    								  new Label(), new Label(), new Label(),
    								  new Label("Quarterfinal 4"), qfMatch4Player1, qfMatch4Player2, qfMatch4Button);
    	
    	HBox matchDraw = new HBox();
    	matchDraw.getChildren().addAll(new Label(" "), qfPanel1,new Label(" "), sfPanel1, new Label(" "), fPanel, new Label(" "), sfPanel2, new Label(" "), qfPanel2, new Label(" " ));
    	
    	HBox titlePane = new HBox();
    	titlePane.getChildren().add(tournamentName);
    	titlePane.setAlignment(Pos.CENTER);
    	VBox finalPane = new VBox();
    	finalPane.getChildren().addAll(titlePane,matchDraw);
    	attachEventsToButtons();
    	Scene scene = new Scene(finalPane, 700, 400);
    	return scene;
    }
    
    public void attachEventsToButtons() {
    	
    	qfMatch1Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(0);
			}
	    });
    	qfMatch2Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(1);
			}
	    });
    	
    	qfMatch3Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(2);
			}
	    });
    	
    	qfMatch4Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(3);
			}
	    });
    	
    	sfMatch1Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(4);
			}
	    });
    	sfMatch2Button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(5);
			}
	    });
    	
    	fMatchButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				genericHandleMatch(6);
			}
	    });
    }
    
    private void genericHandleMatch(int index) {
    	
    	MatchDTO match = tournamentInfo.getMatches()[index];
    	
    	if(match==null||match.getNamePlayer2()==null) 
    		alertError("Match is not ready!");
    	else {
     	MatchScene matchScene = new MatchScene(this,tournamentName.getText(), match);
    	matchScene.displayScene();
    	}
    }
    
	private void alertError(String error) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("ERROR");
		alert.setContentText(error);

		alert.showAndWait();
	}
	
    public void displayTournamentScene() {
    	
    	Scene scene = getTournamentScene();
    	Stage stage = new Stage();
    	stage.setTitle(tournamentName.getText());
    	stage.setScene(scene);
    	stage.show();
    }
    
	
}
