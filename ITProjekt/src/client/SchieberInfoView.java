package client;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * @author Luca Meyer 
 * @author sarah
 *
 *
 */

public class SchieberInfoView extends InfoView{
	protected Label pointsTeamlbl;
	protected Label pointsTeam;
	protected Label pointsOppolbl;
	protected Label pointsOppo;
	protected Label goalPointslbl;
	protected Label goalPoints;

	
	public SchieberInfoView() {
		
		super();
		
		pointsTeamlbl = new Label("Dein Team");
		pointsTeam = new Label("");
		
		pointsOppolbl = new Label("Gegnerisches Team");
		pointsOppo = new Label("");
		
		goalPointslbl = new Label("Ziel Punkte");
		goalPoints = new Label("");		
		
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
		
		this.setPadding(new Insets(5,5,5,5));
		this.getChildren().addAll(pointsTeamlbl, pointsTeam, spacer1, pointsOppolbl, pointsOppo, 
				spacer2, goalPointslbl, goalPoints, spacer3, trumpflbl, picTrump, spacer4, popUplbl, popUp);
		
		}	
}
