package Commons;
/**
 * Message from client to server to create new Game
 * @author mibe1
 *
 */
public class Message_CreateGame extends Message{
	//Commons.game created from the client changed on the Serverside to a Server.game
	private Game game;
	/**
	 * Constructor
	 * @param game
	 */
	public Message_CreateGame(Game game) {
		super();
		this.game = game;
	}
	/**
	 * Getter
	 * @return
	 */
	public Game getGame() {
		return game;
	}
	
	

}
