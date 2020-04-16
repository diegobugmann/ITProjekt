package Commons;
/**
 * Message from client to server to create new Game
 * @author mibe1
 *
 */
public class Message_CreateGame extends Message{
	

	private boolean isSchieber;
	private boolean isGermanCards;
	private int numOfPlayers;
	private int numOfRounds;
	private int winningPoints;
	public Message_CreateGame(boolean isSchieber, boolean isGermanCards, int numOfPlayers, int numOfRounds, int winningPoints) {
		super();
		this.isSchieber = isSchieber;
		this.isGermanCards = isGermanCards;
		this.numOfPlayers = numOfPlayers;
		this.numOfRounds = numOfRounds;
		this.winningPoints = winningPoints;
	}
	public boolean isGermanCards() {
		return isGermanCards;
	}
	public boolean isSchieber() {
		return isSchieber;
	}
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public int getNumOfRounds() {
		return numOfRounds;
	}
	public int getWinningPoints() {
		return winningPoints;
	}
	
	
	
	

}
