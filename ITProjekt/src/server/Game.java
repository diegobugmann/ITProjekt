package server;

import java.util.ArrayList;

import Commons.GameType;
import Commons.Message;
import Commons.Message_Hand;
import javafx.beans.property.SimpleIntegerProperty;

public class Game extends Commons.Game {
	
	public static int nextID = 1;
	private ArrayList<Team> teams = new ArrayList<Team>();
	private GameType trumpf;
	private CardDeck deck;
	private boolean isFistPlay;
	private ArrayList<Play> plays;
	private SimpleIntegerProperty numOfPlayers = new SimpleIntegerProperty(0);
	
	public Game(boolean isGermanCards, int rounds, int winningPoints, boolean isSchieber) {
		super(isGermanCards, rounds, winningPoints, isSchieber, nextID++);
		this.deck = new CardDeck(isGermanCards);
		this.isFistPlay = true;
		plays = new ArrayList<Play>();
		
		if (isSchieber) {
			for (int i = 0; i < 2; i++) //Bei Schieber 2 Teams erstellen
				teams.add(new Team());
		} else {
			for (int i = 0; i < 4; i++) //Bei Differenzler 4 Teams erstellen
				teams.add(new Team());
		}
	}
	
	public boolean addPlayer(Player p) {
		if (this.isSchieber()) {
			if (teams.get(0).getPlayerList().size() < 2)
				teams.get(0).addPlayer(p);
			else if (teams.get(1).getPlayerList().size() < 2)
				teams.get(1).addPlayer(p);
			else return false; //wenn schon 4 Spieler im Spiel sind, wird false zurückgegeben
			numOfPlayers.setValue(teams.get(0).getPlayerList().size()+teams.get(1).getPlayerList().size()); //Spielerzahl aktualisieren
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

	public void setTrumpf(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public SimpleIntegerProperty getNumOfPlayersAsProperty() {
		return numOfPlayers;
	}
	
	public CardDeck getDeck() {
		return this.deck;
	}
	
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayerList())
				players.add(p);
		}
		return players;
	}
	
	//Gibt den als erstes beigetretenen Spieler zurück
	public Player getStartingPlayer() {
		return teams.get(0).getPlayerList().get(0);
	}
	
	public void start() {
		Message msgOut = null;
		ArrayList<Player> players = getPlayers();
		//TODO broadcast GameList? gem. Diagramm
		this.deck.dealCards(players);
		for (Player p : players) {
			p.organizeHand();
			msgOut = new Message_Hand(p.getHand());
			msgOut.send(p.getSocket());
		}
	}
}
