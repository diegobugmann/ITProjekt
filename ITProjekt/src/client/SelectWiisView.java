package client;

import java.util.ArrayList;
import Commons.Wiis;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Luca Meyer & Sarah
 * View to give the option to select all possible Wiis
 * 
 */
public class SelectWiisView extends VBox {
	
	protected VBox checkVBox;
	protected Label userlbl;
	protected Button confirmBtn;
	protected Insets insets;
	public ArrayList<CheckBox> checkBoxes = new ArrayList<>();
	

	public SelectWiisView(ArrayList<Wiis> wiisArray, int cardStyle) {

		super();
		
		this.getStylesheets().add(getClass().getResource("CSS/popups.css").toExternalForm());
		this.getStyleClass().add("root");
		
		userlbl = new Label("");
		
		checkVBox = new VBox(10);
		checkVBox.setPadding(new Insets(0,0,0,50));
		for(Wiis w : wiisArray) {
			String text = w.toString();
			CheckBox b = null;
			if (w.getBlatt() == Wiis.Blatt.viergleiche) {
				b = new CheckBox(CardNameTranslator.getBlattName(w, cardStyle) + " von " + CardNameTranslator.getRankName(w, cardStyle));
			} else if (w.getBlatt()== Wiis.Blatt.vierNeuner || w.getBlatt()== Wiis.Blatt.vierBauern) {
				b = new CheckBox(CardNameTranslator.getBlattName(w, cardStyle));
			} else {
				b = new CheckBox(CardNameTranslator.getBlattName(w, cardStyle) + " von " + CardNameTranslator.getSuitName(w, cardStyle) + " " 
				+ CardNameTranslator.getRankName(w, cardStyle));
			}
			b.setId(text);
			b.getStyleClass().add("checkbox");
			checkBoxes.add(b);
			checkVBox.getChildren().add(b);
		}
		
		confirmBtn = new Button("Weisen");
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(50);
		
		Region spacer2 = new Region();
		spacer2.setPrefHeight(20);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(userlbl, spacer1, checkVBox, spacer2, confirmBtn);
		

	}
	 

}
