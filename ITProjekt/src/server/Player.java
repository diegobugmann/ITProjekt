package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
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
	
	/**
	 * @author digib
	 * @param model, clientSocket
	 */
	public Player(ServerModel model, Socket clientSocket) {
		super(model, clientSocket);
	}
	
	/**
	 * @author digib
	 * @param card
	 * adds a card to the hand
	 */
	public void addCard(Card c) {
		this.hand.add(c);
	}
	
	/**
	 * @author digib
	 * @param ArrayList<Wiis>
	 */
	public void addWiis(ArrayList<Wiis> wiis) {
		this.wiis.addAll(wiis);
	}
	
	/**
	 * @author digib
	 * empties the wiis-list
	 */
	public void resetWiis() {
		this.wiis.clear();
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
	
	/**
	 * @author digib
	 * adds the points from all wiis-Objects to the currentTeams totalPoints
	 */
	public void addWiisPointsToTeam() {
		for (Wiis w : wiis) {
			int points = w.getBlatt().getPoints();
			this.currentGame.addPointsToTotal(points, this.currentTeam);
		}
	}
	
	/**
	 * @author digib
	 * @return ArrayList<Wiis>
	 * validates the wiis-objects from the hand
	 */
	public ArrayList<Wiis> validateWiis() {
		ArrayList<Wiis> wiis = WiisValidation.validateWiis(hand);
		return wiis;
	}
	
	/**
	 * @author digib
	 * @return ArrayList<Card>
	 * validates and returns the playableCards
	 */
	public ArrayList<Card> getPlayableCards() {
		ArrayList<Card> playableCards = PlayValidation.getPlayableCards(
				hand, currentGame.getCurrentPlay().getPlayedCards(), currentGame.getTrumpf(), currentGame.isSchieber());
		return playableCards;
	}
	
	/**
	 * @author digib
	 * @param cards
	 * removes a specific card from the hand (used if card is played)
	 */
	public void removeCard(Card c) {
		Card cardToRemove = null;
		for (Card card : hand) {
			if (c.getRank() == card.getRank() && c.getSuit() == card.getSuit())
				cardToRemove = card;
		}
		hand.remove(cardToRemove);
	}
	
	/**
	 * @author digib
	 * clears hand
	 */
	public void clearHand() {
		hand.clear();
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
	 * looks whether a player has played both of the stoeck. If so, set hasStoeck to false and return true
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
	
	/**
	 * @author digib
	 * @return Player
	 * returns the teammate of a player
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
	
	/**
	 * @author digib
	 * resets the player (currentGame, hand and wiis)
	 */
	public void reset() {
		this.setCurrentGame(null);
		this.clearHand();
		this.resetWiis();
	}
	
	//getters and setters
	
	public ArrayList<Card> getHand() {
		return this.hand;
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
	
	public boolean hasStoeck() {
		return hasStoeck;
	}
	
	public void setHasStoeck(boolean hasStoeck) {
		this.hasStoeck = hasStoeck;
	}
	
	public ArrayList<Wiis> getWiis(){
		return this.wiis;
	}
	
	public int getBeginningOrder() {
		return this.beginningOrder;
	}
	
	public void setBeginningOrder(int beginningOrder) {
		this.beginningOrder = beginningOrder;
	}
}
