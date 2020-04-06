package server;

import java.net.Socket;

public class User {
	
	private int userID;
	private String name;
	private Socket clientSocket;
	
	public User(Socket clientSocket, String name) {
		this.clientSocket = clientSocket;
		this.name = name;
	}

}
