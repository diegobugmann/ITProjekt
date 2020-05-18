package client;

import java.util.ArrayList;
import Commons.GameType;
import Commons.Message_Points;
import Commons.Message_WiisInfo;
import Commons.Wiis;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Luca Meyer
 * @author sarah
 *
 */

public class InfoViewModel {
	
	protected SimpleIntegerProperty cardStyle;
	
	protected SimpleIntegerProperty pointsTeam;
	protected SimpleIntegerProperty pointsOppo;
	protected int pointsTeamOld;
	protected int pointsOppoOld;
	
	protected final SimpleIntegerProperty goalPoints;
	
	protected SimpleStringProperty numOfRounds; 
	protected SimpleStringProperty points;
	protected int angesagtePoints = 0;
	protected int aktuellePoints = 0;
	protected SimpleIntegerProperty diffPoints;	
	protected final int numOfRoundsTotal;
	protected int numOfRoundsCurrent;
	
	protected SimpleObjectProperty<Integer> trump;
	protected SimpleStringProperty popUp;
	
	protected final String player;
	
	protected int rounds = 1;
	protected boolean isWiis = false;
	
	public InfoViewModel(int goalPoints, int cardStyle, int numOfRoundsTotal, String player) {
		this.player = player;
		this.goalPoints = new SimpleIntegerProperty();
		this.goalPoints.set(goalPoints);
		this.pointsOppo = new SimpleIntegerProperty();
		this.pointsOppo.set(0);
		this.pointsTeam = new SimpleIntegerProperty();
		this.pointsTeam.set(0);
		
		this.numOfRounds = new SimpleStringProperty();
		this.points = new SimpleStringProperty();
		this.diffPoints = new SimpleIntegerProperty();
		this.numOfRoundsTotal = numOfRoundsTotal;
		this.numOfRoundsCurrent = 1;
		this.numOfRounds.set(this.numOfRoundsCurrent + "/" + this.numOfRoundsTotal);
		
		
		this.cardStyle = new SimpleIntegerProperty();
		this.cardStyle.set(cardStyle);
		this.trump = new SimpleObjectProperty<>();
		this.popUp = new SimpleStringProperty();
		this.popUp.set("");
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
	//increments 1 on the int currentRound and updates the view
	public void incrementNumberOfRounds() {
		this.numOfRoundsCurrent++;
		this.numOfRounds.set(this.numOfRoundsCurrent + "/" + this.numOfRoundsTotal);
	}
	
	//Wird ev. benoetigt
	public void addInfoPopUp(String text) {
		if(this.popUp.get() == "") {
			popUp.set(text);
		}else {
			popUp.set(popUp.get() + "\n" + text);
		}
	}
	/**
	 * @author sarah
	 * @param msgWiisInfo
	 */
	public void processWiisInfo(Message_WiisInfo msgWiisInfo) {
		isWiis = true;
		String player1 = msgWiisInfo.getPlayerI();
		ArrayList<Wiis> wiisPlayer1 = new ArrayList<>(msgWiisInfo.getWiisPlayerI());

		String player2 = msgWiisInfo.getPlayerII();
		ArrayList<Wiis> wiisPlayer2 = new ArrayList<>();
		
		String content = "";
		if(player2 == null) {
			Wiis mvw = wiisPlayer1.get(0);
			for(Wiis w : wiisPlayer1) {
				if(mvw.compareTo(w) < 0) {
					mvw = w;					
				}
			}
			content += player1 +" sagt " + CardNameTranslator.getBlattName(mvw, cardStyle.get()) + " an.\n";
			addInfoPopUp(content);
		} else {
			wiisPlayer2 = msgWiisInfo.getWiisPlayerII();
			content = "";
			if(wiisPlayer1.size() > 0) {
				content = player1 +" weist:\n";
				
				for(Wiis w : wiisPlayer1) {
					if(w.getBlatt()== Wiis.Blatt.viergleiche) {
						content += CardNameTranslator.getBlattName(w, cardStyle.get()) + " von "+ 
								CardNameTranslator.getRankName(w, cardStyle.get()) +"\n";
					}else if(w.getBlatt()== Wiis.Blatt.vierNeuner || w.getBlatt()== Wiis.Blatt.vierBauern){
						content += CardNameTranslator.getBlattName(w, cardStyle.get());
					}else {			
					content += CardNameTranslator.getBlattName(w, cardStyle.get()) + " von "+ CardNameTranslator.getSuitName(w, cardStyle.get()) + " " + 
							CardNameTranslator.getRankName(w, cardStyle.get()) +"\n";
					}
				}	
			}
			if(wiisPlayer2.size() > 0) {
				content += player2 +" weist:\n";
				for(Wiis w : wiisPlayer2) {
					if(w.getBlatt()== Wiis.Blatt.viergleiche) {
						content += CardNameTranslator.getBlattName(w, cardStyle.get()) + " von "+ 
								CardNameTranslator.getRankName(w, cardStyle.get()) +"\n";
					}else if(w.getBlatt()== Wiis.Blatt.vierNeuner || w.getBlatt()== Wiis.Blatt.vierBauern){
						content += CardNameTranslator.getBlattName(w, cardStyle.get());
					}else {			
					content += CardNameTranslator.getBlattName(w, cardStyle.get()) + " von "+ CardNameTranslator.getSuitName(w, cardStyle.get()) + " " + 
							CardNameTranslator.getRankName(w, cardStyle.get()) +"\n";
					}
				}
			}			
			popUp.set(content);
		}
	}
	public void setDiffPoints() {
		if(this.aktuellePoints != 0) {
			this.diffPoints.set(Math.abs(this.angesagtePoints - this.aktuellePoints));
		}else {
			this.diffPoints.set(this.angesagtePoints);
		}
	}
	//TODO Aufruf der Methode nach Punkte ansage
	public void setAngesagtePoints(int points) {
		this.aktuellePoints = 0;
		this.angesagtePoints = points;
		this.points.set(this.aktuellePoints + "/" + this.angesagtePoints);
	}
	//TODO Aufruf der Methode nach Stich
	public void setAktuellePoints(int points) {
		this.aktuellePoints = points;
		this.points.set(this.aktuellePoints + "/" + this.angesagtePoints);
	}

	public void setPoints(Message_Points msgPoints) {
		//Change for Diego
		
		if(msgPoints.getPlayerI().equalsIgnoreCase(player) || msgPoints.getPlayerII().equalsIgnoreCase(player)) {
			this.pointsTeamOld = this.pointsTeam.get();
			this.addInfoPopUp("Punkte dein Team: " + (msgPoints.getPoints() - this.pointsTeamOld));
			this.pointsTeam.set(msgPoints.getPoints());
		}else {
			this.pointsOppoOld = this.pointsOppo.get();
			this.addInfoPopUp("Punkte gegnerisches Team: " + (msgPoints.getPoints() - this.pointsOppoOld));
			this.pointsOppo.set(msgPoints.getPoints());
		}
		
	}
	
	public void setStoeck(String playerName) {
		if(this.popUp.get() == "") {
			this.popUp.set(playerName + " hat Stöck gewiesen.");
		}else {
			this.popUp.set(this.popUp.get() + "\n" + playerName + " hat Stöck gewiesen.");
		}
	}

	public void updateInfoView() {
		if (this.rounds != 1 || !this.isWiis) {
			this.popUp.set("");
		}
		this.rounds++;
		if (this.rounds == 10) {
			this.isWiis = false;
			this.rounds = 1;
		}
	}
}
