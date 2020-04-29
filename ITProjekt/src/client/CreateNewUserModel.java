package client;

import Commons.Message_Register;
import Commons.Message_UserNameAvailable;
import Commons.Validation_LoginProcess;

public class CreateNewUserModel {
	/**
	 * @author sarah
	 */
	protected boolean isNewUserNameAvailable = false;
	protected boolean isNewPasswordValid = false;
	protected boolean isPasswordConfirmed = false;
	protected CommunicationThread connection;
	
	public CreateNewUserModel (CommunicationThread connection) {
		this.connection = connection;
	}

	public void setisUserNameAvaiable(boolean isAvaiable) {
		this.isNewUserNameAvailable = isAvaiable;
	}
	/**
	 * @author sarah
	 * @param newValue
	 */
	public void validateUsername(String newValue) {
		newValue = newValue.trim();
		this.isNewUserNameAvailable = false;
		if(newValue.isEmpty() == false) {
			Message_UserNameAvailable msg = new Message_UserNameAvailable(newValue);
			connection.sendMessage(msg);				
		}
	}
	/**
	 * @author sarah
	 * @param newValue
	 */
	public void validatePassword(String newValue) {	
		if(Validation_LoginProcess.isPasswordValid(newValue)) {
			isNewPasswordValid = true;
		}else {
			isNewPasswordValid = false;
		}
	}
	
	/**
	 * @author sarah
	 * @param newPasswordtxt
	 * @param confirmPasswordtxt
	 * 
	 */
	public void confirmPw(String newPasswordtxt, String confirmPasswordtxt) {		
		if(newPasswordtxt.equals(confirmPasswordtxt)) {
			this.isPasswordConfirmed = true;
		} else {
			this.isPasswordConfirmed = false;
		}
	}
	/**
	 * @author sarah
	 * @param userName
	 * @param password
	 */
	public void createUser(String userName, String password) {
		userName = userName.trim();
		Message_Register msg = new Message_Register(userName, password);
		connection.sendMessage(msg);		
	}


}
