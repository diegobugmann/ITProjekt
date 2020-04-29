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
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	protected WaitingScreen_Preloader splashScreen;
	protected boolean user;
	protected boolean pw;
	protected boolean cn;
	protected boolean validateTrumpf;
	protected boolean oneChecked = false;
	protected String actualTrumpf;
	protected ArrayList<Wiis> wiisReturn;
	protected Wiis wiisNew;
	private SoundModule soundModule;

	protected GameView gameView;

	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		soundModule = new SoundModule();
		
		view.showLoginView(stage, model.ipAddress + ":" + model.port);
		

		view.loginView.cnAddress.textProperty().addListener((observable, 
				oldValue, newValue)-> {validateCn(newValue);});
		
		view.loginView.userName.textProperty().addListener((observable, 
				oldValue, newValue)-> {validateUser(newValue);});
			
		view.loginView.passwordField.textProperty().addListener((observable,
				oldValue, newValue)-> {validatePw(newValue);});
		
		view.loginView.cnBtn.setOnAction(event -> {
			connectionProcess();
		});
		
		view.loginView.loginBtn.setOnAction(event -> {
			model.loginProcess(view.loginView.userName.getText(), 
					view.loginView.passwordField.getText());
		});
		
		view.loginView.newUserLink.setOnAction(event -> {
			createNewUserView();
		});
		
			
	}
	/**
	 * @author sarah 
	 * @param newValue
	 */
	private void validateCn(String newValue) {
		cn = model.validateCnAdress(newValue);
		cnActivate();
		
	}

	private void validateUser(String newValue) {
		
		if (!newValue.isEmpty()) {
			user = true;
			loginActivate();
		}
	
	}
	
	private void validatePw(String newValue) {
		if(!newValue.isEmpty()) {
			pw = true;
			loginActivate();
		}
	
	}
	
	private void loginActivate() {
		
		if(user && pw) {
			view.loginView.loginBtn.setDisable(false);			
				
		} else {
			view.loginView.loginBtn.setDisable(true);
		}
		
	};	
	
	/**@author sarah
	 * 
	 */
	private void cnActivate() {
		
		if(cn) {			
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
	 * Creates Window to select game options
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
		stage2.show();
		
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
			model.closeConnection();
			
	}
	

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
		stage2.show();
		cardStyleView.confirmBtn.setOnAction(event -> {
			ClientModel.cardStyle = cardStyleView.getSelectedRadio();
			stage2.close();
			
			if(model.getActualHand().isEmpty()) {
				//do nothing
			}else {
				this.updateCardArea(model.getActualHand());
				view.gameView.infoView.setTrumpf(actualTrumpf);
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
	 * Most likely useless code just here as a backup
	 * Login is not accepted from Server, display Errormessage as popup and restart login page
	 * @param message
	 * @author mibe1
	 */
	/*public void loginfaild(String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Login failed");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		// Sarah: Stage wird nicht mehr neu geladen, Problem mit Login button somit geloest
	}*/
	/**
	 * @author sarah
	 */
	public void userNameisAvaiable(boolean isUserNameAvaiable) {
		model.setisUserNameAvaiable(isUserNameAvaiable);
		this.view.createNewUserView.setUserNameAvaiable(isUserNameAvaiable);
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
		Stage createNewUserStage = new Stage();
		createNewUserStage.initModality(Modality.NONE);
		view.showCreateNewUserView(createNewUserStage);
		view.createNewUserView.newUserNametxt.textProperty().addListener((observable, 
				oldValue, newValue)-> {model.validateUsername(newValue);activateNewUserbtn();});
		view.createNewUserView.newPasswordtxt.textProperty().addListener((observable,
				oldValue, newValue)-> {model.validatePassword(newValue); model.confirmPw(newValue, view.createNewUserView.confirmPasswordtxt.getText());activateNewUserbtn();});
		view.createNewUserView.confirmPasswordtxt.textProperty().addListener((observable,
				oldValue, newValue)-> {model.confirmPw(newValue, view.createNewUserView.newPasswordtxt.getText());activateNewUserbtn();});
		
		view.createNewUserView.cancelbtn.setOnAction(event ->{
			createNewUserStage.close();
		});
		view.createNewUserView.createUserbtn.setOnAction(event ->{
			model.createUser(view.createNewUserView.newUserNametxt.getText(), view.createNewUserView.newPasswordtxt.getText());
		});
	}
	/**
	 * @author sarah
	 */
	public void activateNewUserbtn() {
		if(model.isNewPasswordValid && model.isNewUserNameAvailable && model.isPasswordConfirmed) {
			view.createNewUserView.activateNewUserbtn(true);
		}else {
			view.createNewUserView.activateNewUserbtn(false);
		}
	}
	/**
	 * @author sarah
	 */
	public void registrationSucceded() {
		String info = "Congratulations! User successfully created";
		Alert alert = new Alert(AlertType.INFORMATION, info );
		alert.setHeaderText(null);
		alert.setTitle(null);
		alert.showAndWait();
		view.createNewUserView.stage.close();
	}
	
	/**
	 * @author sarah
	 */
	public void registerFailed(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Registration failed");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}

	/**
	 * Start the Game;
	 */
	public void startGame() {
		try {
			stage.setTitle("");
			view.showGameView(stage);
		
			view.gameView.gameMenu.statistik.setOnAction(event ->{
				processStatisitc();
			});
		
			view.gameView.gameMenu.karten.setOnAction(event -> {
				processCardStyle();
			});
			/*gameView.gameMenu.sprache.setOnAction(event ->{
				processSprache();
			});*/
		
			view.gameView.gameMenu.regeln.setOnAction(event ->{
				processRegeln();
			});
		
			view.gameView.gameMenu.about.setOnAction(event ->{
				processAbout();
			});
		
			view.gameView.gameMenu.exit.setOnAction(event -> {
				processExitGame(event, stage);
			});
						
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateCardArea(ArrayList<Card> hand) {
		model.setActualHand(hand);
		view.gameView.cardArea.setCards(hand);	
		
	}

	private void processExitGame(ActionEvent event, Stage stage2) {
		stage2.close();
		startLobby(stage);
		model.exitGame();
		
	
	}
	
	public void processSetTrumpf() {
		SelectTrumpfView selectTrumpfView = new SelectTrumpfView();
		validateTrumpf = false;
	
		Scene scene2 = new Scene(selectTrumpfView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
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
			//processWiis(null); //TODO wieder löschen
			});
			
	}
	
	private void validateTrumpf(Toggle newValue) {
		if(newValue.isSelected()) {
			validateTrumpf = true;
		}
	}
	
	public void processYourTurn() {
		// TODO Auto-generated method stub
		view.gameView.cardArea.infolbl.setText("Du bist am Zug!");
		
		for(Button b : view.gameView.cardArea.cardButtons) {
			b.setDisable(false);
			b.setOnAction(event ->{
				processPlayCard(event);
			});
		}
		
	}
	
	private void processPlayCard(Event e) {
		Button cardBtn = (Button) e.getSource();
		String cardName = cardBtn.getId();

		
		boolean found = false;
		int i = 0;
		while(i< model.actualHand.size()-1 && found == false) {
			if(model.actualHand.get(i).toString().contains(cardName)) {
				found = true;
				
				Path path = new Path();
				
				path.getElements().add(new MoveTo(150, 150));
				
				PathTransition move = new PathTransition(Duration.seconds(1), path, (Button) e.getSource());
				move.play();
			}else {
				i++;
			}
		}
		Card card = model.actualHand.get(i);
		//model.actualHand.remove(index);
		model.playCard(card);
			
	}
	
	public void processAnsagePoints() {
		// TODO Auto-generated method stub
		
	}
	public void processWiis(ArrayList<Wiis> wiisArray) {
		
		wiisReturn=null;
		oneChecked = false;
		SelectWiisView selectWiisView = new SelectWiisView(wiisArray);
	
		Scene scene2 = new Scene(selectWiisView);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setHeight(300);
		stage2.setWidth(300);
		stage2.initStyle(StageStyle.UNDECORATED);
		stage2.show();
		
		for(CheckBox c : selectWiisView.checkBoxes) {
			c.armedProperty().addListener((observable, 
				oldValue, newValue)-> {
					
					if(newValue == true) {
						selectWiisView.confirmBtn.setDisable(false);
					}
					
					//TODO muss wenn wieder alle abgewählt sind wieder deaktiviert werden
					
				});
		}
		
		selectWiisView.confirmBtn.setOnAction(event -> {
			int i = 0;
			
			for(CheckBox c : selectWiisView.checkBoxes) {
				if(c.isArmed()) {
					wiisReturn.add(wiisArray.get(i));
					i++;
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
		alert.showAndWait();
	}

}
