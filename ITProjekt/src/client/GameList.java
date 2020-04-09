package client;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class GameList extends VBox {
	
	protected TableView<Table> tableView;

	
	public GameList() {
		super();
		
		TableView<Table> tableView = createTableView();
		
		this.getChildren().add(tableView);
	}
	
	private TableView<Table> createTableView(){
		tableView = new TableView<>();
		
		TableColumn<Table, String> colGames = new TableColumn<>("Spiele");
		colGames.setPrefWidth(90);
		tableView.getColumns().add(colGames);
		
		
		//Wenn keine Daten vorhanden, leeres Label darstellen
		tableView.setPlaceholder(new Label(""));
		
		return tableView;
	}
	

}
