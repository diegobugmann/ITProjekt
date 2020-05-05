package client;

import java.net.Socket;
import java.util.ArrayList;
import Commons.*;
import client.CommunicationThread.Status;
import javafx.scene.control.TextField;

public class ClientModel {
	
	protected String user;
	protected String pw;
	protected String ipAddress = "127.0.0.1";
	protected int port = 6666;
	protected boolean isConnected = false;
	protected CommunicationThread connection;
	public static int cardStyle=0; // 0 = französisch
	protected ArrayList<Card> actualHand = new ArrayList<>();
	protected ArrayList<String> teams = new ArrayList<>();
	protected boolean isGameTypeSchieber = true;

	
	/**
	 * Connects to the Server and creates a connection object to send or receive data from the server
	 * @param c Controller for Thread to Send actions
	 * @param ipAdress Server adress
	 * @param port Serverport
	 * @category Serverconnection
	 * @author mibe1 inspired by Bradley Richards code example, sarah completed to connect
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
            e.printStackTrace();
            return false;
        }
	}
	
	/** 
	 * @author sarah: connection string 
	 * (For IP Address: Regex: https://www.oreilly.com/library/view/regular-expressions-cookbook/9780596802837/ch07s16.html, added port manual)
	 * @param newValue
	 * @return
	 */
	public boolean validateCnAdress(String newValue) {
		
		boolean cnAdress = false;
		String pattern = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
				"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
				":([0-9]){1,5}$";
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
	
	/**
	 * Code Michi Creates a game based on the Inputs form the GUI via controller and sends creation to Server via connection thread
	 */
	public void newGame(boolean isSchieber, boolean isGermanCards, int numOfRounds, int winningPoints) {
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
		}
		else
			System.out.println("Wrong Status");
		//TODO Errorhandling
	}


	public void joinGame(int gameId) {
		//Only join Game when user is in the correct Status to join a Game
		if(connection.getStatus() == Status.logedin) {
			connection.setStatus(Status.joingamerequested);
			Message_JoinGame msg = new Message_JoinGame(gameId);
			connection.sendMessage(msg);
		}
		else
			System.out.println("Wrong Status");
		//TODO Errorhandling
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
	 * @author Luca meyer
	 * sends the played Card to the CommunicationThread
	 */
	public void playCard(Card card) {
		Message_Turn turn = new Message_Turn(card, user);
		connection.sendMessage(turn);
		
		//System.out.println("Model Gespielte Karte: "+card); //TODO löschen
		//System.out.println("Model ActualHand: "+actualHand); //TODO löschen
		//System.out.println("Model Zug Fertig\n"); //TODO löschen
	}
	
	/**
	 * @author Luca meyer
	 * sends the selected Trumpf to the CommunicationThread
	 */
	public void setTrumpf(GameType gameType) {
		Message_Trumpf trumpf = new Message_Trumpf(gameType);
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

//Getters from connection---------------------------------------------------------------------------------------------
	public void setCurretnGame(Game g) {
		connection.setCurrentGame(g);
		
	}
	
	public Game getCurrentGame() {
		return connection.getCurrentGame();
	}
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

	public void closeConnection() {
		connection.closeConnection();
	}


	public void sendMessage(String input) {
		Message_Chat msg = new Message_Chat(input);
		connection.sendMessage(msg);
	}

}
