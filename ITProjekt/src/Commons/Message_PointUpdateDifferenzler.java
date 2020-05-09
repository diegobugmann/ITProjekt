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

	public Message_PointUpdateDifferenzler(int aktuellePoints) {
		super();
		this.aktuellePoints = aktuellePoints;
	}

	public int getaktuellePoints() {
		return aktuellePoints;
	}
	
}
