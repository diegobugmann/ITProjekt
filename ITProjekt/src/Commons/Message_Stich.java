package Commons;
/**
 * Sent from Server to all players the Stich for display, the logic about stich such as whom is the next player is made on the Server
 * @author mibe1
 *
 */
public class Message_Stich extends Message{
	private int points;
	private int StichId;
	public Message_Stich(int points, int stichId) {
		super();
		this.points = points;
		StichId = stichId;
	}
	public int getPoints() {
		return points;
	}
	public int getStichId() {
		return StichId;
	}
	


}
