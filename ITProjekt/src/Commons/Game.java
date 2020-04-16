package Commons;

public class Game {
	public static int nextID = 1;
	private int gameId;
	private boolean isRunning;
	private boolean isSchieber;
	private int numOfPlayers;
	private int numOfRounds;
	private int winningPoints;
	private boolean isGermanCards;

	public Game() {
		// TODO Auto-generated constructor stub
	}
	
	public int getGameId() {
		return gameId;
	}


	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


	public boolean isRunning() {
		return isRunning;
	}


	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}


	public boolean isSchieber() {
		return isSchieber;
	}


	public void setSchieber(boolean isSchieber) {
		this.isSchieber = isSchieber;
	}


	public int getNumOfPlayers() {
		return numOfPlayers;
	}


	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}


	public int getNumOfRounds() {
		return numOfRounds;
	}


	public void setNumOfRounds(int numOfRounds) {
		this.numOfRounds = numOfRounds;
	}


	public int getWinningPoints() {
		return winningPoints;
	}


	public void setWinningPoints(int winningPoints) {
		this.winningPoints = winningPoints;
	}
	
	public boolean isGermanCards() {
		return isGermanCards;
	}

	public void setGermanCards(boolean isGermanCards) {
		this.isGermanCards = isGermanCards;
	}
	
}
