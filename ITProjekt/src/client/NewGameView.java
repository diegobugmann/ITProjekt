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
 * creates the view to set the gameinformation
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
		
		gameTypelbl = new Label("Spielart:");
		rbSchieber = new RadioButton("Schieber");
		rbDifferenzler = new RadioButton("Differenzler");
		typeGroup = new ToggleGroup();
		rbSchieber.setToggleGroup(typeGroup);
		rbDifferenzler.setToggleGroup(typeGroup);
		
		radioBoxType = new HBox();
		radioBoxType.setAlignment(Pos.CENTER);
		radioBoxType.getChildren().addAll(rbSchieber, rbDifferenzler);
		
		Region spacer1 = new Region();
		spacer1.setPrefHeight(10);
		
		pointslbl = new Label("Spielpunkte:");
		rb1000 = new RadioButton("1000");
		rb2500 = new RadioButton("2500");
		pointsGroup = new ToggleGroup();
		rb1000.setToggleGroup(pointsGroup);
		rb2500.setToggleGroup(pointsGroup);
		pointsGroup.selectToggle(rb1000);
		
		radioBoxPoints = new HBox();
		radioBoxPoints.setAlignment(Pos.CENTER);
		radioBoxPoints.getChildren().addAll(rb1000, rb2500);
		
		numOfRoundslbl = new Label("Anzahl Runden:");
		
		//https://o7planning.org/de/11185/anleitung-javafx-spinner
		numOfRounds = new Spinner<Integer>();
		
		final int initialValue = 5;
		 
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, initialValue);
 
        numOfRounds.setValueFactory(valueFactory);
        
        
        typeGroup.selectToggle(rbSchieber);
        numOfRoundslbl.setVisible(false);
        numOfRounds.setVisible(false);
		
		
		Region spacer2 = new Region();
		spacer2.setPrefHeight(10);
		
		Region spacer3 = new Region();
		spacer3.setPrefHeight(10);
		
		okBtn = new Button("Erstellen");
		
		this.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(gameTypelbl, radioBoxType, spacer1, 
				pointslbl, radioBoxPoints, spacer2, numOfRoundslbl, 
				numOfRounds, spacer3, okBtn);

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
