package server;

import java.net.Socket;

public class User {
	
	private int userID;
	private String name;
	private Socket clientSocket;
	private ServerModel model;
	
	public User(ServerModel model, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.model = model;
		
		Runnable run = new Runnable() {
			@Override
			public void run() {
				while(true) {
					Message msg = Message.receive(socket); //Methode, die auf Msges wartet
					
					/* Hier wird entschieden, bei welchen Msgs was gemacht wird
					 
					if (msg instanceof ChatMsg) {				
						model.broadcast((ChatMsg) msg);
					} else if (msg instanceof JoinMsg) {
						Client.this.name = ((JoinMsg) msg).getName();
					}
					
					*/
					
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
		
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
