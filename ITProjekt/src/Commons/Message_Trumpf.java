package Commons;

public class Message_Trumpf extends Message{
	
	public GameType trumpf;
	
	public Message_Trumpf(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public GameType getTrumpf() {
		return this.trumpf;
	}
}
