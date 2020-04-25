package Commons;

import java.util.ArrayList;

public class Message_YourTurn extends Message{
	private ArrayList<Card> playableCards;
	
	public Message_YourTurn(ArrayList<Card> playableCards) {
		this.playableCards = playableCards;
	}

	public ArrayList<Card> getHand() {
		return playableCards;
	}
	
	

}
