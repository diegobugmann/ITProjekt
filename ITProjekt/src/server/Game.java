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
	private boolean hasEvents = false;
	
	/**
	 * @author digib
	 * @param isGermanCards, rounds, winningPoints, isSchieber
	 */
	public Game(boolean isGermanCards, int rounds, int winningPoints, boolean isSchieber) {
		super(isGermanCards, rounds, winningPoints, isSchieber, nextID++);
		this.deck = new CardDeck();
		plays = new ArrayList<Play>();
		
		//create 2 teams for schieber and 4 teams for differenzler
		if (isSchieber) {
			for (int i = 0; i < 2; i++) {
				Team t = new Team();
				t.setTeamID(i+1);
				teams.add(t);
			}
		} else {
			for (int i = 0; i < 4; i++) {
				Team t = new Team();
				t.setTeamID(i+1);
				teams.add(t);
			}
		}
	}
	
	/**
	 * @author digib
	 * @param Player
	 * @return int teamIndex (-1 if failed)
	 * adds a player to the next possible spot and increases the numOfPlayers
	 */
	public int addPlayer(Player p) {
		int teamIndex = -1;
		if (this.isSchieber()) {
			if (teams.get(0).getPlayerList().size() < 2) {
				teams.get(0).addPlayer(p);
				teamIndex = 0;
			} else if (teams.get(1).getPlayerList().size() < 2) {
				teams.get(1).addPlayer(p);
				teamIndex = 1;
			}
			numOfPlayers.set(teams.get(0).getPlayerList().size()+teams.get(1).getPlayerList().size()); //refresh numOfPlayers
		} else {
			for (Team t : teams) {
				if (t.getPlayerList().size() == 0) { //add a player to the first yet empty team
					t.addPlayer(p);
					numOfPlayers.setValue(numOfPlayers.get()+1); //refresh numOfPlayers
					teamIndex = teams.indexOf(t);
					return teamIndex;
				}
			}
		}
		return teamIndex;
	}
	
	/**
	 * @author digib
	 * removes all Players from a game
	 */
	public void removeAllPlayers() {
		for (Team t : teams)
			t.getPlayerList().clear();
		numOfPlayers.setValue(0);
	}
	
	/**
	 * @author digib
	 * @param Player
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
	 * resets teamScores in case of cancellation
	 */
	public void resetTeamScores() {
		for (Team t : teams) {
			t.resetScore();
			t.resetTotalScore();
		}
	}
	
	/**
	 * @author digib
	 * resets plays in case of cancellation
	 */
	public void resetPlays() {
		this.plays = new ArrayList<Play>();
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
		else { //only if different teams want to wiis, compare who has the strongest one.
			Player winner = WiisValidation.validateWiisWinner(players, trumpf);
			Team winningTeam = winner.getCurrentTeam();
			return winningTeam;
		}
	}
	
	/**
	 * @author digib
	 * @param points, team
	 * adds points according to the gameMode to the teams currentPoints (used for Stich (including the last one) and Match)
	 */
	public void addPoints(int points, Team team) {
		if (!isSchieber()) //differenzler has no multiplication depending on trumpf
			team.addPoints(points);
		else {
			if (trumpf == GameType.TopsDown || trumpf == GameType.BottomsUp) 
				points *= 3;
			else if (trumpf == GameType.BellsOrClubs || trumpf == GameType.ShieldsOrSpades) 
				points *= 2;
			team.addPoints(points);
		}
	}
	
	/**
	 * @author digib
	 * @param points, team
	 * adds points according to the gameMode to the teams totalPoints (used for Wiis and Stöck)
	 */
	public void addPointsToTotal(int points, Team team) {
		if (trumpf == GameType.TopsDown || trumpf == GameType.BottomsUp) 
			points *= 3;
		else if (trumpf == GameType.BellsOrClubs || trumpf == GameType.ShieldsOrSpades) 
			points *= 2;
		team.addToTotal(points);
	}
	
	/**
	 * @author digib
	 * @param points, team
	 * removes points according to the gameMode from the team (used only for stoeck during schieber, if cannot be wiised at start)
	 */
	public void removePointsFromTotal(int points, Team team) {
		if (trumpf == GameType.BellsOrClubs || trumpf == GameType.ShieldsOrSpades) 
			points *= 2;
		team.removePoints(points);
	}
	
	/**
	 * @author digib
	 * adds the points from current game to totalpoints for each team
	 */
	public void updateTeamPoints() {
		for (Team t : teams)
			t.updateTotalPoints();
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
	 * @return ArrayList<Player> (all Players from all teams, no specific order)
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
		this.deck.dealCards(players);
		for (Player p : players) {
			p.organizeHand();
			msgOut = new Message_Hand(p.getHand());
			p.sendMessage(msgOut);
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
		Play newPlay = new Play(this.trumpf);
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
	 * checks if game is over; if so, sets the winning team and returns false (only possible for differenzler)
	 * if not, prepares new game (resetting Wiis and Score, setting new startingPlayer, deal cards etc.) and returns true
	 */
	public boolean prepareNewGameIfNeeded() {
		if (!isSchieber()) {
			this.increaseNumOfRoundsPlayed();
			if (this.numOfRoundsPlayed >= this.getNumOfRounds()) { //numOfRounds reached, game finished
				int lowestPoints = Integer.MAX_VALUE;
				for (Team t : teams) {
					if (t.getTotalScore() < lowestPoints) {
						lowestPoints = t.getTotalScore();
						this.winnerTeam = t;
					}
				}
				return false;
			}
		}
		for (Team t : teams) {
			t.resetScore(); //reset score for a new round (totalScore keeps counting)
			for (Player p : t.getPlayerList())
				p.resetWiis(); //reset the previous wiis (only important for schieber)
		}
		this.setNextStartingPlayer();
		this.setBeginningOrder();
		this.dealCards();
		this.trumpf = null;
		this.plays = new ArrayList<Play>();
		if (isSchieber())
			this.setUpSchieber();
		else {
			this.setNumOfAnsagen(0);
			this.setUpDifferenzler();
		}
		return true;
	}
	
	/**
	 * @author digib
	 * @return boolean (if a team has scored a match)
	 */
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
	 * increases the nunOfRoundsPlayed (only for differenzler)
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
	
	//getters and setters
	
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
	
	public Team getTeam(int teamIndex) {
		return teams.get(teamIndex);
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
	
	public void setWinnerTeam(Team winnerTeam) {
		this.winnerTeam = winnerTeam;
	}
	
	public boolean hasEvents() {
		return hasEvents;
	}

	public void setHasEvents(boolean hasEvents) {
		this.hasEvents = hasEvents;
	}
	
}
