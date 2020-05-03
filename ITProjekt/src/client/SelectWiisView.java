package client;

import java.io.InputStream;
import java.util.ArrayList;

import Commons.GameType;
import Commons.Wiis;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
/**
 * @author Luca Meyer
 * View to give the option to select all possible Wiis
 * 
 */
public class SelectWiisView extends VBox {
	
	protected VBox checkVBox;
	protected Label userlbl;
	
	protected Button confirmBtn;
	protected Insets insets;
	public ArrayList<CheckBox> checkBoxes = new ArrayList<>();
	
	

	/**
	 * @author Luca Meyer
	 * View to give the option to select all possible Wiis
	 * 
	 */
	

	
	public SelectWiisView(ArrayList<Wiis> wiisArray, int cardStyle) {

		super();
		userlbl = new Label("");
		
		checkVBox = new VBox();
		
		for(Wiis w : wiisArray) {
			String text = w.toString();
			
			CheckBox b = new CheckBox(CardNameTranslator.getBlattName(w) + " von " + CardNameTranslator.getSuitName(w, cardStyle) + " " + w.getHighestCard().getRank());
			b.setId(text);
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
