package server;

import java.io.IOException;

import java.net.Socket;
import java.util.logging.Logger;

import Commons.Card;
import Commons.GameType;
import Commons.Message;
import Commons.MessageType;
import Commons.Message_CreateGame;
import Commons.Message_Error;
import Commons.Message_GameList;
import Commons.Message_JoinGame;
import Commons.Message_Login;
import Commons.Message_Trumpf;
import Commons.Message_Turn;
import Commons.Simple_Message;

import DB.UserData;

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
		Player p = (Player) this; //downcasting
		switch (MessageType.getType(msgIn)) {
		
		//Trial code Michael can be deleted as soon as implemented properly
		case login : {
			/*UserData ud = new UserData(); 
			if(ud.check(((Message_Login)msgIn).getLoginName(), ((Message_Login)msgIn).getPassword())){*/
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
			g.addPlayer(p); //Player, welcher Spiel erstellt hat, hinzufügen
			p.setCurrentGame(g);
			//Send joining message so client knows the game
			Message_JoinGame msgJoin = new Message_JoinGame(g.getGameId());
			msgJoin.send(clientSocket);
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case joinGame : {
			boolean added = false;
			for (Game g : model.getGames()) {
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) { //dem richtigen Game hinzufügen
					added = g.addPlayer(p);
					p.setCurrentGame(g);
				}	
			}
			if (added) {
				//Send back the join Message so client knows the Game
				msgIn.send(clientSocket);			
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
			p.getCurrentGame().setTrumpf(trumpf);
			msgOut = msgIn;
			model.broadcast(p.getCurrentGame().getPlayers(), msgOut); //Trumpf an alle broadcasten
			p.getCurrentGame().startPlaying();
			break;
		}
		//------------------------------------------------------------------------------------------------------
		case ansage: {
			int points = ((Message_Ansage)msgIn).getPoints();
			p.setAnnouncedPoints(points);
			p.getCurrentGame().incrementNumOfAnsagen(); //bei 4 erhaltenen Ansagen wird das Spiel starten
			break;
		}
		//------------------------------------------------------------------------------------------------------
		case turn : {
			Game currentGame = p.getCurrentGame();
			Play currentPlay = currentGame.getCurrentPlay();
			msgOut = msgIn;
			model.broadcast(currentGame.getPlayers(), msgOut); //broadcast played card
			Card playedCard = ((Message_Turn)msgIn).getCard();
			currentPlay.addCard(playedCard);
			currentPlay.getPlayedBy().add(p);
			if (currentPlay.getPlayedCards().size() == 4) {
				//TODO Nachricht -> Du hast den Stich geholt? + PlayValidierung + Gewinnerteam gutschreiben & YourTurn an Gewinner
				Play nextPlay = new Play(currentGame.getTrumpf());
				currentGame.setCurrentPlay(nextPlay);
			}
			//TODO YourTurn an nächsten Spieler
		}
		//-------------------------------------------------------------------------------------------------------
		case simple_Message : {
			switch(((Simple_Message)msgIn).getType()) {
			case Received: {
				//TODO
				break;
			}
			case Get_GameList:{
				msgOut = new Message_GameList(model.getCastedGames());
				msgOut.send(clientSocket);
				break;
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
