package client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView extends VBox{
	
	protected TextField userName;
	protected PasswordField passwordField;
	protected Label textlbl;
	protected Label pwlbl;
	protected Button loginBtn;
	protected Stage stage;
	

	
	
	public LoginView(Stage stage) {
		
		this.stage = stage;
		
		textlbl = new Label("Username:");
		userName = new TextField();
		
		pwlbl = new Label("Passwort erstellen:");
		passwordField = new PasswordField();
		loginBtn = new Button("Login");
		
		Region region1 = new Region();
		region1.setPrefHeight(50);
		
		Region region2 = new Region();
		region2.setPrefHeight(50);
		
		this.getChildren().addAll(textlbl, userName, region1, pwlbl, passwordField, region2, loginBtn);
		
		Scene scene = new Scene(this);
		/* scene.getStylesheets().add(getClass().getResource("").toExternalForm()); */
		
		stage.setFullScreen(false);
		
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
