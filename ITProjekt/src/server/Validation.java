package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Card.Suit;
import Commons.GameType;

public class Validation {
	
	public static ArrayList<Card> getPlayableCards(ArrayList<Card> hand, ArrayList<Card> playedCards, GameType gameType) {
		if (playedCards.isEmpty()) return hand; //Man kann ausspielen, was man möchte
		Card firstPlayed = playedCards.get(0);
		ArrayList<Card> playableCards = hand;
		//TODO Nicht erlaubte Karten entfernen
		return playableCards;
	}
	
	public static int validatePoints(ArrayList<Card> cards, GameType gameType) {
		int points = 0;
		for (Card c : cards) {
			switch (c.getRank()) {
			case Ace:
				if (gameType != GameType.BottomsUp) points += 11; break;
			case King:
				points += 4; break;
			case Queen:
				points += 3; break;
			case Jack:
				if (c.getSuit().toString().equals(gameType.toString())) points += 20;
				else points += 2; break;
			case Ten:
				points += 10; break;
			case Nine:
				if (c.getSuit().toString().equals(gameType.toString())) points += 14; break;
			case Eight:
				if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) points += 8; break;
			case Seven:
				break;
			case Six:
				if (gameType == GameType.BottomsUp) points += 11; break;
			}
		}
		if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) points *= 3;
		else if (gameType == GameType.BellsOrClubs || gameType == GameType.ShieldsOrSpades) points *= 2;
		return points;
	}
	
	private boolean hasTrump(ArrayList<Card> hand, GameType gameType) {
		//TODO
		return true;
	}
	
	private boolean hasSuit(ArrayList<Card> hand, Suit suit) {
		//TODO
		return true;
	}
	
}
