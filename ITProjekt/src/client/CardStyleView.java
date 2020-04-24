package client;

import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CardStyleView extends VBox {
	
	protected RadioButton rb1;
	protected RadioButton rb2;
	protected ToggleGroup tg;
	protected Button confirmBtn;
	
	/**
	 * @author Luca Meyer
	 * View to give the option to select between german or french cards
	 * View instead of Alert because Alerts can't have RadioButtons
	 */
	
	public CardStyleView() {
		super();
		tg = new ToggleGroup();
		
		rb1 = new RadioButton("Franzöisch");
		InputStream is1 = getClass().getResourceAsStream("Bilder/Bilder_franz.png");
		Image img1= new Image(is1);
		ImageView v1 = new ImageView(img1);
		v1.setPreserveRatio(true);
		v1.setFitWidth(200);
		v1.setFitHeight(20);
		rb1.setGraphic(v1);
		rb1.setToggleGroup(tg);
		
		rb2 = new RadioButton("Deutsch");
		InputStream is2 = getClass().getResourceAsStream("Bilder/Bilder_deutsch.png");
		Image img2= new Image(is2);
		ImageView v2 = new ImageView(img2);
		v2.setPreserveRatio(true);
		v2.setFitWidth(200);
		v1.setFitHeight(20);
		rb2.setGraphic(v2);
		rb2.setToggleGroup(tg);
		
		confirmBtn = new Button("Übernehmen");
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(20);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(rb1,rb2, spacer1, confirmBtn);
		
	}
	
	public void setSelectedStyle(int selected) {
		if(selected == 0) {
			rb1.setSelected(true);
			
		}else {
			rb2.setSelected(true);
		}
	}
	
	public int getSelectedRadio() {
		int selected = 0;
		if(tg.getSelectedToggle()== rb1) {
			selected = 0;
		}else {
			selected = 1;
		}
		
		return selected;
	}
	
	
	
 

}
