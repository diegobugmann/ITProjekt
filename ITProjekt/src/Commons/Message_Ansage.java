package Commons;
/**
 * Represents a Ansage of Points only used in Gamemode Differenzler
 * @author mibe1
 *
 */
public class Message_Ansage extends Message{

	private int points;
	public Message_Ansage(int points) {
		super();
		this.points = points;
	}
	public int getPoints() {
		return points;
	}

}
