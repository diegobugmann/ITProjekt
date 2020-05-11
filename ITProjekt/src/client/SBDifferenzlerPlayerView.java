package client;

import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SBDifferenzlerPlayerView extends HBox {
	
	protected Label playerName;
	protected Label angesagtePoints;
	protected Label gemachtePoints;
	protected Label differenzPoints;
	protected Label pointsTotal;
	
	public SBDifferenzlerPlayerView() {
		super();
		BorderStroke borderStroke = new BorderStroke(Color.WHITE,BorderStrokeStyle.SOLID, null, new BorderWidths(5));
		this.playerName = new Label();
		this.playerName.setBorder(new Border(borderStroke));
		this.playerName.setMinWidth(180);
		this.angesagtePoints = new Label();
		this.angesagtePoints.setBorder(new Border(borderStroke));
		this.angesagtePoints.setMinWidth(60);
		this.gemachtePoints = new Label();
		this.gemachtePoints.setBorder(new Border(borderStroke));
		this.gemachtePoints.setMinWidth(60);
		this.differenzPoints = new Label();
		this.differenzPoints.setBorder(new Border(borderStroke));
		this.differenzPoints.setMinWidth(60);
		this.pointsTotal = new Label();
		this.pointsTotal.setBorder(new Border(borderStroke));
		this.pointsTotal.setMinWidth(60);
		
		this.getChildren().addAll(playerName, angesagtePoints, gemachtePoints, differenzPoints, pointsTotal);
		
	}
	

}
