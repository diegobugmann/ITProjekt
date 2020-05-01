package client;

import java.util.ArrayList;

import Commons.Card;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


// vieles übernommen aus PokerProjekt von R. Bradley
public class CardArea extends VBox {
	protected Label infolbl = new Label("");
	protected HBox hboxCards = new HBox();
	public ArrayList<Button> cardButtons = new ArrayList<>();
	
	
	public CardArea() {
		super();
		
		infolbl.setStyle("-fx-font-weight: bold;");
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(hboxCards, infolbl);
		
		 // Add CardButtons and Spacers for the cards
		for (int i = 0; i < 9; i++) {
        	Button cardBtn = new CardButton();
            Region spacer = new Region();
            spacer.setPrefWidth(10);
            hboxCards.getChildren().add(cardBtn);
            hboxCards.getChildren().add(spacer);
            cardButtons.add(i, cardBtn);
            
		}
		hboxCards.setAlignment(Pos.CENTER);
			
	}
	
	public void setCards(ArrayList<Card> hand) {
		for(int i = 0; i<hand.size(); i++) {
			Card card = hand.get(i);
			CardButton cardBtn = (CardButton) hboxCards.getChildren().get(i*2);
    		cardBtn.setCard(card);
    		cardBtn.setDisable(true);
    		
    		
		}
	}
	
	public void setLabelText(String text) {
		infolbl.setText(text);
	}
	

}
