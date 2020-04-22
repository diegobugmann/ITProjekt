package Commons;

import java.util.logging.Logger;

/**
 * Pr�fung auf Client und auf DB 
 * @author sarah
 *
 */

public class Validation_LoginProcess {
	
	private final Logger logger = Logger.getLogger("");
	
	public boolean isPasswordValid(String password) {
			
			Boolean isPasswordValid = false;
			Boolean isUpperCase = false;
			Boolean isLowerCase = false;
			Boolean isDigit = false;
			
			for(int i = 0; i < password.length(); i++) {
				char ch = password.charAt(i);
				if(Character.isUpperCase(ch)) {
					isUpperCase = true;
				}
				if(Character.isLowerCase(ch)) {
					isLowerCase = true;
				}
				if(Character.isDigit(ch)) {
					isDigit = true;
				}
			}
			if (password.length() >= 6 && isUpperCase && isLowerCase && isDigit) {			
				isPasswordValid = true;
			}
			
			logger.info("Password is valid: " + isPasswordValid.toString());
			return isPasswordValid;
			
		}
		

}
