package vc.views;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.modules.DaoConfiguration;

public class MainWindow extends Application{

	public void start(Stage primaryStage) throws Exception {
		LogInView view = LogInView.getInstance();
		primaryStage.setScene(view.getScene());
		primaryStage.show();
	}
	

	public static void main (String args[]) {
		
		launch(args);
	}
	
	@Override
	public void stop() {
		
		DaoConfiguration.closeHibernate();
		Platform.exit();
		
	}
}
