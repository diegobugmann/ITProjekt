package server;

import java.util.ArrayList;
import java.util.Collections;

import Commons.Card;

public class CardDeck {
	
	private boolean germanCards;
	private final ArrayList<Card> cards;
	
	public CardDeck(boolean germanCards) {
		cards = new ArrayList<Card>();
		this.germanCards = germanCards;
		shuffleCards();
	}
	
    public void shuffleCards() {
    	for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }
        Collections.shuffle(cards);
    }
    
    public Card dealCard() {
    	if (cards.size() > 0)
    		return cards.remove(cards.size()-1);
    	else
    		return null;
    }

}