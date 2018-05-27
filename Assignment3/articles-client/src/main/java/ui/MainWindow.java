package ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import listener.ServerListener;


public class MainWindow extends Application{

	public void start(Stage primaryStage) throws Exception {
		
		ServerListener.getInstance().start();
		MainView view  = MainView.getInstance();
		view.load();
		primaryStage.setScene(view.getScene());
		primaryStage.show();
	}
	

	public static void main (String args[]) {
		
		launch(args);
	}
	
	@Override
	public void stop() {
		ServerListener.getInstance().sendClose();
		ServerListener.getInstance().close();
		Platform.exit();
		
	}

}
