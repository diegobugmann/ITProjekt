package Commons;

import java.util.ArrayList;
/**
 * Sends the hand to the player 
 * @author mibe1
 *
 */
public class Message_Hand extends Message{
	
	private int size;
	private ArrayList<Card> hand;
	
	public Message_Hand(ArrayList<Card> hand) {
		super();
		this.hand = hand;
		this.size = hand.size();
	}

	//Getters and Setters
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
}
