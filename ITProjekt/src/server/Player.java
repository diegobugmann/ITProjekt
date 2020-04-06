package server;

import java.net.Socket;
import java.util.ArrayList;

import Commons.Card;

public class Player extends User {
	
	private ArrayList<Card> hand;
	private boolean hisTurn;
	private Wiis wiis; //muss Wiis[] sein bei mehreren Wiis
	
	public Player(Socket clientSocket, String name) {
		super(clientSocket, name);
	}
	
}
