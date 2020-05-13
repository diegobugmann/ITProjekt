package server;

import java.util.ArrayList;

/**
 * @author digib
 */
public class Team {
	
	private int teamID;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int score;
	private int totalScore;
	
	public void addPlayer(Player p) {
		this.playerList.add(p);
	}
	
	/**
	 * @author digib
	 */
	public void addPoints(int points) {
		this.score += points;
	}
	
	/**
	 * @author digib
	 * only used to delete stoeck in case they cannot be wiised in the first round
	 */
	public void removePoints(int points) {
		this.totalScore -= points;
	}
	
	/**
	 * @author digib
	 * used to update totalPoints
	 */
	public void addToTotal(int points) {
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
	
	/**
	 * @author digib
	 * @return boolean
	 * true if points from current and previous games add up to the winningPoints
	 */
	public boolean isFinished(Game currentGame) {
		if (this.score + this.totalScore >= currentGame.getWinningPoints())
			return true;
		else
			return false;
	}
	
	/**
	 * @author digib
	 * adds the points from current game to totalpoints
	 */
	public void updateTotalPoints() {
		this.totalScore += this.score;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}
	
	public int getTeamID() {
		return this.teamID;
	}
	
}
