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

public class SelectTrumpfView extends VBox {
	
	protected RadioButton rbShieldsOrSpades;
	protected RadioButton rbRosesOrHearts;
	protected RadioButton rbAcornsOrDiamonds;
	protected RadioButton rbBellsOrClubs;
	protected ToggleGroup tg;
	protected Button confirmBtn;
	protected String cardStyle;
	protected HBox rbBox;
	
	/**
	 * @author Luca Meyer
	 * View to give the option to select between german or french cards
	 * View instead of Alert because Alerts can't have RadioButtons
	 */
	
	public SelectTrumpfView() {
		super();
		rbBox = new HBox();
		tg = new ToggleGroup();
		
		if(ClientModel.cardStyle==0) {
			cardStyle = "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
		
		rbShieldsOrSpades = new RadioButton("");
		InputStream is1 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Shields or Spades.png");
		Image img1= new Image(is1);
		ImageView v1 = new ImageView(img1);
		v1.setPreserveRatio(true);
		v1.setFitWidth(30);
		v1.setFitHeight(30);
		rbShieldsOrSpades.setGraphic(v1);
		rbShieldsOrSpades.setToggleGroup(tg);
		
		rbRosesOrHearts = new RadioButton("");
		InputStream is2 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Roses or Hearts.png");
		Image img2= new Image(is2);
		ImageView v2 = new ImageView(img2);
		v2.setPreserveRatio(true);
		v2.setFitWidth(30);
		v2.setFitHeight(30);
		rbRosesOrHearts.setGraphic(v2);
		rbRosesOrHearts.setToggleGroup(tg);
		
		rbAcornsOrDiamonds = new RadioButton("");
		InputStream is3 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Acorns or Diamonds.png");
		Image img3= new Image(is3);
		ImageView v3 = new ImageView(img3);
		v3.setPreserveRatio(true);
		v3.setFitWidth(30);
		v3.setFitHeight(30);
		rbAcornsOrDiamonds.setGraphic(v3);
		rbAcornsOrDiamonds.setToggleGroup(tg);
		
		rbBellsOrClubs = new RadioButton("");
		InputStream is4 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Bells or Clubs.png");
		Image img4= new Image(is4);
		ImageView v4 = new ImageView(img4);
		v4.setPreserveRatio(true);
		v4.setFitWidth(30);
		v4.setFitHeight(30);
		rbBellsOrClubs.setGraphic(v4);
		rbBellsOrClubs.setToggleGroup(tg);
		
		confirmBtn = new Button("Trumpf angeben");
		confirmBtn.setDisable(true);
		
		Region spacer1 = new Region();
		spacer1.setPrefWidth(20);
		
		Region spacer2 = new Region();
		spacer2.setPrefWidth(20);
		
		Region spacer3 = new Region();
		spacer3.setPrefWidth(20);
		
		Region spacer4 = new Region();
		spacer4.setPrefHeight(20);
		
		rbBox.setAlignment(Pos.CENTER);
		rbBox.getChildren().addAll(rbShieldsOrSpades, spacer1, rbRosesOrHearts,
		spacer2, rbAcornsOrDiamonds, spacer3, rbBellsOrClubs);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(rbBox, spacer4, confirmBtn);
		
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
