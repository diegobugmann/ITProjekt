package server;

import java.util.ArrayList;

public class Game extends Commons.Game {
	
	private Team[] teams;
	private GameType trumpf;
	private CardDeck deck;
	private boolean isFistPlay;
	private ArrayList<Play> plays;
	
	public Game(GameType gameType, DeckType deckType, int rounds, Team ... teams) {
		this.trumpf = gameType;
		this.deck = new CardDeck(deckType);
		this.numOfRounds = rounds;
		this.teams = teams;
		this.isFistPlay = true;
		plays = new ArrayList<Play>();
	}

}
