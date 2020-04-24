package Commons;

import java.util.logging.Logger;

/**
 * Prüfung auf Client und auf DB 
 * @author sarah
 *
 */

public class Validation_LoginProcess {
	
	public static boolean isPasswordValid(String password) {
			final Logger logger = Logger.getLogger("");
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
