package client;

import java.util.ArrayList;
import Commons.*;
import Soundmodule.SoundModule;
import Soundmodule.SoundSettingsView;
import client.CommunicationThread.Status;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	protected SoundModule soundModule;
	
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
		soundModule.playBackgroundSound();
		view.showLobbyView(stage);
		
		view.lobbyView.gameMenu.exit.setOnAction(event -> {
			processExit(event, stage);
		});
		
		view.lobbyView.gameMenu.karten.setOnAction(event -> {
			processCardStyle();
		});
		
		view.lobbyView.gameMenu.sound.setOnAction(event -> {
			processSoundSettings();
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
				System.out.println(numOfRounds);
				
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
		soundModule.pauseBackgroundSound();
		soundModule.playWaitingSound();
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
			soundModule.pauseWaitingSound();
			splashScreen.stop();
			startLobby(stage);
			model.processAbbruch();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void processExit(Event event, Stage stage) {
			stage.close();
			if(model.connection != null && model.connection.isAlive()) {
				model.closeConnection();
			}	
	}
	
	/**
	 * @author Luca Meyer
	 * process to change the CardStyle, creates a new Window with the option,
	 * takes the selected Radiobutton and changes the cardStyle-Number in the model
	 * sends the new cardStyle to the infoview and re-updates the Cardarea
	 */
	public void processCardStyle() {
		int cardStyle=ClientModel.cardStyle;
		CardStyleView cardStyleView = new CardStyleView();
		cardStyleView.setSelectedStyle(cardStyle);
		
		Scene scene2 = new Scene(cardStyleView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.initOwner(stage);
		stage2.show();
		cardStyleView.confirmBtn.setOnAction(event -> {
			ClientModel.cardStyle = cardStyleView.getSelectedRadio();
			if(infoViewController != null) {
				infoViewController.model.setcardStyle(ClientModel.cardStyle);
			}
			stage2.close();
			
			if(!model.getActualHand().isEmpty()) {
				view.gameView.cardArea.updateCardStyle(model.getActualHand());
			}
			if(view.gameView != null && view.gameView.centerView != null) {
				view.gameView.centerView.updateCard();
			}
		});
		
	}
	
	private void processSoundSettings() {
	SoundSettingsView soundSettingsView = new SoundSettingsView(soundModule);
	soundSettingsView.show();		
	}
	/**
	 * @author Luca Meyer
	 * Creates a new Window and creates a RuleView in it
	 */
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
	 * Starts the Game;
	 */
	public void startGame() {
		soundModule.pauseWaitingSound();
		soundModule.playBackgroundSound();
		try {
			stage.setTitle("Player: "+model.user);
			this.infoViewController = new InfoViewController(model.getCurrentGame(), ClientModel.cardStyle, model.user);
			if(model.getCurrentGame().isSchieber()) {
				view.showGameView(stage, infoViewController.schView);
			} else {
				view.showGameView(stage, infoViewController.diffView);
			}
		
			view.gameView.gameMenu.karten.setOnAction(event -> {
				processCardStyle();
			});
			view.gameView.gameMenu.sound.setOnAction(event -> {
				processSoundSettings();
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
			view.gameView.chatBox.getSend().setOnAction(event ->{
				model.sendMessage(view.gameView.chatBox.getInput().getText());
				view.gameView.chatBox.getInput().setText("");
			});
							
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCardArea(ArrayList<Card> hand) {
			view.gameView.cardArea.setButtons(hand);
			view.gameView.cardArea.setCards(hand);
	}

	/**
	 * Called when the Client exits the game also called when the game is exited by another player
	 * @param event
	 */
	public void processExitGame(ActionEvent event) {
		soundModule.pauseBackgroundSound();
		stage.close();
		startLobby(stage);
		if(event != null)
			model.exitGame();
	}
	
	public void processSetTrumpf(boolean isGeschoben) {
		SelectTrumpfView selectTrumpfView = new SelectTrumpfView(isGeschoben);
		selectTrumpfView.userlbl.setText("Player: "+model.user);
		validateTrumpf = false;
	
		Scene scene2 = new Scene(selectTrumpfView);
		Stage stage2 = new Stage();
		
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL);
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
	
	/**
	 * @author Luca Meyer
	 * Methode to handle the yourTurnMessage. Shows a label to the player, and makes all valide Cards clickable
	 */
	public void processYourTurn(ArrayList<Card> validCards) {
		view.gameView.cardArea.infolbl.setText("Du bist am Zug!");
		//System.out.println("Controller Valide Karten: "+validCards); //TODO löschen
		//System.out.println("Controller actualHand: "+model.actualHand); //TODO löschen
		
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
	
	/**
	 * @author Luca Meyer
	 * Methode to process the played card, sends it to the model
	 */
	private void processPlayCard(Event event1, ArrayList<Card> validCards) {
		Button cardBtn = (Button) event1.getSource();
		boolean found = false;
		
		while(!found) {
			for(Card c: validCards) {
					
						if(cardBtn.getId().contains(c.getRank().toString()) && 
								cardBtn.getId().contains(c.getSuit().toString())) {
							found = true;
							//System.out.println("Controller Played Card: "+c); //TODO löschen
							
							//TODO Animation
							/**
							Path path = new Path();
							
							path.getElements().add(new MoveTo(view.gameView.centerView.getBoundsInParent().getHeight()/2, 
									view.gameView.centerView.getBoundsInParent().getWidth()/2));
							path.getElements().add(new LineTo(view.gameView.centerView.getBoundsInParent().getHeight()/2, 
									view.gameView.centerView.getBoundsInParent().getWidth()/2));
							
							PathTransition move = new PathTransition(Duration.seconds(1), path, (Button) event1.getSource());
							move.play();
							*/
							
							model.removeCard(c);
							model.playCard(c);
							//processTurn(c, model.user);
							view.gameView.cardArea.infolbl.setText("");
							
							
							updateCardArea(model.getActualHand());
							
							/**
							move.statusProperty().addListener(new ChangeListener<Animation.Status>() {
								
								public void changed(ObservableValue<? extends Animation.Status> observable, 
										Animation.Status oldValue, Animation.Status newValue) {
									System.out.println("Move old value: "+oldValue); //TODO löschen
									System.out.println("Move old value: "+newValue); //TODO löschen
									
									if(newValue == Animation.Status.STOPPED) {
										
											
									}else if(newValue == Animation.Status.RUNNING) {
										oldValue = null;
									}
								}
							});
							*/	
							break;
							
						}
					
				}
			}
		//setDisable all Cards
		for(Button b : view.gameView.cardArea.cardButtons) {
				b.setDisable(true);
		}
	}
	
	/**
	 * @author Luca Meyer
	 * creates a new Window to choose the points for differenzler and sends them to the model
	 * There is no check needed if letters are written. The getValue() only takes the first
	 * numbers until the first letter, if the number is higher than the max it takes
	 * the factoryValue
	 */
	public void processAnsagePoints() {
		
		AnsagePointsView ansagePointsView = new AnsagePointsView();
		
		Scene scene2 = new Scene(ansagePointsView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.initModality(Modality.APPLICATION_MODAL);
		stage2.initOwner(stage);
		stage2.show();
		
		
		ansagePointsView.okBtn.setOnAction(event-> {
			
			int ansagePoints = ansagePointsView.numOfPoints.getValue();
			model.sendAnsagePoints(ansagePoints);
			stage2.close();
		});
	}
	
	/**
	 * @author Luca Meyer
	 * Creates a view to show all possible Wiis and sends the selected to the model
	 * @param wiisArray
	 */
	public void processWiis(ArrayList<Wiis> wiisArray) {
		wiisReturn = new ArrayList<>();
		oneChecked = false;
		
		SelectWiisView selectWiisView = new SelectWiisView(wiisArray, ClientModel.cardStyle);
		selectWiisView.userlbl.setText("Player: " + model.user);
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
			for(Wiis w: wiisArray) {
				for(CheckBox c : selectWiisView.checkBoxes) {
						
						if(c.getId().contains(w.getBlatt().toString()) && 
								c.getId().contains(w.getHighestCard().toString())
								&& c.isSelected()) {
								
								wiisReturn.add(w);
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
	 * Shows the player who made the stich and removes all played cards from the center
	 */
	public void processStich(String player) {
		String stichInfo = player+" macht den Stich!";
		view.gameView.centerView.stichInfo.setText(stichInfo);
		
		for(Button b : view.gameView.centerView.centerButtons) {
			CardButton cardBtn = (CardButton) b;
			cardBtn.setVisible(false);
			cardBtn.setCard(null);
		}
		
	}

	/**
	 * @author Luca Meyer
	 * resets the text in the middle of the gamefield 
	 * and sends the played card to the setCard Method in the centerView
	 * @param card
	 * @param player
	 */
	public void processTurn(Card card, String player) {
		view.gameView.centerView.stichInfo.setText("");
		view.gameView.centerView.setCard(card, player);
	}
	
	/**
	 * @author Luca Meyer
	 * Process to set the right order of the players in the centerview
	 * and set the labels and ids
	 */
	public void processPlayers(ArrayList<String> players) {
		// ArrayList players is in gameorder of the players
		//Store Teams
		if(model.isGameTypeSchieber == true) {
			//Team1 player 1 & 3 of the gameorder
			model.teams.add(0, players.get(0));
			model.teams.add(1, players.get(2));
			//Team2 player 2 & 4 of the gameorder
			model.teams.add(2, players.get(1));
			model.teams.add(3, players.get(3));
			
		}else if(model.isGameTypeSchieber== false) {
			//Each player is a team
			model.teams.add(0, players.get(0));
			model.teams.add(1, players.get(1));
			model.teams.add(2, players.get(2));
			model.teams.add(3, players.get(3));
		}
		//Case1 this Player is position 0 in array
		if(players.get(0).contains(model.user)) {
			view.gameView.centerView.userlblCenterBottom.setText(players.get(0).toString());
			view.gameView.centerView.userlblRight.setText(players.get(1).toString());
			view.gameView.centerView.userlblCenterTop.setText(players.get(2).toString());
			view.gameView.centerView.userlblLeft.setText(players.get(3).toString());
			
			view.gameView.centerView.cardBtnCenterBottom.setId(players.get(0).toString());
			view.gameView.centerView.cardBtnRight.setId(players.get(1).toString());
			view.gameView.centerView.cardBtnCenterTop.setId(players.get(2).toString());
			view.gameView.centerView.cardBtnLeft.setId(players.get(3).toString());
		}
		//Case2 this Player is position 1 in array
		else if(players.get(1).contains(model.user)) {
			view.gameView.centerView.userlblCenterBottom.setText(players.get(1).toString());
			view.gameView.centerView.userlblRight.setText(players.get(2).toString());
			view.gameView.centerView.userlblCenterTop.setText(players.get(3).toString());
			view.gameView.centerView.userlblLeft.setText(players.get(0).toString());
			
			view.gameView.centerView.cardBtnCenterBottom.setId(players.get(1).toString());
			view.gameView.centerView.cardBtnRight.setId(players.get(2).toString());
			view.gameView.centerView.cardBtnCenterTop.setId(players.get(3).toString());
			view.gameView.centerView.cardBtnLeft.setId(players.get(0).toString());
			
		}
		//Case3 this Player is position 2 in array
		else if(players.get(2).contains(model.user)) {
			view.gameView.centerView.userlblCenterBottom.setText(players.get(2).toString());
			view.gameView.centerView.userlblRight.setText(players.get(3).toString());
			view.gameView.centerView.userlblCenterTop.setText(players.get(0).toString());
			view.gameView.centerView.userlblLeft.setText(players.get(1).toString());
			
			view.gameView.centerView.cardBtnCenterBottom.setId(players.get(2).toString());
			view.gameView.centerView.cardBtnRight.setId(players.get(3).toString());
			view.gameView.centerView.cardBtnCenterTop.setId(players.get(0).toString());
			view.gameView.centerView.cardBtnLeft.setId(players.get(1).toString());
			
		}
		//Case4 this Player is position 3 in array
		else if(players.get(3).contains(model.user)) {
			view.gameView.centerView.userlblCenterBottom.setText(players.get(3).toString());
			view.gameView.centerView.userlblRight.setText(players.get(0).toString());
			view.gameView.centerView.userlblCenterTop.setText(players.get(1).toString());
			view.gameView.centerView.userlblLeft.setText(players.get(2).toString());
			
			view.gameView.centerView.cardBtnCenterBottom.setId(players.get(3).toString());
			view.gameView.centerView.cardBtnRight.setId(players.get(0).toString());
			view.gameView.centerView.cardBtnCenterTop.setId(players.get(1).toString());
			view.gameView.centerView.cardBtnLeft.setId(players.get(2).toString());
		}
		
	}
	
	/**
	 * @author Luca Meyer
	 * 
	 */

	public void processEndResults(int winningTeamID, int pointsTeamI, int pointsTeamII, int pointsTeamIII,
			int pointsTeamIV) {
		view.gameView.centerView.stichInfo.setText("");
		//Remove all played Cards
		for(Button b : view.gameView.centerView.centerButtons) {
			CardButton cardBtn = (CardButton) b;
			cardBtn.setVisible(false);
			cardBtn.setCard(null);
		}
		String winInfo = "";
		if(model.isGameTypeSchieber==true) {
			if(winningTeamID == 1) {
				winInfo += model.teams.get(0)+ " und " +model.teams.get(1)+
				" bedanken sich. \n\n";
				winInfo += pointsTeamI+ " zu "+pointsTeamII;
				
			}else if(winningTeamID == 2) {
				winInfo += model.teams.get(2)+ " und " +model.teams.get(3)+
						" bedanken sich.";
				winInfo += pointsTeamII+ " zu "+pointsTeamI;
			}
		}else if(model.isGameTypeSchieber== false) {
			if(winningTeamID == 1) {
				winInfo += model.teams.get(0)+ " bedankt sich.";
				
			}else if(winningTeamID == 2) {
				winInfo += model.teams.get(0)+ " bedankt sich.";
				
			}else if(winningTeamID == 3) {
				winInfo += model.teams.get(0)+ " bedankt sich.";
				
			}else if(winningTeamID == 4) {
				winInfo += model.teams.get(0)+ " bedankt sich.";
				
			}
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText(winInfo);
			alert.initModality(Modality.APPLICATION_MODAL);
	        alert.initOwner(stage);
			alert.showAndWait();
		}
		
	}

}
