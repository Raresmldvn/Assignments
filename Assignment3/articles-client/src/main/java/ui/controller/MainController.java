package ui.controller;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import listener.ServerListener;
import model.Article;
import model.ArticleList;
import model.User;
import ui.AdminView;
import ui.ArticleView;
import ui.WriterView;

public class MainController implements Observer {

	private ServerListener listener;
	private ArticleList articleList;
	
	public MainController() {
		
		listener = ServerListener.getInstance();
		articleList = ArticleList.getInstance();
		articleList.addObserver(this);
	}
    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private Button logInButton;

    @FXML
    private TableView<Article> articleTabel;

    @FXML
    private TableColumn<Article, String> titleColumn;

    @FXML
    private TableColumn<Article, String> abstractColum;

    @FXML
    void handleLogIn(ActionEvent event) {

    	User constructed = new User();
    	constructed.setEmail(emailTF.getText());
    	constructed.setPassword(passwordTF.getText());
    	User user = listener.logInCommand(constructed);
    	//System.out.println(user.getName() + user.getName() + user.getPassword());
    	if(user.getName()==null) {
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ERROR");
			alert.setContentText("Incorrect username!");

			alert.showAndWait();
    	} else {
    		
    		if(user.getPassword()==null) {
    			
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Information Dialog");
    			alert.setHeaderText("ERROR");
    			alert.setContentText("Incorrect password!");

    			alert.showAndWait();
    		} else {
    			
    			if(user.isAdmin()==false) {
    				WriterView writerView = new WriterView();
    				WriterController controller = writerView.getController();
    				controller.setUser(user);
    				Stage stage = (Stage)emailTF.getScene().getWindow();
    				stage.setScene(writerView.getScene());
    			} else {
    				AdminView adminView = new AdminView();
    				AdminController controller = adminView.getController();
    				controller.setAdmin(user);
    				Stage stage = (Stage)emailTF.getScene().getWindow();
    				stage.setScene(adminView.getScene());
    				
    			}
    		}
    	}
    }

    @FXML
    void openArticle(MouseEvent event) {

    	Article chosen = articleTabel.getSelectionModel().getSelectedItem();
    	System.out.println(chosen);
    	ArticleView articleView = new ArticleView();
    	ArticleController controller = articleView.getController();
    	System.out.println(controller);
    	controller.setTitle(chosen.getTitle());
    	controller.setAuthor(chosen.getAuthor().getName());
    	controller.setBody(chosen.getBody());
    	controller.setRelatedArticles(chosen.getRelatedArticles());
    	Stage stage = (Stage) emailTF.getScene().getWindow();
    	stage.setScene(articleView.getScene());
    }
    
    @FXML
    public void initialize() {
    	
    	listener.getArticlesCommand();
    	populateTables();
    }

    
    private void populateTables() {
    	
    	titleColumn.setCellValueFactory(new PropertyValueFactory<Article, String>("title"));
    	abstractColum.setCellValueFactory(new PropertyValueFactory<Article, String>("articleAbstract"));
    	List<Article> articles = articleList.getArticles();
    	articleTabel.getItems().setAll(articles);
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		articleTabel.getItems().removeAll();
    	List<Article> articles = articleList.getArticles();
    	articleTabel.getItems().setAll(articles);	
		
	}
}
