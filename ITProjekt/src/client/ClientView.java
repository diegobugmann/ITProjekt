package client;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientView {
	
	protected ClientModel model;
	protected Stage primaryStage;
	protected LoginView loginView;	
	protected LobbyView lobbyView;
	
	protected CreateNewUserView createNewUserView;
	
	protected GameMenu gameMenu;
	protected CardArea cardArea;
	protected CenterView centerView;
	protected VBox infoView;

	protected GameView gameView;
	
	protected Stage stage;

	

	public ClientView(Stage primaryStage, ClientModel model) {
		this.primaryStage = primaryStage;
		this.model = model;
		
	}
	
	public void showLoginView(Stage stage, String address) {
		this.stage = stage;
		loginView = new LoginView(stage, address);
		
	}
	
	public void showLobbyView(Stage stage) {
		this.stage = stage;
		lobbyView = new LobbyView(stage);
	}
	
	/**
	 * @author sarah
	 * @param stage
	 */
	public void showCreateNewUserView(Stage stage) {
		this.stage = stage;
		createNewUserView = new CreateNewUserView(stage);
	}

	
	public void showGameView(Stage stage, VBox infoView) {
		this.stage = stage;
		gameView = new GameView(stage, infoView);
		
	}

	
}
	
