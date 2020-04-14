package Commons;

import java.util.ArrayList;
import java.util.Collections;

public class Message_Hand extends Message{
	
	private int size;
	private ArrayList<Card> hand;
	
	public Message_Hand(ArrayList<Card> hand) {
		super();
		this.hand = hand;
		this.size = hand.size();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	@Override
	public String toString() {
		return "Message_Hand" + this.getId();
	}
	
	/**
	 * Organizing the cards in a certain order
	 */
	public void organize() {
		ArrayList<Card> spades = new ArrayList<Card>();
		ArrayList<Card> hearts = new ArrayList<Card>();
		ArrayList<Card> clubs = new ArrayList<Card>();
		ArrayList<Card> diamonds = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.getSuit() == Card.Suit.ShieldsOrSpades)
				spades.add(c);
			else if(c.getSuit() == Card.Suit.RosesOrHearts)
				hearts.add(c);
			else if(c.getSuit() == Card.Suit.BellsOrClubs)
				clubs.add(c);
			else
				diamonds.add(c);
		}
		Collections.sort(spades);
		Collections.sort(hearts);
		Collections.sort(clubs);
		Collections.sort(diamonds);
		spades.addAll(hearts);
		spades.addAll(clubs);
		spades.addAll(diamonds);
		hand = spades;
	}
}
