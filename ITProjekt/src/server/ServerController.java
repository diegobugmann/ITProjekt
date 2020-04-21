package server;

import java.util.ArrayList;

import Commons.Message;
import Commons.Message_GameList;
import Commons.Message_Hand;
import Commons.Message_Trumpf;
import Commons.Simple_Message;
import javafx.collections.ListChangeListener;

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
		
		model.getGames().addListener( (ListChangeListener) (c) -> {
			for (Game g : model.getGames()) {
				addGameEvents(g); //events auf Game hinzufügen
			}
		});
		
	}
	
	
	
	public void addGameEvents(Game g) {
		
		//listener is waiting for 4 players to join a game
		g.getNumOfPlayersAsProperty().addListener( (obs, oV, nV) -> {
			
			//numOfCurrentPlayers im castedGame anpassen
			for (Commons.Game game : model.getCastedGames()) {
				if (g.getGameId() == game.getGameId())
					game.setCurrentNumOfPlayers((int)nV);
			}
			
			//Bei 4 Spielern das Spiel starten
			if ((int) nV == 4) {
				ArrayList<Player> players = g.getPlayers();
				Message msgOut = new Simple_Message(Simple_Message.Msg.Game_Start);
				model.broadcast(players, msgOut); 
				g.dealCards();
				//TODO broadcast GameList? gem. Diagramm, um zu zeigen, dass das Spiel am laufen ist
				if (g.isSchieber()) {
					Player starter = g.getStartingPlayer();
					msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Trumpf);
					msgOut.send(starter.getSocket());
				} else {
					g.createRandomTrumpf();
					msgOut = new Message_Trumpf(g.getTrumpf());
					model.broadcast(players, msgOut);
					msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Points);
					model.broadcast(players, msgOut);
				}
			}
		});
		
		//Methode, die in einem Game nach allen 4 Ansagen weiterfährt
		g.getNumOfAnsagenAsProperty().addListener( (obs, oV, nV) -> {
			if ((int) nV == 4) {
				
			}
		});
		
		
	}

}
