package client;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameView extends BorderPane {
	protected VBox infoView;
	protected CardArea cardArea;
	protected CenterView centerView;
	protected GameMenu gameMenu;
	protected ChatBox chatBox;
	protected Stage stage;
	
	public GameView(Stage stage, VBox infoView) {
		this.stage = stage;
		this.infoView = infoView;
		
		cardArea = new CardArea();
		centerView = new CenterView();
		gameMenu = new GameMenu();
		chatBox = new ChatBox();
		
		this.setTop(gameMenu);
		this.setRight(infoView);
		this.setLeft(chatBox);
		this.setCenter(centerView);
		this.setBottom(cardArea);
		
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setHeight(800);
		stage.setWidth(1000);
		stage.show();
	}

}
