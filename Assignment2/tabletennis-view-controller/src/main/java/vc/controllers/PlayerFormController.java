package vc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dto.PlayerDTO;
import model.modules.PlayerHelper;
import model.modules.PlayerModule;
import vc.views.PlayerForm;

public class PlayerFormController {

		private PlayerDTO player;
		private PlayerForm view;
		private boolean addOrUpdate=true;
		private PlayerModule pModule;
		
		public PlayerFormController() {
		
			pModule = PlayerModule.getInstance();
		}
	
		public void setAdd() {
			
			this.addOrUpdate = true;
		}
		
		public void setUpdate() {
			
			this.addOrUpdate = false;
		}
		
		public PlayerForm getView() {
			
			return this.view;
		}
		public void setPlayer(PlayerDTO player) {
			
			this.player = player;
		}
	 	@FXML
	    private TextField nameTF;

	    @FXML
	    private TextField emailTF;

	    @FXML
	    private TextField passwordTF;

	    @FXML
	    private Button finishButton;

	    @FXML
	    void handleFinishButton(ActionEvent event) {
	    		
	    	if(addOrUpdate==true) {
	    		
	    		pModule.createPlayer(nameTF.getText(), emailTF.getText(), passwordTF.getText());
	    	} else {
	    		
	    		pModule.updatePlayer(new PlayerDTO(nameTF.getText(), emailTF.getText(), passwordTF.getText(), player.getMoney(), player.getIsAdmin()));
	    	}
	    }
	    
	    @FXML
	    public void initialize() {
	    
	    	if(PlayerHelper.currentPlayer !=null) {
	    		
	    		forUpdate();
	    	}
	    }
	    
	    public void forUpdate() {
	    	
	    	player = PlayerHelper.currentPlayer;
	    	addOrUpdate = false;
	    	this.nameTF.setText(PlayerHelper.currentPlayer.getName());
	    	this.emailTF.setText(PlayerHelper.currentPlayer.getEmail());
	    	this.passwordTF.setText(PlayerHelper.currentPlayer.getPassword());
	    }
	    
	    
}
