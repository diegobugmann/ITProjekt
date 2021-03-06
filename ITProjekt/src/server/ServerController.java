package server;

import java.util.ArrayList;

import Commons.Message;
import Commons.Message_Players;
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
				if (!g.hasEvents()) {
					addGameEvents(g);
					g.setHasEvents(true);
				}
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
				if (g.isSchieber())
					g.setUpSchieber();
				else
					g.setUpDifferenzler();
			//delete game when no player is left in game
			} else if ((int) nV == 0) {
				model.deleteGame(g);
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
