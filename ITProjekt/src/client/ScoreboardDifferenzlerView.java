package client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

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
		this.playernameslbl = new Label("Spieler");
		this.angesagtlbl = new Label("Angesagt");
		this.gemachtlbl = new Label("Gemacht");
		this.differenzlbl = new Label("Differenz");
		this.total = new Label("Total");
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
