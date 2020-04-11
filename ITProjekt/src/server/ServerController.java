package server;

import java.util.ArrayList;

import Commons.Simple_Message;

public class ServerController {
	
	private ServerModel model;
	private ServerView view;
	
	public ServerController(ServerModel model, ServerView view) {
		this.model = model;
		this.view = view;
		
		view.btnStart.setOnAction(event -> { //starting server on buttonclick
			view.btnStart.setDisable(true);
			int port = Integer.parseInt(view.txtPort.getText());
			model.startServer(port);
		});
		view.primaryStage.setOnCloseRequest(event -> model.stopServer()); //stopping server when closing window
		
		for (Game g : model.getGames()) {
			g.getNumOfPlayers().addListener( (obs, oV, nV) -> {
				//TODO Spieleranzahl aktualisieren??
				if ((int) nV == 4) {
					//TODO Wenn 4 Spieler einem Spiel beigetreten sind, wird das Spiel gestartet
					ArrayList<? extends User> players = g.getPlayers();
					//model.broadcast(startGameMsg);
			}
			});
		}
		
	}

}
