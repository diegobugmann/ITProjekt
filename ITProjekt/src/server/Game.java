package server;

import java.util.ArrayList;
import java.util.Random;

import Commons.Card;
import Commons.GameType;
import Commons.Message;
import Commons.Message_Hand;
import Commons.Message_Trumpf;
import Commons.Message_Wiis;
import Commons.Message_YourTurn;
import Commons.Simple_Message;
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
	private ArrayList<Play> plays;
	private SimpleIntegerProperty numOfPlayers = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty numOfAnsagen = new SimpleIntegerProperty(0);
	private Play currentPlay;
	private Player startingPlayer;
	private int numOfRoundsPlayed = 0;
	private Team winnerTeam;
	
	/**
	 * @author digib
	 * @param isGermanCards, rounds, winningPoints, isSchieber
	 */
	public Game(boolean isGermanCards, int rounds, int winningPoints, boolean isSchieber) {
		super(isGermanCards, rounds, winningPoints, isSchieber, nextID++);
		this.deck = new CardDeck(isGermanCards);
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
	
	/**
	 * @author digib
	 * removes all Players from a game (in case a game gets cancelled)
	 */
	public void removeAllPlayers() {
		for (Team t : teams)
			t.getPlayerList().clear();
		numOfPlayers.setValue(0);
	}
	
	/**
	 * @author digib
	 * removes a certain Player from a game (in case a game is left during loading screen)
	 */
	public void removePlayer(Player p) {
		for (Team t : teams)
			for (int i = t.getPlayerList().size()-1; i >=0; i--) {
				if (t.getPlayerList().get(i) == p) {
					t.getPlayerList().remove(i);
					numOfPlayers.setValue(numOfPlayers.get()-1);
				}
			}
	}
	
	/**
	 * @author digib
	 * @return Team with the highest Wiis
	 * adds all players with at least 1 Wiis to a list and compares their Wiis-Objects, 
	 * if more than one team wants to wiis
	 */
	public Team validateWiisWinner() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : teams) {
			for (Player p : t.getPlayerList()) {
				if (!p.getWiis().isEmpty())
					players.add(p);
			}
		}
		
		//check if only one team wants to wiis
		boolean onlyOneTeamHasWiis = false;
		if (players.size() == 1)
			onlyOneTeamHasWiis = true;
		else if (players.size() == 2) {
			if (players.get(0).getTeammate() == players.get(1))
				onlyOneTeamHasWiis = true;
		}
		
		if (players.isEmpty()) //no Wiis - no Validation
			return null;
		else if (onlyOneTeamHasWiis) //The only wiising team wins the Wiis-battle
			return players.get(0).getCurrentTeam();
		else {
			Player winner = WiisValidation.validateWiisWinner(players, trumpf);
			Team winningTeam = winner.getCurrentTeam();
			return winningTeam;
		}
	}
	
	/**
	 * @author digib
	 * adds points according to the gameMode to the winningTeam (used for Last Stich and Match)
	 */
	public void addPoints(int points, Team winningTeam) {
		if (!isSchieber()) //differenzler has no multiplication depending on trumpf
			winningTeam.addPoints(points);
		else {
			if (trumpf == GameType.TopsDown || trumpf == GameType.BottomsUp) 
				points *= 3;
			else if (trumpf == GameType.BellsOrClubs || trumpf == GameType.ShieldsOrSpades) 
				points *= 2;
			winningTeam.addPoints(points);
		}
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
			playerTwo = teams.get(2).getPlayerList().get(0);
			playerThree = teams.get(1).getPlayerList().get(0);
			playerFour = teams.get(3).getPlayerList().get(0);
		}
		playerOne.setFollowingPlayer(playerThree);
		playerTwo.setFollowingPlayer(playerFour);
		playerThree.setFollowingPlayer(playerTwo);
		playerFour.setFollowingPlayer(playerOne);
	}
	
	/**
	 * @author digib
	 * sets the beginning order based on the starting Player. Used for special cases in validating the highest wiis
	 */
	public void setBeginningOrder() {
		Player first = startingPlayer;
		first.setBeginningOrder(1);
		Player second = first.getFollowingPlayer();
		second.setBeginningOrder(2);
		Player third = second.getFollowingPlayer();
		third.setBeginningOrder(3);
		Player fourth = third.getFollowingPlayer();
		fourth.setBeginningOrder(4);
	}
	
	/**
	 * @author digib
	 * @return ArrayList<String>
	 * Returns a list of the playernames in gameorder
	 */
	public ArrayList<String> getPlayersInOrder(){
		Player first = startingPlayer;
		Player second = first.getFollowingPlayer();
		Player third = second.getFollowingPlayer();
		Player fourth = third.getFollowingPlayer();
		ArrayList<String> playersInOrder = new ArrayList<String>();
		playersInOrder.add(first.getName());
		playersInOrder.add(second.getName());
		playersInOrder.add(third.getName());
		playersInOrder.add(fourth.getName());
		return playersInOrder;
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
		}
	}
	
	/**
	 * @author digib
	 * get the starting player and tell him to start playing, with or without wiis (dependent on schieber/differenzler)
	 */
	public void startPlaying() {
		Message msgOut = null;
		this.newPlay();
		Player starter = getStartingPlayer();
		ArrayList<Card> playableCards = starter.getHand(); //he can play what he wants at first
		if (this.isSchieber()) {
			ArrayList<Wiis> wiis = starter.validateWiis();
			msgOut = new Message_Wiis(wiis, starter.getID());
			starter.sendMessage(msgOut);
		}
		msgOut = new Message_YourTurn(playableCards);
		msgOut.send(starter.getSocket());
	}
	
	/**
	 * @author digib
	 * creates a new Play object, adds it to the game and sets it as currentPlay
	 */
	public void newPlay() {
		Play newPlay = new Play(this.trumpf, this.isSchieber());
		plays.add(newPlay);
		this.currentPlay = newPlay;
	}
	
	/**
	 * @author digib
	 * sends Ansage_Trumpf to startingPlayer
	 */
	public void setUpSchieber() {
		Player starter = this.startingPlayer;
		Message msgOut = new Simple_Message(Simple_Message.Msg.Ansage_Trumpf);
		starter.sendMessage(msgOut);
	}
	
	/**
	 * @author digib
	 * generates random trumpf and broadcasts trumpf and Ansage_Points messages to players
	 */
	public void setUpDifferenzler() {
		this.createRandomTrumpf();
		Message msgTrumpf = new Message_Trumpf(this.trumpf);
		Message msgAnsage = new Simple_Message(Simple_Message.Msg.Ansage_Points);
		for (Player p : this.getPlayers()) {
			p.sendMessage(msgTrumpf);
			p.sendMessage(msgAnsage);
		}
	}
	
	/**
	 * @author digib
	 * @return boolean
	 * checks if game is over; if so, sets the winning team and returns false
	 * if not, prepares new game and returns true
	 */
	public boolean prepareNewGameIfNeeded() {
		if (isSchieber()) {
			int pointsReached = 0;
			Team winningTeam = null;
			for (Team t : teams) {
				if (t.getTotalScore() > pointsReached) {
					pointsReached = t.getTotalScore();
					winningTeam = t;
				}
			}
			if (pointsReached >= this.getWinningPoints()) { //winningPoints reached, game finished
				this.winnerTeam = winningTeam;
				return false;
			}
		} else {
			this.increaseNumOfRoundsPlayed();
			if (this.numOfRoundsPlayed >= this.getNumOfRounds()) { //numOfRounds reached, game finished
				//TODO VALIDATE AND SET WINNING TEAM (PLAYER)
				return false;
			}
		}
		
		for (Team t : teams)
			t.resetScore(); //reset score for a new round (Totalscore keeps counting)
		this.setNextStartingPlayer();
		this.setBeginningOrder();
		this.dealCards();
		this.trumpf = null;
		this.plays = new ArrayList<Play>();
		//TODO previous plays irgendwo abspeichern?
		
		if (isSchieber())
			this.setUpSchieber();
		else {
			this.setNumOfAnsagen(0);
			this.setUpDifferenzler();
		}
		return true;
	}
	
	public boolean isMatch() {
		for (int i = 1; i < plays.size(); i++) {
			if (plays.get(0).getWinningTeam() != plays.get(i).getWinningTeam())
				return false;
		}
		return true;
	}
	
	/**
	 * @author digib
	 * increases the numOfAnsagen (only for differenzler)
	 */
	public void incrementNumOfAnsagen() {
		this.numOfAnsagen.set(numOfAnsagen.get()+1);
	}
	
	/**
	 * @author digib
	 */
	public void increaseNumOfRoundsPlayed() {
		this.numOfRoundsPlayed++;
	}
	
	/**
	 * @author digib
	 * sets the next startingPlayer (next in the order)
	 */
	public void setNextStartingPlayer() {
		this.startingPlayer = startingPlayer.getFollowingPlayer();
	}
	
	public SimpleIntegerProperty getNumOfPlayersAsProperty() {
		return numOfPlayers;
	}
	
	public SimpleIntegerProperty getNumOfAnsagenAsProperty() {
		return numOfAnsagen;
	}
	
	public void setNumOfAnsagen(int i) {
		this.numOfAnsagen.set(i);
	}
	
	public CardDeck getDeck() {
		return this.deck;
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
	
	public int getNumOfPlays() {
		return this.plays.size();
	}
	
	public Player getStartingPlayer() {
		return this.startingPlayer;
	}
	
	public void setStartingPlayer(Player starter) {
		this.startingPlayer = starter;
	}
	
	public Play getCurrentPlay() {
		return this.currentPlay;
	}
	
	public void setCurrentPlay(Play play) {
		this.currentPlay = play;
	}
	
	public Team getWinnerTeam() {
		return this.winnerTeam;
	}
	
}
