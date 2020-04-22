package client;

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
	
	
	public CardArea() {
		super();
		
		this.getChildren().addAll(hboxCards, infolbl);
		
		 // Add CardButtons and Spacers for the cards
		for (int i = 0; i < 6; i++) {
        	Button cardBtn = new CardButton();
            Region spacer = new Region();
            spacer.setPrefWidth(10);
            hboxCards.getChildren().add(cardBtn);
            hboxCards.getChildren().add(spacer);
		}
		hboxCards.setAlignment(Pos.CENTER);
			
	}
	
	public void setCards(Card card) {
		for (int i = 0; i < 6; i++) {
    		CardButton cardBtn = (CardButton) hboxCards.getChildren().get(i*2);
    		cardBtn.setCard(card);
		}
	}
	
	public void setLabelText(String text) {
		infolbl.setText(text);
	}
	

}
