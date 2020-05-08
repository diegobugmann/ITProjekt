package client;

import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
		
		
		
		Image image = new Image(GameView.class.getResourceAsStream("Bilder/jassteppich.jpg"));
		BackgroundSize backSize = new BackgroundSize(700, 700, false, false, false, false);
		BackgroundPosition backPos = new BackgroundPosition(Side.LEFT, 190, false, null, 0, false);
		Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, backPos, backSize));
		
		this.setBackground(background);
		this.setTop(gameMenu);
		this.setRight(infoView);
		this.setLeft(chatBox);
		this.setCenter(centerView);
		this.setBottom(cardArea);
		
		
		Scene scene = new Scene(this);
		stage.setScene(scene);
		stage.setHeight(880);
		stage.setWidth(1000);
		stage.setResizable(false);
		stage.show();
	}

}
