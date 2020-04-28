package client;

import java.util.ArrayList;

import Commons.Game;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class GameList extends VBox {
	
	protected TableView<Game> tableView;

	
	public GameList() {
		super();
		
		TableView<Game> tableView = createTableView();
		
		this.getChildren().add(tableView);
	}
	
	private TableView<Game> createTableView(){
		tableView = new TableView<Game>();
		
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
					tableView.getItems().add(g);
				}
		}
	}
	
	public Game getSelectedGame() {
		Game g = tableView.getSelectionModel().getSelectedItem();
		return g;
	}

}
