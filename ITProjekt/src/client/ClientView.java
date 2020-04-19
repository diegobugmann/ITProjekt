package client;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientView {
	
	protected ClientModel model;
	protected Stage primaryStage;
	
	protected LoginView loginView;
	
	protected LobbyView lobbyView;
	
	protected GameMenu gameMenu;
	protected CardArea cardArea;
	protected CenterView centerView;
	protected InfoView infoView;
	
	protected Stage stage;

	

	public ClientView(Stage primaryStage, ClientModel model) {
		this.primaryStage = primaryStage;
		this.model = model;
		
		
		
	}
	
	public void showLoginView(Stage stage) {
		this.stage = stage;
		loginView = new LoginView(stage);
		
	}
	
	public void showLobbyView(Stage stage) {
		this.stage = stage;
		lobbyView = new LobbyView(stage);
	}

	
	
	public void loadMainView(Stage stage) {
		
		this.stage = stage;
		
		infoView = new InfoView();
		centerView = new CenterView();
		cardArea = new CardArea();
		
		BorderPane root = new BorderPane();
		
		root.setTop(gameMenu);
		root.setRight(infoView);
		root.setCenter(centerView);
		root.setBottom(cardArea);
		
		
		Scene scene = new Scene(root);
		/* scene.getStylesheets().add(getClass().getResource("").toExternalForm()); */
		
		primaryStage.setFullScreen(false);
		
		primaryStage.setTitle("CodingKittens");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
	
