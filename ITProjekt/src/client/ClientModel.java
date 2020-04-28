package client;

import java.net.Socket;
import java.util.ArrayList;
import Commons.*;
import client.CommunicationThread.Status;

public class ClientModel {
	
	protected boolean userName;
	protected boolean password;
	protected boolean cnAdress;
	protected boolean isNewUserNameAvailable = false;
	protected boolean isNewPasswordValid = false;
	protected boolean isPasswordConfirmed = false;
	protected String user;
	protected String pw;
	protected String ipAddress = "127.0.0.1";
	protected int port = 6666;
	protected boolean isConnected = false;
	//Michis Variabel
	private CommunicationThread connection;
	public static int cardStyle=0; // 0 = französisch
	protected ArrayList<Card> actualHand = new ArrayList<>();

	
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
		
		cnAdress = false;
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
	/**
	 * @author sarah
	 * @param newValue
	 */
	public void validateUsername(String newValue) {
		newValue = newValue.trim();
		this.isNewUserNameAvailable = false;
		if(newValue.isEmpty() == false) {
			Message_UserNameAvailable msg = new Message_UserNameAvailable(newValue);
			connection.sendMessage(msg);				
		}
	}
	
	public void validatePassword(String newValue) {	
		if(Validation_LoginProcess.isPasswordValid(newValue)) {
			isNewPasswordValid = true;
		}else {
			isNewPasswordValid = false;
		}
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
	
	/**@author sarah
	 * create new user
	 */
	public boolean isPwValid(String newPasswordtxt) {
		boolean pwValid = false;
		if(Validation_LoginProcess .isPasswordValid(newPasswordtxt)) {
			pwValid = true;
		} 
		return pwValid;
	}
	/**
	 * @author sarah
	 * @param newPasswordtxt
	 * @param confirmPasswordtxt
	 */
	public void confirmPw(String newPasswordtxt, String confirmPasswordtxt) {		
		if(newPasswordtxt.equals(confirmPasswordtxt)) {
			this.isPasswordConfirmed = true;
		} else {
			this.isPasswordConfirmed = false;
		}
	}
	/**
	 * @author sarah
	 * @param userName
	 * @param password
	 */
	public void createUser(String userName, String password) {
		userName = userName.trim();
		Message_Register msg = new Message_Register(userName, password);
		connection.sendMessage(msg);		
	}

	/**
	 * @author sarah
	 * @param isAvaiable
	 */
	public void setisUserNameAvaiable(boolean isAvaiable) {
		this.isNewUserNameAvailable = isAvaiable;
	}

	
	public void playCard(Card card) {
		Message_Turn turn = new Message_Turn(card, user);
		connection.sendMessage(turn);
		
		
	}
	
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
	
	public void sendWiis(ArrayList<Wiis> wiisReturn) {
		Message_Wiis wiis = new Message_Wiis(wiisReturn, ); //TODO userID mitsenden
		connection.sendMessage(wiis);
	}

//Getters from connection---------------------------------------------------------------------------------------------
	public void setCurretnGame(Game g) {
		connection.setCurrentGame(g);
		
	}
	
	public Game getCurrentGame() {
		return connection.getCurrentGame();
	}
//-----------------------------------------------------------------------------------------------------------


	public void closeConnection() {
		connection.closeConnection();
	}

}
