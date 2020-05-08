package client;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreateNewUserController {
	/**
	 * @author sarah
	 */
	protected Stage stage;
	protected CreateNewUserModel model;
	protected CreateNewUserView view;
	
	public CreateNewUserController(CommunicationThread connection) {
		this.model = new CreateNewUserModel(connection);
		this.stage  = new Stage();
		stage.initModality(Modality.NONE);
		
		view = new CreateNewUserView(stage);
		
		view.newUserNametxt.textProperty().addListener((observable, 
				oldValue, newValue)-> {model.validateUsername(newValue);activateNewUserbtn();});
		view.newPasswordtxt.textProperty().addListener((observable,
				oldValue, newValue)-> {model.validatePassword(newValue); model.confirmPw(newValue, view.confirmPasswordtxt.getText());activateNewUserbtn();});
		view.confirmPasswordtxt.textProperty().addListener((observable,
				oldValue, newValue)-> {model.confirmPw(newValue, view.newPasswordtxt.getText());activateNewUserbtn();});
		
		view.cancelbtn.setOnAction(event ->{
			stage.close();
		});
		view.createUserbtn.setOnAction(event ->{
			model.createUser(view.newUserNametxt.getText(), view.newPasswordtxt.getText());
		});
			
	}
	
	public void userNameisAvailable(boolean isUserNameAvailable) {
		model.setisUserNameAvaiable(isUserNameAvailable);
		view.setUserNameAvailable(isUserNameAvailable);
	}
	
	/**
	 * @author sarah
	 */
	public void activateNewUserbtn() {
		if(model.isNewPasswordValid && model.isNewUserNameAvailable && model.isPasswordConfirmed) {
			view.activateNewUserbtn(true);
		}else {
			view.activateNewUserbtn(false);
		}
	}
	/**
	 * @author sarah
	 */
	public void registrationSucceded() {
		String info = "Neuer Benutzer erfolgreich erstellt";
		Alert alert = new Alert(AlertType.INFORMATION, info );
		alert.setHeaderText(null);
		alert.setTitle(null);
		alert.showAndWait();
		view.stage.close();
	}
	
	/**
	 * @author sarah
	 */
	public void registerFailed(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Registration fehlgeschlagen");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();		
	}
}
