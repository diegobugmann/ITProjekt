package server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import Commons.GameType;
import Commons.Message;
import Commons.MessageType;
import Commons.Message_CreateGame;
import Commons.Message_Error;
import Commons.Message_GameList;
import Commons.Message_JoinGame;
import Commons.Message_JoinedGame;
import Commons.Message_Login;
import Commons.Message_Trumpf;
import Commons.Simple_Message;
import Commons.Message_Ansage;

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
				Message gameUpdate = new Message_GameList(model.getCastedGames());
				gameUpdate.send(clientSocket);
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
			model.addGame(g);
			Player p = (Player) this; //downcasting
			g.addPlayer(p); //Player, welcher Spiel erstellt hat, hinzufügen
			p.setCurrentGame(g);
			//sendReceived();
			Commons.Game commonsGame = new Commons.Game(g.isGermanCards(), g.getNumOfRounds(), g.getWinningPoints(), g.isSchieber(), g.getGameId());
			//Sends joined game back to client t store
			Message_JoinedGame joined = new Message_JoinedGame(commonsGame);
			joined.send(clientSocket);
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case joinGame : {
			boolean added = false;
			Player p = (Player) this;
			for (Game g : model.getGames()) {
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) { //dem richtigen Game hinzufügen
					added = g.addPlayer(p);
					p.setCurrentGame(g);
				}	
			}
			if (added) {
				Game current = p.getCurrentGame();
				Commons.Game commonsGame = new Commons.Game(current.isGermanCards(), current.getNumOfRounds(), current.getWinningPoints(), current.isSchieber(), current.getGameId());
				//Sends joined game back to client t store
				Message_JoinedGame joined = new Message_JoinedGame(commonsGame);
				joined.send(clientSocket);
				
				msgOut = new Message_GameList(model.getCastedGames());
				model.broadcast(msgOut);
			} else {
				//TODO User benachrichtigen, dass er nicht joinen konnte
			}
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case trumpf: {
			GameType trumpf = ((Message_Trumpf)msgIn).getTrumpf();
			Player p = (Player) this;
			p.getCurrentGame().setTrumpf(trumpf);
			msgOut = msgIn;
			model.broadcast(p.getCurrentGame().getPlayers(), msgOut); //Trumpf an alle broadcasten
			p.getCurrentGame().startPlaying();
			//Hier beginnt nun der Spielablauf (1. Runde) und den Spielern werden YourFirstTurn(Wiis) Nachrichten geschickt
			break;
		}
		//------------------------------------------------------------------------------------------------
		case ansage: {
			int points = ((Message_Ansage)msgIn).getPoints();
			Player p = (Player) this;
			p.setAnnouncedPoints(points);
			p.getCurrentGame().incrementNumOfAnsagen(); //bei 4 erhaltenen Ansagen wird das Spiel starten
			//Hier beginnt nun der Spielablauf (1. Runde) und den Spielern werden YourFirstTurn(Wiis) Nachrichten geschickt
		}
		//-----------------------------------------------------------------------------------------------
		case simple_Message : {
			switch(((Simple_Message)msgIn).getType()) {
			case Received: {
				//TODO
				break;
			}
			case Get_GameList:{
				msgOut = new Message_GameList(model.getCastedGames());
				msgOut.send(clientSocket);
			}
			default: {
				break; //Sollten keine anderen Simple_Messages vom Server empfangen werden
			}
			}
		}
		}
	}
	
	private void startPlaying() {
		
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
