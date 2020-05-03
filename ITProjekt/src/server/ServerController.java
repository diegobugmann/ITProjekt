package server;

import java.util.ArrayList;

import Commons.Message;
import Commons.Message_GameList;
import Commons.Message_Hand;
import Commons.Message_Players;
import Commons.Message_Trumpf;
import Commons.Simple_Message;
import javafx.collections.ListChangeListener;

/**
 * @author digib
 */
public class ServerController {
	
	private ServerModel model;
	private ServerView view;
	
	/**
	 * @author digib
	 * @param model, view
	 */
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
				addGameEvents(g);
			}
		});
		
	}
	
	/**
	 * @author digib
	 * @param game
	 * add events to game whenever new game is added
	 */
	public void addGameEvents(Game g) {
		
		//listener is waiting for 4 players to join a game
		g.getNumOfPlayersAsProperty().addListener( (obs, oV, nV) -> {
			//set numOfCurrentPlayers in castinggame
			for (Commons.Game game : model.getCastedGames()) {
				if (g.getGameId() == game.getGameId())
					game.setCurrentNumOfPlayers((int)nV);
			}
			//start game at 4 players
			if ((int) nV == 4) {
				g.setFollowingPlayers(); //give every player a following Player (for gamedirection)
				g.setStartingPlayer(g.getTeam(0).getPlayerList().get(0));
				g.setBeginningOrder();
				ArrayList<Player> players = g.getPlayers();
				Message msgOut = new Simple_Message(Simple_Message.Msg.Game_Start);
				model.broadcast(players, msgOut); 
				ArrayList<String> playersInOrder = g.getPlayersInOrder();
				msgOut = new Message_Players(playersInOrder);
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
		
		//as soon as all players guessed their points, the game starts with the first turn
		g.getNumOfAnsagenAsProperty().addListener( (obs, oV, nV) -> {
			if ((int) nV == 4) {
				g.startPlaying();
			}
		});
		
		
	}

}
