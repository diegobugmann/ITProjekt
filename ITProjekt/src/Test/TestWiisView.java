package Test;

import java.util.ArrayList;

import Commons.Card;
import Commons.Wiis;
import client.SelectWiisView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestWiisView extends Application{

	
	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("GUI");
		ArrayList<Wiis> wiisArray = new ArrayList<Wiis>();
		wiisArray.add(new Wiis(Wiis.Blatt.achtblatt, new Card(Card.Suit.AcornsOrDiamonds, Card.Rank.Ace)));
		wiisArray.add(new Wiis(Wiis.Blatt.achtblatt, new Card(Card.Suit.AcornsOrDiamonds, Card.Rank.Ace)));
		wiisArray.add(new Wiis(Wiis.Blatt.achtblatt, new Card(Card.Suit.AcornsOrDiamonds, Card.Rank.Ace)));
		SelectWiisView root = new SelectWiisView(wiisArray,1);
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
		primaryStage.setHeight(300);
		primaryStage.setWidth(300);
		primaryStage.initStyle(StageStyle.TRANSPARENT);

        
        
        primaryStage.show();
	}
}
