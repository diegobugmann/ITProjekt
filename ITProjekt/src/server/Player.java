package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.Wiis;

public class Player extends User {
	
	private Game currentGame;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private int announcedPoints;
	private ArrayList<Wiis> wiis = new ArrayList<Wiis>();
	private Player followingPlayer;
	private Team currentTeam;
	
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
	
	//Organizing the cards in a certain order (by Michi)
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
	
	public ArrayList<Wiis> validateWiis() {
		ArrayList<Wiis> wiis = Validation.validateWiis(hand);
		return wiis;
	}
	
	public ArrayList<Card> getPlayableCards() {
		ArrayList<Card> playableCards = Validation.getPlayableCards(
				hand, currentGame.getCurrentPlay().getPlayedCards(), currentGame.getTrumpf());
		return playableCards;
	}
	
	public void removeCard(Card c) {
		hand.remove(c);
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
