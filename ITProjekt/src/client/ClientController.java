package client;

import java.util.ArrayList;
import Commons.*;
import Soundmodule.SoundModule;
import client.CommunicationThread.Status;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Toggle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	protected CreateNewUserController createNewUserController;
	protected InfoViewController infoViewController;
	protected WaitingScreen_Preloader splashScreen;
	protected boolean validateTrumpf;
	protected boolean oneChecked = false;
	protected String actualTrumpf;
	protected ArrayList<Wiis> wiisReturn;
	protected Wiis wiisNew;
	protected GameView gameView;
	//Sounds
	private SoundModule soundModule;
	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		//Sounds
		soundModule = new SoundModule();
		
		view.showLoginView(stage, model.ipAddress + ":" + model.port);
		

		view.loginView.cnAddress.textProperty().addListener((observable, 
				oldValue, newValue)-> {cnActivate(newValue);});
		
		view.loginView.userName.textProperty().addListener((observable, 
				oldValue, newValue)-> {loginActivate();});
			
		view.loginView.passwordField.textProperty().addListener((observable,
				oldValue, newValue)-> {loginActivate();});
		
		view.loginView.cnBtn.setOnAction(event -> {
			connectionProcess();
		});
		
		view.loginView.loginBtn.setOnAction(event -> {
			model.loginProcess(view.loginView.userName.getText(), 
					view.loginView.passwordField.getText());
		});
		
		view.loginView.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ENTER && !view.loginView.loginBtn.isDisabled()) {
				view.loginView.loginBtn.fire();
				event.consume();
			}
		});
		
		view.loginView.newUserLink.setOnAction(event -> {
			createNewUserView();
		});
		
			
	}
	
	private void loginActivate() {		
		if(!view.loginView.userName.getText().isEmpty() && !view.loginView.passwordField.getText().isEmpty()) {
			view.loginView.loginBtn.setDisable(false);
		} else {
			view.loginView.loginBtn.setDisable(true);
		}
	};	
	
	/**@author sarah
	 * 
	 */
	private void cnActivate(String newValue) {		
		if(model.validateCnAdress(newValue)) {			
			view.loginView.cnBtn.setDisable(false);						
		} else {
			view.loginView.cnBtn.setDisable(true);
		}		
	}
	
	/**@author sarah
	 * connect to server
	 */
	private void connectionProcess() {
		if(model.connect(this)) {
			view.loginView.activateLoginFields();
			view.loginView.cnBtn.setOnAction(event -> {
				disconnectProcess();
			});
			view.loginView.toggleCnBtn();
		}else {
			view.loginView.deactivateLoginFields();

			/*TODO mach no schoene
			Alert
			*/			
		}
	}
	
	/**
	 * @author sarah
	 * disconnect from server
	 */
	private void disconnectProcess() {
		model.disconnect();
		view.loginView.cnBtn.setOnAction(event -> {
			connectionProcess();
		});
		view.loginView.toggleCnBtn();
		view.loginView.deactivateLoginFields();
		
	}
	
	/**
	 * @author Luca Meyer
	 * starts the Lobbyview and sets all the buttons on action
	 */
	private void startLobby(Stage stage) {
		this.stage = stage;
		view.showLobbyView(stage);
		
		view.lobbyView.gameMenu.statistik.setOnAction(event ->{
			processStatisitc();
		});
		view.lobbyView.gameMenu.exit.setOnAction(event -> {
			processExit(event, stage);
		});
		
		view.lobbyView.gameMenu.karten.setOnAction(event -> {
			processCardStyle();
		});
		
		view.lobbyView.gameMenu.regeln.setOnAction(event ->{
			processRegeln();
		});
		
		view.lobbyView.gameMenu.about.setOnAction(event ->{
			processAbout();
		});
		
		view.lobbyView.newBtn.setOnAction(event ->{
			createNewGame(event);
		});
		
		view.lobbyView.joinBtn.setOnAction(event -> {
			try {
				joinGame(event);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void joinGame(Event e) {
		Game g = this.view.lobbyView.gameList.getSelectedGame();
		model.setCurretnGame(g);
		int gameId = g.getGameId();
		model.joinGame(gameId);

	}
	
	public void joinGameApproved(Game game) {
		try {
			startSplash();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @author Luca Meyer
	 * Creates a view with the gamecontrols
	 * Changing the visibiliy of the controls, depending on the selcted GameType 
	 * @param wiisArray
	 */
	private void createNewGame(Event e) {
		
		stage.close();
		NewGameView newGameView = new NewGameView();
		
		Scene scene2 = new Scene(newGameView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL); /* *** */
		stage2.initOwner(stage);
		stage2.show();
		
		newGameView.rbSchieber.armedProperty().addListener((observable, 
				oldValue, newValue)-> {
					if(newValue) {
						newGameView.numOfRounds.setVisible(false);
						newGameView.numOfRoundslbl.setVisible(false);
						newGameView.rb1000.setVisible(true);
						newGameView.rb2500.setVisible(true);
						newGameView.pointslbl.setVisible(true);
					}
				});
		
		newGameView.rbDifferenzler.armedProperty().addListener((observable, 
				oldValue, newValue)-> {
					if(newValue) {
						newGameView.numOfRounds.setVisible(true);
						newGameView.numOfRoundslbl.setVisible(true);
						newGameView.rb1000.setVisible(false);
						newGameView.rb2500.setVisible(false);
						newGameView.pointslbl.setVisible(false);
					}
				});
		
		
		newGameView.okBtn.setOnAction(event -> {
			boolean isSchieber = true;
			boolean isGermanCards = false;
			int winningPoints = 1000;

			try {
				if(newGameView.rbDifferenzler.isSelected()) {
					isSchieber = false;	
				}
				if(ClientModel.cardStyle == 1) {
					isGermanCards = true;
				}
				if(newGameView.rb2500.isSelected()) {
					winningPoints = 2500;
				}
				int numOfRounds = newGameView.numOfRounds.getValue();
				
				model.newGame(isSchieber,isGermanCards,
						numOfRounds,winningPoints);
				stage2.close();
				startSplash();
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}
	
	/**
	 * @author Luca Meyer
	 * Creates splashscreen
	 */
	private void startSplash() throws Exception {
		splashScreen = new WaitingScreen_Preloader();
		view.lobbyView.stage.close();
		soundModule.playBackgroundSound();
		splashScreen.start(stage);
		
		splashScreen.abbruchBtn.setOnAction(event -> {
			processAbbruch(event);
		});	
	
	}
	
	/**
	 * Stops the Waiting Screen and tells the model to exit the current game
	 * @param e
	 */
	public void processAbbruch(Event e) {
		try {
			soundModule.pauseBackgroundSound();
			splashScreen.stop();
			startLobby(stage);
			model.processAbbruch();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void processStatisitc() {
		//TODO Statistik holen und anzeigen
	}
	
	public void processExit(Event event, Stage stage) {
			stage.close();
			if(model.connection != null && model.connection.isAlive()) {
				model.closeConnection();
			}	
	}
	
	public void processCardStyle() {
		int cardStyle=ClientModel.cardStyle;
		CardStyleView cardStyleView = new CardStyleView();
		cardStyleView.setSelectedStyle(cardStyle);
		infoViewController.model.setcardStyle(cardStyle);
		
		Scene scene2 = new Scene(cardStyleView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.show();
		cardStyleView.confirmBtn.setOnAction(event -> {
			ClientModel.cardStyle = cardStyleView.getSelectedRadio();
			stage2.close();
			
			if(model.getActualHand().isEmpty()) {
				//do nothing
			}else {
				updateCardArea(model.getActualHand());				
			}
		});
		
	}
	
	public void processRegeln() {
		RuleView ruleView = new RuleView();
	
		Scene scene2 = new Scene(ruleView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(700);
		stage2.setWidth(900);
		stage2.show();
	}
	
	public void processAbout() {
		String info = "CodingKittens \n"
				+ "Version V 1.0 \n"
				+ "IT-Projekt 2020";
		Alert alert = new Alert(AlertType.INFORMATION, info );
		alert.setHeaderText(null);
		alert.setTitle(null);
		alert.showAndWait();
	}
	
	/**
	 * @author mibe1
	 * When the login gets accepted the mainstage is shown
	 */
	public void loginaccepted() {
		startLobby(stage);
	}
	
/**
 	* Called when the Game list on the Serverlist gets changed and sent to the Client
 * @author mibe1 
 * @param games all games
 * @param status 
 */
	public void updateGamelist(ArrayList<Commons.Game> games, Status status) {
		if(status == Status.logedin) {
			this.view.lobbyView.gameList.setAllGames(games);
		}
		else if(status == Status.joinedgame) {
			int numOfPlayers = 1;
			for(Game g : games)
			{
				if(g.getGameId() == model.getCurrentGame().getGameId()) {
					model.setCurretnGame(g);
					numOfPlayers = g.getCurrentNumOfPlayers();
					this.splashScreen.updateAnzahlPers(numOfPlayers);
				}
			}
		}
		
	}
	
	/**
	 * @author sarah
	 */
	public void createNewUserView() {
		this.createNewUserController = new CreateNewUserController(model.connection);
	}
	
	/**
	 * Start the Game;
	 */
	public void startGame() {
		soundModule.pauseBackgroundSound();
		try {
			stage.setTitle("Player: "+model.user);
			this.infoViewController = new InfoViewController(model.getCurrentGame());
			if(model.getCurrentGame().isSchieber()) {
				view.showGameView(stage, infoViewController.schView);
			} else {
				view.showGameView(stage, infoViewController.diffView);
			}
			view.gameView.gameMenu.statistik.setOnAction(event ->{
				processStatisitc();
			});
		
			view.gameView.gameMenu.karten.setOnAction(event -> {
				processCardStyle();
			});
		
			view.gameView.gameMenu.regeln.setOnAction(event ->{
				processRegeln();
			});
		
			view.gameView.gameMenu.about.setOnAction(event ->{
				processAbout();
			});
		
			view.gameView.gameMenu.exit.setOnAction(event -> {
				processExitGame(event);
			});
							
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateCardArea(ArrayList<Card> hand) {
		view.gameView.cardArea.setCards(hand);
	}

	/**
	 * Called when the Client exits the game also called when the game is exited by another player
	 * @param event
	 */
	public void processExitGame(ActionEvent event) {
		stage.close();
		startLobby(stage);
		if(event != null)
			model.exitGame();
	}
	
	public void processSetTrumpf() {
		SelectTrumpfView selectTrumpfView = new SelectTrumpfView();
		selectTrumpfView.userlbl.setText("Player: "+model.user);
		validateTrumpf = false;
	
		Scene scene2 = new Scene(selectTrumpfView);
		Stage stage2 = new Stage();
		
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL); /* *** */
		stage2.initOwner(stage);
		stage2.show();
		
		selectTrumpfView.tg.selectedToggleProperty().addListener((observable, 
				oldValue, newValue)-> {
					validateTrumpf(newValue);
					if(validateTrumpf == true) {
						selectTrumpfView.confirmBtn.setDisable(false);
					}
				});
		
			
		selectTrumpfView.confirmBtn.setOnAction(event -> {
			GameType gameType = selectTrumpfView.getSelectedTrumpf();
			model.setTrumpf(gameType);
			actualTrumpf = gameType.toString();
			
			stage2.close();
			});
			
	}
	
	private void validateTrumpf(Toggle newValue) {
		if(newValue.isSelected()) {
			validateTrumpf = true;
		}
	}
	
	public void processYourTurn(ArrayList<Card> validCards) {
		view.gameView.cardArea.infolbl.setText("Du bist am Zug!");
		System.out.println("Controller Valide Karten: "+validCards);
		
		//only valide Cards made clickable
		for(Card c: validCards) {
			for(Button b : view.gameView.cardArea.cardButtons) {
				
				if(b.getId().contains(c.getRank().toString()) && 
						b.getId().contains(c.getSuit().toString())) {
					b.setDisable(false);
				}
			}
			
		}
		
		for(Button b : view.gameView.cardArea.cardButtons) {
			b.setOnAction(event ->{
				processPlayCard(event, validCards);
			});
		}
		
	}
	
	private void processPlayCard(Event e, ArrayList<Card> validCards) {
		Button cardBtn = (Button) e.getSource();
		boolean found = false;
		
		while(!found) {
			for(Card c: validCards) {
					
						if(cardBtn.getId().contains(c.getRank().toString()) && 
								cardBtn.getId().contains(c.getSuit().toString())) {
							found = true;
							System.out.println("Controller Played Card: "+c);
							
							//TODO Animation
							/**
							Path path = new Path();
							
							path.getElements().add(new MoveTo(0, 0));
							path.getElements().add(new LineTo(0, 0));
							
							PathTransition move = new PathTransition(Duration.seconds(1), path, cardBtn);
							move.play();
							*/
							
							model.playCard(c);
							
							
							view.gameView.cardArea.infolbl.setText("");
							model.actualHand.remove(c);
							
							updateCardArea(model.getActualHand());
							break;
							
						}
					
				}
			}
		//setDisable all Cards
		for(Button b : view.gameView.cardArea.cardButtons) {
				b.setDisable(true);
		}
	}
	
	public void processAnsagePoints() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @author Luca Meyer
	 * Creates a view to show all possible Wiis and sends the selected to the model
	 * @param wiisArray
	 */
	public void processWiis(ArrayList<Wiis> wiisArray) {
		wiisReturn = new ArrayList<>();
		oneChecked = false;
		
		SelectWiisView selectWiisView = new SelectWiisView(wiisArray);
		selectWiisView.userlbl.setText("Player: " +model.user);
		Scene scene2 = new Scene(selectWiisView);
		Stage stage2 = new Stage();
		
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL); /* *** */
        stage2.initOwner(stage);
		stage2.show();
		
		selectWiisView.confirmBtn.setOnAction(event -> {
			
			boolean found = false;
			
			while(!found) {
				for(Wiis w: wiisArray) {
					for(CheckBox c : selectWiisView.checkBoxes) {
						
							if(c.getId().contains(w.getBlatt().toString()) && 
									c.getId().contains(w.getHighestCard().toString())) {
								found = true;
								wiisReturn.add(w);
								}
								
							}
						
					}
				}
			model.sendWiis(wiisReturn);
			stage2.close();
			
		});
	}
	
	/**
	 * @author mibe1
	 * @param title
	 * @param message
	 * Shows an alterbox of the type Error to tell the user about an error messae or an exception
	 */
	public void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
		alert.showAndWait();
	}
	
	/**
	 * @author Luca Meyer
	 * @param msgWiisInfo
	 */
	public void processWiisInfo(Message_WiisInfo msgWiisInfo) {
		String player1 = msgWiisInfo.getPlayerI();
		ArrayList<Wiis> wiisPlayer1 = new ArrayList<>(msgWiisInfo.getWiisPlayerI());

		String player2 = msgWiisInfo.getPlayerII();
		ArrayList<Wiis> wiisPlayer2 = new ArrayList<>();
		
		if(player2 != null) {
			wiisPlayer2 = msgWiisInfo.getWiisPlayerII();
			
		}
		
		String content = "Player " +player1+" weist:\n";
		
		for(Wiis w : wiisPlayer1) {
			content += w.toString()+"\n"; 
		}
		
		
		if(player2 != null) {
			content += "Player "+player2+" weist:\n";
			for(Wiis w : wiisPlayer2) {
				content += w.toString()+"\n"; 
			}
		}
		
		Alert wiisInfo = new Alert(AlertType.INFORMATION);
		wiisInfo.setTitle(null);
		wiisInfo.setHeaderText(null);
		wiisInfo.setContentText(content);
		wiisInfo.initModality(Modality.APPLICATION_MODAL);
        wiisInfo.initOwner(stage);
		wiisInfo.showAndWait();
	}
	
	public void processStich(Message_Stich msgStich) {
		// TODO Auto-generated method stub
		
	}

}
