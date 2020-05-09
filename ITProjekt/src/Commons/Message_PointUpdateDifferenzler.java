/**
 * 
 */
package Commons;

/**
 * @author mibe1
 *
 */


public class Message_PointUpdateDifferenzler extends Message {
	private int pointsToGo;

	public Message_PointUpdateDifferenzler(int pointsToGo) {
		super();
		this.pointsToGo = pointsToGo;
	}

	public int getPointsToGo() {
		return pointsToGo;
	}
	
}
