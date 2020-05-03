package client;

import java.util.ArrayList;

import Commons.Card;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * 
 * @author lucameyer
 *Creates the centerview with 4 buttons and Labes with the usernames to show the played cards
 *Buttons because then it can reused the CardButton, if the buttons are not
 *set on action, nothing happens
 */
public class CenterView extends VBox {
	protected HBox boxLeft;
	protected HBox boxCenter;
	protected HBox boxRight;
	
	protected Label userlblLeft;
	protected Label userlblCenterTop;
	protected Label userlblCenterBottom;
	protected Label userlblRight;
	
	public ArrayList<Button> centerButtons = new ArrayList<>();
	
	
	public CenterView() {
		super();
		
		Button cardBtnLeft = new CardButton();
		cardBtnLeft.setVisible(false);
		userlblLeft = new Label("");
		Region spacer1 = new Region();
        spacer1.setPrefHeight(10);
		
		boxLeft = new HBox();
		boxLeft.setAlignment(Pos.CENTER);
		boxLeft.getChildren().addAll(cardBtnLeft, spacer1, userlblLeft);
		
		
		Button cardBtnCenterTop = new CardButton();
		cardBtnCenterTop.setVisible(false);
		Button cardBtnCenterBottom = new CardButton();
		cardBtnCenterBottom.setVisible(false);
		
		userlblCenterTop = new Label("");;
		userlblCenterBottom = new Label("");
		
		Region spacer2 = new Region();
        spacer2.setPrefHeight(10);
        Region spacer3 = new Region();
        spacer3.setPrefHeight(10);
        spacer3.setPrefWidth(110);
        Region spacer4 = new Region();
        spacer4.setPrefHeight(10);
		
		boxCenter = new HBox();
		boxCenter.setAlignment(Pos.CENTER);
		boxCenter.getChildren().addAll(userlblCenterTop, spacer2, cardBtnCenterTop,
				spacer3, cardBtnCenterBottom, spacer4, userlblCenterBottom);
		
		
		Button cardBtnRight = new CardButton();
		cardBtnRight.setVisible(false);
		userlblRight = new Label("");
		Region spacer5 = new Region();
        spacer5.setPrefHeight(10);
		
		boxRight  = new HBox();
		boxRight.setAlignment(Pos.CENTER);
		boxRight.getChildren().addAll(cardBtnRight, spacer5, userlblLeft);
		
		
		Region spacer6 = new Region();
        spacer6.setPrefWidth(10);
        Region spacer7 = new Region();
        spacer7.setPrefWidth(10);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(boxLeft, spacer6, boxCenter, spacer7, boxRight);
		
	}
	
	public void setCardId() {
		//userlblCenterBottom.setText();
	}
	
	public void setCard(Card card, String player) {
		
		
		if(userlblLeft.getText()==player) {
			 //setVisible(true);
			
		}
		if(userlblCenterTop.getText()==player) {
			
		}
		
	}
		
}


