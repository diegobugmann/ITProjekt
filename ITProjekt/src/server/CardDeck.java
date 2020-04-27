package server;

import java.util.ArrayList;
import java.util.Collections;

import Commons.Card;

/**
 * @author digib
 */
public class CardDeck {
	
	private boolean germanCards;
	private final ArrayList<Card> cards;
	
	/**
	 * @author digib
	 */
	public CardDeck(boolean germanCards) {
		cards = new ArrayList<Card>();
		this.germanCards = germanCards;
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
    	if (cards.isEmpty()) 
    		shuffleCards();
    	for (Player p : players) {
    		for (int i = 0; i < 9; i++)
    			p.addCard(cards.remove(cards.size()-1));
    	}
    }

}