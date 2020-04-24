package client;

import Commons.Card;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardButton extends Button {
	protected String cardStyle;

	public CardButton() {
		super();
		this.getStylesheets().add(getClass().getResource("CSS/card.css").toExternalForm());
		this.getStyleClass().add("card");

		
	}
	/**
	 * @author Luca Meyer
	 * sets path for german or french cards
	 */
	public void setCard(Card card) {
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
		//copied from R. Bradley		
		if (card != null) {
			this.setVisible(true);
			String fileName = cardToFileName(card);
			System.out.println("client/Karten_"+cardStyle+"/" + fileName);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("client/Karten_"+cardStyle+"/" + fileName+".jpg"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
			this.setId(fileName);
		}else {
			this.setGraphic(null);
			this.setVisible(false);
		}

	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return rank + "_" + suit;
	}
}
