package vc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.dto.PlayerDTO;
import model.modules.PlayerHelper;
import model.modules.PlayerModule;

public class MoneyFormController {

	
	PlayerModule pModule;
	public MoneyFormController() {
		
		pModule = PlayerModule.getInstance();
	}
    @FXML
    private TextField playerTF;

    @FXML
    private TextField moneyTF;

    @FXML
    private TextField amountTF;

    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    void handleDeposit(ActionEvent event) {

    	if(amountTF.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Introduce an amount!");

			alert.showAndWait();
    	} else {
    		
    		float amount = Float.parseFloat(amountTF.getText());
    		float newAmount = PlayerHelper.currentPlayer.getMoney() + amount;
    		PlayerHelper.currentPlayer.setMoney(newAmount);
    		pModule.updatePlayer(PlayerHelper.currentPlayer);
    		moneyTF.setText(Float.toString(newAmount));
    	}
    }

    @FXML
    void handleWithdraw(ActionEvent event) {

    	if(amountTF.getText().equals("")) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Introduce an amount!");

			alert.showAndWait();
    	} else {
    	float amount = Float.parseFloat(amountTF.getText());
		float newAmount = PlayerHelper.currentPlayer.getMoney() - amount;
		if(newAmount<=0) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Not enough money!");

			alert.showAndWait();
		} else {
		PlayerHelper.currentPlayer.setMoney(newAmount);
		pModule.updatePlayer(PlayerHelper.currentPlayer);
		moneyTF.setText(Float.toString(newAmount));
		}
    	}
    }
    
    public void initialize() {
    	
    	this.playerTF.setText(PlayerHelper.currentPlayer.getName());
    	this.moneyTF.setText(Float.toString(PlayerHelper.currentPlayer.getMoney()));
    }

}