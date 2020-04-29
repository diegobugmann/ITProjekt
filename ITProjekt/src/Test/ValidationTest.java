package Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import Commons.Card;
import Commons.Wiis;
import Commons.Wiis.Blatt;
import server.WiisValidation;

/**
 * @author digib
 * source: B. Richards, Poker
 */
public class ValidationTest {
	
	/**
	 * @author digib
	 * source: B. Richards, Poker
	 * We define the hands using abbreviations. The code at the bottom
	 * of this class can translate one of these strings into a card.
	 * Another method takes a set of five cards, and translates the whole hand
	 * Yet another method does this for a whole set of hands
	 */

	private static String[][] dreiBlaetter = {
			{ "6S", "7S", "8S", "QD", "TH", "9C", "JD", "AS", "7H" },
			{ "6S", "7S", "8S", "QD", "TH", "9C", "JD", "AS", "7H" },
			{ "6S", "7S", "8S", "QD", "TH", "9C", "JD", "AS", "7H" },
			{ "6S", "7S", "8S", "QD", "TH", "9C", "JD", "AS", "7H" }
			};
	
	private static String[][] vierBlaetter = {
			{ "6S", "9C", "TS", "QD", "TH", "7S", "JD", "AS", "8S" },
			{ "6S", "9C", "TS", "QD", "TH", "7S", "JD", "AS", "8S" },
			{ "6S", "9C", "TS", "QD", "TH", "7S", "JD", "AS", "8S" },
			{ "6S", "9C", "TS", "QD", "TH", "7S", "JD", "AS", "8S" }
			};
	
	private static String[][] doppelVierlinge = {
			{ "8C", "9C", "TC", "8D", "TD", "8S", "TS", "8H", "TH" },
			};
	
	
	ArrayList<ArrayList<Card>> dreiBlattHands;
	ArrayList<ArrayList<Card>> vierBlattHands;
	ArrayList<ArrayList<Card>> doppelVierlingeHands;
	
	/**
	 * @author digib
	 * source: B. Richards, Poker
	 */
	@Before
	public void makeHands() {
		dreiBlattHands = makeHands(dreiBlaetter);
		vierBlattHands = makeHands(vierBlaetter);
		doppelVierlingeHands = makeHands(doppelVierlinge);
	}

	/**
	 * @author digib
	 * source: B. Richards, Poker
	 */
	@Test
	public void testDreiBlatt() {
		for (ArrayList<Card> hand : dreiBlattHands) {
			ArrayList<Wiis> wiis = WiisValidation.validateWiis(hand);
			for (Wiis w : wiis)
				assertTrue(w.getBlatt() == Blatt.dreiblatt);
		}
	}
	
	
	@Test
	public void testVierlingeVierlingeDreiblatt() {
		for (ArrayList<Card> hand : doppelVierlingeHands) {
			ArrayList<Wiis> wiis = WiisValidation.validateWiis(hand);
			assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
			assertTrue(wiis.get(1).getBlatt() == Blatt.viergleiche);
			assertTrue(wiis.get(2).getBlatt() == Blatt.viergleiche);
		}
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
