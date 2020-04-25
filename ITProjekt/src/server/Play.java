package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.GameType;

public class Play {
	
	private int points;
	private Team playWinner;
	private Player winningPlayer;
	private ArrayList<Card> playedCards = new ArrayList<Card>();
	private ArrayList<Player> playedBy = new ArrayList<Player>(); //linking the player to the cards (same indices)
	private GameType trumpf;
	
	public Play(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public Player validateWinner() {
		winningPlayer = Validation.validateWinner(playedCards, playedBy, trumpf);
		return winningPlayer;
	}
	
	public int validatePoints() {
		points = Validation.validatePoints(playedCards, trumpf);
		return points;
	}
	
	public void addCard(Card c) {
		this.playedCards.add(c);
	}
	
	public ArrayList<Card> getPlayedCards(){
		return this.playedCards;
	}
	
	public ArrayList<Player> getPlayedBy(){
		return this.playedBy;
	}
	
}
