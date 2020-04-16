package Commons;

import java.util.ArrayList;

/**
 * This message is used to send an ArrayList with all gmes wich are accessable to the client
 * Games are only in this list, when thei exist and are not started jet
 * This is also used to update the gamelist on the client side
 * @author mibe1
 *
 */
public class Message_GameList extends Message{

	//All Games
	private ArrayList<Game> games;
	
	/**
	 * Main constructor create a Message with all open games on the server
	 * @param games
	 */
	public Message_GameList(ArrayList<Game> games) {
		super();
		this.games = games;
	}
	/**
	 * Overloaded COnstructor in case no games created jet
	 */
	public Message_GameList() {
		super();
		this.games = new ArrayList<Game>();
	}

	/**
	 * Getters and setters of Gamelist
	 * @return
	 */
	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
	
	
	
}
