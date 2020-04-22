package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;
import Commons.Wiis;
import Commons.Wiis.Blatt;

public class Validation {
	
	public static ArrayList<Card> getPlayableCards(ArrayList<Card> hand, ArrayList<Card> playedCards, GameType gameType) {
		if (playedCards.isEmpty()) return hand; //If yu are the first to play, you can play whatever you like
		ArrayList<Card> playableCards = (ArrayList<Card>) hand.clone();
		Card firstPlayed = playedCards.get(0);
		Suit playedSuit = firstPlayed.getSuit();
		
		//If player has cards from played suit, he can only play those. If not, he can play whichever card he wants
		if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) {
			if (containsSuit(playableCards, playedSuit)) {
				for (Card c : playableCards) {
					if (playedSuit != c.getSuit())
						playableCards.remove(c);
				}
			}
			return playableCards;
		}
		
		//TODO bei trumpf sonderheiten: bauer muss nicht angegeben werden, man darf immer stechen, untertrumpfen nur, wenn nicht angegeben werden kann
		else {
			String trumpf = gameType.toString();
			if (containsTrumpf(playableCards, gameType)) {
				//TODO wenn er trumpf hat, was darf er spielen? Dinge wie untertrumpfen hier beachten!
			} else { //gets called if player has no trumpf
				if (containsSuit(playableCards, playedSuit)) {
					for (Card c : playableCards) {
						if (playedSuit != c.getSuit())
							playableCards.remove(c);
					}
				}
				return playableCards;
			}
		}
		
		
		return playableCards;
	}
	
	
	//As soon as list contains at least one card from trumpf, it returns true
	private static boolean containsTrumpf(ArrayList<Card> hand, GameType gameType) {
		for (Card c : hand) {
			if (c.getSuit().toString().equals(gameType.toString()))
				return true;
		}
		return false;
	}
	
	
	//As soon as list contains at least one card from suit, it returns true
	private static boolean containsSuit(ArrayList<Card> hand, Suit suit) {
		for (Card c : hand) {
			if (c.getSuit() == suit)
				return true;
		}
		return false;
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
	
	
	//TODO weitere Wiis-Validierung sinnvoll gestalten (mit Liste)
	public static Wiis validateWiis(ArrayList<Card> hand) {
		Wiis currentWiis = isBlatt(hand);
		//7, 8 und 9-Blatt sind nur einzeln möglich ohne Kollision
		if (currentWiis.getBlatt() == Blatt.siebenblatt || currentWiis.getBlatt() == Blatt.achtblatt ||
			currentWiis.getBlatt() == Blatt.neunblatt)
			return currentWiis;
		else if (currentWiis.getBlatt() == Blatt.sechsblatt) {
			/*
			 * TODO Noch nach einem dreiblatt suchen
			 * Dazu Karten, die schon in einem Wiis verwendet werden, entfernen
			 */
		}
		else {
			/*
			 * TODO Bei 5-Blatt oder Kleiner noch nach vierlingen suchen und nach weiteren Blättern
			 * Dazu Karten, die schon in einem Wiis verwendet werden, entfernen
			 */
		}
		
		
		return null;
	}
	
	private static Wiis isVierGleiche(ArrayList<Card> hand) {
		int counter = 0;
		for (Rank r : Rank.values()) {
			counter = 0;
			for (Card c : hand) {
				if (c.getRank() == r) {
					counter++;
					//allenfalls remove, um weniger zu durchsuchen?
					if (counter == 4) {
						if (r == Rank.Nine) return new Wiis(Blatt.vierNeuner, c);
						else if (r == Rank.Jack) return new Wiis(Blatt.vierBauern, c);
						else return new Wiis(Blatt.viergleiche, c);
					}
				}
			}
		}
		return null;
	}
	
	//gibt bei Erfolg das höchste Blatt als Wiis zurück, ohne die Karten zu entfernen
	private static Wiis isBlatt(ArrayList<Card> hand) {
		for (int diff = hand.size()-1; diff >= 2; diff--) {
			for (int highest = hand.size()-1; highest >= diff; highest--) {
				if (hand.get(highest).getSuit() == hand.get(highest-diff).getSuit()) { //same suit?
					if (hand.get(highest).getRank().ordinal() == hand.get(highest-diff).getRank().ordinal()+diff) { //difference = 7 ranks?
						for (Blatt b : Blatt.values()) {
							if (diff == b.ordinal()+2) 
								return new Wiis(b, hand.get(highest));
						}
					}
				}
			}
		}
		return null;
	}
	
	
	/*Methoden ersetzt durch isVierGleiche
	 * 
	private static Wiis isVierBauern(ArrayList<Card> hand) {
		int counter = 0;
		for (Card c : hand) {
			if (c.getRank() == Rank.Jack)
				counter++;
		}
		if (counter < 4) return new Wiis(Blatt.vierBauern, null);
		else return null;
	}

	private static Wiis isVierNeuner(ArrayList<Card> hand) {
		int counter = 0;
		for (Card c : hand) {
			if (c.getRank() == Rank.Nine)
				counter++;
		}
		if (counter < 4) return new Wiis(Blatt.vierNeuner, null);
		else return null;
	}
	*/
	
	
	
	
	/* Alle Methoden werden ersetzt durch isBlatt()
	 *
	private static boolean isNeunBlatt(ArrayList<Card> hand) {
		if (hand.get(0).getSuit() == hand.get(8).getSuit())
			return true;
		else 
			return false;
	}

	private static boolean isAchtBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 7; i--) {
			if (hand.get(i).getSuit() == hand.get(i-7).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-7).getRank().ordinal()+7) { //difference = 7 ranks?
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isSiebenBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 6; i--) {
			if (hand.get(i).getSuit() == hand.get(i-6).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-6).getRank().ordinal()+6) { //difference = 6 ranks?
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isSechsBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 5; i--) {
			if (hand.get(i).getSuit() == hand.get(i-5).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-5).getRank().ordinal()+5) { //difference = 5 ranks?
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isFuenfBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 4; i--) {
			if (hand.get(i).getSuit() == hand.get(i-4).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-4).getRank().ordinal()+4) { //difference = 4 ranks?
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isVierBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 3; i--) {
			if (hand.get(i).getSuit() == hand.get(i-3).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-3).getRank().ordinal()+3) { //difference = 3 ranks?
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isDreiBlatt(ArrayList<Card> hand) {
		for (int i = hand.size()-1; i >= 2; i--) {
			if (hand.get(i).getSuit() == hand.get(i-2).getSuit()) { //same suit?
				if (hand.get(i).getRank().ordinal() == hand.get(i-2).getRank().ordinal()+2) { //difference = 2 ranks?
					return true;
				}
			}
		}
		return false;
	}
	*/
}
