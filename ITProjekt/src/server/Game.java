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

/**
 * @author digib
 */
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
	
	/**
	 * @author digib
	 * @param isGermanCards, rounds, winningPoints, isSchieber
	 */
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
	 * @return int teamNumber (-1 if failed)
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
			numOfPlayers.set(teams.get(0).getPlayerList().size()+teams.get(1).getPlayerList().size()); //refresh numOfPlayers
		} else {
			for (Team t : teams) {
				if (t.getPlayerList().size() == 0) { //add a player to the first yet empty team
					t.addPlayer(p);
					numOfPlayers.setValue(numOfPlayers.get()+1); //refresh numOfPlayers
					teamNr = teams.indexOf(t);
				}
			}
		}
		return teamNr;
	}
	
	public void removeAllPlayers() {
		for (Team t : teams)
			t.getPlayerList().clear();
		numOfPlayers.setValue(0);
	}
	
	public void removePlayer(Player p) {
		for (Team t : teams)
			for (int i = t.getPlayerList().size()-1; i >=0; i--) {
				if (t.getPlayerList().get(i) == p) {
					t.getPlayerList().remove(i);
					numOfPlayers.setValue(numOfPlayers.get()-1);
				}
			}
	}
	
	public Team validateWiisWinner() {
		ArrayList<ArrayList<Wiis>> wiis = new ArrayList<ArrayList<Wiis>>();
		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayerList()) {
				if (!p.getWiis().isEmpty()) {
					wiis.add(p.getWiis());
					players.add(p);
				}
			}
		}
		
		//TODO wenn nur 1 Team gewiesen hat wird das zurückgegeben
		
		if (wiis.isEmpty())
			return null;
		else {
			Player winner = WiisValidation.validateWiisWinner(wiis, players, trumpf);
			Team winningTeam = winner.getCurrentTeam();
			return null;
		}

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
	
	public boolean isFirstPlay() {
		return this.isFistPlay;
	}
	
	public void setFirstPlay(boolean isFirstPlay) {
		this.isFistPlay = isFirstPlay;
	}

	/**
	 * @author digib
	 * generates a random trumpf, without bottomsup and topdown (used only for differenzler)
	 */
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
	
	/**
	 * @author digib
	 * give every player a following Player (for gamedirection)
	 */
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
	
	/**
	 * @author digib
	 * increases the numOfAnsagen (only for differenzler)
	 */
	public void incrementNumOfAnsagen() {
		this.numOfAnsagen.set(numOfAnsagen.get()+1);
	}
	
	public CardDeck getDeck() {
		return this.deck;
	}
	
	/**
	 * @author digib
	 * @return ArrayList<Player> (all Players from all teams)
	 */
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayerList())
				players.add(p);
		}
		return players;
	}
	
	/**
	 * @author digib
	 * @return Player
	 */
	public Player getStartingPlayer() {
		return teams.get(0).getPlayerList().get(0);
	}
	
	public Play getCurrentPlay() {
		return this.currentPlay;
	}
	
	public void setCurrentPlay(Play play) {
		this.currentPlay = play;
	}
	
	/**
	 * @author digib
	 * deals cards to all players, organizes them and sends the hand to the client
	 */
	public void dealCards() {
		Message msgOut = null;
		ArrayList<Player> players = getPlayers();
		//TODO broadcast GameList? gem. Diagramm
		this.deck.dealCards(players);
		for (Player p : players) {
			p.organizeHand();
			msgOut = new Message_Hand(p.getHand());
			msgOut.send(p.getSocket());
			/* TODO delete (or leave?)
			 * ArrayList<Wiis> wiis = p.validateWiis();
			 * if (!wiis.isEmpty()) {
				for (Wiis w : wiis)
					System.out.println(w);
			}
			 */
		}
	}
	
	/**
	 * @author digib
	 * get the starting player and tell him to start playing, with or without wiis (dependent on schieber/differenzler)
	 */
	public void startPlaying() {
		Message msgOut = null;
		Player starter = getStartingPlayer();
		newPlay();
		ArrayList<Card> playableCards = starter.getHand(); //he can play what he wants at first
		if (this.isSchieber()) {
			ArrayList<Wiis> wiis = starter.validateWiis();
			msgOut = new Message_Wiis(wiis, starter.getID());
			msgOut.send(starter.getSocket());
		}
		msgOut = new Message_YourTurn(playableCards);
		msgOut.send(starter.getSocket());
	}
	
	/**
	 * @author digib
	 * creates a new Play object, adds it to the game and sets it as currentPlay
	 */
	public void newPlay() {
		Play newPlay = new Play(this.trumpf);
		plays.add(newPlay);
		this.currentPlay = newPlay;
	}
	
}
