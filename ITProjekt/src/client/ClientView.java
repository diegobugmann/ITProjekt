package client;

import javafx.stage.Stage;

public class ClientView {
	
	protected ClientModel model;
	protected Stage primaryStage;
	protected LoginView loginView;	
	protected LobbyView lobbyView;
	protected GameView gameView;
	
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
	
	public void showGameView(Stage stage) {
		this.stage = stage;
		gameView = new GameView(stage);
		
	}

	
}
	
