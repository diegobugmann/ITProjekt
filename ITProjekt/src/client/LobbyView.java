package client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView {
	
	protected Button newBtn;
	protected Button joinBtn;
	protected GameMenu gameMenu;
	protected BorderPane lobby;
	protected VBox center;
	protected Stage stage;
	protected GameList gameList;
	
	
	
	public LobbyView(Stage stage) {
		
		this.stage = stage;
		
		gameList  = new GameList();
		gameMenu = new GameMenu();
		
		newBtn = new Button("Neues Spiel");
		joinBtn = new Button("Spiel beitreten");
		//joinBtn.disableProperty().set(true);
		
		int spacerMinSize = 20;
		int spacerPrefSize = 50;
		int spacerMaxSize = 100;
		Region spacer1 = new Region();
		Region spacer2 = new Region();
		
		spacer1.setMinHeight(spacerMinSize);
		spacer2.setMinHeight(spacerMinSize);
		
		spacer1.setPrefHeight(spacerPrefSize);
		spacer2.setPrefHeight(spacerPrefSize);
		
		spacer1.setMaxHeight(spacerMaxSize);
		spacer2.setMaxHeight(spacerMaxSize);
		
		Image image = new Image(LobbyView.class.getResourceAsStream("Bilder/Lobby.jpg"));
		BackgroundSize backSize = new BackgroundSize(800, 800, false, false, false, false);
		Background background = new Background(new BackgroundImage(image, null, null, BackgroundPosition.CENTER, backSize)); 
		center = new VBox(newBtn, spacer1, gameList, spacer2, joinBtn);
		center.setAlignment(Pos.CENTER);
		
		lobby = new BorderPane();
		
		int regionWith = 150;
		int regionHeight = 250;
		Region region1 = new Region();
		Region region2 = new Region();
		Region region3 = new Region();
		Region region4 = new Region();
		
		region1.setPrefWidth(regionWith);
		region2.setPrefWidth(regionWith);
		
		region3.setPrefHeight(regionHeight);
		region4.setPrefHeight(regionHeight);
		
		BorderPane.setAlignment(center, Pos.CENTER);
		lobby.setCenter(center);
		lobby.setBackground(background);
		
		lobby.setTop(gameMenu);
		BorderPane.setMargin(gameMenu, new Insets(0, 0, 250, 0));
		lobby.setRight(region1);
		lobby.setBottom(region4);
		lobby.setLeft(region2);
		
		Scene scene = new Scene(lobby);
		scene.getStylesheets().add(getClass().getResource("CSS/Lobby.css").toExternalForm());
		

		stage.setFullScreen(false);
		stage.setHeight(800);
		stage.setWidth(800);
		stage.setTitle("Lobby");
		stage.setScene(scene);
		stage.show();
		
		
	}

}
