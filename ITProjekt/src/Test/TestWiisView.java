package Test;

import java.util.ArrayList;

import Commons.Card;
import Commons.Wiis;
import Soundmodule.SoundModule;
import client.ChatBox;
import client.SelectWiisView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
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
		
        primaryStage.setScene(new Scene(root));
		primaryStage.setHeight(300);
		primaryStage.setWidth(300);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		//primaryStage.initModality(Modality.APPLICATION_MODAL); /* *** */

        
        
        primaryStage.show();
	}
}
