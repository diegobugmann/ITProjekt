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
	public static int cardStyle=0;
	
	/**
	 * Connects to the Server and creates a connection object to send or receive data from the server
	 * @param c Controller for Thread to Send actions
	 * @param ipAdress Server adress
	 * @param port Serverport
	 * @category Serverconnection
	 * @author mibe1 ispired by Bradley Richards code example
	 */
	private void connect(ClientController c, String ipAddress, int port) {
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
		userName = false;
		
		if(newValue.isEmpty() == false) {
			userName = true;
			//sendet an DB
			
		}
		return userName;
	}
	
	public boolean validatePassword(String newValue) {
		password = false;
		
		if(newValue.isEmpty() == false) {
			//sendet an DB
			password = true;
		}
		
		
		return password;
	}
	
	public boolean loginProcess(String user, String pw) {
		boolean done = false;
		this.user = user;
		this.pw = pw;
		//Codevorschlag Michi kann auch anders ablaufen
		//Message_Login msg = new Message_Login(user, pw);
		//connection.sendMessage(msg);
		
		//an DB senden
		done = true;
		return done;
		
	}
	
	
	public void newGame() {
		
	}
	
	public void setCardStyle(int style) {
		this.cardStyle = style;
	}
	
	public int getCardStyle() {
		return this.cardStyle;
	}


}


