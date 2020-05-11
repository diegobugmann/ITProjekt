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
	private Boolean isWiis;
	
	public Message_Points(String playerI, String playerII, int points, Boolean isWiis) {
		super();
		this.playerI = playerI;
		this.playerII = playerII;
		this.points = points;
		this.isWiis = isWiis;
	}
	public Boolean getIsWiis() {
		return isWiis;
	}
	//Getters
	public String getPlayerI() {
		return playerI;
	}

	public String getPlayerII() {
		return playerII;
	}

	public int getPoints() {
		return points;
	}


}
