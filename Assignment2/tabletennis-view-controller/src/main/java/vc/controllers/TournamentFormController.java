package vc.controllers;

import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.dto.PaidTournamentDTO;
import model.modules.TournamentModule;

public class TournamentFormController {

	TournamentModule tModule;
	
	public TournamentFormController() {
		
		tModule = TournamentModule.getInstance();
	}
	
    @FXML
    private TextField nameTF;

    @FXML
    private TextField dayTF;

    @FXML
    private TextField monthTF;

    @FXML
    private TextField yearTF;

    @FXML
    private CheckBox paidCB;

    @FXML
    private TextField feeTF;

    @FXML
    private Button finishButton;

    @FXML
    void handleFinish(ActionEvent event) {

    	if(tModule.getCurrentTournament()==null) {
    	if(paidCB.isSelected()) {
    		tModule.createNewTournament(nameTF.getText(), dayTF.getText(), monthTF.getText(), yearTF.getText(), true, Float.parseFloat(feeTF.getText()));
    	} else {
    		tModule.createNewTournament(nameTF.getText(), dayTF.getText(), monthTF.getText(), yearTF.getText(), false, 0);
    	}
    	} else {
    		
    		if(paidCB.isSelected()==false) {
    			
    			tModule.updateTournament(nameTF.getText(), dayTF.getText(), monthTF.getText(),yearTF.getText(), "0");
    		} else {
    			
    			tModule.updateTournament(nameTF.getText(), dayTF.getText(), monthTF.getText(),yearTF.getText(), feeTF.getText());
    		}
    	}
    }
    
    @FXML
    public void initialize() {
    	
    	PaidTournamentDTO p = tModule.getCurrentTournament();
    	if(p!=null) {
    		
    		nameTF.setText(p.getT().getName());
    		Calendar C = Calendar.getInstance();
    		C.setTime(p.getT().getDate());
    		if(C.get(Calendar.DAY_OF_MONTH)<10)
    			dayTF.setText("0" + Integer.toString(C.get(Calendar.DAY_OF_MONTH)));
    		else
    			dayTF.setText(Integer.toString(C.get(Calendar.DAY_OF_MONTH)));
    		if(C.get(Calendar.MONTH)<10)
    			monthTF.setText("0" + Integer.toString(C.get(Calendar.MONTH)));
    		yearTF.setText(Integer.toString(C.get(Calendar.YEAR)));
    		if(p.getT().isPaid()==true) {
    			
    			paidCB.setSelected(true);
    			feeTF.setText(Float.toString(p.getFee()));
    		}
    	}
    }

}

