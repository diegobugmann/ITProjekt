/**
 * 
 */
package Commons;

import java.util.ArrayList;

/**
 * @author mibe1
 *
 */
public class Message_WiisInfo extends Message {

	private ArrayList<Wiis> wiisPlayerI;
	private ArrayList<Wiis> wiisPlayerII;
	private String playerI;
	private String playerII;
	/**
	 * 
	 * @param wiisPlayerI
	 * @param wiisPlayerII
	 * @param playerI
	 * @param playerII
	 */
	public Message_WiisInfo(ArrayList<Wiis> wiisPlayerI, ArrayList<Wiis> wiisPlayerII, String playerI,
			String playerII) {
		super();
		this.wiisPlayerI = wiisPlayerI;
		this.wiisPlayerII = wiisPlayerII;
		this.playerI = playerI;
		this.playerII = playerII;
	}
	public ArrayList<Wiis> getWiisPlayerI() {
		return wiisPlayerI;
	}
	public ArrayList<Wiis> getWiisPlayerII() {
		return wiisPlayerII;
	}
	public String getPlayerI() {
		return playerI;
	}
	public String getPlayerII() {
		return playerII;
	}
	


}
