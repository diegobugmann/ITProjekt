/**
 * 
 */
package Commons;

/**
 * @author mibe1
 *
 */


public class Message_PointUpdateDifferenzler extends Message {
	private int aktuellePoints;

	public Message_PointUpdateDifferenzler(int pointsToGo) {
		super();
		this.aktuellePoints = pointsToGo;
	}

	public int getaktuellePoints() {
		return aktuellePoints;
	}
	
}
