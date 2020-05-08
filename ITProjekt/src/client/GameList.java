package client;

import java.util.ArrayList;

import Commons.Game;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class GameList extends VBox {
	
	public class DisplayGame{
		private int gameId;
		private String isSchieberDisplay;
		private String numOfRounds;
		private String winningPoints;
		private String currentNumOfPlayersString;
		private Game game;
		public DisplayGame(Game g) {
			this.game = g;
			this.gameId = g.getGameId();
			this.currentNumOfPlayersString = g.getCurrentNumOfPlayers() + " / 4";
			if(game.isSchieber()) {
				this.isSchieberDisplay = "Schieber";
				this.winningPoints = Integer.toString(game.getWinningPoints());
				this.numOfRounds = "-";
			}
			else {
				this.isSchieberDisplay = "Differenzler";
				this.winningPoints = "-";
				this.numOfRounds = Integer.toString(game.getNumOfRounds());
			}
			
		}
		private Game getGame() {
			return this.game;
		}
		public int getGameId() {
			return gameId;
		}
		public String getIsSchieberDisplay() {
			return isSchieberDisplay;
		}
		public String getNumOfRounds() {
			return numOfRounds;
		}
		public String getWinningPoints() {
			return winningPoints;
		}
		public String getCurrentNumOfPlayersString() {
			return currentNumOfPlayersString;
		}
		
	}
	
	protected TableView<DisplayGame> tableView;

	
	public GameList() {
		super();
		
		TableView<DisplayGame> tableView = createTableView();
		
		this.getChildren().add(tableView);
	}
	
	private TableView<DisplayGame> createTableView(){
		tableView = new TableView<DisplayGame>();
		
		TableColumn gameId = new TableColumn("Spiel ID");
		gameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
		
		
		TableColumn gameType = new TableColumn("Spielmodus");
		gameType.setCellValueFactory(new PropertyValueFactory<>("isSchieberDisplay"));
		
		
		TableColumn numOfRounds = new TableColumn("Runden");
		numOfRounds.setCellValueFactory(new PropertyValueFactory<>("numOfRounds"));
		
		TableColumn winningPoints = new TableColumn("Ziel Punkte");
		winningPoints.setCellValueFactory(new PropertyValueFactory<>("winningPoints"));
		
		TableColumn currentNumOfPlayers = new TableColumn("Spieler");
		currentNumOfPlayers.setCellValueFactory(new PropertyValueFactory<>("currentNumOfPlayersString"));
		
		tableView.getColumns().addAll(gameId, gameType, numOfRounds, winningPoints, currentNumOfPlayers);
		
		tableView.setPlaceholder(new Label(""));
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tableView;
	}
	
	public void setAllGames(ArrayList<Commons.Game> games) {
		tableView.getItems().clear();
			for(Game g : games) {
				if(!g.isRunning() && g.getCurrentNumOfPlayers() < 4) {
					DisplayGame dg = new DisplayGame(g);
					tableView.getItems().add(dg);
					
				}
		}
	}
	
	public Game getSelectedGame() {
		DisplayGame dg = tableView.getSelectionModel().getSelectedItem();
		Game g = dg.getGame();
		
		return g;
	}

}
