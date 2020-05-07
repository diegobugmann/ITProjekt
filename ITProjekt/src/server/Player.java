package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;
import Commons.Wiis;
 
/**
  * @author digib
  */
public class Player extends User {
	
	private Game currentGame;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Wiis> wiis = new ArrayList<Wiis>();
	private Player followingPlayer;
	private Team currentTeam;
	private int announcedPoints;
	private int beginningOrder;
	private boolean hasStoeck;
	
	public Player(ServerModel model, Socket clientSocket) {
		super(model, clientSocket);
	}
	
	public void addCard(Card c) {
		this.hand.add(c);
	}
	
	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	public void addWiis(ArrayList<Wiis> wiis) {
		this.wiis.addAll(wiis);
	}
	
	public ArrayList<Wiis> getWiis(){
		return this.wiis;
	}
	
	public void resetWiis() {
		this.wiis.clear();
	}
	
	public int getBeginningOrder() {
		return this.beginningOrder;
	}
	
	public void setBeginningOrder(int beginningOrder) {
		this.beginningOrder = beginningOrder;
	}
	
	/**
	 * @author michi
	 * Organizing the cards in a certain order
	 */
	public void organizeHand() {
		ArrayList<Card> spades = new ArrayList<Card>();
		ArrayList<Card> hearts = new ArrayList<Card>();
		ArrayList<Card> clubs = new ArrayList<Card>();
		ArrayList<Card> diamonds = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.getSuit() == Card.Suit.ShieldsOrSpades)
				spades.add(c);
			else if(c.getSuit() == Card.Suit.RosesOrHearts)
				hearts.add(c);
			else if(c.getSuit() == Card.Suit.BellsOrClubs)
				clubs.add(c);
			else
				diamonds.add(c);
		}
		Collections.sort(spades);
		Collections.sort(hearts);
		Collections.sort(clubs);
		Collections.sort(diamonds);
		spades.addAll(hearts);
		spades.addAll(clubs);
		spades.addAll(diamonds);
		hand = spades;
	}
	
	public void setCurrentGame(Game g) {
		this.currentGame = g;
	}
	
	public Game getCurrentGame() {
		return this.currentGame;
	}
	
	public void setAnnouncedPoints(int announcedPoints) {
		this.announcedPoints = announcedPoints;
	}
	
	public int getAnnouncedPoints() {
		return this.announcedPoints;
	}
	
	/**
	 * @author digib
	 * adds the points from all wiis-Objects to the currentTeam
	 */
	public void addWiisPointsToTeam() {
		for (Wiis w : wiis) {
			int points = w.getBlatt().getPoints();
			this.currentGame.addPoints(points, this.currentTeam);
		}
	}
	
	/**
	 * @author digib
	 * @return ArrayList<Wiis>
	 */
	public ArrayList<Wiis> validateWiis() {
		ArrayList<Wiis> wiis = WiisValidation.validateWiis(hand);
		return wiis;
	}
	
	/**
	 * @author digib
	 * @return ArrayList<Card>
	 */
	public ArrayList<Card> getPlayableCards() {
		ArrayList<Card> playableCards = PlayValidation.getPlayableCards(
				hand, currentGame.getCurrentPlay().getPlayedCards(), currentGame.getTrumpf(), currentGame.isSchieber());
		return playableCards;
	}
	
	public void removeCard(Card c) {
		Card cardToRemove = null;
		for (Card card : hand) {
			if (c.getRank() == card.getRank() && c.getSuit() == card.getSuit())
				cardToRemove = card;
		}
		hand.remove(cardToRemove);
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	public Player getFollowingPlayer() {
		return this.followingPlayer;
	}
	
	public void setFollowingPlayer(Player followingPlayer) {
		this.followingPlayer = followingPlayer;
	}

	public Team getCurrentTeam() {
		return currentTeam;
	}
	
	public void setCurrentTeam(Team t) {
		this.currentTeam = t;
	}
	
	/**
	 * @author digib
	 * looks whether a player has the stoeck or not. If so, sets boolean to true
	 */
	public void lookForStoeck() {
		Suit trumpf = PlayValidation.getTrumpfAsSuit(currentGame.getTrumpf());
		boolean hasQueen = false;
		boolean hasKing = false;
		//only look for stoeck if trumpf is neither bottomsup nor topsdown
		if (trumpf != null) {
			for (Card c : hand) {
				if (c.getSuit() == trumpf && c.getRank() == Rank.King)
					hasKing = true;
				else if (c.getSuit() == trumpf && c.getRank() == Rank.Queen)
					hasQueen = true;
			}
		}
		if (hasQueen && hasKing)
			this.hasStoeck = true;
		else
			this.hasStoeck = false;
	}
	
	/**
	 * @author digib
	 * @return boolean
	 * looks whether a player has played both of the stoeck. If so, return true
	 */
	public boolean hasPlayedStoeck() {
		Suit trumpf = PlayValidation.getTrumpfAsSuit(currentGame.getTrumpf());
		boolean hasQueen = false;
		boolean hasKing = false;
		for (Card c : hand) {
			if (c.getSuit() == trumpf && c.getRank() == Rank.King)
				hasKing = true;
			else if (c.getSuit() == trumpf && c.getRank() == Rank.Queen)
				hasQueen = true;
		}
		if (!hasQueen && !hasKing) {
			this.hasStoeck = false;
			return true;
		} else
			return false;
	}
	
	public boolean hasStoeck() {
		return hasStoeck;
	}
	
	/**
	 * @author digib
	 * @return Player
	 */
	public Player getTeammate() {
		Player teammate = null;
		for (Player p : currentTeam.getPlayerList()) {
			if (p != this) {
				teammate = p;
			}
		}
		return teammate;
	}
}
