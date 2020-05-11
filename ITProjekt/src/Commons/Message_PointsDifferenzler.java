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

	public Message_PointsDifferenzler(int ansage, int points, int difference, String playerName, int differenceTotal) {
		super();
		this.ansage = ansage;
		this.points = points;
		this.difference = difference;
		this.playerName = playerName;
		this.differenceTotal = differenceTotal;
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
	public String getPlayerName() {
		return playerName;
	}
	public int getDifferenceTotal() {
		return differenceTotal;
	}
	
	


}
