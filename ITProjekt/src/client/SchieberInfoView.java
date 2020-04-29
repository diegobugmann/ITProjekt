package client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SchieberInfoView extends VBox{
	protected Label pointsTeamlbl;
	protected Label pointsTeam;
	protected Label pointsOppolbl;
	protected Label pointsOppo;
	protected Label goalPointslbl;
	protected Label goalPoints;
	protected Label trumpflbl;

	
	public SchieberInfoView() {
		
		super();
		
		//TODO Dein Team and gegn.Team -> actual usernames
		pointsTeamlbl = new Label("Dein Team");
		pointsTeam = new Label("");
		
		pointsOppolbl = new Label("Gegnerisches Team");
		pointsOppo = new Label("");
		
		goalPointslbl = new Label("Ziel Punkte");
		goalPoints = new Label("");
		
		//TODO Trump Bild
		trumpflbl = new Label("Trumpf");
		//ivModel.picTrumpf = new Label("");	
		
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(50);
		
		Region spacer2 = new Region();
		spacer2.setPrefHeight(50);
		
		Region spacer3 = new Region();
		spacer3.setPrefHeight(50);
		
		Region spacer4 = new Region();
		spacer4.setPrefHeight(50);
		
		Region spacer5 = new Region();
		spacer5.setPrefHeight(50);
		
		//TODO Trump Bild
		this.getChildren().addAll(pointsTeamlbl, pointsTeam, spacer1, pointsOppolbl, pointsOppo, 
				spacer2, goalPointslbl, goalPoints, spacer3, trumpflbl);
		
	}
	
	/*
	 * Moved to InfoViewModel
	 * 
	 * public void setTrumpf(String trumpf) {
		
		if(ClientModel.cardStyle==0) {
			cardStyle= "franz";
		}else if(ClientModel.cardStyle==1) {
			cardStyle = "deutsch";
		}
		
		if(trumpf != null) {
			InputStream is1 = getClass().getResourceAsStream("Trumpf_"+cardStyle+"/"+trumpf+".png");
			Image image = new Image(is1);
			ImageView imv = new ImageView(image);
			imv.setFitWidth(40);
			imv.setFitHeight(40);
			imv.setPreserveRatio(true);
			picTrumpf.setGraphic(imv);
		}else {
			picTrumpf.setGraphic(null);
		}
	}
	*/


}
