package client;

import Commons.Game;

public class InfoViewController {
	
	protected InfoViewModel model; 
	protected SchieberInfoView schView;
	protected DifferenzlerInfoView diffView;
	
	public InfoViewController (Game game, int cardStyle, String player) {

		this.model = new InfoViewModel(game.getWinningPoints(), cardStyle, game.getNumOfRounds(), player);
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
			diffView.numOfRounds.textProperty().bind(model.numOfRounds);
			diffView.points.textProperty().bind(model.points);
			diffView.diffPoints.textProperty().bind(model.diffPoints.asString());
			model.points.addListener((observable, 
					oldValue, newValue)-> {model.setDiffPoints();});
			model.trump.addListener((observable, 
					oldValue, newValue)-> {diffView.updateTrump(model.trump.get(), model.cardStyle.get());});
				model.cardStyle.addListener((observable, 
						oldValue, newValue)-> {diffView.updateTrump(model.trump.get(), model.cardStyle.get());});
		}
	}
}
