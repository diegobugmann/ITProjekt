package client;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import Commons.Game;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientController {
	
	protected Stage stage;
	protected ClientModel model;
	protected ClientView view;
	protected WaitingScreen_Preloader splashScreen;
	protected boolean user;
	protected boolean pw;
	
	
	public ClientController(ClientModel model, ClientView view, Stage stage) {
		this.model = model;
		this.view = view;
		this.stage = stage;
		
		
		view.showLoginView(stage);
		
		view.loginView.userName.textProperty().addListener((observable, 
				oldValue, newValue)-> {validateUser(newValue);});
			
		view.loginView.passwordField.textProperty().addListener((observable,
				oldValue, newValue)-> {validatePw(newValue);});
		
			
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
			view.loginView.loginBtn.setOnAction(event -> {
				model.loginProcess(view.loginView.userName.getText(), view.loginView.passwordField.getText());
			});
				
		}
		
	};	
	
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
			processKarten();
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
			
	}
	
	public void processKarten() {
		
		
		
		int cardStyle=0;
		
		model.setCardStyle(cardStyle);
	}
	
	public void processSprache() {
		
	}
	
	public void processRegeln() {
		
	}
	
	public void processAbout() {
		
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
		view.showLoginView(stage);
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


}
