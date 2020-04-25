package Commons;

import java.util.ArrayList;

public class Message_YourTurn extends Message{
	private ArrayList<Card> hand;
	
	public Message_YourTurn(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	

}
