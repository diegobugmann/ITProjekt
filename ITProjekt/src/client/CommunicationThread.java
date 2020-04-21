package client;

import java.io.IOException;
import java.net.Socket;

import Commons.*;
import javafx.application.Platform;
/**
 * 
 * @author mibe1
 *TODO Michael:
 *-Korrektes Schliessen
 *-Anzahl Spieler anzeigen
 *-Tabelle verschönern
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
						System.out.println("That works allready");
						status = Status.ingame;
						controller.startGame();
						break;
					}
					case Your_Turn :{
						status = Status.onturn;
						break;
					}
					case Ansage_Points :{
						break;
					}
					case GameEnded :{
						status = Status.logedin;
						break;
					}
					case Ansage_Trumpf :{
						break;
					}
					
					case Login_accepted :{
				        this.status = Status.logedin;
						controller.loginaccepted();
						returnMsg = null;
						break;
					}
					
					case registration_accepted:{
						
						break;
					}
				}
				break;
			}
			case gamelist : {
				Message_GameList msglist = (Message_GameList) msgIn;
				controller.updateGamelist(msglist.getGames(), status);
				returnMsg = null;
				break;
			}
			case joined : {
				Message_JoinedGame msgjoined = (Message_JoinedGame) msgIn;
				status = Status.joinedgame;
				controller.joinGameApproved(msgjoined.getGame());
				returnMsg = null;
				break;
			}
			case players : {
				
				break;
			}
			case hand : {
				
				break;
			}
			case turn : {
				
				break;
			}
			case wiis : {
				
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

	//---------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Closes the connection to the Server on shutting down the program
	 */
	public void closeConnection() {
		try { if (socket != null) socket.close(); } catch (IOException e) {}
	}
	

}
