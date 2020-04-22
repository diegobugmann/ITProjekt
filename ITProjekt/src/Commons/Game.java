package Commons;

import java.io.Serializable;

public class Game implements Serializable {
	private int gameId;
	private boolean isRunning;
	private boolean isSchieber;
	private String isSchieberDisplay;
	private int numOfRounds;
	private int winningPoints;
	private boolean isGermanCards;
	private int currentNumOfPlayers;
	private String currentNumOfPlayersString;
	
	public Game(boolean isGermanCards, int rounds, int winningPoints, boolean isSchieber, int gameId) {
		this.isGermanCards = isGermanCards;
		this.numOfRounds = rounds;
		this.winningPoints = winningPoints;
		this.isSchieber = isSchieber;
		this.gameId = gameId;
		this.currentNumOfPlayers = 1;
		this.currentNumOfPlayersString = currentNumOfPlayers + " / 4";
		if(isSchieber)
			this.isSchieberDisplay = "Schieber";
		else
			this.isSchieberDisplay = "Differenzler";

	}
	
	//Getters and Setters ---------------------------------------------------------------------------------------------------------
	public String getIsSchieberDisplay() {
		return isSchieberDisplay;
	}

	public int getGameId() {
		return gameId;
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

	public int getNumOfRounds() {
		return numOfRounds;
	}

	public int getWinningPoints() {
		return winningPoints;
	}
	
	public boolean isGermanCards() {
		return isGermanCards;
	}
	
	public void setCurrentNumOfPlayers(int i) {
		this.currentNumOfPlayers = i;
		currentNumOfPlayersString = i + " / 4";
	}
	
	public String getCurrentNumOfPlayersString() {
		return currentNumOfPlayersString;

	}

	public int getCurrentNumOfPlayers() {
		return this.currentNumOfPlayers;
	}
//--------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", isRunning=" + isRunning + ", isSchieber=" + isSchieber + ", numOfRounds="
				+ numOfRounds + ", winningPoints=" + winningPoints + ", isGermanCards=" + isGermanCards + " Num of Players "+currentNumOfPlayers+"]";
	}
	
}
