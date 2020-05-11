package client;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DifferenzlerInfoView extends InfoView {
	
		protected Label numOfRoundslbl;
		protected Label numOfRounds;
		protected Label pointslbl;
		protected Label points;
		protected Label diffPointslbl;
		protected Label diffPoints;	
		protected Button openSBbtn;
		
		public DifferenzlerInfoView() {
			
			super();
			
			numOfRoundslbl = new Label("Spielrunden");
			numOfRounds = new Label("");
			
			pointslbl = new Label("Punkte");
			points = new Label ("");
			
			diffPointslbl = new Label("Differenz total");
			diffPoints = new Label("");	
			
			openSBbtn = new Button("Punktestand");
			
			Region spacer1 = new Region();
			spacer1.setPrefHeight(50);
			
			Region spacer2 = new Region();
			spacer2.setPrefHeight(50);
			
			Region spacer3 = new Region();
			spacer3.setPrefHeight(50);
			
			Region spacer4 = new Region();
			spacer4.setPrefHeight(50);
			
			
			this.setPadding(new Insets(5,5,5,5));
			this.getChildren().addAll(numOfRoundslbl, numOfRounds, spacer1, pointslbl, points, 
					spacer2, diffPointslbl, diffPoints, spacer3, trumpflbl, picTrump, spacer4, openSBbtn);			
		}
}
