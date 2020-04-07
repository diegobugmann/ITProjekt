package server;

import java.net.Socket;
import java.util.ArrayList;

import Commons.Card;

public class Player extends User {
	
	private ArrayList<Card> hand;
	private boolean hisTurn;
	private Wiis wiis; //muss Wiis[] sein bei mehreren Wiis
	
	public Player(ServerModel model, Socket clientSocket) {
		super(model, clientSocket);
	}
	
}
