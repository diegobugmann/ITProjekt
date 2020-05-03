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
	private boolean isSchieber;
	
	public Play(GameType trumpf, boolean isSchieber) {
		this.trumpf = trumpf;
		this.isSchieber = isSchieber;
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
	 */
	public int validatePoints() {
		points = PlayValidation.validatePoints(playedCards, trumpf, isSchieber);
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
	
	public Team getWinningTeam() {
		return this.winningTeam;
	}
	
}
