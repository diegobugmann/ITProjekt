package client;

import java.util.ArrayList;

import Commons.Game;
import Commons.Message_WiisInfo;
import Commons.Wiis;

public class InfoViewController {
	
	protected InfoViewModel model; 
	protected SchieberInfoView schView;
	protected DifferenzlerInfoView diffView;
	
	
	public InfoViewController (Game game, int cardStyle) {
		
		//TODO WInning Points
		this.model = new InfoViewModel(game.getWinningPoints(), cardStyle);
		if (game.isSchieber()) {
			this.schView = new SchieberInfoView();
			schView.goalPoints.textProperty().bind(model.goalPoints.asString());
			schView.pointsOppo.textProperty().bind(model.pointsOppo.asString());
			schView.pointsTeam.textProperty().bind(model.pointsTeam.asString());
			schView.popUp.textProperty().bind(model.popUp);
			model.trump.addListener((observable, 
				oldValue, newValue)-> {schView.updateTrump(model.trump.get(), model.cardStyle.get());});
			model.cardStyle.addListener((observable, 
					oldValue, newValue)-> {schView.updateTrump(model.trump.get(), model.cardStyle.get());});
		} else {
			this.diffView = new DifferenzlerInfoView();
		}
	}
	
	//Wird ev. benoetigt
	public void setInfoPopUp(String text) {
		model.popUp.set(text);
	}
	//Wird ev. benoetigt
	public void addInfoPopUp(String text) {
		model.popUp.set(schView.popUp.getText() + "\n" + text);
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
			String content = "Player " + player1 +" sagt " + getBlattName(mvw) + " an.";
			model.popUp.set(model.popUp.get() + "\n" + content);
		} else {
			wiisPlayer2 = msgWiisInfo.getWiisPlayerII();
			
			String content = "Player " + player1 +" weist:\n";
			
			for(Wiis w : wiisPlayer1) {
				content += getBlattName(w) + " von "+ getSuitName(w, model.cardStyle.get()) + " " + w.getHighestCard().getRank() +"\n"; 
			}	
			
			if(wiisPlayer2.size() > 0) {
				content += "Player " + player2 +" weist:\n";
				for(Wiis w : wiisPlayer2) {
					content += getBlattName(w) + " von "+ getSuitName(w, model.cardStyle.get()) + " " + w.getHighestCard().getRank() + "\n"; 
				}
			}			
			model.popUp.set(content);
		}
	}
	
	private String getBlattName(Wiis w) {
		String blattName = "";
		switch (w.getBlatt()){
			case dreiblatt :{
				blattName = "Dreiblatt";
				break; 
			}
			case vierblatt :{
				blattName = "Vierblatt";
				break; 
			}
			case fuenfblatt :{
				blattName = "Fuenfblatt";
				break; 
			}
			case sechsblatt :{
				blattName = "Sechsblatt";
				break; 
			}
			case siebenblatt :{
				blattName = "Siebenblatt";
				break; 
			}
			case achtblatt :{
				blattName = "Achtblatt";
				break; 
			}
			case neunblatt :{
				blattName = "Neunblatt";
				break; 
			}
			case viergleiche :{
				blattName = "Vier Gleiche";
				break;
			}
			case vierNeuner :{
				blattName = "Vier Neuner";
				break;
			}
			case vierBauern :{
				blattName = "Vier Bauern";
				break;
			}
		}
		return blattName;
	}
	
	private String getSuitName(Wiis w, int isDeutsch) {
		String suitName = "";
		if(isDeutsch == 1) {
			switch (w.getHighestCard().getSuit()){
				case BellsOrClubs :{
					suitName = "Schellen";
					break; 
				}
				case AcornsOrDiamonds :{
					suitName = "Eicheln";
					break; 
				}
				case RosesOrHearts :{
					suitName = "Rosen";
					break; 
				}
				case ShieldsOrSpades :{
					suitName = "Schilten";
					break; 
				}
			}
		} else {
			switch (w.getHighestCard().getSuit()){
			case BellsOrClubs :{
				suitName = "Kreuz";
				break; 
			}
			case AcornsOrDiamonds :{
				suitName = "Egge";
				break; 
			}
			case RosesOrHearts :{
				suitName = "Herz";
				break; 
			}
			case ShieldsOrSpades :{
				suitName = "Schaufel";
				break; 
			}
		}
		}
		return suitName;
	}

}
