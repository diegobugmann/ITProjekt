package Commons;

import java.util.ArrayList;
/**
 * Sends the names of all Players of a game to dispaly them
 * @author mibe1
 *
 */
public class Message_Players extends Message{
	 private ArrayList<String> players;
	/**
	 * Main constructor
	 * @param players
	 */
	public Message_Players(ArrayList<String> players) {
		super();
		this.players = players;
	}
//Getters and Setters
	public ArrayList<String> getPlayers() {
		return players;
	}

	
}
