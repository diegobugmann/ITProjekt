package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Commons.Message;

public class ServerModel {
	
	private final Logger logger = Logger.getLogger("");
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Game> games = new ArrayList<Game>();
	private ServerSocket listener;
	private volatile boolean stop = false;
	
	private int port;
	
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
							logger.info(e.toString());
						}
					}
				}
			};
			Thread thread = new Thread(run, "ServerSocket");
			thread.start(); //Starten des threads und somit aufrufen von run()
		} catch (IOException e) {
			logger.info(e.toString());
		}
	}
	
	//An alle User broadcasten
	public void broadcast(Message msg) {
		logger.info("Broadcasting to all clients");
		for (User u : users) {
			msg.send(u.getSocket()); //Methode, um Msg zu versenden
		}
	}
	
	//Nur an bestimmte User broadcasten
	public void broadcast(ArrayList<Player> players, Message msg) {
		for (Player p : players) {
			msg.send(p.getSocket());
		}
	}
	
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
				logger.info(e.toString());
			}
		}
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}

	public ArrayList<Game> getGames() {
		return games;
	}
	
	public void addGame(Game g) {
		this.games.add(g);
	}
	
}