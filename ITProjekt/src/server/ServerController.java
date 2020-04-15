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
			g.getNumOfPlayers().addListener( (obs, oV, nV) -> {
				if ((int) nV == 4) {
					ArrayList<Player> players = g.getPlayers();
					Message msgOut = new Simple_Message(Simple_Message.Msg.Game_Start);
					model.broadcast(players, msgOut); //Spiel starten
					//TODO broadcast GameList? gem. Diagramm
					g.getDeck().dealCards(players);
					for (Player p : players) {
						p.organizeHand();
						msgOut = new Message_Hand(p.getHand());
						msgOut.send(p.getSocket());
					}
					if (!g.isSchieber()) {
						//TODO Message Ansage_Trumpf bei startspieler
					} else {
						//TODO Message Ansage_Punkte bei allen, Trumpf bestimmen
					}
			}
			});
		}
		
	}

}
