package Commons;

public class Message_CreateGame extends Message{

	private boolean germanCards;
	private boolean isSchieber;
	private int numOfRounds;
	private int winningPoints;
	
	public boolean isGermanCards() {
		return germanCards;
	}

	public boolean isSchieber() {
		return isSchieber;
	}

	public int getNumOfRounds() {
		return numOfRounds;
	}

	public int getWinningPoints() {
		return winningPoints;
	}

	public Message_CreateGame() {
		// TODO Auto-generated constructor stub
	}

}
