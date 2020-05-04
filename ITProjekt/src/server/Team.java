package server;

import java.util.ArrayList;

/**
 * @author digib
 */
public class Team {
	
	private static int nextID = 1;
	private int teamID;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int score;
	private int totalScore;
	
	public Team() {
		this.teamID = nextID++;
	}
	
	public void addPlayer(Player p) {
		this.playerList.add(p);
	}
	
	/**
	 * @author digib
	 */
	public void addPoints(int points) {
		this.score += points;
	}
	
	public void addPointsToTotal(int points) {
		this.totalScore += points;
	}
	
	/**
	 * @author digib
	 * resets score in case of multiple rounds or cancellation
	 */
	public void resetScore() {
		this.score = 0;
	}
	
	/**
	 * @author digib
	 * resets totalScore in case of cancellation
	 */
	public void resetTotalScore() {
		this.totalScore = 0;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getTotalScore() {
		return this.totalScore;
	}
	
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	
	public boolean isFinished(Game currentGame) {
		if (this.score + this.totalScore >= currentGame.getWinningPoints())
			return true;
		else
			return false;
	}
	
}
