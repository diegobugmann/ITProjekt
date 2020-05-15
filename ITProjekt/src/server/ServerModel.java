package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Commons.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author digib
 */
public class ServerModel {
	
	private final Logger logger = Logger.getLogger("");
	private ArrayList<User> users = new ArrayList<User>();
	private ObservableList<Game> games = FXCollections.observableArrayList();
	private ArrayList<Commons.Game> castGames = new ArrayList<Commons.Game>();
	private ServerSocket listener;
	private volatile boolean stop = false;
	private int port;
	
	/**
	 * @author digib
	 * @param port
	 * starts the server and waits for sockets to connect
	 */
	public void startServer(int port) {
		logger.info("Starting server");
		logger.info("Listening on Port: "+port);
		this.port = port;
		try { 
			listener = new ServerSocket(port, 10, null);
			Runnable run = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = listener.accept(); // wait for users to connect
							User user = new Player(ServerModel.this, socket); //upcasting
							users.add(user);
						} catch (Exception e) {
							logger.info("Server successfully closed");
						}
					}
				}
			};
			Thread thread = new Thread(run, "ServerSocket");
			thread.start(); //starting of the thread and calling run()
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}
	
	/**
	 * @author digib
	 * @param msg
	 * broadcast messages to all users
	 */
	public void broadcast(Message msg) {
		logger.info("Broadcasting to all clients ("+msg.toString()+")");
		for (User u : users) {
			msg.send(u.getSocket());
		}
	}
	
	/**
	 * @author digib
	 * @param players, msg
	 * broadcast messages to only certain users (used for users playing a game)
	 */
	public void broadcast(ArrayList<Player> players, Message msg) {
		logger.info("Broadcasting to all clients in corresponding game ("+msg.toString()+")");
		for (Player p : players) {
			msg.send(p.getSocket());
		}
	}
	
	/**
	 * @author digib
	 * closes all sockets and shuts down the server
	 */
	public void stopServer() {
		logger.info("Stopping all clients");
		for (User u : users) {
			try {
				u.getSocket().close();
			} catch (IOException e1) {
				logger.info(e1.toString());
			}
		}
		logger.info("Stopping server");
		stop = true;
		if (listener != null) {
			try {
				listener.close();
			} catch (IOException e) {
				logger.info("Server successfully closed");
			}
		}
	}
	
	/**
	 * @author digib
	 * @param user
	 * removes a user from the userslist
	 */
	public void removeUser(User u) {
		users.remove(u);
	}
	
	/**
	 * @author digib
	 * @param Game
	 * adds a game to games and generates a corresponding Commons.Game (with the same ID but less info), used to send to client
	 */
	public void addGame(Game g) {
		this.games.add(g);
		this.castGames.add(new Commons.Game(g.isGermanCards(), g.getNumOfRounds(), g.getWinningPoints(), g.isSchieber(), g.getGameId()));
	}
	
	/**
	 * @author digib
	 * @param Game
	 * deletes a game from games and castGames
	 */
	public void deleteGame(Game g) {
		this.games.remove(g);
		for (Commons.Game gg : this.castGames) {
			if (gg.getGameId() == g.getGameId()) {
				this.castGames.remove(gg);
				return;
			}
		}
	}
	
	//getters and setters
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ObservableList<Game> getGames() {
		return games;
	}
	
	public ArrayList<Commons.Game> getCastedGames() {
		return castGames;
	}
	
}