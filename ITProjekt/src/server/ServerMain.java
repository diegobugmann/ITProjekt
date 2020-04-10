package server;

import javafx.application.Application;
import javafx.stage.Stage;

public class ServerMain extends Application {
	
	private ServerModel model;
	private ServerController controller;
	private ServerView view;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		model = new ServerModel();
		view = new ServerView(model, primaryStage);
		controller = new ServerController(model, view);
	}

}