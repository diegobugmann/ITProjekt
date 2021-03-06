package server;

import java.io.EOFException;
import java.io.IOException;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import Commons.*;


import DB.UserData;
import Commons.Message_Error.ErrorType;
import Commons.Simple_Message.Msg;

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
						e.printStackTrace();
					}catch (EOFException e) {
						logger.info("Connection to "+name+" (User "+userID+") determined");
						model.removeUser(User.this);
					}catch (IOException e) {
						e.printStackTrace();
					}
					finally { try { if (clientSocket != null) clientSocket.close(); } catch (IOException e) {}}
				}
		};
		Thread thread = new Thread(run);
		thread.start();
	}
	
	/**
	 * @author digib (mostly, database and login from sarah)
	 * @param Message
	 * processes the message from the client and decides what to do for each type, including Simple_Messages
	 */
	protected void processMessage(Message msgIn) {
		logger.info("Message received from client: "+ msgIn.toString());
		Message msgOut = null;
		Player p = (Player) this; //downcasting
		switch (MessageType.getType(msgIn)) {
		case login : {
			try {
				UserData ud = new UserData(); 
				ArrayList<String> userNames = new ArrayList<>();
				for(User user : model.getUsers()) {
					userNames.add(user.getName());
				}
				
				if(ud.check(((Message_Login)msgIn).getLoginName(), ((Message_Login)msgIn).getPassword()) && !userNames.contains(((Message_Login)msgIn).getLoginName())){
					this.setName(((Message_Login)msgIn).getLoginName());
					msgOut = new Simple_Message(Simple_Message.Msg.Login_accepted);
					this.sendMessage(msgOut);
					Message gameUpdate = new Message_GameList(model.getCastedGames());
					this.sendMessage(gameUpdate);
				}
				else {
					msgOut = new Message_Error("Anmeldung fehlgeschlagen", Message_Error.ErrorType.logginfailed);
					this.sendMessage(msgOut);
				}
			}
			//Catach Error when Database is not acessiable and tell client
			catch(java.lang.NullPointerException e) {
				msgOut = new Message_Error("Server is not connected to the Database please contact your system adminstrator", ErrorType.logginfailed);
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
			int teamIndex = g.addPlayer(p); //add player to the game
			Team t = g.getTeam(teamIndex);
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
				if (g.getGameId() == ((Message_JoinGame)msgIn).getGameId()) { //add player to the correct game
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
			for (Player pp : p.getCurrentGame().getPlayers())
				pp.lookForStoeck(); //look if any player has the stoeck
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
			logger.info(p.getName().toUpperCase()+" PLAYED "+playedCard.toString().toUpperCase());
			p.removeCard(playedCard); //remove played card from hand
			currentPlay.addCard(playedCard);
			currentPlay.getPlayedBy().add(p);
			if (currentGame.isSchieber() && p.hasStoeck()) {
				//has a player played both stoeck?
				if (p.hasPlayedStoeck()) {
					currentGame.addPointsToTotal(20, p.getCurrentTeam());
					msgOut = new Message_Stoeck(p.getName());
					model.broadcast(currentGame.getPlayers(), msgOut);
					msgOut = new Message_Points(p.getName(), p.getTeammate().getName(), p.getCurrentTeam().getTotalScore(), true);
					model.broadcast(currentGame.getPlayers(), msgOut);
					//has the stoeckPlayer reached the points? (Stoeck count before a regular stich)
					if (p.getCurrentTeam().isFinished(currentGame)) {
						currentGame.updateTeamPoints();
						currentGame.setWinnerTeam(p.getCurrentTeam());
						msgOut = new Message_EndResult(p.getCurrentTeam().getTeamID(), currentGame.getTeam(0).getTotalScore(), currentGame.getTeam(1).getTotalScore());
						model.broadcast(currentGame.getPlayers(), msgOut);
						for (Player player : currentGame.getPlayers()) player.reset();
						model.deleteGame(currentGame);
						return; //game is over
					}
				}
			}
			
			//is the play over?
			if (currentPlay.getPlayedCards().size() == 4) {
				//FIRST PLAY OF THE ROUND
				if (currentGame.getNumOfPlays() == 1 && currentGame.isSchieber()) {
					for (Player pp : currentGame.getPlayers()) {
						//check if someone has the stoeck
						if (pp.hasStoeck()) {
							currentGame.addPointsToTotal(20, pp.getCurrentTeam());
							//has the stoeckWiiser reached the points? (Stöck - Wys - Stich)
							if (pp.getCurrentTeam().isFinished(currentGame)) {
								currentGame.setWinnerTeam(pp.getCurrentTeam());
								msgOut = new Message_Stoeck(pp.getName());
								model.broadcast(currentGame.getPlayers(), msgOut);
								msgOut = new Message_Points(pp.getName(), pp.getTeammate().getName(), pp.getCurrentTeam().getTotalScore(), true);
								model.broadcast(currentGame.getPlayers(), msgOut);
								msgOut = new Message_EndResult(pp.getCurrentTeam().getTeamID(), currentGame.getTeam(0).getTotalScore(), currentGame.getTeam(1).getTotalScore());
								model.broadcast(currentGame.getPlayers(), msgOut);
								for (Player player : currentGame.getPlayers()) player.reset();
								model.deleteGame(currentGame);
								return; //game is over
							} else
								currentGame.removePointsFromTotal(20, pp.getCurrentTeam()); //not allowed to wiis stöck if game is not over in first round
						}
					}
					//validate who has the highest wiis
					Team wiisWinner = currentGame.validateWiisWinner();
					if (wiisWinner != null) {
						Player p1 = wiisWinner.getPlayerList().get(0);
						p1.addWiisPointsToTeam();
						Player p2 = wiisWinner.getPlayerList().get(1);
						p2.addWiisPointsToTeam();
						msgOut = new Message_WiisInfo(p1.getWiis(), p2.getWiis(), p1.getName(), p2.getName());
						model.broadcast(currentGame.getPlayers(), msgOut);
						//does a wiis contain the stoeck?
						for (Player pp : wiisWinner.getPlayerList()) {
							for (Wiis w : pp.getWiis()) {
								if (w.containsStoeck(currentGame.getTrumpf())) {
									currentGame.addPointsToTotal(20, pp.getCurrentTeam());
									msgOut = new Message_Stoeck(pp.getName());
									model.broadcast(currentGame.getPlayers(), msgOut);
									pp.setHasStoeck(false); //stoeck are now already wiised
								}
							}
						}
						msgOut = new Message_Points(p1.getName(), p2.getName(), wiisWinner.getTotalScore(), true);
						model.broadcast(currentGame.getPlayers(), msgOut);
						//has the wiisWinner reached the points? (Stöck - Wys - Stich)
						if (wiisWinner.isFinished(currentGame)) {
							currentGame.setWinnerTeam(wiisWinner);
							msgOut = new Message_EndResult(wiisWinner.getTeamID(), currentGame.getTeam(0).getTotalScore(), currentGame.getTeam(1).getTotalScore());
							model.broadcast(currentGame.getPlayers(), msgOut);
							for (Player player : currentGame.getPlayers()) player.reset();
							model.deleteGame(currentGame);
							return; //game is over
						}
					}
				}
				//https://javabeginners.de/Grundlagen/Zeitsteuerung_ohne_Threads.php
				long end = (new Date()).getTime() + 1100; //set time in future
		        while( (new Date()).getTime() < end ) {
		        	//waiting until set time is reached, to see the last played card
		        }
				Player winningPlayer = currentPlay.validateWinner();
				int playPoints = currentPlay.validatePoints();
				Team winningTeam = winningPlayer.getCurrentTeam();
				currentGame.addPoints(playPoints, winningTeam);		        
				msgOut = new Message_Stich(winningPlayer.getName());
				model.broadcast(currentGame.getPlayers(), msgOut);
				//update user with the points made in this round in differenzler
				if (!currentGame.isSchieber() && !(currentGame.getNumOfPlays() == 9)) {
					msgOut = new Message_PointUpdateDifferenzler(winningTeam.getScore());
					winningTeam.getPlayerList().get(0).sendMessage(msgOut);
				}
				//has the playWinner reached the points? (Stöck - Wys - Stich)
				if (currentGame.isSchieber() && winningTeam.isFinished(currentGame)) {
					currentGame.updateTeamPoints();
					currentGame.setWinnerTeam(winningTeam);
					msgOut = new Message_EndResult(winningTeam.getTeamID(), currentGame.getTeam(0).getTotalScore(), currentGame.getTeam(1).getTotalScore());
					model.broadcast(currentGame.getPlayers(), msgOut);
					for (Player player : currentGame.getPlayers()) player.reset();
					model.deleteGame(currentGame);
					return; //game is over
				}
				//LAST PLAY OF THE ROUND
				if (currentGame.getNumOfPlays() == 9) {
					currentGame.addPoints(5, winningTeam);
					if (currentGame.isSchieber()) {
						//check for match (only when schieber)
						if (currentGame.isMatch()) {
							currentGame.addPoints(100, winningTeam);
							msgOut = new Simple_Message(Msg.Match);
							model.broadcast(currentGame.getPlayers(), msgOut);
						}
						//has the playWinner reached the points now?
						if (winningTeam.isFinished(currentGame)) {
							currentGame.updateTeamPoints();
							currentGame.setWinnerTeam(winningTeam);
							msgOut = new Message_EndResult(winningTeam.getTeamID(), currentGame.getTeam(0).getTotalScore(), currentGame.getTeam(1).getTotalScore());
							model.broadcast(currentGame.getPlayers(), msgOut);
							for (Player player : currentGame.getPlayers()) player.reset();
							model.deleteGame(currentGame);
							return; //game is over
						}
						//if game is not over, send total scores for both teams
						currentGame.updateTeamPoints();
						for (int i = 0; i < 2; i++) {
							Player p1 = currentGame.getTeam(i).getPlayerList().get(0);
							msgOut = new Message_Points(p1.getName(), p1.getTeammate().getName(), currentGame.getTeam(i).getTotalScore(), false);
							model.broadcast(currentGame.getPlayers(), msgOut);
						}
					} else {
						//send pointupdate considering last stich
						msgOut = new Message_PointUpdateDifferenzler(winningTeam.getScore());
						winningTeam.getPlayerList().get(0).sendMessage(msgOut);
						//add score to total and send scores for all 4 teams (for Differenzler)
						for (int i = 0; i < 4; i++) {
							Player pp = currentGame.getTeam(i).getPlayerList().get(0);
							int diff = Math.abs(currentGame.getTeam(i).getScore() - pp.getAnnouncedPoints());
							currentGame.getTeam(i).addToTotal(diff);
							msgOut = new Message_PointsDifferenzler(pp.getName(), pp.getAnnouncedPoints(),
									pp.getCurrentTeam().getScore(), diff, pp.getCurrentTeam().getTotalScore());
							model.broadcast(currentGame.getPlayers(), msgOut);
						}
					}
					boolean keepPlaying = currentGame.prepareNewGameIfNeeded();
					//are numOfRounds reached?
					if (!keepPlaying) {
						Team winnerTeam = currentGame.getWinnerTeam();
						msgOut = new Message_EndResult(winnerTeam.getTeamID(), currentGame.getTeam(0).getTotalScore(), 
							currentGame.getTeam(1).getTotalScore(), currentGame.getTeam(2).getTotalScore(), currentGame.getTeam(3).getTotalScore());
						model.broadcast(currentGame.getPlayers(), msgOut);
						model.deleteGame(currentGame);
						return; //game over
					} else if (!currentGame.isSchieber()) {
						msgOut = new Simple_Message(Msg.nextRound);
						model.broadcast(currentGame.getPlayers(), msgOut);
					}
				//it was not the last round
				} else {
					currentGame.newPlay(); //creates a new play object, adds it to the game and sets it as currentPlay
					msgOut = new Message_YourTurn(winningPlayer.getHand()); //player can play everything he wants
					msgOut.send(winningPlayer.getSocket());
				}
			//play is not over: send the next player a yourTurn-Message (and a wiis-Message if it's the first round)
			} else {
				Player nextPlayer = p.getFollowingPlayer();
				if (currentGame.getNumOfPlays() == 1 && currentGame.isSchieber()) {
					ArrayList<Wiis> wiis = nextPlayer.validateWiis();
					msgOut = new Message_Wiis(wiis, p.getID());
					msgOut.send(nextPlayer.getSocket());
				}
				ArrayList<Card> playableCards = nextPlayer.getPlayableCards();
				msgOut = new Message_YourTurn(playableCards);
				msgOut.send(nextPlayer.getSocket());
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
			if (g != null) {
				for (Player player : g.getPlayers())
					player.reset();
				g.removeAllPlayers();
				g.resetTeamScores();
				g.resetPlays();
				model.broadcast(msgIn);
				model.deleteGame(g);
			}
			break;
		}
		//-------------------------------------------------------------------------------------------------------
		case simple_Message : {
			switch(((Simple_Message)msgIn).getType()) {
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
				msgOut = new Simple_Message(Simple_Message.Msg.Schiebe_Trumpf);
				msgOut.setClient(name);
				msgOut.send(teammate.getSocket()); //and tell him to make trumpf
				break;
			}
			default: {
				break; //no other simpleMsgs should be received
			}
			}
		}
		//-----------------------------------------------------------------------------------------------------------
		default : {
			//no other Msgs should be received
		}
		}
	}
	/**
	 * @author digib
	 * @param msg
	 * sets the clientName and sends the message to this User's socket
	 */
	public void sendMessage(Message msg) {
		msg.setClient(name);
		msg.send(clientSocket);
		logger.info("Message sent to client: "+msg.toString());
	}
	
	//getters and setters
	
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
