package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.Game;
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
			createNewUser();
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
			/*TODO mach no schön i de View
			Alert
			*/			
		}
	}
	/**
	 * @author sarah
	 * disconnetct from server
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
		view.lobbyView.gameMenu.sprache.setOnAction(event ->{
			processSprache();
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
		int gameId = g.getGameId();
		model.joinGame(gameId);
		try {
			startSplash(e);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void createNewGame(Event e) {
		
		try {
			//TODO read userinput and set as parameters for new game 
			model.newGame(true,true,100,100);
			startSplash(e);
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void startSplash(Event e) throws Exception {
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
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void processStatisitc() {
		
	}
	
	public void processExit(Event event, Stage stage) {
		//Verbindung schliessen
			stage.close();
			
	}
	
	public void processKartenStyle() {
		int cardStyle=model.getCardStyle();
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
			model.setCardStyle(cardStyleView.getSelectedRadio());
			stage2.close();
		});

		
		
	}
	
	
	public void processSprache() {
		
	}
	
	public void processRegeln() {
		
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
		JOptionPane.showMessageDialog(null, message, "InfoBox: Login faild from Server", JOptionPane.ERROR_MESSAGE);
		view.showLoginView(stage, model.ipAddress + ":" + model.port);
	}
/**
 * Called when the Game list on the Serverlist gets changed and sent to the Client
 * @author mibe1 
 * @param games all games
 */
	public void updateGamelist(ArrayList<? extends Game> games) {
		//Testcode as long as not implemented
		for(Game g : games){
			System.out.println(g);
		}
		//TODO update game list with the new game list from the server
		
	}
	
	
	/**@author sarah
	 * create new user
	 */
	public void createNewUser() {
		Stage createNewUserStage = new Stage();
		createNewUserStage.initModality(Modality.NONE);
		view.showCreateNewUserView(createNewUserStage);
		view.createNewUserView.cancelbtn.setOnAction(event ->{
			createNewUserStage.close();
		});
	}


}
