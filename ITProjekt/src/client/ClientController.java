package client;

import javafx.event.Event;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	protected WaitingScreen_Preloader splashScreen;
	
	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		
		
		view.lobbyView.joinBtn.setOnAction(event -> {
			try {
				startSplash(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
			
	}


	public void processExit(WindowEvent event, Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}
	
	private void startSplash(Event e) throws Exception {
		splashScreen = new WaitingScreen_Preloader();
		view.lobbyView.stage.close();
		splashScreen.start(stage);
	
	}

}
