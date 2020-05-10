package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Commons.Card;
import Test.ValidationTest;

/**
 * @author digib
 */
public class CardDeck {
	
	private final ArrayList<Card> cards;
	private final boolean testMode = false; //set on true to test different hands
	private Random rand = new Random();
	
	/**
	 * @author digib
	 */
	public CardDeck(boolean germanCards) {
		cards = new ArrayList<Card>();
		shuffleCards();
	}
	
	/**
	 * @author digib
	 * generates the entire deck and shuffles the cards
	 */
    private void shuffleCards() {
    	for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
    }
    
	/**
	 * @author digib
	 * @param ArrayList<Player> players
	 * deals 9 cards to each player, shuffling first for new Rounds
	 */
    public void dealCards(ArrayList<Player> players) {
    	if (!testMode) {
    		if (cards.isEmpty()) 
        		shuffleCards();
        	for (Player p : players) {
        		for (int i = 0; i < 9; i++)
        			p.addCard(cards.remove(cards.size()-1));
        	}
    	} else {
    		ArrayList<ArrayList<Card>> testHands = ValidationTest.makeHands(ValidationTest.differentHands);
    		for (Player p : players) {
    			ArrayList<Card> testHand = testHands.remove(rand.nextInt(testHands.size()));
    			p.getHand().addAll(testHand);
    		}
    	}
    	
    }

}