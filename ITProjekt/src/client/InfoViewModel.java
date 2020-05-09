package client;

import java.util.ArrayList;
import Commons.GameType;
import Commons.Message_Points;
import Commons.Message_WiisInfo;
import Commons.Wiis;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InfoViewModel {
	
	protected SimpleIntegerProperty cardStyle;
	
	protected SimpleIntegerProperty pointsTeam;
	protected SimpleIntegerProperty pointsOppo;
	protected final SimpleIntegerProperty goalPoints;
	
	protected SimpleStringProperty numOfRounds; 
	protected SimpleStringProperty points;
	protected int angesagtePoints = 0;
	protected int aktuellePoints = 0;
	protected SimpleIntegerProperty diffPoints;	
	protected final int numOfRoundsTotal;
	protected int numOfRoundsCurrent;
	
	protected SimpleIntegerProperty trump;
	protected SimpleStringProperty popUp;
	
	protected final String player;
	
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
		this.trump = new SimpleIntegerProperty();
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
	public void setInfoPopUp(String text) {
		popUp.set(text);
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
	 * @author Luca Meyer (sarah: angepasst fuer infobox)
	 * @param msgWiisInfo
	 */
	public void processWiisInfo(Message_WiisInfo msgWiisInfo) {
		String player1 = msgWiisInfo.getPlayerI();
		ArrayList<Wiis> wiisPlayer1 = new ArrayList<>(msgWiisInfo.getWiisPlayerI());

		String player2 = msgWiisInfo.getPlayerII();
		ArrayList<Wiis> wiisPlayer2 = new ArrayList<>();
		
		if(player2 == null) {
			Wiis mvw = wiisPlayer1.get(0);
			for(Wiis w : wiisPlayer1) {
				if(mvw.compareTo(w) < 0) {
					mvw = w;
				}
			}						
			String content = "Player " + player1 +" sagt " + CardNameTranslator.getBlattName(mvw, cardStyle.get()) + " an.";
			if(popUp.get() == "") {
				popUp.set(content);
			}else {
				popUp.set(popUp.get() + "\n" + content);
			}
			
		} else {
			wiisPlayer2 = msgWiisInfo.getWiisPlayerII();
			String content = "";
			if(wiisPlayer1.size() > 0) {
				content = "Player " + player1 +" weist:\n";
				
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
				content += "Player " + player2 +" weist:\n";
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
		if(this.angesagtePoints != 0) {
			if(this.aktuellePoints != 0) {
				this.diffPoints.set(this.angesagtePoints - this.aktuellePoints);
			}else {
				this.diffPoints.set(this.angesagtePoints);
			}
		}else {
			this.diffPoints = null;
		}
	}
	//TODO Aufruf der Methode nach Punkte ansage
	public void setAngesagtePoints(int points) {
		this.angesagtePoints = points;
		this.points.set(this.aktuellePoints + "/" + this.angesagtePoints);
	}
	//TODO Aufruf der Methode nach Stich
	public void setAktuellePoints(int points) {
		this.aktuellePoints = points;
		this.points.set(this.aktuellePoints + "/" + this.angesagtePoints);
	}

	public void setPoints(Message_Points msgPoints) {
		if(msgPoints.getPlayerI().equalsIgnoreCase(player) || msgPoints.getPlayerII().equalsIgnoreCase(player)) {
			this.pointsTeam.set(this.pointsTeam.get() + msgPoints.getPoints());
		}else {
			this.pointsOppo.set(this.pointsOppo.get() + msgPoints.getPoints());
		}
		
	}
	
	public void setStoeck(String playerName) {
		if(this.popUp.get() == "") {
			this.popUp.set(playerName + " hat Stoeck gewiesen.");
		}else {
			this.popUp.set(this.popUp.get() + "\n" + playerName + " hat Stoeck gewiesen.");
		}
	}
}
