package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
/**
 * 
 * @author sarah
 *
 */

public class ScoreboardDifferenzlerView extends VBox{
	
	private Label titellbl;
	protected Label roundslbl;
	private HBox titels;
	private Label playernameslbl;
	private Label angesagtlbl;
	private Label gemachtlbl;
	private Label differenzlbl;
	private Label total;
	protected SBDifferenzlerPlayerView player1;
	protected SBDifferenzlerPlayerView player2;
	protected SBDifferenzlerPlayerView player3;
	protected SBDifferenzlerPlayerView player4;
	protected Button playbtn;
	
	public ScoreboardDifferenzlerView() {
		super();	
		this.titellbl = new Label("Punktestand");
		Region spacer1 = new Region();
		spacer1.prefHeight(15);
		BorderStroke borderStroke = new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, null, new BorderWidths(1));
		this.playernameslbl = new Label("Spieler");
		this.playernameslbl.setBorder(new Border(borderStroke));
		this.playernameslbl.setMinWidth(150);
		this.playernameslbl.setStyle("-fx-padding: 0 0 0 5;");
		this.angesagtlbl = new Label("Angesagt");
		this.angesagtlbl.setBorder(new Border(borderStroke));
		this.angesagtlbl.setMinWidth(60);
		this.angesagtlbl.setAlignment(Pos.CENTER);
		this.gemachtlbl = new Label("Gemacht");
		this.gemachtlbl.setBorder(new Border(borderStroke));
		this.gemachtlbl.setMinWidth(60);
		this.gemachtlbl.setAlignment(Pos.CENTER);
		this.differenzlbl = new Label("Differenz");
		this.differenzlbl.setBorder(new Border(borderStroke));
		this.differenzlbl.setMinWidth(60);
		this.differenzlbl.setAlignment(Pos.CENTER);
		this.total = new Label("Total");
		this.total.setBorder(new Border(borderStroke));
		this.total.setMinWidth(60);
		this.total.setAlignment(Pos.CENTER);
		this.titels = new HBox();
		this.titels.getChildren().addAll(playernameslbl, angesagtlbl, gemachtlbl, differenzlbl, total);
		this.player1 = new SBDifferenzlerPlayerView();
		this.player2 = new SBDifferenzlerPlayerView();
		this.player3 = new SBDifferenzlerPlayerView();
		this.player4 = new SBDifferenzlerPlayerView();
		Region spacer = new Region();
		spacer.setPrefHeight(15);
		this.playbtn = new Button("Schliessen");

		//CSS---------------------------------------------------
		this.getStylesheets().add(getClass().getResource("CSS/scoreboard.css").toExternalForm());	
		this.getStyleClass().add("root");
		playbtn.getStyleClass().add("button");
		player1.getStyleClass().add("row");
		player2.getStyleClass().add("row");
		player3.getStyleClass().add("row");
		player4.getStyleClass().add("row");
		titels.getStyleClass().add("row");
		playernameslbl.getStyleClass().add("titlelable");
		angesagtlbl.getStyleClass().add("titlelable");
		gemachtlbl.getStyleClass().add("titlelable");
		differenzlbl.getStyleClass().add("titlelable");
		total.getStyleClass().add("titlelable");
		titellbl.getStyleClass().add("title");
		this.setAlignment(Pos.CENTER);
		System.out.println("Test");
		//-------------------------------------------------------


		this.getChildren().addAll(titellbl, spacer1, titels, player1, player2, player3, player4, spacer, playbtn);
	}


}
