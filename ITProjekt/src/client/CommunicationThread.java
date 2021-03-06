package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import Commons.*;
import Commons.Message_Error.ErrorType;
import javafx.application.Platform;
/**
 * 
 * @author mibe1
 *The Commncation Thread handles all communication between Server and client.
 *It listens for messages and processes them
 *It prepares all Messages before sending and sends them
 *It controls the Status of the client and checks if the client is allowned to send a certain message or not
 *This class runs as own thread!! 
 */
public class CommunicationThread extends Thread{
	/**
	 * Status of the CLient
	 * @author mibe1
	 *Idea: Depending on the Status Messages get handled differently
	 *
	 */
	public enum Status{
		connected,
		logedin,
		joingamerequested,
		joinedgame,
		ingame,
		onturn
	}
	
	private Socket socket;
	private ClientController controller;
	private String senderName = "undefined";
	private Status status;
	private ArrayList<Game> allGames;
	//Information about the current Game
	private int gameId = -1;
	private Game currentGame;
	
	public CommunicationThread(Socket s, ClientController controller) throws IOException {
		//run the Constructor of Thread
		super();
		this.socket = s;
		this.controller = controller;
		status = Status.connected;
	}
	
		/**
     * Handles all incoming Messages by just receiving them and then giving them to the processMessage method 
     * If the answer of the message is not null, this method also response to the server and starts listening again 
     */
    @Override
    public void run() {
    	 try {
    		// Read a message from the client
    		 Message msgIn = Message.receive(socket); 			
    		 Platform.runLater(new Runnable() {
		            @Override public void run() {
		     			Message msgOut = processMessage(msgIn);
		     			if(msgOut != null) {
		     				sendMessage(msgOut);
		     			}
		            }
		        });

			this.run();
         } catch (Exception e) {
        	//TODO process Exception
         }    
    }
    
    /**
     * Prepare a Message msg for sendig by setting sender id
     * This is an option to do last checks for a message such as if it is null
     * @param msg
     */
	public void sendMessage(Message msg) {
		if(msg != null) {
			msg.setClient(senderName);
			msg.send(this.socket);
		}
		else {
			controller.showAlert("Outgoing Message is Null","The Sending Message is null and was not sent.");
		}
	}
	
	/**
	 * @author mibe1
	 * Get a Game form the list of all Games by its GameId
	 * @param id
	 * @return game
	 */
	public Game getGamefromList(int id) {
		Game selectedGame = null;
		for(Game g : allGames) {
			if(id == g.getGameId()) {
				selectedGame = g;
			}
		}
		return selectedGame;
	}
	
