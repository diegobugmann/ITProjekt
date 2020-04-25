package client;

import java.io.InputStream;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InfoView extends VBox{
	protected Label pointsTeamlbl;
	protected Label pointsTeam;
	protected Label pointsOppolbl;
	protected Label pointsOppo;
	protected Label goalPointslbl;
	protected Label goalPoints;
	protected Label numOfRoundsRemainlbl;
	protected Label numOfRoundsRemain;
	protected Label numOfRoundsTotallbl;
	protected Label numOfRoundsTotal;
	protected Label trumpflbl;
	protected Label picTrumpf;
	protected String cardStyle;
	
	
	public InfoView() {
		super();
		pointsTeamlbl = new Label("Dein Team");
		pointsTeam = new Label("");
		
		pointsOppolbl = new Label("Gegnerisches Team");
		pointsOppo = new Label("");
		
		goalPointslbl = new Label("Ziel Punkte");
		goalPoints = new Label("");
		
		numOfRoundsTotallbl = new Label("Anzahl Runden Total");
		numOfRoundsTotal = new Label("");
		
		numOfRoundsRemainlbl = new Label("Runden zu spielen");
		numOfRoundsRemain = new Label("");
		
		
		trumpflbl = new Label("Trumpf");
		picTrumpf = new Label("");	
		
		
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
		
		
		this.getChildren().addAll(pointsTeamlbl, pointsTeam, spacer1, pointsOppolbl, pointsOppo, 
				spacer2, goalPointslbl, goalPoints, spacer3, numOfRoundsTotallbl, numOfRoundsTotal,
				spacer4, numOfRoundsRemainlbl, numOfRoundsRemain, spacer5, trumpflbl, picTrumpf);
		
	}
	
	public void setTrumpf(String trumpf) {
		
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

	public void setPointsTeam(String pointsTeamText) {
		pointsTeam.setText(pointsTeamText);
	}

	public void setPointsOppo(String pointsOppoText) {
		pointsOppo.setText(pointsOppoText);
	}

	public void setPointsTeam(Label pointsTeam) {
		this.pointsTeam = pointsTeam;
	}

	public void setPointsOppo(Label pointsOppo) {
		this.pointsOppo = pointsOppo;
	}

	public void setGoalPoints(Label goalPoints) {
		this.goalPoints = goalPoints;
	}

	public void setNumOfRoundsRemain(Label numOfRoundsRemain) {
		this.numOfRoundsRemain = numOfRoundsRemain;
	}

	public void setNumOfRoundsTotal(Label numOfRoundsTotal) {
		this.numOfRoundsTotal = numOfRoundsTotal;
	}

	public void setTrumpflbl(Label trumpflbl) {
		this.trumpflbl = trumpflbl;
	}

	public void setPicTrumpf(Label picTrumpf) {
		this.picTrumpf = picTrumpf;
	}

	public void setCardStyle(String cardStyle) {
		this.cardStyle = cardStyle;
	}
	
	
	

}
