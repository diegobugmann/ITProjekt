package client;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SBDifferenzlerController {
	
	private ArrayList<SBDifferenzlerPlayerModel> playerModels;
	private ScoreboardDifferenzlerView view;
	private Stage stage2;
	
	public SBDifferenzlerController(Stage stage, ArrayList<String> players) {
		this.playerModels = new ArrayList<>();
		
		for(String player : players) {
			this.playerModels.add(new SBDifferenzlerPlayerModel(player));
		}
		
		this.view = new ScoreboardDifferenzlerView();
		
		Scene scene = new Scene(view);
		stage2 = new Stage();
		
		stage2.setScene(scene);
		stage2.setHeight(300);
		stage2.setWidth(600);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initOwner(stage);
		
		view.playbtn.setOnAction(event -> {
			stage2.close();
		});
		
		view.player1.playerName.textProperty().bind(playerModels.get(0).playerName);
		view.player1.angesagtePoints.textProperty().bind(playerModels.get(0).angesagtePoints.asString());
		view.player1.gemachtePoints.textProperty().bind(playerModels.get(0).gemachtePoints.asString());
		view.player1.differenzPoints.textProperty().bind(playerModels.get(0).differenzPoints.asString());
		view.player1.pointsTotal.textProperty().bind(playerModels.get(0).pointsTotal.asString());
		view.player2.playerName.textProperty().bind(playerModels.get(1).playerName);
		view.player2.angesagtePoints.textProperty().bind(playerModels.get(1).angesagtePoints.asString());
		view.player2.gemachtePoints.textProperty().bind(playerModels.get(1).gemachtePoints.asString());
		view.player2.differenzPoints.textProperty().bind(playerModels.get(1).differenzPoints.asString());
		view.player2.pointsTotal.textProperty().bind(playerModels.get(1).pointsTotal.asString());
		view.player3.playerName.textProperty().bind(playerModels.get(2).playerName);
		view.player3.angesagtePoints.textProperty().bind(playerModels.get(2).angesagtePoints.asString());
		view.player3.gemachtePoints.textProperty().bind(playerModels.get(2).gemachtePoints.asString());
		view.player3.differenzPoints.textProperty().bind(playerModels.get(2).differenzPoints.asString());
		view.player3.pointsTotal.textProperty().bind(playerModels.get(2).pointsTotal.asString());
		view.player4.playerName.textProperty().bind(playerModels.get(3).playerName);
		view.player4.angesagtePoints.textProperty().bind(playerModels.get(3).angesagtePoints.asString());
		view.player4.gemachtePoints.textProperty().bind(playerModels.get(3).gemachtePoints.asString());
		view.player4.differenzPoints.textProperty().bind(playerModels.get(3).differenzPoints.asString());
		view.player4.pointsTotal.textProperty().bind(playerModels.get(3).pointsTotal.asString());
		
	}
	
	public void showScoreboard() {
		stage2.show();
	}
	
	public void setPoints(String playerName, int ansage, int points, int difference, int differenceTotal) {
		for (SBDifferenzlerPlayerModel p : playerModels) {
			if (p.playerName.get().equalsIgnoreCase(playerName)) {
				p.setPoints(ansage, points, difference, differenceTotal);
			}
		}
	}

}
