package server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import Commons.Message;
import Commons.MessageType;
import Commons.Message_CreateGame;
import Commons.Message_GameList;
import Commons.Message_JoinGame;

public class User {
	
	private int userID;
	private String name;
	private Socket clientSocket;
	private ServerModel model;
	private final Logger logger = Logger.getLogger("");
	
	public User(ServerModel model, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.model = model;
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Message msgIn = Message.receive(clientSocket);
						processMessage(msgIn);
						
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
	
	protected void processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());
		Message msgOut = null;
		switch (MessageType.getType(msgIn)) {
		
		case createGame : {
			boolean isSchieber = ((Message_CreateGame)msgIn).isSchieber();
			boolean germanCards = ((Message_CreateGame)msgIn).isGermanCards();
			int numOfRounds = ((Message_CreateGame)msgIn).getNumOfRounds();
			int winningPoints = ((Message_CreateGame)msgIn).getWinningPoints();
			Game g = new Game(germanCards, numOfRounds, winningPoints, isSchieber);
			Player p = new Player(this.model, this.clientSocket);
			g.addPlayer(p); //Player, welcher Spiel erstellt hat, hinzufügen
			model.addGame(g);
			msgOut = new Message_GameList();
			model.broadcast(msgOut); //TODO Message_GameList zurückschicken
			break;
		}
			
		case joinGame : {
			boolean added = false;
			Player p = new Player(model, clientSocket); //Player aus diesem User erstellen??
			for (Game g : model.getGames()) {
				if (g == ((Message_JoinGame)msgIn).getGame()) //dem richtigen Game hinzufügen
					added = g.addPlayer(p);
			}
			if (added) { //Wenn der Spieler hinzugefügt wurde, wird dies gebroadcasted
				msgOut = msgIn;
				model.broadcast(msgOut);
			} else {
				//TODO User benachrichtigen, dass er nicht joinen konnte
			}
			break;	
		}
			
	 }
		
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}

}
