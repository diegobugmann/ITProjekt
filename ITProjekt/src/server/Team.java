package server;

import java.util.ArrayList;

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
	
	public void addPoints(int points) {
		this.score += points; //TODO score wird bei mehreren Spielen jeweils am Anfang zurückgesetzt
		this.totalScore += points;
	}
	
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	
}
