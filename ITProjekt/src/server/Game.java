package server;

import java.util.ArrayList;

import javafx.beans.property.SimpleIntegerProperty;

public class Game extends Commons.Game {
	
	private ArrayList<Team> teams = new ArrayList<Team>();
	private GameType trumpf;
	private CardDeck deck;
	private boolean isFistPlay;
	private ArrayList<Play> plays;
	private SimpleIntegerProperty numOfPlayers = new SimpleIntegerProperty(0);
	
	public Game(boolean germanCards, int rounds, int winningPoints, boolean isSchieber) {
		this.deck = new CardDeck(germanCards);
		this.numOfRounds = rounds;
		this.isFistPlay = true;
		this.isSchieber = isSchieber;
		plays = new ArrayList<Play>();
		this.winningPoints = winningPoints;
		
		if (isSchieber) {
			for (int i = 0; i < 2; i++) //Bei Schieber 2 Teams erstellen
				teams.add(new Team());
		} else {
			for (int i = 0; i < 4; i++) //Bei Differenzler 4 Teams erstellen
				teams.add(new Team());
		}
	}
	
	public boolean addPlayer(Player p) {
		if (isSchieber) {
			if (teams.get(0).getPlayerList().size() < 2)
				teams.get(0).addPlayer(p);
			else if (teams.get(1).getPlayerList().size() < 2)
				teams.get(1).addPlayer(p);
			else return false; //wenn schon 4 Spieler im Spiel sind, wird false zurückgegeben
			numOfPlayers.setValue(teams.get(0).getPlayerList().size()+teams.get(1).getPlayerList().size());
			return true;
		} else {
			for (Team t : teams) {
				if (t.getPlayerList().size() == 0) { //fügt ein Spieler in einem noch leeren Team hinzu
					t.addPlayer(p);
					numOfPlayers.setValue(teams.indexOf(t)+1); //Spielerzahl aktualisieren
					return true;
				}
			}
			return false;
		}
	}

	public void setGameType(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public SimpleIntegerProperty getNumOfPlayers() {
		return numOfPlayers;
	}
}