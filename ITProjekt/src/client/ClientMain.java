package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientMain extends Application{
	
	private ClientModel model;
	private ClientController controller;
	private ClientView view;

	public static void main(String[] args) {
		launch();
		

	}
	
	public void start(Stage primaryStage) throws Exception{
		model = new ClientModel();
		view = new ClientView(primaryStage, model);
		controller = new ClientController(model, view, primaryStage);
		controller.serverClosed.addListener(event -> {
	        Platform.exit();
	        System.exit(0);
		});
		primaryStage.setOnCloseRequest(event -> {
			controller.processExit(primaryStage);
		});
	}

}
