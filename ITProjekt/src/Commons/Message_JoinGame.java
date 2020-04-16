package Commons;

public class Message_JoinGame extends Message{

	private int gameId;
	
	/**
	 * main Constructor containing the game the client wants to join
	 * @param gameId
	 * @param playerName
	 */
	public Message_JoinGame(int gameId, String playerName) {
		super();
		this.gameId = gameId;
	}

	//Getters
	public int getGameId() {
		return gameId;
	}
	
	

}
