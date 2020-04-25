package client;

import java.io.InputStream;
import java.util.ArrayList;

import Commons.GameType;
import Commons.Wiis;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SelectWiisView extends VBox {
	
	protected VBox checkVBox;
	
	
	protected Button confirmBtn;
	protected Insets insets;
	public ArrayList<CheckBox> checkBoxes = new ArrayList<>();
	
	
	/**
	 * @author Luca Meyer
	 * View to give the option to select between german or french cards
	 * View instead of Alert because Alerts can't have RadioButtons
	 */
	

	
	public SelectWiisView(Wiis[] wiis) {
		super();
		
		checkVBox = new VBox();
		
		for(Wiis w : wiis) {
			String text = w.toString();
			String textWiis = text.substring(0, text.indexOf("_")-1);
			String highcardText = text.substring(text.indexOf("_"));
			CheckBox b = new CheckBox(textWiis+ "ab"+highcardText);
			b.setId(text);
			checkBoxes.add(b);
			checkVBox.getChildren().add(b);
		}
		
		confirmBtn = new Button("Weisen");
		confirmBtn.setDisable(true);
		
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(20);
		
		
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(checkVBox, spacer1, confirmBtn);
		
	}
	 

}
