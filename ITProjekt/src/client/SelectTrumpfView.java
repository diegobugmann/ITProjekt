package client;

import java.io.InputStream;

import Commons.GameType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SelectTrumpfView extends VBox {
	protected Label userlbl;
	protected RadioButton rbShieldsOrSpades;
	protected RadioButton rbRosesOrHearts;
	protected RadioButton rbAcornsOrDiamonds;
	protected RadioButton rbBellsOrClubs;
	protected RadioButton rbTopsDown;
	protected RadioButton rbBottomUp;
	protected RadioButton rbSchieber;
	protected ToggleGroup tg;
	protected Button confirmBtn;
	protected String cardStyle;
	protected HBox rbBox;
	protected HBox rbBox2;
	
	/**
	 * @author Luca Meyer
	 * View to give the option to select between german or french cards
	 * View instead of Alert because Alerts can't have RadioButtons
	 */
	
	public SelectTrumpfView() {
		super();
		rbBox = new HBox();
		rbBox2 = new HBox();
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
		
		//TODO Topsdown and bottomup and schieber for Schieber
		rbTopsDown = new RadioButton("");
		InputStream is5 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Tops Down.png");
		Image img5= new Image(is5);
		ImageView v5 = new ImageView(img5);
		v5.setPreserveRatio(true);
		v5.setFitWidth(30);
		v5.setFitHeight(30);
		rbTopsDown.setGraphic(v5);
		rbTopsDown.setToggleGroup(tg);
		
		rbBottomUp = new RadioButton("");
		InputStream is6 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Bottoms Up.png");
		Image img6= new Image(is6);
		ImageView v6 = new ImageView(img6);
		v6.setPreserveRatio(true);
		v6.setFitWidth(30);
		v6.setFitHeight(30);
		rbBottomUp.setGraphic(v6);
		rbBottomUp.setToggleGroup(tg);
		
		rbSchieber = new RadioButton("");
		InputStream is7 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/Schieber.png");
		Image img7= new Image(is7);
		ImageView v7 = new ImageView(img7);
		v7.setPreserveRatio(true);
		v7.setFitWidth(30);
		v7.setFitHeight(30);
		rbSchieber.setGraphic(v7);
		rbSchieber.setToggleGroup(tg);
		
		confirmBtn = new Button("Trumpf angeben");
		confirmBtn.setDisable(true);
		
		Region spacer1 = new Region();
		spacer1.setPrefWidth(20);
		
		Region spacer2 = new Region();
		spacer2.setPrefWidth(20);
		
		Region spacer3 = new Region();
		spacer3.setPrefWidth(20);
		
		Region spacer6 = new Region();
		spacer6.setPrefWidth(20);
		
		Region spacer7 = new Region();
		spacer7.setPrefWidth(20);

		rbBox.setAlignment(Pos.CENTER);
		rbBox.getChildren().addAll(rbShieldsOrSpades, spacer1, rbRosesOrHearts,
		spacer2, rbAcornsOrDiamonds, spacer3, rbBellsOrClubs);
		
		rbBox2.setAlignment(Pos.CENTER);
		rbBox2.getChildren().addAll(rbTopsDown, spacer6, rbBottomUp, spacer7, rbSchieber);
		
		userlbl = new Label("");
		
		Region spacer4 = new Region();
		spacer4.setPrefHeight(50);
		
		Region spacer5 = new Region();
		spacer5.setPrefHeight(20);
		
		Region spacer8 = new Region();
		spacer8.setPrefHeight(10);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(userlbl, spacer4, rbBox, spacer8, rbBox2, spacer5, confirmBtn);
		
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
		}else if(tg.getSelectedToggle()== rbTopsDown) {
			gameType = Commons.GameType.TopsDown;
		}else if(tg.getSelectedToggle()== rbBottomUp) {
			gameType = Commons.GameType.BottomsUp;
		}else if(tg.getSelectedToggle()== rbSchieber) {
			gameType = Commons.GameType.Schieber;
		}
		return gameType;
	}
	
	
	
 

}
