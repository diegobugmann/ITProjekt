package client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	protected TextField userName;
	protected PasswordField passwordField;
	protected Label textlbl;
	protected Label pwlbl;
	protected Button loginBtn;
	//protected Button newUserBtn;
	protected Stage stage;
	

	
	
	public LoginView(Stage stage) {
		
		this.stage = stage;
		
		textlbl = new Label("Username:");
		userName = new TextField();
		userName.setMaxWidth(300);
		
		pwlbl = new Label("Passwort erstellen:");
		passwordField = new PasswordField();
		passwordField.setMaxWidth(300);
		
		loginBtn = new Button("Login");
		loginBtn.setDisable(true);
		
		//newUserBtn = new Button("Neuer User");
		//newUserBtn.setDisable(true);
		
		
		Region region0 = new Region();
		region0.setPrefHeight(20);
		
		Region region1 = new Region();
		region1.setPrefHeight(50);
		
		Region region2 = new Region();
		region2.setPrefHeight(50);
		
		Region region3 = new Region();
		region3.setPrefHeight(10);
		
		Image image = new Image(LobbyView.class.getResourceAsStream("Bilder/Lobby.jpg"));
		BackgroundSize backSize = new BackgroundSize(800, 800, false, false, false, false);
		Background background = new Background(new BackgroundImage(image, null, null, BackgroundPosition.CENTER, backSize));
		
		this.setBackground(background);
		
		this.getChildren().addAll(region0, textlbl, userName, region1, pwlbl, passwordField, region2, loginBtn);
		
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
	
	

}
