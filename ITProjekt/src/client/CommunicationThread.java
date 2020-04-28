package client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import Commons.*;
import javafx.application.Platform;
/**
 * 
 * @author mibe1
 *
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
	private String senderName = "";
	private Status status;
	private ArrayList<Game> allGames;
	
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
		     				msgOut.send(socket);
		     			}
		            }
		        });

			this.run();
         } catch (Exception e) {
         }    

    }
    
	public void sendMessage(Message msg) {
		msg.setClient(senderName);
		msg.send(this.socket);
	}
	
	public Game getGamefromList(int id) {
		Game selectedGame = null;
		for(Game g : allGames) {
			if(id == g.getGameId()) {
				selectedGame = g;
			}
		}
		System.out.println("Joined Game " + selectedGame);
		return selectedGame;
	}
	
    /**
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
					
					case registration_accepted:{
						controller.registrationSucceded();
						break;
					}					
					
					case Username_accepted:{
						controller.userNameisAvaiable(true);
						controller.activateNewUserbtn();
						break;
					}
					
					case Username_declined:{
						controller.userNameisAvaiable(false);
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
			case yourTurn : {
				status = Status.onturn;
				controller.processYourTurn();
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
				Message_Hand msghand = (Message_Hand) msgIn;
				controller.updateCardArea(msghand.getHand());
				break;
			}
			case turn : {
				
				break;
			}
			case wiis : {
				Message_Wiis msgWiis = (Message_Wiis) msgIn;
				if(msgWiis.getWiis().size()>0) {
					//TODO Change into ArrayList
					controller.processWiis(msgWiis.getWiis());
				}
				
				break;
			}
			case stich : {
				
				break;
			}
			case points : {
				
				break;
			}
			case cancel : {
				
				break;
			}
			case trumpf : {
				Message_Trumpf msgTrumpf = (Message_Trumpf) msgIn;
				GameType trumpf = msgTrumpf.getTrumpf();
				controller.view.gameView.infoView.setTrumpf(trumpf.toString());
				break;
			}
			
			case endResults : {
				
				break;
			}
			
			case error : {
				Message_Error msgError = (Message_Error) msgIn;
				switch(msgError.getType()) {
					case logginfalied :{
						controller.loginfaild(msgError.getErrorMessage());
						returnMsg = null;
						break;
					}
					case not_loggedin :{
						break;
					}
					
					case Registration_failed :{
						controller.registerFailed(msgError.getErrorMessage());
						returnMsg = null;
						break;
					}
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
		try { if (socket != null) socket.close(); } catch (IOException e) {}
	}


	

}
