package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Commons.*;
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
		Message returnMsg = new Simple_Message(Simple_Message.Msg.Received);
		
		MessageType type = MessageType.getType(msgIn);
		switch(type){
		/**
		 * Go through all Simple Messages and react to them
		 */
			case simple_Message : {
				Simple_Message msg =(Simple_Message) msgIn;
				switch(msg.getType()) {
				//If the Message is a received message from the Server the communication has ended (block loops)	
					case Received :{
						returnMsg = null;
						break;
					}
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
						controller.processSetTrumpf();
						break;
					}
					case Login_accepted :{
				        this.status = Status.logedin;
						controller.loginaccepted();
						returnMsg = null;
						break;
					}
					case Registration_accepted :{
						controller.createNewUserController.registrationSucceded();
						break;
					}
					case Registration_failed :{
						controller.createNewUserController.registerFailed("Registration failed");
						break;
					}
					case Registration_invalidPW :{
						controller.createNewUserController.registerFailed("Invalid Password");
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
				returnMsg = null;
				break;
			}
			case chat : {
				Message_Chat chatmsg = (Message_Chat) msgIn;
				controller.view.gameView.chatBox.receiveChatMessage(chatmsg.getClient(), chatmsg.getMessage());
				controller.soundModule.playNewMesage(null);
				returnMsg = null;
				break;
			}
			
			case yourTurn : {
				status = Status.onturn;
				Message_YourTurn msgYourTurn = (Message_YourTurn) msgIn;
				System.out.println("ComThred valide Karten: "+msgYourTurn.getHand()); //TODO löschen
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
				returnMsg = null;
				break;
			}
			case players : {
				
				break;
			}
			case hand : {
				Message_Hand msgHand = (Message_Hand) msgIn;
				controller.updateCardArea(msgHand.getHand());
				controller.model.setActualHand(msgHand.getHand());
				break;
			}
			case turn : {
				Message_Turn msgTurn = (Message_Turn) msgIn;
				controller.processTurn(msgTurn.getCard(), msgTurn.getPlayer());
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
				controller.infoViewController.processWiisInfo(msgWiisInfo);
				break;
			}
			case stich : {
				Message_Stich msgStich = (Message_Stich) msgIn;
				controller.processStich(msgStich);
				break;
			}
			case points : {
				Message_Points msgPoints = (Message_Points) msgIn;
				controller.infoViewController.model.pointsTeam.set(Integer.parseInt(msgPoints.getPlayerI()));
				controller.infoViewController.model.pointsOppo.set(Integer.parseInt(msgPoints.getPlayerII()));
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
				//TODO
				break;
			}
			case error : {
				Message_Error msgError = (Message_Error) msgIn;
				controller.showAlert(msgError.getType().toString(), msgError.getErrorMessage());
				returnMsg = null;
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