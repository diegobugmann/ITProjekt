package Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import Commons.Card;
import Commons.Card.Rank;
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

	public static String[][] differentHands = {
		{ "6S", "7S", "8S", "AS", "7H", "TH", "JD", "QD", "9H" }, //dreiblatt
		{ "6S", "7S", "QS", "9H", "TH", "JH", "QH", "KH", "AD" }, //fuenfblatt
		{ "6S", "7S", "8S", "9S", "TS", "JS", "QS", "KS", "8D" }, //achtblatt
		{ "8S", "9S", "TS", "QS", "QH", "KH", "AH", "8D", "JC" }, //dreiblatt 2x
		{ "8S", "QS", "KS", "8H", "TH", "KH", "8D", "8C", "9C" }, //vierlinge
		{ "8S", "QS", "KS", "AS", "KH", "6D", "8D", "KD", "KC" }, //dreiblatt + vierlinge
		{ "9S", "7H", "8H", "9H", "TH", "JH", "9D", "9C", "AC" }, //fuenfblatt + 4nell
		{ "JS", "JH", "JD", "9C", "TC", "JC", "QC", "KC", "AC" }, //sechsblatt + 4bauern
		{ "8S", "9S", "QS", "9H", "QH", "9D", "QD", "9C", "QC" }, //4nell + vierlinge
		{ "8C", "9C", "TC", "JC", "TD", "TS", "TH", "JH", "QH" }, //vierblatt + dreiblatt + vierlinge
		{ "TC", "JC", "QC", "JD", "QD", "JS", "QS", "JH", "QH" }, //dreiblatt + vierlinge + 4bauern
		{ "6C", "7C", "8C", "9C", "TC", "9H", "TH", "JH", "QH" }, //fuenfblatt + vierblatt
		{ "7C", "8C", "9C", "QC", "KC", "AC", "6D", "7D", "8D" }, //dreiblatt 3x
		{ "9C", "QC", "KC", "AC", "8D", "9D", "TD", "9H", "9S" }, //dreiblatt 2x + 4nell
		};
	
	ArrayList<ArrayList<Card>> hands;
	
	/**
	 * @author digib
	 * source: B. Richards, Poker
	 */
	@Before
	public void makeHands() {
		hands = makeHands(differentHands);
	}

	/**
	 * @author digib
	 * tests all the different hands to see if they contain the expected Wiis-objects
	 */
	@Test
	public void testHands() {
		ArrayList<Card> hand;
		ArrayList<Wiis> wiis;
		
		//test dreiblatt
		hand = hands.get(0);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.size() == 1);
		
		//test fuenfblatt
		hand = hands.get(1);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.fuenfblatt);
		assertTrue(wiis.size() == 1);
		
		//test achtblatt
		hand = hands.get(2);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.achtblatt);
		assertTrue(wiis.size() == 1);
		
		//test dreiblatt 2x
		hand = hands.get(3);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.size() == 2);
		
		//test vierlinge
		hand = hands.get(4);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.viergleiche);
		assertTrue(wiis.size() == 1);
		
		//test dreiblatt + vierlinge
		hand = hands.get(5);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.viergleiche);
		assertTrue(wiis.size() == 2);
		
		//test fuenfblatt + 4nell
		hand = hands.get(6);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.fuenfblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.vierNeuner);
		assertTrue(wiis.size() == 2);
		
		//test sechsblatt + 4bauern
		hand = hands.get(7);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.sechsblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.vierBauern);
		assertTrue(wiis.size() == 2);
		
		//test 4nell + vierlinge
		hand = hands.get(8);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.vierNeuner);
		assertTrue(wiis.get(1).getBlatt() == Blatt.viergleiche);
		assertTrue(wiis.size() == 2);
		
		//test vierblatt + dreiblatt + vierlinge
		hand = hands.get(9);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.vierblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.viergleiche);
		assertTrue(wiis.get(2).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.size() == 3);
		
		//test dreiblatt + vierlinge + 4bauern
		hand = hands.get(10);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.vierBauern);
		assertTrue(wiis.get(2).getBlatt() == Blatt.viergleiche);
		assertTrue(wiis.size() == 3);
		
		//test fuenfblatt + vierblatt
		hand = hands.get(11);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.fuenfblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.vierblatt);
		assertTrue(wiis.size() == 2);
		
		//test dreiblatt 3x
		hand = hands.get(12);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(2).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.size() == 3);
		
		//test dreiblatt 2x + 4nell
		hand = hands.get(13);
		wiis = WiisValidation.validateWiis(hand);
		assertTrue(wiis.get(0).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.get(1).getBlatt() == Blatt.vierNeuner);
		assertTrue(wiis.get(2).getBlatt() == Blatt.dreiblatt);
		assertTrue(wiis.size() == 3);
		
	}
	
	
	/**
	 * @author digib
	 * Make an ArrayList of hands from an array of string-arrays
	 * source: B. Richards, Poker
	 */
	public static ArrayList<ArrayList<Card>> makeHands(String[][] handsIn) {
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
	public static ArrayList<Card> makeHand(String[] inStrings) {
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
	public static Card makeCard(String in) {
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
