package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;

/**
 * @author digib
 */
public class PlayValidation {
	
	/**
	 * @author digib
	 * @return ArrayList<Card> containing all playable cards
	 */
	public static ArrayList<Card> getPlayableCards(ArrayList<Card> hand, ArrayList<Card> playedCards, GameType gameType, boolean isSchieber) {
		if (playedCards.isEmpty()) return hand; //If you are the first to play, you can play whatever you like
		Suit playedSuit = playedCards.get(0).getSuit();
		//System.out.println("Played Suit: "+playedSuit);
		//System.out.println("Trumpf: "+gameType);
		boolean hasSuit = containsSuit(hand, playedSuit);
		ArrayList<Card> playableCards = (ArrayList<Card>) hand.clone();
		
		//GameMode BottomsUp or TopsDown
		if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) {
			if (hasSuit)
				removeNonSuitable(playableCards, playedSuit);
			return playableCards;
		} else {
			//GameMode other than BottomsUp or TopsDows
			if (!hasSuit && !isSchieber) //untertrumpfen is valid as soon as player doesnt have played suit (in differenzler!)
				return playableCards;
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
		for (int i = hand.size()-1; i >= 0; i--) {
			if (playedSuit != hand.get(i).getSuit())
				hand.remove(i);
		}
	}
	
	/**
	 * @author digib
	 * @return void
	 * Removes all cards but the trumpf that don't match the played suit
	 */
	private static void removeNonSuitable(ArrayList<Card> hand, Suit playedSuit, GameType trumpf) {
		for (int i = hand.size()-1; i >= 0; i--) {
			if (playedSuit != hand.get(i).getSuit() && !hand.get(i).getSuit().toString().equals(trumpf.toString()))
				hand.remove(i);
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
		for (int i = playableCards.size()-1; i >= 0; i--) {
			if (playableCards.get(i).getSuit() == trumpf && playableCards.get(i).getRank().getTrumpfValue() < highestTrumpf.getRank().getTrumpfValue())
				playableCards.remove(i);
		}
	}
	
	/**
	 * @author digib
	 * @return Suit
	 */
	public static Suit getTrumpfAsSuit(GameType gameType) {
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
	public static int validatePoints(ArrayList<Card> cards, GameType gameType, boolean isSchieber) {
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
		if (!isSchieber) //differenzler has no multiplication depending on trumpf
			return points;
		else {
			if (gameType == GameType.BottomsUp || gameType == GameType.TopsDown) points *= 3;
			else if (gameType == GameType.BellsOrClubs || gameType == GameType.ShieldsOrSpades) points *= 2;
			return points;
		}
	}
		
}