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
public class CenterView extends HBox {
	protected HBox boxLeft;
	protected VBox boxCenter;
	protected HBox boxRight;
	
	protected Label userlblLeft;
	protected Label userlblCenterTop;
	protected Label userlblCenterBottom;
	protected Label userlblRight;
	
	protected Label stichInfo;
	
	protected Button cardBtnLeft;
	protected Button cardBtnCenterTop;
	protected Button cardBtnCenterBottom;
	protected Button cardBtnRight;
	
	public ArrayList<Button> centerButtons = new ArrayList<>();
	
	
	public CenterView() {
		super();
		cardBtnCenterBottom = new CardButton();
		cardBtnCenterBottom.setVisible(false);
		centerButtons.add(cardBtnCenterBottom);
		userlblCenterBottom = new Label("Bottom");
		
		cardBtnCenterTop = new CardButton();
		cardBtnCenterTop.setVisible(false);
		userlblCenterTop = new Label("Top");
		centerButtons.add(cardBtnCenterTop);
		
		
		Region spacer2 = new Region();
        spacer2.setPrefHeight(10);
        
        Region spacer3 = new Region();
        spacer3.setPrefHeight(10);
        
        Region spacer4 = new Region();
        spacer4.setPrefHeight(10);
        
        Region spacer8 = new Region();
        spacer8.setPrefHeight(10);
		
        stichInfo = new Label("");
        
		boxCenter = new VBox();
		boxCenter.setAlignment(Pos.CENTER);
		boxCenter.getChildren().addAll(userlblCenterTop, spacer2, cardBtnCenterTop,
				spacer3, stichInfo, spacer8, cardBtnCenterBottom, spacer4, userlblCenterBottom);
		
		
		cardBtnLeft = new CardButton();
		cardBtnLeft.setVisible(false);
		centerButtons.add(cardBtnLeft);
		userlblLeft = new Label("Left");
		Region spacer1 = new Region();
        spacer1.setPrefWidth(10);
		
		boxLeft = new HBox();
		boxLeft.setAlignment(Pos.CENTER);
		boxLeft.getChildren().addAll(userlblLeft, spacer1, cardBtnLeft);
		
	
		cardBtnRight = new CardButton();
		cardBtnRight.setVisible(false);
		centerButtons.add(cardBtnRight);
		userlblRight = new Label("Right");
		Region spacer5 = new Region();
        spacer5.setPrefWidth(10);
		
		boxRight  = new HBox();
		boxRight.setAlignment(Pos.CENTER);
		boxRight.getChildren().addAll(cardBtnRight, spacer5, userlblRight);
		
		
		Region spacer6 = new Region();
        spacer6.setPrefWidth(10);
        Region spacer7 = new Region();
        spacer7.setPrefWidth(10);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(boxLeft, spacer6, boxCenter, spacer7, boxRight);
		
	}
	
	public void setCard(Card card, String player) {
		for(Button b : centerButtons) {
			if(b.getId().equalsIgnoreCase(player)) {
				CardButton cardBtn = (CardButton) b;
				cardBtn.setCardCenter(card);
				cardBtn.setVisible(true);
			}
		}
		
	}
		
}


