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
	public Message_PointsDifferenzler(int ansage, int points, int difference) {
		super();
		this.ansage = ansage;
		this.points = points;
		this.difference = difference;
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
	
	


}
