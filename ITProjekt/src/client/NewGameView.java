package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Luca Meyer
 * creates the view to select the gameoptions
 *
 */
public class NewGameView extends VBox {
	protected HBox radioBoxType;
	protected RadioButton rbSchieber;
	protected RadioButton rbDifferenzler;
	protected ToggleGroup typeGroup;
	protected Label gameTypelbl;
	
	protected Label numOfRoundslbl;
	protected Spinner<Integer> numOfRounds;
	
	protected HBox radioBoxPoints;
	protected RadioButton rb1000;
	protected RadioButton rb2500;
	protected ToggleGroup pointsGroup;
	protected Label pointslbl;
	
	protected Button okBtn;
	
	
	public NewGameView() {
		super();
		//PopUp Style
		this.getStylesheets().add(getClass().getResource("CSS/popups.css").toExternalForm());
		this.getStyleClass().add("root");
		gameTypelbl = new Label("Spielart:");
		rbSchieber = new RadioButton("Schieber");
		rbDifferenzler = new RadioButton("Differenzler");
		typeGroup = new ToggleGroup();
		rbSchieber.setToggleGroup(typeGroup);
		rbDifferenzler.setToggleGroup(typeGroup);
		
		Region spacer0 = new Region();
		spacer0.setPrefWidth(10);
		
		
		radioBoxType = new HBox();
		radioBoxType.setAlignment(Pos.CENTER);
		radioBoxType.getChildren().addAll(rbSchieber, spacer0, rbDifferenzler);
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(10);
		
		pointslbl = new Label("Spielpunkte:");
		rb1000 = new RadioButton("1000");
		rb2500 = new RadioButton("2500");
		pointsGroup = new ToggleGroup();
		rb1000.setToggleGroup(pointsGroup);
		rb2500.setToggleGroup(pointsGroup);
		pointsGroup.selectToggle(rb1000);
		
		Region spacer2 = new Region();
		spacer2.setPrefWidth(10);
		
		radioBoxPoints = new HBox();
		radioBoxPoints.setAlignment(Pos.CENTER);
		radioBoxPoints.getChildren().addAll(rb1000, spacer2, rb2500);
		
		numOfRoundslbl = new Label("Anzahl Runden (1-20):");
		
		//https://o7planning.org/de/11185/anleitung-javafx-spinner
		numOfRounds = new Spinner<Integer>();
		
		final int initialValue = 5;
		 
        //sets the range of the spinner
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, initialValue);
 
        numOfRounds.setValueFactory(valueFactory);
        numOfRounds.setEditable(true);
        
        
        typeGroup.selectToggle(rbSchieber);
        numOfRoundslbl.setVisible(false);
        numOfRounds.setVisible(false);
		
		
		Region spacer3 = new Region();
		spacer3.setPrefHeight(10);
		
		Region spacer4 = new Region();
		spacer4.setPrefHeight(10);
		
		okBtn = new Button("Erstellen");
		
		this.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(gameTypelbl, radioBoxType, spacer1, 
				pointslbl, radioBoxPoints, spacer3, numOfRoundslbl, 
				numOfRounds, spacer4, okBtn);

	}


	public Button getOkBtn() {
		return okBtn;
	}


	public HBox getRadioBoxType() {
		return radioBoxType;
	}


	public RadioButton getRbSchieber() {
		return rbSchieber;
	}


	public RadioButton getRbDifferenzler() {
		return rbDifferenzler;
	}


	public ToggleGroup getTypeGroup() {
		return typeGroup;
	}


	public Label getGameTypelbl() {
		return gameTypelbl;
	}

	public HBox getRadioBoxPoints() {
		return radioBoxPoints;
	}


	public RadioButton getRb1000() {
		return rb1000;
	}


	public RadioButton getRb2500() {
		return rb2500;
	}


	public ToggleGroup getPointsGroup() {
		return pointsGroup;
	}


	public Label getPointslbl() {
		return pointslbl;
	}
	
	

}
