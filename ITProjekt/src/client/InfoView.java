package client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InfoView extends VBox{
	protected Label pointsTeam;
	protected Label pointsOppo;
	protected Label picTrumpf;
	protected String cardStyle;
	
	
	public InfoView() {
		super();
		pointsTeam = new Label("");
		pointsOppo = new Label("");
		picTrumpf = new Label("");	
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(50);
		
		Region spacer2 = new Region();
		spacer2.setPrefHeight(50);
		
		this.getChildren().addAll(pointsTeam, spacer1, pointsOppo, spacer2, picTrumpf);
		
	}
	
	public void setTrumpf(String trumpf) {
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
		
		if(trumpf != null) {
			Image image = new Image(picTrumpf.getClass().getClassLoader().getResourceAsStream("Trumpf_"+cardStyle+"/" +trumpf+".png"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(picTrumpf.widthProperty());
			imv.fitHeightProperty().bind(picTrumpf.heightProperty());
			imv.setPreserveRatio(true);
			picTrumpf.setGraphic(imv);
		}else {
			picTrumpf.setGraphic(null);
		}
	}

	public void setPointsTeam(String pointsTeamText) {
		pointsTeam.setText(pointsTeamText);
	}

	public void setPointsOppo(String pointsOppoText) {
		pointsOppo.setText(pointsOppoText);
	}
	

}
