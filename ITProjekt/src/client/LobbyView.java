package client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyView {
	
	protected Button newBtn;
	protected Button joinBtn;
	protected GameMenu gameMenu;
	protected BorderPane lobby;
	protected VBox center;
	protected Stage stage;
	
	
	public LobbyView(Stage stage) {
		
		this.stage = stage;
		
		newBtn = new Button("Neues Spiel");
		joinBtn = new Button("Spiel beitreten");
		joinBtn.disableProperty().set(true);
		
		Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("/ITProjekt/client/Lobby.jpg"));
		Background background = new Background(new BackgroundImage(image, null, null, null, null)); 
		
		center = new VBox(newBtn, joinBtn);
		
		lobby = new BorderPane();
		
		lobby.setCenter(center);
		lobby.setBackground(background);
		
		Scene scene = new Scene(lobby);
		/* scene.getStylesheets().add(getClass().getResource("").toExternalForm()); */
		
		stage.setFullScreen(false);
		
		stage.setTitle("Lobby");
		stage.setScene(scene);
		stage.show();
		
		
	}

}
