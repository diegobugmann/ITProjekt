package client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends Commons.Card {
	protected Label cardlbl;
	protected String cardStyle;

	public Card(Suit suit, Rank rank) {
		super(suit, rank);
		cardlbl = new Label();
		cardlbl.getStyleClass().add("card");
		
	}
	
	public void setCard(Card card) {
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
				
		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(cardlbl.getClass().getClassLoader().getResourceAsStream("Karten_"+cardStyle+"/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(cardlbl.widthProperty());
			imv.fitHeightProperty().bind(cardlbl.heightProperty());
			imv.setPreserveRatio(true);
			cardlbl.setGraphic(imv);
		}else {
			cardlbl.setGraphic(null);
		}

	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return rank + "_" + suit + ".jpg";
	}
}
