package server;

import java.io.IOException;
import java.net.Socket;

import Commons.Message;
import Commons.Message_CreateGame;
import Commons.Message_JoinGame;

public class User {
	
	private int userID;
	private String name;
	private Socket clientSocket;
	private ServerModel model;
	
	public User(ServerModel model, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.model = model;
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while(true) {
					Message msg;
					try {
						msg = Message.receive(clientSocket);
						 if (msg instanceof Message_CreateGame) {
								boolean isSchieber = ((Message_CreateGame)msg).isSchieber();
								boolean germanCards = ((Message_CreateGame)msg).isGermanCards();
								int numOfRounds = ((Message_CreateGame)msg).getNumOfRounds();
								int winningPoints = ((Message_CreateGame)msg).getWinningPoints();
								Game g = new Game(germanCards, numOfRounds, winningPoints, isSchieber);
								model.addGame(g);
								model.broadcast(msg); //TODO Message_GameList zurückschicken
								//TODO den Client, welcher das Game erstellt hat, dem Game hinzufügen

						 }
							// Hier wird entschieden, bei welchen Msgs was gemacht wird
						 if (msg instanceof Message_JoinGame) {
							 boolean added = false;
							 Player p = new Player(model, clientSocket); //Player aus diesem User erstellen??
							 for (Game g : model.getGames()) {
								 if (g == ((Message_JoinGame)msg).getGame()) { //dem richtigen Game hinzufügen
									 added = g.addPlayer(p);
								 }
							 }
							 if (added) //Wenn der Spieler hinzugefügt wurde, wird dies gebroadcasted
								 model.broadcast(msg);
							 else {
								 //TODO User benachrichtigen, dass er nicht joinen konnte
							 }
						 }
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //TODO Methode, die auf Msges wartet 
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}

}
