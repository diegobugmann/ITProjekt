package client;

import Commons.Card;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * @author Luca Meyer
 * creates a Button with the picture of the card
 * parts copied from R. Bradley PokerProject
 */
public class CardButton extends Button {
	protected Card card;
	protected String cardStyle;

	public CardButton() {
		super();
		this.getStylesheets().add(getClass().getResource("CSS/card.css").toExternalForm());
		this.getStyleClass().add("card");
		
	}
	/**
	 * @author Luca Meyer
	 * sets the playing cards
	 */
	//sets path for german or french cards
	public void setCard(Card card) {
		this.card = card;
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
				
		if (card != null) {
			this.setVisible(true);
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("client/Karten_"+cardStyle+"/" + fileName+".jpg"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
			this.setId(fileName);
		}else {
			this.setGraphic(null);
		}

	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return rank + "_" + suit;
	}
	
	//Methode to set the centercards but without setting an ID
	public void setCardCenter(Card card) {
		this.card = card;
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
				
		if (card != null) {
			this.setVisible(true);
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("client/Karten_"+cardStyle+"/" + fileName+".jpg"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
		}else {
			this.setGraphic(null);
		}

	}
}
