package ui.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Article;
import ui.ArticleView;
import ui.MainView;

public class ArticleController {

	private List<Article> related;
	public ArticleController() {
		
		
	}
    @FXML
    private Label titleLabel;

    @FXML
    private TextArea bodyText;

    @FXML
    private Label authorLabel;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void handleBack(ActionEvent event) {

    	Stage stage = (Stage)comboBox.getScene().getWindow();
    	stage.setScene(MainView.getInstance().getScene());
    }
    
    public void setTitle(String title) {
    	
    	titleLabel.setText(title);
    }
    
    public void setAuthor(String author) {
    	
    	authorLabel.setText(author);
    }
    
    public void setBody(String body) {
    	
    	bodyText.setText(body);
    }
    @FXML
    void openArticle(ActionEvent event) {
    	
    	Article chosen = related.get(comboBox.getSelectionModel().getSelectedIndex());
    	ArticleView view = new ArticleView();
    	ArticleController controller = view.getController();
    	controller.setTitle(chosen.getTitle());
    	controller.setAuthor(chosen.getAuthor().getName());
    	controller.setBody(chosen.getBody());
    	controller.setRelatedArticles(chosen.getRelatedArticles());
    	Stage window = new Stage(); 
		window.setTitle("Related");
		window.setScene(view.getScene());
		window.show();
    }
    
    public void setRelatedArticles(List<Article> articleList) {
    	
    	related = articleList;
    	for(Article A : articleList) {
    		
    		comboBox.getItems().add(A.getTitle());
    	}
    }
    
    @FXML
    public void initialize() {
    	//titleLabel.setMinSize(200, 500);
    	bodyText.setEditable(false);
    	bodyText.setWrapText(true);
    }

}