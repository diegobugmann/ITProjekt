/**
 * 
 */
package Commons;

/**
 * @author mibe1
 *
 */
public class Message_JoinedGame extends Message {

	private Commons.Game game;
	/**
	 * 
	 */
	public Message_JoinedGame(Game game) {
		super();
		this.game = game;
	}
	public Commons.Game getGame() {
		return game;
	}
	

}
