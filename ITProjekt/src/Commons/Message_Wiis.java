package Commons;

import java.util.ArrayList;

/**
 * Used to send the wiis from the client to the server and the counting wiis from the Server to all clients only used in Schieber mode
 * @author mibe1
 *
 */
public class Message_Wiis extends Message{

	private ArrayList<Wiis> wiis;
	private int userID;
	
	public Message_Wiis(ArrayList<Wiis> wiis, int userID) {
		super();
		this.wiis = wiis;
		this.userID = userID;
	}
	
	public Message_Wiis(ArrayList<Wiis> wiis) {
		super();
		this.wiis = wiis;
		this.userID = -1;
	}
	
	public ArrayList<Wiis> getWiis() {
		return wiis;
	}
	
	public void setWiis(ArrayList<Wiis> wiis) {
		this.wiis = wiis;
	}
	
}
