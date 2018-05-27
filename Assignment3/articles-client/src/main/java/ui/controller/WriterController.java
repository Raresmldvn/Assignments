package ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import listener.ServerListener;
import model.Article;
import model.ArticleList;
import model.User;
import ui.MainView;

public class WriterController implements Observer {

	public WriterController() {
		
    	articleList = ArticleList.getInstance();
    	listener = ServerListener.getInstance();
    	articleList.addObserver(this);
	}
	private User user;
	
    @FXML
    private TextField userTF;

    @FXML
    private TextField titleTF;

    @FXML
    private TextArea abstractTA;

    @FXML
    private TextArea bodyTA;

    @FXML
    private Button saveButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button logOutButton;

    @FXML
    private TableView<Article> articleTable;

    @FXML
    private TableColumn<Article, String> nameColumn;


    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button openButton;

 
	private ArticleList articleList;
	private ServerListener listener;
    
	
    @FXML
    void handleClear(ActionEvent event) {
    	
    	titleTF.clear();
    	bodyTA.clear();
    	abstractTA.clear();
    }

    @FXML
    void handleLogOut(ActionEvent event) {

    	Stage stage = (Stage)titleTF.getScene().getWindow();
    	stage.setScene(MainView.getInstance().getScene());
    }

    @FXML
    void handleSave(ActionEvent event) {
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Current project is modified");
    	alert.setContentText("Save?");
    	ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
    	ButtonType noButton = new ButtonType("NO", ButtonData.NO);
    	alert.getButtonTypes().setAll(okButton, noButton);
    	alert.showAndWait().ifPresent(type -> {
    	        if (type == okButton) {
    	        	System.out.println("OK");
    	        	Article A = new Article();
    	        	A.setAuthor(user);
    	        	A.setArticleAbstract(abstractTA.getText());
    	        	A.setBody(bodyTA.getText());
    	        	A.setTitle(titleTF.getText());
    	        	A.setRelatedArticles(listOfRelatedArticles());
    	        	listener.saveArticle(A);
    	        } 
    	});
    }
    
    @FXML
    void handleUpdate(ActionEvent event) {
    	
    	System.out.println("Aici");
    	Article A = articleTable.getSelectionModel().getSelectedItem();
    	if(A==null) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You must select an article!");
			alert.showAndWait();
    	} else {
    	if(!A.getAuthor().getEmail().equals(user.getEmail())) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You are not the writer of this article!");
			alert.showAndWait();
    	} else {
    		
    		Article newArticle = new Article();
    		newArticle.setBody(bodyTA.getText());
    		newArticle.setArticleAbstract(abstractTA.getText());
    		newArticle.setAuthor(this.user);
    		newArticle.setTitle(titleTF.getText());
    		listener.updateArticle(A, newArticle);
    	}
    	}
    	
    }

    @FXML
    void handleDelete(ActionEvent event) {
    	
    	Article A = articleTable.getSelectionModel().getSelectedItem();
    	if(A==null) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You must select an article!");
			alert.showAndWait();
    	} else {
    	if(!A.getAuthor().getEmail().equals(user.getEmail())) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("You are not the writer of this article!");
			alert.showAndWait();
    	} else {
    		
    		listener.deleteArticle(A);
    	}
    	}
    }
    
    @FXML 
    void handleOpen(ActionEvent event) {
    	
    	Article A = articleTable.getSelectionModel().getSelectedItem();
    	titleTF.setText(A.getTitle());
    	bodyTA.setText(A.getBody());
    	abstractTA.setText(A.getArticleAbstract());
    }
    
    public void setUser(User u) {
    	
    	user = u;
    	userTF.setText(u.getName());
    }
    
    public List<Article> listOfRelatedArticles() {
    	
    	ObservableList<Article> selected = articleTable.getSelectionModel().getSelectedItems();
    	List<Article> related = new ArrayList<Article>();
    	for(Article A : selected) {
    		
    		related.add(A);
    	}
    	return related;
    }
    @FXML
    public void initialize() {
    	abstractTA.setWrapText(true);
    	bodyTA.setWrapText(true);
    	articleTable.getSelectionModel().setSelectionMode(
    		    SelectionMode.MULTIPLE);

    	nameColumn.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
    	List<Article> articles = articleList.getArticles();
    	articleTable.getItems().setAll(articles);
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		List<Article> articles = articleList.getArticles();
    	articleTable.getItems().setAll(articles);
		
	}


}