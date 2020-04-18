package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Commons.Message;
import Commons.MessageType;
import Commons.Message_CreateGame;
import Commons.Message_Error;
import Commons.Message_GameList;
import Commons.Message_JoinGame;
import Commons.Message_Login;
import Commons.Simple_Message;

public class User {
	
	private static int nextID = 1;
	private int userID;
	private String name;
	private Socket clientSocket;
	private ServerModel model;
	private final Logger logger = Logger.getLogger("");
	
	public User(ServerModel model, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.model = model;
		userID = nextID++;
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
					try {
						while(true) {
							Message msgIn = Message.receive(clientSocket);
							processMessage(msgIn);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally { try { if (clientSocket != null) clientSocket.close(); } catch (IOException e) {}}
				}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	protected void processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());
		Message msgOut = null;
		switch (MessageType.getType(msgIn)) {
		
		//Trial code Michael can be deleted as soon as implemented properly
		case login : {
			if( ((Message_Login)msgIn).getLoginName().equals("m") && ((Message_Login)msgIn).getPassword().equals("m") ) {
				msgOut = new Simple_Message(Simple_Message.Msg.Login_accepted);
				msgOut.send(clientSocket);
			}
			else {
				msgOut = new Message_Error("Username or PW not corrrect", Message_Error.ErrorType.logginfalied);
				msgOut.send(clientSocket);
			}
			break;
		}
		//----------------------------------------------------------------------------------------------
		case createGame : {
			boolean isSchieber = ((Message_CreateGame)msgIn).isSchieber();
			boolean isGermanCards = ((Message_CreateGame)msgIn).isGermanCards();
			int numOfRounds = ((Message_CreateGame)msgIn).getNumOfRounds();
			int winningPoints = ((Message_CreateGame)msgIn).getWinningPoints();
			Game g = new Game(isGermanCards, numOfRounds, winningPoints, isSchieber);
			Player p = (Player) this; //downcasting
			g.addPlayer(p); //Player, welcher Spiel erstellt hat, hinzufügen
			model.addGame(g);
			sendReceived();
			//Castdown games to Games.commons for sending
			/*ArrayList<Commons.Game> castGames = new ArrayList<Commons.Game>();
			for(Game game : model.getGames()) {
				Commons.Game castGame = game.Clone();
				castGames.add();
			}*/
			msgOut = new Message_GameList(model.getGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case joinGame : {
			boolean added = false;
			Player p = (Player) this;
			for (Game g : model.getGames()) {
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) //dem richtigen Game hinzufügen
					added = g.addPlayer(p);
			}
			if (added) {
				sendReceived();
				msgOut = msgIn;
				model.broadcast(msgOut);
			} else {
				//TODO User benachrichtigen, dass er nicht joinen konnte
			}
			break;
		}
		//---------------------------------------------------------------------------------------------
		case simple_Message : {
			//sendReceived(); //TODO
			break;
		}
		
		}
		
	}
	
	public void sendReceived() {
		Simple_Message msg = new Simple_Message(Simple_Message.Msg.Received);
		msg.send(clientSocket);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}
	
	public int getID() {
		return this.userID;
	}

}
