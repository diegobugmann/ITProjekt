package server;

import java.util.ArrayList;
import java.util.Random;

import Commons.Card;
import Commons.GameType;
import Commons.Message;
import Commons.Message_Hand;
import Commons.Message_Wiis;
import Commons.Message_YourTurn;
import Commons.Wiis;
import javafx.beans.property.SimpleIntegerProperty;

public class Game extends Commons.Game {
	
	public static int nextID = 1;
	private ArrayList<Team> teams = new ArrayList<Team>();
	private GameType trumpf;
	private CardDeck deck;
	private boolean isFistPlay;
	private ArrayList<Play> plays;
	private SimpleIntegerProperty numOfPlayers = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty numOfAnsagen = new SimpleIntegerProperty(0);
	private Play currentPlay;
	
	public Game(boolean isGermanCards, int rounds, int winningPoints, boolean isSchieber) {
		super(isGermanCards, rounds, winningPoints, isSchieber, nextID++);
		this.deck = new CardDeck(isGermanCards);
		this.isFistPlay = true;
		plays = new ArrayList<Play>();
		
		//create 2 teams for schieber and 4 teams for differenzler
		if (isSchieber) {
			for (int i = 0; i < 2; i++)
				teams.add(new Team());
		} else {
			for (int i = 0; i < 4; i++)
				teams.add(new Team());
		}
	}
	
	/**
	 * @author digib
	 * @return int teamNumber
	 * adds a player to the next possible spot and increases the numOfPlayers
	 */
	public int addPlayer(Player p) {
		int teamNr = -1;
		if (this.isSchieber()) {
			if (teams.get(0).getPlayerList().size() < 2) {
				teams.get(0).addPlayer(p);
				teamNr = 0;
			}
			else if (teams.get(1).getPlayerList().size() < 2) {
				teams.get(1).addPlayer(p);
				teamNr = 1;
			}
			numOfPlayers.set(teams.get(0).getPlayerList().size()+teams.get(1).getPlayerList().size()); //Spielerzahl aktualisieren
		} else {
			for (Team t : teams) {
				if (t.getPlayerList().size() == 0) { //fügt ein Spieler in einem noch leeren Team hinzu
					t.addPlayer(p);
					numOfPlayers.setValue(teams.indexOf(t)+1); //Spielerzahl aktualisieren
					teamNr = teams.indexOf(t);
				}
			}
		}
		return teamNr;
	}

	public void setTrumpf(GameType trumpf) {
		this.trumpf = trumpf;
	}
	
	public GameType getTrumpf() {
		return this.trumpf;
	}
	
	public Team getTeam(int teamNr) {
		return teams.get(teamNr);
	}
	
	//generates a random trumpf, without bottomsup and topdown
	public void createRandomTrumpf() {
		Random rand = new Random();
		int i = rand.nextInt(4) + 2;
		for (GameType type : GameType.values()) {
			if (type.ordinal() == i)
				this.trumpf = type;
		}
	}
	
	public SimpleIntegerProperty getNumOfPlayersAsProperty() {
		return numOfPlayers;
	}
	
	public SimpleIntegerProperty getNumOfAnsagenAsProperty() {
		return numOfAnsagen;
	}
	
	//give every player a following Player (for gamedirection)
	//TODO Methode mit Schlaufen lösen
	public void setFollowingPlayers() {
		Player playerOne;
		Player playerTwo;
		Player playerThree;
		Player playerFour;
		if (isSchieber()) {
			playerOne = teams.get(0).getPlayerList().get(0);
			playerTwo = teams.get(0).getPlayerList().get(1);
			playerThree = teams.get(1).getPlayerList().get(0);
			playerFour = teams.get(1).getPlayerList().get(1);
			
		} else {
			playerOne = teams.get(0).getPlayerList().get(0);
			playerTwo = teams.get(1).getPlayerList().get(0);
			playerThree = teams.get(2).getPlayerList().get(0);
			playerFour = teams.get(3).getPlayerList().get(0);
		}
		playerOne.setFollowingPlayer(playerThree);
		playerTwo.setFollowingPlayer(playerFour);
		playerThree.setFollowingPlayer(playerTwo);
		playerFour.setFollowingPlayer(playerOne);
	}
	
	public void incrementNumOfAnsagen() {
		this.numOfAnsagen.set(numOfAnsagen.get()+1);
	}
	
	public CardDeck getDeck() {
		return this.deck;
	}
	
	//returns all the players
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayerList())
				players.add(p);
		}
		return players;
	}
	
	//returns the player that joined first
	public Player getStartingPlayer() {
		return teams.get(0).getPlayerList().get(0);
	}
	
	public Player getNextPlayer() {
		//TODO
		return null;
	}
	
	public Play getCurrentPlay() {
		return this.currentPlay;
	}
	
	public void setCurrentPlay(Play play) {
		this.currentPlay = play;
	}
	
	//deals cards to all players, organizes them and sends the hand to the client
	public void dealCards() {
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
	
	/**
	 * @author digib
	 * get the starting player and tell him to start playing, with or without wiis (dependent on the gamemode)
	 */
	public void startPlaying() {
		Message msgOut = null;
		Player starter = getStartingPlayer();
		newPlay();
		ArrayList<Card> playableCards = starter.getHand(); //he can play what he wants at first
		if (this.isSchieber()) {
			ArrayList<Wiis> wiis = starter.validateWiis();
			msgOut = new Message_Wiis(wiis);
			msgOut.send(starter.getSocket());
		}
		msgOut = new Message_YourTurn(playableCards);
		msgOut.send(starter.getSocket());
	}
	
	//creates a new Play object, adds it to the game and sets it as currentPlay
	public void newPlay() {
		Play newPlay = new Play(this.trumpf);
		plays.add(newPlay);
		this.currentPlay = newPlay;
	}

}
