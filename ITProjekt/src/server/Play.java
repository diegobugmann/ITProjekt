package server;

import java.util.ArrayList;

import Commons.Card;

public class Play {
	
	private int points;
	private Team playWinner;
	private Player winningPlayer;
	private ArrayList<Card> playedCards = new ArrayList<Card>();
	
	public Play() {
		
	}
	
	public Team validateWinner() {
		//TODO
		return null;
	}
	
	public int validatePoints() {
		//TODO
		return 0;
	}
	
	public void addCard(Card c) {
		this.playedCards.add(c);
	}
	
}
