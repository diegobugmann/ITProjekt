package client;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SBDifferenzlerPlayerModel {
	
	protected final SimpleStringProperty playerName;
	protected SimpleIntegerProperty angesagtePoints;
	protected SimpleIntegerProperty gemachtePoints;
	protected SimpleIntegerProperty differenzPoints;
	protected SimpleIntegerProperty pointsTotal;
	
	public SBDifferenzlerPlayerModel(String playerName) {
		this.playerName = new SimpleStringProperty();
		this.playerName.set(playerName);
		this.angesagtePoints = new SimpleIntegerProperty();
		this.gemachtePoints = new SimpleIntegerProperty();
		this.differenzPoints = new SimpleIntegerProperty();
		this.pointsTotal = new SimpleIntegerProperty();
	}

}
