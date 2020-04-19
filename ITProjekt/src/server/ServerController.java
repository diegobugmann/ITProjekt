package server;

import java.util.ArrayList;

import Commons.Message;
import Commons.Message_Hand;
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
		
		//listener is waiting for 4 players to join a game
		for (Game g : model.getGames()) {
			g.getNumOfPlayersAsProperty().addListener( (obs, oV, nV) -> {
				if ((int) nV == 4) {
					ArrayList<Player> players = g.getPlayers();
					Message msgOut = new Simple_Message(Simple_Message.Msg.Game_Start);
					model.broadcast(players, msgOut); 
					g.start(); //Spiel starten
					//TODO broadcast GameList? gem. Diagramm
					if (g.isSchieber()) {
						Player starter = g.getStartingPlayer();
						msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Trumpf);
						msgOut.send(starter.getSocket());
					} else {
						msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Points);
						model.broadcast(players, msgOut);
						//TODO Trumpf bestimmen
					}
			}
			});
		}
		
	}

}