    /**
     * @author mibe1
     * process the message based on the Messagetype and gives advice to the controller
     * @param msgIn Incomming Message
     * @return 
     * @return message received or specific answer message
     */
	private Message processMessage(Message msgIn) {
		//The default answer is a Simpe_Message received so the client can interact with the user without blocking the communication.
		Message returnMsg = null;
		
		MessageType type = MessageType.getType(msgIn);
		switch(type){
		/**
		 * Go through all Simple Messages and react to them
		 */
			case simple_Message : {
				Simple_Message msg =(Simple_Message) msgIn;
				switch(msg.getType()) {
				//If the Message is a received message from the Server the communication has ended (block loops)	
					case Game_Start :{
						status = Status.ingame;
						controller.startGame();
						break;
					}
					case Ansage_Points :{
						controller.processAnsagePoints();
						break;
					}
					case GameEnded :{
						status = Status.logedin;
						break;
					}
					case Ansage_Trumpf :{
						controller.processSetTrumpf(false);
						break;
					}
					case Schiebe_Trumpf :{
						controller.processSetTrumpf(true);
						break;
					}
					case nextRound:{
						controller.infoViewController.model.incrementNumberOfRounds();
						break;
					}
					case Match: {
						controller.infoViewController.model.addInfoPopUp("!!!MATCH!!!");;
						break;
					}
					case Login_accepted :{
				        this.status = Status.logedin;
						controller.loginaccepted();
						break;
					}
					case Registration_accepted :{
						controller.createNewUserController.registrationSucceded();
						break;
					}
					case Registration_failed :{
						controller.createNewUserController.registerFailed("Registration fehlgeschlagen");
						break;
					}
					case Registration_invalidPW :{
						controller.createNewUserController.registerFailed("Ungültiges Passwort");
						break;
					}
					
					case Username_accepted :{
						controller.createNewUserController.userNameisAvailable(true);
						controller.createNewUserController.activateNewUserbtn();
						break;
					}
					
					case Username_declined :{
						controller.createNewUserController.userNameisAvailable(false);
						break;
					}
				}
				break;
			}
			case gamelist : {
				Message_GameList msglist = (Message_GameList) msgIn;
				allGames = msglist.getGames();
				if(this.gameId != -1 && this.currentGame == null) {
					this.currentGame = getGamefromList(gameId);
					if(this.currentGame != null) {
						status = Status.joinedgame;
						controller.joinGameApproved(currentGame);
					}
				}
				controller.updateGamelist(msglist.getGames(), status);
				break;
			}
			case chat : {
				Message_Chat chatmsg = (Message_Chat) msgIn;
				controller.view.gameView.chatBox.receiveChatMessage(chatmsg.getClient(), chatmsg.getMessage());
				controller.soundModule.playNewMesage(null);
				break;
			}
			
			case yourTurn : {
				status = Status.onturn;
				Message_YourTurn msgYourTurn = (Message_YourTurn) msgIn;
				controller.processYourTurn(msgYourTurn.getHand());
				break;
			}
			case joinGame : {
				if(this.status != Status.ingame) {
					Message_JoinGame msgJoin = (Message_JoinGame) msgIn;
					this.gameId = msgJoin.getGameId();
					this.currentGame = getGamefromList(gameId);
					if(this.currentGame != null) {
						status = Status.joinedgame;
						controller.joinGameApproved(currentGame);
					}
				}
				break;
			}
			case players : {
				Message_Players msgPlayers = (Message_Players) msgIn;
				controller.processPlayers(msgPlayers.getPlayers());
				controller.createScoreBoard(msgPlayers.getPlayers());
				break;
			}
			case hand : {
				Message_Hand msgHand = (Message_Hand) msgIn;
				controller.soundModule.playMix(null);
				controller.updateCardArea(msgHand.getHand());
				controller.model.setActualHand(msgHand.getHand());
				break;
			}
			case turn : {
				Message_Turn msgTurn = (Message_Turn) msgIn;
				controller.processTurn(msgTurn.getCard(), msgTurn.getPlayer());
				break;
			}
			case stoeck : {
				Message_Stoeck msgStoeck = (Message_Stoeck) msgIn;
				controller.soundModule.playStock();
				controller.infoViewController.model.setStoeck(msgStoeck.getName());
				break;
			}
			case wiis : {
				Message_Wiis msgWiis = (Message_Wiis) msgIn;
				if(msgWiis.getWiis().size()>0) {
					controller.processWiis(msgWiis.getWiis());
				}
				break;
			}
			case wiisInfo : {
				Message_WiisInfo msgWiisInfo = (Message_WiisInfo) msgIn;
				controller.infoViewController.model.processWiisInfo(msgWiisInfo);
				break;
			}
			case stich : {
				
				Message_Stich msgStich = (Message_Stich) msgIn;
				controller.processStich(msgStich.getPlayer());
				break;
			}
			case points : {
				Message_Points msgPoints = (Message_Points) msgIn;
				controller.infoViewController.model.setPoints(msgPoints);
				if(!msgPoints.getIsWiis())
					controller.infoViewController.model.setTrump(GameType.noTrumpf);
				break;
			}
			case pointUpdateDifferenzler : {
				Message_PointUpdateDifferenzler msg = (Message_PointUpdateDifferenzler) msgIn;
				controller.infoViewController.model.setAktuellePoints(msg.getaktuellePoints());
				break;
			}
			case pointsDifferenzler : {
				Message_PointsDifferenzler msg = (Message_PointsDifferenzler) msgIn;
				controller.sbdController.setPoints(msg.getPlayerName(), msg.getAnsage(), msg.getPoints(), msg.getDifference(), msg.getDifferenceTotal());
				break;
			}
			
			case cancel : {
				if(status == status.ingame || status == status.onturn) {
					this.status = status.logedin;
					Message_Cancel msgC = (Message_Cancel) msgIn;
					controller.showAlert("Spielabbruch", msgC.getClient()+" Hat das Spiel vorzeitig abgebrochen!");
					controller.processExitGame(null);
					returnMsg = new Simple_Message(Simple_Message.Msg.Get_GameList);
				}
				break;
			}
			case trumpf : {
				Message_Trumpf msgTrumpf = (Message_Trumpf) msgIn;
				GameType trumpf = msgTrumpf.getTrumpf();
				controller.infoViewController.model.setTrump(trumpf);
				break;
			}
			case endResults : {
				Message_EndResult msgEndResults = (Message_EndResult) msgIn;
				int winningTeamID = msgEndResults.getWinningTeamID();
				int pointsTeamI = msgEndResults.getPointsTeamI();
				int pointsTeamII = msgEndResults.getPointsTeamII();
				int pointsTeamIII = msgEndResults.getPointsTeamIII();
				int pointsTeamIV = msgEndResults.getPointsTeamIV();
				controller.processEndResults(winningTeamID, pointsTeamI,
						pointsTeamII, pointsTeamIII, pointsTeamIV);
				controller.infoViewController.model.setTrump(GameType.noTrumpf);
				break;
			}
			case error : {
				Message_Error msgError = (Message_Error) msgIn;
				controller.showAlert(msgError.getType().toString(), msgError.getErrorMessage());
				if(msgError.getType() == ErrorType.serverDisconnected) {
					controller.serverClosed.set(true);
					
				}
				break;
			}
		}
		return returnMsg;
	}
	
	//Getters and Setters -----------------------------------------------------------------------------------------------------------
	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

    public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}

	public void setCurrentGame(Game g) {
		this.currentGame = g;
		
	}
	
	public Game getCurrentGame() {
		return currentGame;
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Closes the connection to the Server on shutting down the program
	 */
	public void closeConnection() {
		try { if (socket != null) socket.close(); } 
		catch (IOException e) {
			//TODO Process 
		}
	}
}