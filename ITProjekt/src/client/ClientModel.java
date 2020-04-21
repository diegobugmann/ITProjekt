package client;

import java.net.Socket;

import Commons.*;



public class ClientModel {
	protected boolean userName;
	protected boolean password;
	protected String user;
	protected String pw;
	//Michis Variabel
	protected CommunicationThread connection;
	public static int cardStyle=0; // 0 = französisch
	
	/**
	 * Connects to the Server and creates a connection object to send or receive data from the server
	 * @param c Controller for Thread to Send actions
	 * @param ipAdress Server adress
	 * @param port Serverport
	 * @category Serverconnection
	 * @author mibe1 ispired by Bradley Richards code example
	 */
	public void connect(ClientController c, String ipAddress, int port) {
	    Socket socket = null;
	    //Trainingscode to not enter if Gui not ready Delete before publish
	    if(ipAddress.isEmpty())
	    	ipAddress = "127.0.0.1";
	    if(port == 0)
	    	port = 8080;
	    //__________________________________________________
        try {
            socket = new Socket(ipAddress, port);
            connection = new CommunicationThread(socket, c);
            connection.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public boolean validateUserName(String newValue) {
		//userName validierung nur ganz simpelhalte auf der Clientseit die DB abfrage etc wird beim Login auf dem Server gepr�ft!
		userName = false;
		
		if(newValue.isEmpty() == false) {
			userName = true;			
		}
		return userName;
	}
	
	public boolean validatePassword(String newValue) {
		//Passwort validierung nur ganz simpelhalte auf der Clientseit die DB abfrage etc wird beim Login auf dem Server gepr�ft!
		password = false;
		
		if(newValue.isEmpty() == false) {
			password = true;
		}
		return password;
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
		Message_CreateGame msg = new Message_CreateGame(isSchieber, isGermanCards, numOfRounds, winningPoints);
		connection.sendMessage(msg);
	}
	

	public void joinGame(int gameId) {
		Message_JoinGame msg = new Message_JoinGame(gameId);
		connection.sendMessage(msg);
		
	}

	public void updateGameList() {
		Simple_Message msg = new Simple_Message(Simple_Message.Msg.Get_GameList);
		connection.sendMessage(msg);
	}


}


