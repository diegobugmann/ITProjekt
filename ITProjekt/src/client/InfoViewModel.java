package client;

import Commons.GameType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

public class InfoViewModel {
	
	//protected GameType currentTrump;
	protected SimpleIntegerProperty cardStyle;
	//protected Label picTrumpf;
	//protected String cardStyle;
	
	protected SimpleIntegerProperty pointsTeam;
	protected SimpleIntegerProperty pointsOppo;
	protected final SimpleIntegerProperty goalPoints;
	protected SimpleIntegerProperty trump;
	//protected SimpleIntegerProperty numOfRoundsRemain; -> Diff
	//protected SimpleIntegerProperty numOfRoundsTotal; -> Diff
	
	public InfoViewModel(int goalPoints) {
		this.goalPoints = new SimpleIntegerProperty();
		this.goalPoints.set(goalPoints);
		this.pointsOppo = new SimpleIntegerProperty();
		this.pointsOppo.set(0);
		this.pointsTeam = new SimpleIntegerProperty();
		this.pointsTeam.set(0);
		this.cardStyle = new SimpleIntegerProperty();
		this.trump = new SimpleIntegerProperty();
	}
	
	public void setTrump(GameType currentTrump) {
		this.trump.set(currentTrump.ordinal());
		
	}
	
	public void setcardStyle(int cardStyle) {
		this.cardStyle.set(cardStyle);
	}
	
}
