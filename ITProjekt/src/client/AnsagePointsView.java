package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Luca Meyer
 * creates the view to choose the points for differenzler
 *
 */
public class AnsagePointsView extends VBox {
	protected Label numOfPointslbl;
	protected Spinner<Integer> numOfPoints;
	protected Button okBtn;
	
	public AnsagePointsView() {
		super();
		
		numOfPointslbl = new Label("Ansage Punkte (0-157):");
		
		//https://o7planning.org/de/11185/anleitung-javafx-spinner
		numOfPoints = new Spinner<Integer>();
		
		final int initialValue = 75;
		 
        //sets the range of the spinner
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 157, initialValue);
 
        numOfPoints.setValueFactory(valueFactory);
        numOfPoints.setEditable(true);
        
        Region spacer1 = new Region();
		spacer1.setPrefHeight(10);
		
		Region spacer2 = new Region();
		spacer2.setPrefHeight(10);
		
		okBtn = new Button("Ansagen");
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(numOfPointslbl, spacer1,
				numOfPoints, spacer2, okBtn);

	}

}
