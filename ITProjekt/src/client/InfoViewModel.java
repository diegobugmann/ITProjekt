package client;

import Commons.GameType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
	protected SimpleStringProperty popUp;
	//protected SimpleIntegerProperty numOfRoundsRemain; -> Diff
	//protected SimpleIntegerProperty numOfRoundsTotal; -> Diff
	
	public InfoViewModel(int goalPoints, int cardStyle) {
		this.goalPoints = new SimpleIntegerProperty();
		this.goalPoints.set(goalPoints);
		this.pointsOppo = new SimpleIntegerProperty();
		this.pointsOppo.set(0);
		this.pointsTeam = new SimpleIntegerProperty();
		this.pointsTeam.set(0);
		this.cardStyle = new SimpleIntegerProperty();
		this.cardStyle.set(cardStyle);
		this.trump = new SimpleIntegerProperty();
		this.popUp = new SimpleStringProperty();
	}
	
	public void setTrump(GameType currentTrump) {
		this.trump.set(currentTrump.ordinal());
		
	}
	
	public void setcardStyle(int cardStyle) {
		this.cardStyle.set(cardStyle);
		if(this.popUp != null) {
			if(this.cardStyle.get() == 0) {
				this.popUp.set(this.popUp.get().replaceAll("Schellen", "Kreuz"));
				this.popUp.set(this.popUp.get().replaceAll("Eicheln", "Egge"));
				this.popUp.set(this.popUp.get().replaceAll("Rosen", "Herz"));
				this.popUp.set(this.popUp.get().replaceAll("Schilten", "Schaufel"));
			}else {
				this.popUp.set(this.popUp.get().replaceAll("Kreuz", "Schellen"));
				this.popUp.set(this.popUp.get().replaceAll("Egge", "Eicheln"));
				this.popUp.set(this.popUp.get().replaceAll("Herz", "Rosen"));
				this.popUp.set(this.popUp.get().replaceAll("Schaufel", "Schilten"));
			}
		}
	}
	
}
