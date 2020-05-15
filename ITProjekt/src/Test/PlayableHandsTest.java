package Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Commons.Card;
import Commons.GameType;
import server.PlayValidation;

/**
 * @author digib
 */
public class PlayableHandsTest {
	
	/**
	 * @author digib
	 * source: B. Richards, Poker
	 * We define the hands using abbreviations. The code at the bottom
	 * of this class can translate one of these strings into a card.
	 * Another method takes a set of five cards, and translates the whole hand
	 * Yet another method does this for a whole set of hands
	 */

	private static String[][] randomHand = {
			{ "8S", "TS", "JS", "6H", "TH", "JH", "7D", "JD", "AD" },
			};
	
	private static String[][] playedCards = {
			{ "6C", "9C", "TD"},
			};
	
	ArrayList<ArrayList<Card>> randomHands;
	ArrayList<ArrayList<Card>> playedCardss;
	GameType trumpf;
	boolean isSchieber;
	ArrayList<Card> pc;
	ArrayList<Card> hand;
	
	/**
	 * @author digib
	 * source: B. Richards, Poker
	 */
	@Before
	public void makeHands() {
		randomHands = makeHands(randomHand);
		playedCardss = makeHands(playedCards);
		trumpf = GameType.AcornsOrDiamonds;
		isSchieber = true;
		pc = playedCardss.get(0);
		hand = randomHands.get(0);
	}

	/**
	 * @author digib
	 * source: B. Richards, Poker
	 */
	@Test
	public void testPlayableCards() {
		ArrayList<Card> playableCards = PlayValidation.getPlayableCards(hand, pc, trumpf, isSchieber);
		assertTrue(playableCards.size() == 8);
	}
	
	
	/**
	 * @author digib
	 * Make an ArrayList of hands from an array of string-arrays
	 * source: B. Richards, Poker
	 */
	private ArrayList<ArrayList<Card>> makeHands(String[][] handsIn) {
		ArrayList<ArrayList<Card>> handsOut = new ArrayList<>();
		for (String[] hand : handsIn) {
			handsOut.add(makeHand(hand));
		}
		return handsOut;
	}
	
	/**
	 * @author digib
	 * Make a hand (ArrayList<Card>) from an array of 9 strings
	 * source: B. Richards, Poker
	 */
	private ArrayList<Card> makeHand(String[] inStrings) {
		ArrayList<Card> hand = new ArrayList<>();
		for (String in : inStrings) {
			hand.add(makeCard(in));
		}
		return hand;
	}
	
	/**
	 * @author digib
	 * Create a card from a 2-character String.
	 * First character is the rank (6-9, T, J, Q, K, A) 
	 * Second character is the suit (C, D, H, S)
	 * source: B. Richards, Poker
	 */
	private Card makeCard(String in) {
		char r = in.charAt(0);
		Card.Rank rank = null;
		if (r == '6') rank = Card.Rank.Six;
		else if (r == '7') rank = Card.Rank.Seven;
		else if (r == '8') rank = Card.Rank.Eight;
		else if (r == '9') rank = Card.Rank.Nine;
		else if (r == 'T') rank = Card.Rank.Ten;
		else if (r == 'J') rank = Card.Rank.Jack;
		else if (r == 'Q') rank = Card.Rank.Queen;
		else if (r == 'K') rank = Card.Rank.King;
		else if (r == 'A') rank = Card.Rank.Ace;
		
		char s = in.charAt(1);
		Card.Suit suit = null;
		if (s == 'C') suit = Card.Suit.BellsOrClubs;
		if (s == 'D') suit = Card.Suit.AcornsOrDiamonds;
		if (s == 'H') suit = Card.Suit.RosesOrHearts;
		if (s == 'S') suit = Card.Suit.ShieldsOrSpades;

		return new Card(suit, rank);
	}
}
