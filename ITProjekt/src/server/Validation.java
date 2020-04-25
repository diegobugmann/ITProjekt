package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;
import Commons.Wiis;
import Commons.Wiis.Blatt;

public class Validation {
	
	/**
	 * @author digib
	 * @return ArrayList<Card> containing all playable cards
	 */
	public static ArrayList<Card> getPlayableCards(ArrayList<Card> hand, ArrayList<Card> playedCards, GameType gameType) {
		if (playedCards.isEmpty()) return hand; //If you are the first to play, you can play whatever you like
		Suit playedSuit = playedCards.get(0).getSuit();
		boolean hasSuit = containsSuit(hand, playedSuit);
		ArrayList<Card> playableCards = (ArrayList<Card>) hand.clone();
		
		//GameMode BottomsUp or TopsDown
		if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) {
			if (hasSuit)
				removeNonSuitable(playableCards, playedSuit);
			return playableCards;
		} else {
			//GameMode other than BottomsUp or TopsDows
			if (containsOnlyTrumpf(playableCards, gameType)) //if all player has left are trumpfs, he can play whatever he likes
				return playableCards;
			boolean trumpfAus = playedSuit.toString().equals(gameType.toString());
			boolean wurdeAbgestochen = (!trumpfAus && containsTrumpf(playedCards, gameType));
			
			if (containsTrumpf(playableCards, gameType)) { //if player has trumpf
				if (trumpfAus) {
					if (!hasBauerNaked(playableCards, gameType))
						removeNonSuitable(playableCards, playedSuit); //has to play trumpf
					return playableCards; //if he has bauer naked, he can play whichever card he wants
				} else if (wurdeAbgestochen) {
					if (hasSuit)
						removeNonSuitable(playableCards, playedSuit, gameType);
					removeLowerTrumpfs(playableCards, playedCards, gameType);
					return playableCards;
				} else { //player has trumpf and no trumpf has been played yet
					if (hasSuit)
						removeNonSuitable(playableCards, playedSuit, gameType);
					return playableCards;
				}
			} else { //gets called if player has no trumpf
				if (hasSuit)
					removeNonSuitable(playableCards, playedSuit); //if he has cards from the played suit, he can only play those
				return playableCards;
			}
		}
	}
	
	/**
	 * @author digib
	 * @return boolean
	 */
	private static boolean containsTrumpf(ArrayList<Card> cards, GameType trumpf) {
		for (Card c : cards) {
			if (c.getSuit().toString().equals(trumpf.toString()))
				return true;
		}
		return false;
	}
	
	/**
	 * @author digib
	 * @return boolean
	 */
	private static boolean containsOnlyTrumpf(ArrayList<Card> hand, GameType trumpf) {
		for (Card c : hand) {
			if (!c.getSuit().toString().equals(trumpf.toString()))
				return false;
		}
		return true;
	}
	
	/**
	 * @author digib
	 * @return boolean
	 */
	private static boolean containsSuit(ArrayList<Card> hand, Suit suit) {
		for (Card c : hand) {
			if (c.getSuit() == suit)
				return true;
		}
		return false;
	}
	
	/**
	 * @author digib
	 * @return void
	 * Removes all cards that don't match the played suit
	 */
	private static void removeNonSuitable(ArrayList<Card> hand, Suit playedSuit) {
		for (Card c : hand) {
			if (playedSuit != c.getSuit())
				hand.remove(c);
		}
	}
	
	/**
	 * @author digib
	 * @return void
	 * Removes all cards but the trumpf that don't match the played suit
	 */
	private static void removeNonSuitable(ArrayList<Card> hand, Suit playedSuit, GameType trumpf) {
		for (Card c : hand) {
			if (playedSuit != c.getSuit() && !c.getSuit().toString().equals(trumpf.toString()))
				hand.remove(c);
		}
	}
	
	/**
	 * @author digib
	 * @return boolean
	 * return true if player has the jack of trumpf on its own
	 */
	private static boolean hasBauerNaked(ArrayList<Card> hand, GameType trumpf) {
		ArrayList<Card> trumpfs = new ArrayList<Card>();
		for (Card c : hand) {
			if (c.getSuit().toString().equals(trumpf.toString()))
				trumpfs.add(c);
		}
		if (trumpfs.size() == 1) {
			if (trumpfs.get(0).getRank() == Rank.Jack)
				return true;
		}
		return false;
	}
	
	/**
	 * @author digib
	 * @return void
	 * remove all trumpf cards that are less worthy than the highest played trumpf card
	 */
	private static void removeLowerTrumpfs(ArrayList<Card> playableCards, ArrayList<Card> playedCards, GameType gameType) {
		Suit trumpf = getTrumpfAsSuit(gameType);
		//get the highest played trumpf card
		Card highestTrumpf = new Card(trumpf, Rank.Six); //start off with the 6 of trumpf (lowest possible)
		for (Card c : playedCards) {
			if (c.getSuit() == trumpf && c.getRank().getTrumpfValue() > highestTrumpf.getRank().getTrumpfValue())
				highestTrumpf = c;
		}
		//remove all trumpf cards that are less worthy than the highest played trumpf card
		for (Card c : playableCards) {
			if (c.getSuit() == trumpf && c.getRank().getTrumpfValue() < highestTrumpf.getRank().getTrumpfValue())
				playableCards.remove(c);
		}
	}
	
	/**
	 * @author digib
	 * @return Suit
	 */
	private static Suit getTrumpfAsSuit(GameType gameType) {
		Suit trumpf = null;
		for (Suit s : Suit.values()) {
			if (s.toString().equals(gameType.toString()))
				trumpf = s;
		}
		return trumpf;
	}
	
	
	/**
	 * @author digib
	 * @return Player winner
	 */
	public static Player validateWinner(ArrayList<Card> playedCards, ArrayList<Player> correspondingPlayer, GameType gameType) {
		Card winningCard = playedCards.get(0); //assume first card wins
		Suit playedSuit = playedCards.get(0).getSuit();
		
		if (gameType == GameType.TopsDown) {
			for (Card c : playedCards) {
				if (c.getSuit() == playedSuit && c.compareTo(winningCard) > 0) //highest card of played suit wins
					winningCard = c;
			}
		} else if (gameType == GameType.BottomsUp) {
			for (Card c : playedCards) {
				if (c.getSuit() == playedSuit && c.compareTo(winningCard) < 0) //lowest card of played suit wins
					winningCard = c;
			}
		} else {
			boolean trumpfPlayed = containsTrumpf(playedCards, gameType);
			Suit trumpf = getTrumpfAsSuit(gameType);
			if (trumpfPlayed) {
				for (Card c : playedCards) {
					if (c.getSuit() == trumpf && c.getRank().getTrumpfValue() > winningCard.getRank().getTrumpfValue())
						winningCard = c;
				}
			} else {
				for (Card c : playedCards) {
					if (c.getSuit() == playedSuit && c.compareTo(winningCard) > 0) //highest card of played suit wins
						winningCard = c;
				}
			}
		}
		int index = playedCards.indexOf(winningCard);
		Player winner = correspondingPlayer.get(index);
		return winner;
	}
	
	
	/**
	 * @author digib
	 * @return int points
	 */
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
	
	

	/**
	 * @author digib
	 * @return Wiis[] mit allen höchstmöglichen Wiis-Objekten ohne Kollision oder eine leere Liste
	 * //TODO weitere Wiis-Validierung sinnvoll gestalten (mit Liste)
	 */
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
			while (übrige Karten >= 3) {
				if (übrige Karten >= 4)
					suche nach vierlingen; gefunden? karten entfernen;
				suche nach weiteren blättern; gefunden? karten entfernen
			}
			
			 * TODO Bei 5-Blatt oder Kleiner noch nach vierlingen suchen und nach weiteren Blättern
			 * Dazu Karten, die schon in einem Wiis verwendet werden, entfernen
			 */
		}
		return null;
	}
	
	/**
	 * @author digib
	 * @return Vierlinge als Wiis, ohne Karten zu entfernen - oder null bei Misserfolg
	 */
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
	
	/**
	 * @author digib
	 * @return das höchste Blatt als Wiis, ohne Karten zu entfernen - oder null bei Misserfolg
	 */
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
