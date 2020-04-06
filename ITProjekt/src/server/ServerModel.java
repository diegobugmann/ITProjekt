package server;

import java.util.ArrayList;

public class ServerModel {
	
	private ArrayList<User> clients;
	private ArrayList<Game> games;
	
	public ServerModel() {
		clients = new ArrayList<User>();
		games = new ArrayList<Game>();
	}
	
}
