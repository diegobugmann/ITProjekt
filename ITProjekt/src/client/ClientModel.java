package client;

import java.net.Socket;
import java.util.ArrayList;
import Commons.*;
import client.CommunicationThread.Status;

public class ClientModel {
	
	protected String user;
	protected String pw;
	protected String ipAddress = "localhost";
	protected int port = 6666;
	protected boolean isConnected = false;
	protected CommunicationThread connection;
	public static int cardStyle=0; // 0 = französisch
	protected ArrayList<Card> actualHand = new ArrayList<>();
	protected ArrayList<String> teams = new ArrayList<>();
	protected boolean isGameTypeSchieber = true; //= Schieber
	protected ArrayList<String> otherPlayers;

//Login and Connection
//------------------------------------------------------------------------	
	/**
	 * @author mibe1 inspired by Bradley Richards code example, sarah completed to connect
	 * Connects to the Server and creates a connection object to send or receive data from the server
	 * @param c Controller for Thread to Send actions
	 * @param ipAdress Server adress
	 * @param port Serverport
	 * @category Serverconnection
	 */
	public boolean connect(ClientController c) {
	    Socket socket = null;
	    
        try {
            socket = new Socket(ipAddress, port);
            connection = new CommunicationThread(socket, c);
            connection.start();
            this.isConnected = true;
            return true;

        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
	}
	
	/** 
	 * @author sarah: connection string 
	 * (Regex: https://www.oreilly.com)
	 * @param newValue
	 * @return
	 */
	public boolean validateCnAdress(String newValue) {
		
		boolean cnAdress = false;
		String pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))" +
				"|(([a-zA-Z0-9]+(-[a-zA-Z0-9]+)*\\.?)+(\\.[a-zA-Z]{2,})?)" +
				":([1-9]|[0-9]{2,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
		if(newValue.matches(pattern)) {
			cnAdress = true;
			this.ipAddress = newValue.split(":")[0];
			this.port = Integer.parseInt(newValue.split(":")[1]);
		}
		return cnAdress;
	}

	public void loginProcess(String user, String pw) {
		this.user = user;
		this.pw = pw;
		this.connection.setSenderName(user);
		Message_Login msg = new Message_Login(user, pw);
		connection.sendMessage(msg);		
	}
	
	public void setStatusToLogin() {
		connection.setStatus(Status.logedin);
	}
	
	/**
	 * @author mibe1
	 * Creates a game based on the Inputs form the GUI via controller and sends creation to Server via connection thread
	 */
	public Boolean newGame(boolean isSchieber, boolean isGermanCards, int numOfRounds, int winningPoints) {
		if(isSchieber == true) {
			isGameTypeSchieber = true;
		}else if(isSchieber == false) {
			isGameTypeSchieber = false;
		}
		//Only create Game when user is in the correct Status to create a Game
		if(connection.getStatus() == Status.logedin) {
			connection.setStatus(Status.joingamerequested);
			Message_CreateGame msg = new Message_CreateGame(isSchieber, isGermanCards, numOfRounds, winningPoints);
			connection.sendMessage(msg);
			return true;
		}
		else
			return false;
	}

	public Boolean joinGame(int gameId) {
		//Only join Game when user is in the correct Status to join a Game
		if(connection.getStatus() == Status.logedin) {
			connection.setStatus(Status.joingamerequested);
			Message_JoinGame msg = new Message_JoinGame(gameId);
			connection.sendMessage(msg);
			return true;
		}
		else
			return false;
	}
	
	public void disconnect() {
		connection.closeConnection();
		this.isConnected = false;
	}

	public void updateGameList() {
		Simple_Message msg = new Simple_Message(Simple_Message.Msg.Get_GameList);
		connection.sendMessage(msg);
	}
	/**
	 * Sending Chat Message to Server to resend to all Players
	 * @param input
	 */
	public void sendChatMessage(String input) {
		Message_Chat msg = new Message_Chat(input);
		connection.sendMessage(msg);
	}
	
	
//During playing
//------------------------------------------------------------------------
	/**
	 * @author Luca meyer
	 * sends the played Card to the CommunicationThread
	 */
	public void playCard(Card card) {
		Message_Turn turn = new Message_Turn(card, user);
		connection.sendMessage(turn);
	}
	
	/**
	 * @author Luca meyer
	 * sends the selected Trumpf to the CommunicationThread
	 */
	public void setTrumpf(GameType gameType) {
		Message trumpf;
		if(gameType == GameType.Schieber) {
			trumpf = new Simple_Message(Simple_Message.Msg.Schiebe);
		}else {
			trumpf = new Message_Trumpf(gameType);
		}
		connection.sendMessage(trumpf);
	}
	
	public void setActualHand(ArrayList<Card> hand) {
		this.actualHand=hand;
		
	}
	
	public ArrayList<Card> getActualHand(){
		return this.actualHand;
	}
	
	/**
	 * @author Luca meyer
	 * removes the played Card out of the actualHand ArrayList
	 */
	public void removeCard(Card c) {
		Card cardToRemove = null;
		for (Card card : actualHand) {
			if (c.getRank() == card.getRank() && c.getSuit() == card.getSuit())
				cardToRemove = card;
		}
		actualHand.remove(cardToRemove);
	}
	
	/**
	 * @author Luca Meyer
	 * sends the selected Wiis to the CommunicationThread
	 */
	public void sendWiis(ArrayList<Wiis> wiisReturn) {
		Message_Wiis wiis = new Message_Wiis(wiisReturn);
		connection.sendMessage(wiis);
	}
	
	/**
	 * @author Luca Meyer
	 * @param ansagePoints
	 * sends the ansagePoints to the server
	 */
	public void sendAnsagePoints(int ansagePoints) {
		Message_Ansage ansage = new Message_Ansage(ansagePoints);
		connection.sendMessage(ansage);
	}
	
	public void setPlayers(ArrayList<String> players) {
		for(String player : players) {
			if(player != this.user) {
				this.otherPlayers.add(player);
			}
		}
		
	}
	
//Getters from connection
//---------------------------------------------------------------------------------------------
	public void setCurrentGame(Game g) {
		connection.setCurrentGame(g);
		
	}
	
	public Game getCurrentGame() {
		return connection.getCurrentGame();
	}

//End and exit game
//-----------------------------------------------------------------------------------------------------------
	/**
	 * @author mibe1
	 * Tells the Server about an Exit from the Splashscreen with two Messages:
	 * cancelWaiting and get current Gamelist
	 * Sets the Status to logedin
	 */
	public void processAbbruch() {
		Simple_Message cancel = new Simple_Message(Simple_Message.Msg.CancelWaiting);
		connection.sendMessage(cancel);
		connection.setStatus(Status.logedin);
		updateGameList();
	}

	/**
	 * @author mibe1
	 * Exits the onGoing game
	 * Changes the Status and updates the GameList
	 */
	public void exitGame() {
		Message_Cancel msgCancel = new Message_Cancel();
		connection.sendMessage(msgCancel);
		connection.setStatus(Status.logedin);
		updateGameList();
		
	}
}
