package Commons;

public class Simple_Message extends Message{
	
	public enum Msg{
		CheckConnection,
		Received,
		Game_Start,
		Your_Turn,
		Ansage_Points,
		GameEnded,
		Ansage_Trumpf,
		Game_end,
		Login_accepted,
		registration_accepted;
	}
	
	private Msg type;
	public Simple_Message(Msg type) {
		this.type = type;
	}
	public Msg getType() {
		return type;
	}
	public void setType(Msg type) {
		this.type = type;
	}
	
	

}
