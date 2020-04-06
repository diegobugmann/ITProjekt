package server;

import java.util.ArrayList;

public class Team {
	
	private int teamID;
	private ArrayList<Player> playerList;
	private int score;
	private int totalScore;
	
	public Team(ArrayList<Player> players) {
		this.playerList = players;
	}
	
	public void updatePoints(int points) {
		this.score += points; //score wird bei mehreren Spielen jeweils am Anfang zurückgesetzt
		this.totalScore += points;
	}
	
}
