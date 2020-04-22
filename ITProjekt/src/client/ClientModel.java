package client;

import java.net.Socket;

import Commons.*;



public class ClientModel {
	protected boolean userName;
	protected boolean password;
	protected boolean cnAdress;
	protected String user;
	protected String pw;
	protected String ipAddress = "127.0.0.1";
	protected int port = 6666;
	protected boolean isConnected = false;
	//Michis Variabel
	protected CommunicationThread connection;
	public static int cardStyle=0;
	
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
	 * @author sarah: connection string (For IP Address: Regex: https://www.oreilly.com/library/view/regular-expressions-cookbook/9780596802837/ch07s16.html, added port manual)
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
	
	public boolean validateUserName(String newValue) {
		//userName validierung nur ganz simpelhalte auf der Clientseit die DB abfrage etc wird beim Login auf dem Server gepr�ft!
		userName = false;
		
		if(newValue.isEmpty() == false) {
			userName = true;
			//sendet an DB
			
		}
		return userName;
	}
	
	public boolean validatePassword(String newValue) {
		//Passwort validierung nur ganz simpelhalte auf der Clientseit die DB abfrage etc wird beim Login auf dem Server gepr�ft!
		password = false;
		
		if(newValue.isEmpty() == false) {
			//sendet an DB
			password = true;
		}
		return password;
	}
	
	
	public void loginProcess(String user, String pw) {
		boolean done = false;
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
		Message_CreateGame msg = new Message_CreateGame(isSchieber, isGermanCards, numOfRounds, winningPoints);
		connection.sendMessage(msg);
	}
	
	public void setCardStyle(int style) {
		this.cardStyle = style;
	}
	
	public int getCardStyle() {
		return this.cardStyle;
	}

	public void joinGame(int gameId) {
		Message_JoinGame msg = new Message_JoinGame(gameId);
		connection.sendMessage(msg);
		
	}
	
	public void disconnect() {
		connection.closeConnection();
		this.isConnected = false;
	}
	
}


