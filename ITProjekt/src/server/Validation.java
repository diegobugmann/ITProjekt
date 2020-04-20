package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;
import Commons.Wiis.Blatt;

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
	
	public static void validateWiis(ArrayList<Card> hand) {
		Blatt currentBlatt = null;
		if (isDreiBlatt(hand)) currentBlatt = Blatt.dreiblatt;
		if (isVierBlatt(hand)) currentBlatt = Blatt.vierblatt;
		if (isFuenfBlatt(hand)) currentBlatt = Blatt.fuenfblatt;
		if (isSechsBlatt(hand)) currentBlatt = Blatt.sechsblatt;
		if (isSiebenBlatt(hand)) currentBlatt = Blatt.siebenblatt;
		if (isAchtBlatt(hand)) currentBlatt =  Blatt.achtblatt;
		if (isNeunBlatt(hand)) currentBlatt = Blatt.neunblatt;
		if (isVierGleiche(hand)) currentBlatt = Blatt.viergleiche;
		if (isVierNeuner(hand)) currentBlatt = Blatt.vierNeuner;
		if (isVierBauern(hand)) currentBlatt = Blatt.vierBauern;
	}

	private static boolean isVierBauern(ArrayList<Card> hand) {
		int counter = 0;
		for (Card c : hand) {
			if (c.getRank() == Rank.Jack)
				counter++;
		}
		if (counter < 4) return false;
		else return true;
	}

	private static boolean isVierNeuner(ArrayList<Card> hand) {
		int counter = 0;
		for (Card c : hand) {
			if (c.getRank() == Rank.Nine)
				counter++;
		}
		if (counter < 4) return false;
		else return true;
	}

	private static boolean isVierGleiche(ArrayList<Card> hand) {
		int counter = 0;
		for (Rank r : Rank.values()) {
			counter = 0;
			if (r != Rank.Nine && r != Rank.Jack) {
				for (Card c : hand) {
					if (c.getRank() == r) {
						counter++;
						//allenfalls remove, um weniger zu durchsuchen?
						if (counter == 4) 
							return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean isNeunBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isAchtBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isSiebenBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isSechsBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isFuenfBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isVierBlatt(ArrayList<Card> hand) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isDreiBlatt(ArrayList<Card> hand) {
		//TODO Idee: Methode rekursiv auf einzelnen Arrays der Farbe aufrufen
		return false;
	}
	
}
