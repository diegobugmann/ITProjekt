package client;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	
	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
	}


	public void processExit(WindowEvent event, Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}

}
