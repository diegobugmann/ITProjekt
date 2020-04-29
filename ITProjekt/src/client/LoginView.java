package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LoginView extends VBox{
	
	protected Label cnlbl;
	protected TextField cnAddress;
	protected Button cnBtn;
	protected Hyperlink newUserLink;
	
	protected TextField userName;
	protected PasswordField passwordField;
	protected Label textlbl;
	protected Label pwlbl;
	protected Button loginBtn;
	protected Stage stage;
	

	
	
	public LoginView(Stage stage, String address) {
		
		this.stage = stage;
		
		/**
		 * @author luca: Ganze View, sarah: cn und newUserLink 
		 */
		cnlbl = new Label("IP/Port");
		cnAddress = new TextField(address);
		cnAddress.setMaxWidth(300);
		cnBtn = new Button("Connect");
		
		
		newUserLink = new Hyperlink("create new user");
		newUserLink.setDisable(true);
		
		
		textlbl = new Label("Username:");
		userName = new TextField();
		userName.setMaxWidth(300);
		userName.setDisable(true);
		
		
		pwlbl = new Label("Passwort erstellen:");
		passwordField = new PasswordField();
		passwordField.setMaxWidth(300);
		passwordField.setDisable(true);
		
		loginBtn = new Button("Login");
		loginBtn.setDisable(true);
		
		
		Region region0 = new Region();
		region0.setPrefHeight(10);
		
		Region region1 = new Region();
		region1.setPrefHeight(30);
		
		Region region2 = new Region();
		region2.setPrefHeight(50);
		
		Region region3 = new Region();
		region3.setPrefHeight(20);
		
		Region region4 = new Region();
		region4.setPrefHeight(10);
		
		Image image = new Image(LobbyView.class.getResourceAsStream("Bilder/Lobby.jpg"));
		BackgroundSize backSize = new BackgroundSize(800, 800, false, false, false, false);
		Background background = new Background(new BackgroundImage(image, null, null, BackgroundPosition.CENTER, backSize));
		
		this.setBackground(background);
		
		this.getChildren().addAll(region0,cnlbl, cnAddress, cnBtn, region1, textlbl, userName, region2, pwlbl, passwordField, region3, loginBtn, newUserLink);
		
		this.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(this);
		/* scene.getStylesheets().add(getClass().getResource("").toExternalForm()); */
		
		stage.setFullScreen(false);
		
		stage.setHeight(800);
		stage.setWidth(800);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public TextField getCnField() {
		return cnAddress;
	}
	
	public void setCnAdress(TextField cnAdress) {
		this.cnAddress = cnAdress;
	}
	
	public Button getCnBtn() {
		return cnBtn;
	}
	
	public void setCnBtn (Button cnBtn) {
		this.cnBtn = cnBtn;
	}
	
	public TextField getUserName() {
		return userName;
	}

	public void setUserName(TextField userName) {
		this.userName = userName;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(PasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public Button getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(Button loginBtn) {
		this.loginBtn = loginBtn;
	}
	
	public void toggleCnBtn() {
		if(this.cnBtn.getText() == "Connect") {
			this.cnBtn.setText("Disconnect");
		} else {
			this.cnBtn.setText("Connect");
		}
	}
	public void activateLoginFields() {
		this.userName.setDisable(false);
		this.passwordField.setDisable(false);
		this.newUserLink.setDisable(false);
		this.cnAddress.setDisable(true);
	}
	
	public void deactivateLoginFields() {
		this.userName.setDisable(true);
		this.passwordField.setDisable(true);
		this.newUserLink.setDisable(true);
		this.cnAddress.setDisable(false);
	}
	
	
	

}
