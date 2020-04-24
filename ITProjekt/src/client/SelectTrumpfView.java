package client;

import java.io.InputStream;

import Commons.GameType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SelectTrumpfView extends HBox {
	
	protected RadioButton rbShieldsOrSpades;
	protected RadioButton rbRosesOrHearts;
	protected RadioButton rbAcornsOrDiamonds;
	protected RadioButton rbBellsOrClubs;
	protected ToggleGroup tg;
	protected Button confirmBtn;
	
	/**
	 * @author Luca Meyer
	 * View to give the option to select between german or french cards
	 * View instead of Alert because Alerts can't have RadioButtons
	 */
	
	public SelectTrumpfView() {
		super();
		tg = new ToggleGroup();
		
		rbShieldsOrSpades = new RadioButton("");
		InputStream is1 = getClass().getResourceAsStream("Trumpf_franz/Bilder_franz.png");
		Image img1= new Image(is1);
		ImageView v1 = new ImageView(img1);
		v1.setPreserveRatio(true);
		v1.setFitWidth(200);
		v1.setFitHeight(20);
		rbShieldsOrSpades.setGraphic(v1);
		rbShieldsOrSpades.setToggleGroup(tg);
		
		rbRosesOrHearts = new RadioButton("");
		InputStream is2 = getClass().getResourceAsStream("Bilder/Bilder_deutsch.png");
		Image img2= new Image(is2);
		ImageView v2 = new ImageView(img2);
		v2.setPreserveRatio(true);
		v2.setFitWidth(200);
		v2.setFitHeight(20);
		rbRosesOrHearts.setGraphic(v2);
		rbRosesOrHearts.setToggleGroup(tg);
		
		rbAcornsOrDiamonds = new RadioButton("");
		InputStream is3 = getClass().getResourceAsStream("Bilder/Bilder_deutsch.png");
		Image img3= new Image(is3);
		ImageView v3 = new ImageView(img3);
		v3.setPreserveRatio(true);
		v3.setFitWidth(200);
		v3.setFitHeight(20);
		rbAcornsOrDiamonds.setGraphic(v3);
		rbAcornsOrDiamonds.setToggleGroup(tg);
		
		rbBellsOrClubs = new RadioButton("");
		InputStream is4 = getClass().getResourceAsStream("Bilder/Bilder_deutsch.png");
		Image img4= new Image(is4);
		ImageView v4 = new ImageView(img4);
		v4.setPreserveRatio(true);
		v4.setFitWidth(200);
		v4.setFitHeight(20);
		rbBellsOrClubs.setGraphic(v4);
		rbBellsOrClubs.setToggleGroup(tg);
		
		confirmBtn = new Button("Trumpf angeben");
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(20);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(rbShieldsOrSpades, rbRosesOrHearts,
		rbAcornsOrDiamonds,rbBellsOrClubs, spacer1, confirmBtn);
		
	}
	
	public GameType getSelectedTrumpf() {
		GameType gameType = null;
		if(tg.getSelectedToggle()== rbShieldsOrSpades) {
			gameType = Commons.GameType.ShieldsOrSpades;
		}else if (tg.getSelectedToggle()== rbRosesOrHearts) {
			gameType = Commons.GameType.RosesOrHearts;
		}else if(tg.getSelectedToggle()== rbAcornsOrDiamonds) {
			gameType = Commons.GameType.AcornsOrDiamonds;
		}else if(tg.getSelectedToggle()== rbBellsOrClubs) {
			gameType = Commons.GameType.BellsOrClubs;
		}
		return gameType;
	}
	
	
	
 

}
