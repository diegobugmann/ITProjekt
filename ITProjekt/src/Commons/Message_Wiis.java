package Commons;

/**
 * Used to send the wiis from the client to the server and the counting wiis from the Server to all clients only used in Schieber mode
 * @author mibe1
 *
 */
public class Message_Wiis extends Message{

	private Wiis[] wiis;
	public Message_Wiis(String player, Wiis[] wiis) {
		super();
		this.wiis = wiis;
	}
	public Wiis[] getWiis() {
		return wiis;
	}
	public void setWiis(Wiis[] wiis) {
		this.wiis = wiis;
	}
	
	

}
