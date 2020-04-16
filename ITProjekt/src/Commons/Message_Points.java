package Commons;
/**
 * Displays the points of a player or a Team when all cards are played
 * In Gamemode Schieber both players are displayed in Gamemode Differenzler only Playr one is used, playerII is null
 * @author mibe1
 *
 */
public class Message_Points extends Message{
	
	private String playerI;
	private String playerII;
	private int points;
	public Message_Points(String playerI, String playerII, int points) {
		super();
		this.playerI = playerI;
		this.playerII = playerII;
		this.points = points;
	}
	public String getPlayerI() {
		return playerI;
	}
	public void setPlayerI(String playerI) {
		this.playerI = playerI;
	}
	public String getPlayerII() {
		return playerII;
	}
	public void setPlayerII(String playerII) {
		this.playerII = playerII;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

}
