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
	
	private HBox header;
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
	private HBox footer;
	protected Button playbtn;
	
	public ScoreboardDifferenzlerView() {
		super();
		this.titellbl = new Label("Punktestand");
		this.roundslbl = new Label();
		this.header = new HBox();
		Region spacer1 = new Region();
		spacer1.setPrefWidth(300);
		this.header.getChildren().addAll(titellbl, spacer1, roundslbl);
		BorderStroke borderStroke = new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, null, new BorderWidths(1));
		this.playernameslbl = new Label("Spieler");
		this.playernameslbl.setBorder(new Border(borderStroke));
		this.playernameslbl.setMinWidth(150);
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
		this.playbtn = new Button("Schliessen");
		this.footer = new HBox();
		this.footer.getChildren().add(playbtn);
		this.getChildren().addAll(header, titels, player1, player2, player3, player4, footer);
	}


}
