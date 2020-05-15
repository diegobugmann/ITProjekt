package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.GameType;

/**
 * @author digib
 */
public class Play {
	
	private int points;
	private Team winningTeam;
	private Player winningPlayer;
	private ArrayList<Card> playedCards = new ArrayList<Card>();
	private ArrayList<Player> playedBy = new ArrayList<Player>(); //linking the player to the cards (same indices)
	private GameType trumpf;
	
	public Play(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	/**
	 * @author digib
	 * @return Player
	 */
	public Player validateWinner() {
		winningPlayer = PlayValidation.validateWinner(playedCards, playedBy, trumpf);
		winningTeam = winningPlayer.getCurrentTeam();
		return winningPlayer;
	}
	
	/**
	 * @author digib
	 * @return points
	 * validates the points from a play
	 */
	public int validatePoints() {
		points = PlayValidation.validatePoints(playedCards, trumpf);
		return points;
	}
	
	/**
	 * @author digib
	 * @param card
	 * adds a card to playedCards
	 */
	public void addCard(Card c) {
		this.playedCards.add(c);
	}
	
	//Getters and setters
	
	public ArrayList<Card> getPlayedCards(){
		return this.playedCards;
	}
	
	public ArrayList<Player> getPlayedBy(){
		return this.playedBy;
	}
	
	public Team getWinningTeam() {
		return this.winningTeam;
	}
	
}
