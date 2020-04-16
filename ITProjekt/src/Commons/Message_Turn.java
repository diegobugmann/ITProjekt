package Commons;

/**
 * Represents a Turn of a Player to the Server and from the Server to the Other players for display
 * @author mibe1
 *
 */
public class Message_Turn extends Message{

	private Card card;
	private String player;
	
	public Message_Turn(Card card, String player) {
		super();
		this.card = card;
		this.player = player;
	}
	
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}



}
