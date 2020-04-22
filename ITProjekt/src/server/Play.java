package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.GameType;

public class Play {
	
	private int points;
	private Team playWinner;
	private Player winningPlayer;
	private ArrayList<Card> playedCards = new ArrayList<Card>();
	private GameType trumpf;
	
	public Play(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public Team validateWinner() {
		//TODO
		return null;
	}
	
	public int validatePoints() {
		this.points = Validation.validatePoints(playedCards, trumpf);
		return this.points;
	}
	
	public void addCard(Card c) {
		this.playedCards.add(c);
	}
	
}
