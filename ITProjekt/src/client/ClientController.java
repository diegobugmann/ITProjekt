package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.Card;
import Commons.Game;
import Commons.Validation_LoginProcess;
import javafx.event.ActionEvent;

import client.CommunicationThread.Status;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	protected WaitingScreen_Preloader splashScreen;
	protected boolean user;
	protected boolean pw;
	protected boolean cn;

	protected GameView gameView;

	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		
		
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
			model.loginProcess(view.loginView.userName.getText(), view.loginView.passwordField.getText());
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
		user = model.validateUserName(newValue);
		loginActivate();
	
	}
	
	private void validatePw(String newValue) {
		pw = model.validatePassword(newValue);
		loginActivate();
	
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
			/*TODO mach no schöne
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
			processKartenStyle();
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
		model.currentGame = g;
		int gameId = g.getGameId();
		model.joinGame(gameId);

	}
	
	public void joinGameApproved(Game game) {
		model.currentGame = game;
		try {
			startGame(); //TODO wieder lÃ¶schen
			startSplash();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

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
				startGame();
				// TODO wieder aktivieren
				//startSplash(e);
				
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
	}
	
	private void startSplash() throws Exception {
		splashScreen = new WaitingScreen_Preloader();
		view.lobbyView.stage.close();
		splashScreen.start(stage);
		
		splashScreen.abbruchBtn.setOnAction(event -> {
			processAbbruch(event);
		});
		
	
	}
	
	public void processAbbruch(Event e) {
		try {
			splashScreen.stop();
			startLobby(stage);
			model.updateGameList();
			
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
			model.connection.closeConnection();
			
	}
	

	public void processKartenStyle() {
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
		});

	}
	
	/*
	public void processSprache() {
		
	}
	*/
	
	public void processRegeln() {
		//TODO Regeln anzeigen
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
	 * Login is not accepted from Server, display Errormessage as popup and restart login page
	 * @param message
	 * @author mibe1
	 */
	public void loginfaild(String message) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Login failed");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		view.showLoginView(stage, model.ipAddress + ":" + model.port);
		
		//TODO login button wird nicht mehr freigegeben

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
				if(g.getGameId() == model.currentGame.getGameId()) {
					model.currentGame = g;
					numOfPlayers = g.getCurrentNumOfPlayers();
					this.splashScreen.updateAnzahlPers(numOfPlayers);
				}
			}
		}
		
	}
	

	public void createNewUserView() {
		Stage createNewUserStage = new Stage();
		createNewUserStage.initModality(Modality.NONE);
		view.showCreateNewUserView(createNewUserStage);
		view.createNewUserView.cancelbtn.setOnAction(event ->{
			createNewUserStage.close();
		});
		view.createNewUserView.createUserbtn.setOnAction(event ->{
			model.createUser();
		});
	}

	/**
	 * Start the Game;
	 */
	public void startGame() {
		try {
			//splashScreen.stop();
			view.showGameView(stage);
		
			view.gameView.gameMenu.statistik.setOnAction(event ->{
				processStatisitc();
			});
		
		
			view.gameView.gameMenu.karten.setOnAction(event -> {
				processKartenStyle();
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
		for(int i = 0; i<hand.size(); i++) {
			Card card = hand.get(i);
			gameView.cardArea.setCards(card);
		}
		
		
	}

	private void processExitGame(ActionEvent event, Stage stage2) {
		stage2.close();
		startLobby(stage);
		model.updateGameList();
	
	/**
	 * Start the Game;
	
	public void startGame() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Spielstart");
		alert.setHeaderText(null);
		alert.setContentText("Spiel gestartet");
		alert.showAndWait();
		*/
		/*try {
			splashScreen.stop();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	
	}
	
	

}
