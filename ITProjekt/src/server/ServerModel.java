package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Commons.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServerModel {
	
	private ObservableList<User> clients = FXCollections.observableArrayList();
	private ObservableList<Game> games = FXCollections.observableArrayList();
	private ServerSocket listener;
	private volatile boolean stop = false;
	
	private int port = 8080;

	public ServerModel() {
		//diese Infos bei Server mit GUI in Methode startServer(), die vom Contr aufgerufen wird
		try { 
			listener = new ServerSocket(port, 10, null);
			Runnable run = new Runnable() {
				@Override
				public void run() {
					while (!stop) {
						try {
							Socket socket = listener.accept(); // wait for clients to connect
							User user = new User(ServerModel.this, socket);
							clients.add(user);
						} catch (Exception e) {
							//logger.info(e.toString());
						}
					}
				}
			};
			Thread clientThread = new Thread(run, "ServerSocket");
			clientThread.start(); //Starten des threads und somit aufrufen von run()
		} catch (IOException e) {
			//logger.info(e.toString());
		}
		
	}
	
	public void broadcast(Message msg) {
		//logger.info("Broadcasting message to clients");
		for (User u : clients) {
			u.send(outMsg); //Methode, um Msg zu versenden
		}
	}
	
	
	public ObservableList<User> getClients() {
		return clients;
	}

	public ObservableList<Game> getGames() {
		return games;
	}
	
}
