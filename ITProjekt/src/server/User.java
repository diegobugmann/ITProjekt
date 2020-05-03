package server;

import java.io.EOFException;
import java.io.IOException;

import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import Commons.*;


import DB.UserData;

import Commons.Message_Ansage;
import Commons.Message_Chat;
import Commons.Message_Error.ErrorType;

public class User {
	
	private static int nextID = 1;
	private int userID;
	private String name = "";
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
						logger.info("Connection to "+name+" (User "+userID+") determined");
						model.removeUser(User.this);
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
				this.setName(((Message_Login)msgIn).getLoginName());
				msgOut = new Simple_Message(Simple_Message.Msg.Login_accepted);
				this.sendMessage(msgOut);
				Message gameUpdate = new Message_GameList(model.getCastedGames());
				this.sendMessage(gameUpdate);
			}
			else {
				msgOut = new Message_Error("Username or PW not corrrect", Message_Error.ErrorType.logginfalied);
				this.sendMessage(msgOut);
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
			this.sendMessage(msgJoin);
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case joinGame : {
			for (Game g : model.getGames()) {
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) { //dem richtigen Game hinzufügen
					int teamNr = g.addPlayer(p);
					if (teamNr == -1) { //game already full?
						msgOut = new Message_Error("failed to join - game full", ErrorType.failedToJoin);
						this.sendMessage(msgOut);
						return;
					} else {
						Team t = g.getTeam(teamNr);
						p.setCurrentTeam(t); p.setCurrentGame(g); //set current game and team to player
					}
				}	
			}
			//Send back the join Message so client knows the Game
			this.sendMessage(msgIn);		
			msgOut = new Message_GameList(model.getCastedGames());
			model.broadcast(msgOut);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		//Code Michael
		case chat: {
			model.broadcast(p.getCurrentGame().getPlayers(), msgIn);
			break;
		}
		//-----------------------------------------------------------------------------------------------
		case trumpf: {
			GameType trumpf = ((Message_Trumpf)msgIn).getTrumpf();
			p.getCurrentGame().setTrumpf(trumpf);
			model.broadcast(p.getCurrentGame().getPlayers(), msgIn); //broadcast trumpf to everyone
			p.getCurrentGame().startPlaying();
			break;
		}
		//------------------------------------------------------------------------------------------------------
		case ansage: {
			int points = ((Message_Ansage)msgIn).getPoints();
			p.setAnnouncedPoints(points);
			p.getCurrentGame().incrementNumOfAnsagen();
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
				if (currentGame.getNumOfPlays() == 1 && currentGame.isSchieber()) { //was it the first round and Schieber?
					Team wiisWinner = currentGame.validateWiisWinner();
					if (wiisWinner != null) {
						Player p1 = wiisWinner.getPlayerList().get(0);
						p1.addWiisPointsToTeam();
						Player p2 = wiisWinner.getPlayerList().get(1);
						p2.addWiisPointsToTeam();
						msgOut = new Message_WiisInfo(p1.getWiis(), p2.getWiis(), p1.getName(), p2.getName());
						model.broadcast(currentGame.getPlayers(), msgOut);
					}
				}
				Player winningPlayer = currentPlay.validateWinner();
				int playPoints = currentPlay.validatePoints();
				Team winningTeam = winningPlayer.getCurrentTeam();
				winningTeam.addPoints(playPoints);
				if (currentGame.getNumOfPlays() == 9) { //End of game?
					currentGame.addPoints(5, winningTeam);
					System.out.println(currentGame.getTeam(0).getScore()); //TODO löschen
					System.out.println(currentGame.getTeam(1).getScore()); //TODO löschen
					if (currentGame.isMatch())
						currentGame.addPoints(100, winningTeam);
					boolean keepPlaying = currentGame.prepareNewGameIfNeeded();
					if (!keepPlaying) {
						//TODO GEWINNER BEKANNTGEBEN (currentGame.getWinnerTeam() ist GewinnerTeam)
					}
				} else {
					currentGame.newPlay(); //creates a new play object, adds it to the game and sets it as currentPlay
					msgOut = new Message_Stich(winningPlayer.getName());
					model.broadcast(currentGame.getPlayers(), msgOut);
					msgOut = new Message_YourTurn(winningPlayer.getHand()); //player can play everything he wants
					msgOut.send(winningPlayer.getSocket());
				}
				
			} else { //play is not over
				Player nextPlayer = p.getFollowingPlayer();
				if (currentGame.getNumOfPlays() == 1 && currentGame.isSchieber()) {
					ArrayList<Wiis> wiis = nextPlayer.validateWiis();
					msgOut = new Message_Wiis(wiis, p.getID());
					msgOut.send(nextPlayer.getSocket()); //send player possible wiis in the first play
				}
				ArrayList<Card> playableCards = nextPlayer.getPlayableCards();
				msgOut = new Message_YourTurn(playableCards);
				msgOut.send(nextPlayer.getSocket()); //and send him yourTurn with playableCards
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case wiis : {
			ArrayList<Wiis> wiis = ((Message_Wiis)msgIn).getWiis();
			if (!wiis.isEmpty()) {
				p.addWiis(wiis);
				msgOut = new Message_WiisInfo(p.getWiis(), null, p.getName(), null);
				msgOut.setClient(name);
				model.broadcast(p.getCurrentGame().getPlayers(), msgOut);
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case newUserName : {
			UserData ud = new UserData();
			String userName = ((Message_UserNameAvailable)msgIn).getUserName();
			if (ud.isUserNameAvailable(userName)) {
				msgOut = new Simple_Message(Simple_Message.Msg.Username_accepted);
				this.sendMessage(msgOut);
			} else {
				msgOut = new Simple_Message(Simple_Message.Msg.Username_declined);
				this.sendMessage(msgOut);
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case register : {
			UserData ud = new UserData();
			String userName = ((Message_Register)msgIn).getUserName();
			String password = ((Message_Register)msgIn).getPassword();
			//TODO REname Validation_LoginProcess to Validation_RegisteRProcess
			if(Validation_LoginProcess.isPasswordValid(password)) {
				if (ud.createUser(userName, password)) {
					msgOut = new Simple_Message(Simple_Message.Msg.Registration_accepted);
					this.sendMessage(msgOut);
				} else {
					msgOut = new Simple_Message(Simple_Message.Msg.Registration_failed);
					this.sendMessage(msgOut);
				}
			} else {
				msgOut = new Simple_Message(Simple_Message.Msg.Registration_invalidPW);
				this.sendMessage(msgOut);			
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case cancel : {
			Game g = p.getCurrentGame();
			for (Player player : g.getPlayers()) {
				player.setCurrentGame(null);
				player.clearHand();
			}
			g.removeAllPlayers();
			model.broadcast(msgIn);
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
				this.sendMessage(msgOut);
				break;
			}
			case CancelWaiting: {
				p.getCurrentGame().removePlayer(p);
				msgOut = new Message_GameList(model.getCastedGames());
				model.broadcast(msgOut);
				break;
			}
			case Schiebe: {
				Player teammate = p.getTeammate(); //get the "Schieber"s teammate
				msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Trumpf);
				msgOut.setClient(name);
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
	
	/**
	 * @author digib
	 * sends a received Message
	 */
	public void sendReceived() {
		Simple_Message msg = new Simple_Message(Simple_Message.Msg.Received);
		msg.send(clientSocket);
	}
	
	/**
	 * @author digib
	 * sets the clientName and sends the message to this User's socket
	 */
	public void sendMessage(Message msg) {
		msg.setClient(name);
		msg.send(clientSocket);
		logger.info("Message sent to client: "+msg.toString());
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Socket getSocket() {
		return this.clientSocket;
	}
	
	public int getID() {
		return this.userID;
	}

}
