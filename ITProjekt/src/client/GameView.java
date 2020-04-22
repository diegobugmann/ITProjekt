package client;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameView extends BorderPane {
	protected InfoView infoView;
	protected CardArea cardArea;
	protected CenterView centerView;
	protected GameMenu gameMenu;
	protected Stage stage;
	
	public GameView(Stage stage) {
		this.stage = stage;
		infoView = new InfoView();
		cardArea = new CardArea();
		centerView = new CenterView();
		gameMenu = new GameMenu();
		
		
		this.setTop(gameMenu);
		this.setRight(infoView);
		this.setCenter(centerView);
		this.setBottom(cardArea);
		
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setHeight(800);
		stage.setWidth(1000);
		stage.show();
	}

}
