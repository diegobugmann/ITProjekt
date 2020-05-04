package client;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DifferenzlerInfoView extends InfoView {
	
		protected Label numOfRoundslbl;
		protected Label numOfRounds;
		protected Label angesagtPointslbl;
		protected Label angesagtPoints;
		protected Label aktuellePointslbl;
		protected Label aktuellePoints;
		protected Label diffPointslbl;
		protected Label diffPoints;		
		
		public DifferenzlerInfoView() {
			
			super();
			
			numOfRoundslbl = new Label("Spielrunden");
			numOfRounds = new Label("");
			
			angesagtPointslbl = new Label("Punkte angesagt");
			angesagtPoints = new Label ("");
			
			aktuellePointslbl = new Label("Aktuelle Punktzahl:");
			aktuellePoints = new Label("");
			
			diffPointslbl = new Label("Differenz:");
			diffPoints = new Label("");			
			
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
			this.getChildren().addAll(numOfRoundslbl, numOfRounds, spacer1, 
					angesagtPointslbl, angesagtPoints, spacer2, aktuellePointslbl, aktuellePoints, 
					spacer3, diffPointslbl, diffPoints, spacer4, trumpflbl, picTrump, spacer5, popUplbl, popUp);			
		}
}
