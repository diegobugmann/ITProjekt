package server;

import java.util.ArrayList;

public class Team {
	
	private int teamID;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private int score;
	private int totalScore;
	
	public Team() {
		//ID vergeben
	}
	
	public void addPlayer(Player p) {
		this.playerList.add(p);
	}
	
	public void updatePoints(int points) {
		this.score += points; //score wird bei mehreren Spielen jeweils am Anfang zurückgesetzt
		this.totalScore += points;
	}
	
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	
}
