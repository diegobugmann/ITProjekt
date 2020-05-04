package Commons;
/**
 * Sent from Server to all players the Stich for display, the logic about stich such as whom is the next player is made on the Server
 * @author mibe1
 *
 */
public class Message_Stich extends Message {
	
	//private int StichId; TODO nötig?
	private String player;
	
	public Message_Stich(String player) {
		super();
		//StichId = stichId;
		this.player = player;
	}
	
	public String getPlayer() {
		return this.player;
	}
	
}
