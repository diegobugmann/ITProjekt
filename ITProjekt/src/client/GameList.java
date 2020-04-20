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
		TableColumn<Table, String> colGames = new TableColumn<>("Spiele");
		colGames.setPrefWidth(150);
		TableColumn gameId = new TableColumn("GameName");
		gameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
		
		TableColumn gameType = new TableColumn("Is Schieber");
		gameType.setCellValueFactory(new PropertyValueFactory<>("isSchieber"));
		
		TableColumn numOfRounds = new TableColumn("Is Schieber");
		numOfRounds.setCellValueFactory(new PropertyValueFactory<>("numOfRounds"));
		
		TableColumn winningPoints = new TableColumn("Is Schieber");
		winningPoints.setCellValueFactory(new PropertyValueFactory<>("winningPoints"));
		
		tableView.getColumns().addAll(gameId, gameType, numOfRounds, winningPoints);
		
		return tableView;
	}
	
	public void setAllGames(ArrayList<Commons.Game> games) {
		tableView.getItems().clear();
		System.out.println("Cleared");
		for(Game g : games) {
			tableView.getItems().add(g);
		}
	}
	
	/*
	private TableView<Table> createTableView(){
		tableView = new TableView<>();
		
		TableColumn<Table, String> colGames = new TableColumn<>("Spiele");
		colGames.setPrefWidth(150);
		
		tableView.getColumns().add(colGames);
		
		
		//Wenn keine Daten vorhanden, leeres Label darstellen
		tableView.setPlaceholder(new Label(""));
		
		return tableView;
	}
	*/
	public Game getSelectedGame() {
		//TODO selektiertes Game zurückgeben
		return null;
	}

}
