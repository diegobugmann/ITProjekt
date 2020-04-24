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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Commons.Validation_LoginProcess;

public class CreateNewUserView extends VBox{
	
	/**
	 * @author sarah, GUI design by Luca Meyer
	 */
	
	protected Stage stage;
	protected Label newUserNamelbl;
	protected TextField newUserNametxt;
	protected Label userNameAvailable;
	protected Label newPasswordlbl;
	protected PasswordField newPasswordtxt;
	protected Label confirmPasswordlbl;
	protected PasswordField confirmPasswordtxt;
	protected Label pwConventionslbl;
	protected Button createUserbtn;
	protected Button cancelbtn;
	
	public CreateNewUserView(Stage stage) {
		
		this.stage = stage;
		
		newUserNamelbl = new Label("Username");
		newUserNametxt = new TextField();
		newUserNametxt.setMaxWidth(300);
		userNameAvailable = new Label("");
		userNameAvailable.setTextFill(Color.RED);
		newPasswordlbl = new Label("Password");
		newPasswordtxt = new PasswordField();
		newPasswordtxt.setMaxWidth(300);
		pwConventionslbl = new Label("at least 6 characters, upper and lower case letters, at least 1 number");
		confirmPasswordlbl = new Label ("Confirm Password");
		confirmPasswordtxt = new PasswordField();
		confirmPasswordtxt.setMaxWidth(300);
		createUserbtn = new Button("Create new user");
		createUserbtn.setDisable(true);
		cancelbtn = new Button("Cancel");
		
		Region region0 = new Region();
		region0.setPrefHeight(10);
		
		Region region1 = new Region();
		region1.setPrefHeight(40);
		
		Region region2 = new Region();
		region2.setPrefHeight(20);
		
		Region region3 = new Region();
		region3.setPrefHeight(20);
		
		Region region4 = new Region();
		region4.setPrefHeight(10);
		
		Image image = new Image(LobbyView.class.getResourceAsStream("Bilder/Lobby.jpg"));
		BackgroundSize backSize = new BackgroundSize(800, 800, false, false, false, false);
		Background background = new Background(new BackgroundImage(image, null, null, BackgroundPosition.CENTER, backSize));
		
		this.setBackground(background);
		
		this.getChildren().addAll(region0, newUserNamelbl, newUserNametxt, userNameAvailable, region1, newPasswordlbl, newPasswordtxt, pwConventionslbl, 
				region2, confirmPasswordlbl, confirmPasswordtxt, region3, createUserbtn, region4, cancelbtn);
		
		this.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(this);
		/* scene.getStylesheets().add(getClass().getResource("").toExternalForm()); */
		
		stage.setFullScreen(false);
		
		stage.setHeight(800);
		stage.setWidth(800);
		stage.setTitle("Create new user");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public TextField getUserName() {
		return newUserNametxt;
	}
	
	public void setUserName(TextField newUserNametxt) {
		this.newUserNametxt = newUserNametxt;
	}

	public PasswordField getPasswordField() {
		return newPasswordtxt;
	}

	public void setPasswordField(PasswordField newPasswordtxt) {
		this.newPasswordtxt = newPasswordtxt;
	}
	
	public PasswordField getConfirmedPwField() {
		return confirmPasswordtxt;
	}

	public void setConfirmedPwField(PasswordField confirmPasswordtxt) {
		this.confirmPasswordtxt = confirmPasswordtxt;
	}

	public Button getCreateBtn() {
		return createUserbtn;
	}

	public void setCreateUserBtn(Button createUserbtn) {
		this.createUserbtn = createUserbtn;
	}
	
	public void setCancelBtn(Button cancelbtn) {
		this.cancelbtn = cancelbtn;
	}
	
	public void setUserNameAvaiable(boolean isUserNameAvaiable) {
		if(isUserNameAvaiable) {
			this.userNameAvailable.setText("");
		} else {
			this.userNameAvailable.setText("User not avaiable!");
		}
	}
	
	public void activateNewUserbtn(boolean isNewLoginValid) {
		if(isNewLoginValid) {
			this.createUserbtn.setDisable(false);
		}else {
			this.createUserbtn.setDisable(true);
		}
	}
	
}
