package server;

import java.io.EOFException;
import java.io.IOException;

import java.net.Socket;
import java.util.ArrayList;
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
import Commons.Message_UserNameAvailable;
import Commons.Message_Wiis;
import Commons.Message_YourTurn;
import Commons.Message_Register;
import Commons.Validation_LoginProcess;
import Commons.Wiis;
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
	
	
	/**
	 * @author digib
	 * @param clientSocket, model
	 * waits for messages to come in
	 */
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
					}catch (EOFException e) {
						//TODO send to Logger
						System.out.println("Connection to User "+ userID + " determined.");
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally { try { if (clientSocket != null) clientSocket.close(); } catch (IOException e) {}}
				}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	/**
	 * @author digib (mostly, database from sarah)
	 * @param Message
	 * processes the message from the client and decides what to do for each type, including Simple_Messages
	 */
	protected void processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());
		Message msgOut = null;
		Player p = (Player) this; //downcasting
		switch (MessageType.getType(msgIn)) {
		
		//Trial code Michael can be deleted as soon as implemented properly
		case login : {
			UserData ud = new UserData(); 
			if(ud.check(((Message_Login)msgIn).getLoginName(), ((Message_Login)msgIn).getPassword())){
			//if( ((Message_Login)msgIn).getLoginName().equals("m") && ((Message_Login)msgIn).getPassword().equals("m") ) {
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
			int teamNr = g.addPlayer(p); //Player, welcher Spiel erstellt hat, hinzufügen
			Team t = g.getTeam(teamNr);
			p.setCurrentTeam(t); p.setCurrentGame(g); //set current game and team to player
			//Send joining message so client knows the game
			Message_JoinGame msgJoin = new Message_JoinGame(g.getGameId());
			msgJoin.send(clientSocket);
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case joinGame : {
			for (Game g : model.getGames()) {
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) { //dem richtigen Game hinzufügen
					int teamNr = g.addPlayer(p);
					Team t = g.getTeam(teamNr);
					p.setCurrentTeam(t); p.setCurrentGame(g); //set current game and team to player
				}	
			}
			//Send back the join Message so client knows the Game
			msgIn.send(clientSocket);			
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case trumpf: {
			GameType trumpf = ((Message_Trumpf)msgIn).getTrumpf();
			p.getCurrentGame().setTrumpf(trumpf);
			model.broadcast(p.getCurrentGame().getPlayers(), msgIn); //Trumpf an alle broadcasten
			p.getCurrentGame().startPlaying();
			break;
		}
		//------------------------------------------------------------------------------------------------------
		case ansage: {
			int points = ((Message_Ansage)msgIn).getPoints();
			p.setAnnouncedPoints(points);
			p.getCurrentGame().incrementNumOfAnsagen(); //bei 4 erhaltenen Ansagen wird das Spiel gestartet
			break;
		}
		//------------------------------------------------------------------------------------------------------
		case turn : {
			Game currentGame = p.getCurrentGame();
			Play currentPlay = currentGame.getCurrentPlay();
			model.broadcast(currentGame.getPlayers(), msgIn); //broadcast played card
			Card playedCard = ((Message_Turn)msgIn).getCard();
			p.removeCard(playedCard); //remove played card from hand
			currentPlay.addCard(playedCard);
			currentPlay.getPlayedBy().add(p);
			
			if (currentPlay.getPlayedCards().size() == 4) { //is the play over?
				if (currentGame.isFirstPlay() && currentGame.isSchieber()) { //was it the first round and Schieber?
					//TODO Wiise vergleichen (z.B. currentGame.validateWiisWinner();)
					//TODO Punkte dem WiisTeam hinzufügen
					//TODO Message, welche Weise gezählt werden?
					currentGame.setFirstPlay(false);
				}
				Player nextPlayer = currentPlay.validateWinner();
				int playPoints = currentPlay.validatePoints();
				Team winningTeam = nextPlayer.getCurrentTeam();
				winningTeam.addPoints(playPoints);
				currentGame.newPlay(); //creates a new play object, adds it to the game and sets it as currentPlay
				//TODO winner-Message, damit er zeigen kann wer den Stich geholt hat und Karten wegräumen kann?
				msgOut = new Message_YourTurn(p.getHand());
				msgOut.send(nextPlayer.getSocket());
				
			} else { //play is not over
				Player nextPlayer = p.getFollowingPlayer();
				if (currentGame.isFirstPlay() && currentGame.isSchieber()) {
					ArrayList<Wiis> wiis = nextPlayer.validateWiis();
					msgOut = new Message_Wiis(wiis, p.getID());
					msgOut.send(clientSocket); //send player possible wiis
				}
				ArrayList<Card> playableCards = nextPlayer.getPlayableCards();
				msgOut = new Message_YourTurn(playableCards);
				msgOut.send(clientSocket); //and send him yourTurn with playableCards
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case wiis : {
			ArrayList<Wiis> wiis = ((Message_Wiis)msgIn).getWiis();
			if (!wiis.isEmpty()) 
				p.addWiis(wiis);
			model.broadcast(p.getCurrentGame().getPlayers(), msgIn);
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case newUserName : {
			UserData ud = new UserData();
			String userName = ((Message_UserNameAvailable)msgIn).getUserName();
			if (ud.isUserNameAvailable(userName)) {
				msgOut = new Simple_Message(Simple_Message.Msg.Username_accepted);
				msgOut.send(clientSocket);
			} else {
				msgOut = new Simple_Message(Simple_Message.Msg.Username_declined);
				msgOut.send(clientSocket);
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case register : {
			UserData ud = new UserData();
			String userName = ((Message_Register)msgIn).getUserName();
			String password = ((Message_Register)msgIn).getPassword();
			if(Validation_LoginProcess.isPasswordValid(password)) {
				if (ud.createUser(userName, password)) {
					msgOut = new Simple_Message(Simple_Message.Msg.registration_accepted);
					msgOut.send(clientSocket);
				} else {
					msgOut = new Message_Error("Registration failed", Message_Error.ErrorType.Registration_failed);
					msgOut.send(clientSocket);
				}
			} else {
				msgOut = new Message_Error("Password invalid", Message_Error.ErrorType.Registration_failed);
				msgOut.send(clientSocket);				
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case simple_Message : {
			switch(((Simple_Message)msgIn).getType()) {
			case Received: {
				//TODO
				break;
			}
			case Get_GameList: {
				msgOut = new Message_GameList(model.getCastedGames());
				msgOut.send(clientSocket);
				break;
			}
			case Schiebe: {
				Player teammate = p.getTeammate(); //get the "Schieber"s teammate
				msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Trumpf);
				msgOut.send(teammate.getSocket()); //and tell him to make trumpf
				break;
			}
			default: {
				break; //Sollten keine anderen Simple_Messages vom Server empfangen werden
			}
			}
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
