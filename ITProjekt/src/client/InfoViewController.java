package client;

import Commons.Game;

public class InfoViewController {
	
	protected InfoViewModel model; 
	protected SchieberInfoView schView;
	protected DifferenzlerInfoView diffView;
	
	
	public InfoViewController (Game game) {
		
		//TODO WInning Points
		this.model = new InfoViewModel(game.getWinningPoints());
		if (game.isSchieber()) {
			this.schView = new SchieberInfoView();
			schView.goalPoints.textProperty().bind(model.goalPoints.asString());
			schView.pointsOppo.textProperty().bind(model.pointsOppo.asString());
			schView.pointsTeam.textProperty().bind(model.pointsTeam.asString());
			model.trump.addListener((observable, 
				oldValue, newValue)-> {schView.updateTrump((int) newValue, model.cardStyle.get());});
			model.cardStyle.addListener((observable, 
					oldValue, newValue)-> {schView.updateTrump(model.trump.get(), (int) newValue);});
		} else {
			this.diffView = new DifferenzlerInfoView();
		}
	}
	
	public void setInfoPopUp(String text) {
		schView.popUp.setText(text);
	}

}
