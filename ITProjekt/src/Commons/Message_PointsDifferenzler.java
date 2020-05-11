/**
 * 
 */
package Commons;

/**
 * @author mibe1
 *
 */
public class Message_PointsDifferenzler extends Message {

	private int ansage;
	private int points;
	private int difference;
	private String playerName;
	private int differenceTotal;

	public Message_PointsDifferenzler(String playerName, int ansage, int points, int difference, int differenceTotal) {
		super();
		this.playerName = playerName;
		this.ansage = ansage;
		this.points = points;
		this.difference = difference;
		this.differenceTotal = differenceTotal;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getAnsage() {
		return ansage;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getDifference() {
		return difference;
	}
	
	public int getDifferenceTotal() {
		return differenceTotal;
	}
	
}
