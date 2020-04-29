package client;

import Commons.GameType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public class InfoViewModel {
	
	protected GameType currentTrump;
	protected int cardStyle;
	//protected Label picTrumpf;
	//protected String cardStyle;
	
	protected SimpleIntegerProperty pointsTeam;
	protected SimpleIntegerProperty pointsOppo;
	protected SimpleIntegerProperty goalPoints;
	protected SimpleIntegerProperty numOfRoundsRemain;
	protected SimpleIntegerProperty numOfRoundsTotal;
	
	public InfoViewModel(int goalPoints) {
		this.goalPoints = new SimpleIntegerProperty();
		this.goalPoints.set(goalPoints);
		this.pointsOppo = new SimpleIntegerProperty();
		this.pointsOppo.set(0);
		this.pointsTeam = new SimpleIntegerProperty();
		this.pointsTeam.set(0);
	}
	
	public void setTrumpf(GameType currentTrump) {
		this.currentTrump = currentTrump;			
	}
	
	public void setcardStyle(int cardStyle) {
		this.cardStyle = cardStyle;
	}
	
}
