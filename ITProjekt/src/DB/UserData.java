package DB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Logger;

public class UserData {
	
	private final DB_Connection dbcn = new DB_Connection();
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private final Logger logger = Logger.getLogger("");
	
	// Validation data for createUser()
	public boolean isUserNameAvailable(String userName) {
		
		Connection cn = dbcn.serverConnection();
        boolean isUserNameAvailable = false;
		
        try {
            String query = "SELECT COUNT(*) FROM jassDB.userData WHERE username = ?";
            stmt = cn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1,  userName);            
            rs = stmt.executeQuery();
            rs.next();

            isUserNameAvailable = (rs.getInt(1) == 0);          
        	logger.info("Username available: " + isUserNameAvailable);            	
           
        } catch (SQLException e) {
        	
        	logger.info(e.toString());
        	
        } finally {
           
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
            if (cn != null) try {
                if (!cn.isClosed()) cn.close();
            } catch (Exception e) {}
        }
		
		return isUserNameAvailable;
	}
	
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
	
	public boolean createUser(String userName, String password) {
		
		Connection cn = dbcn.serverConnection();
        boolean createUserSucceded = false;
        
        try{
	        String insertSQL = "INSERT INTO jassDB.userData (username, password) VALUES (?, ?)";
	        stmt = cn.prepareStatement(insertSQL);
	        stmt.setString(1,  userName);
	        stmt.setString(2,  hashPassword(password));
	        stmt.executeUpdate();
	        createUserSucceded = true;
        }catch (SQLException e) {
        	
        	logger.info(e.toString());
        	
        } finally {
           
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
            if (cn != null) try {
                if (!cn.isClosed()) cn.close();
            } catch (Exception e) {}
        }		 
		return createUserSucceded;
	}
	
	public void setUserInactive() {
		
		// TODO nice to have 
		
	}
	
	public boolean setNewPassword(String userName, String password) {
		
		Connection cn = dbcn.serverConnection();
		Boolean newPasswordSucceded = false;
		
		try{
	        String updateSQL = "UPDATE jassDB.userData SET password = ? WHERE username = ?";
	        stmt = cn.prepareStatement(updateSQL);
	        stmt.setString(1,  hashPassword(password));
	        stmt.setString(2,  userName);
	        stmt.executeUpdate();
	        newPasswordSucceded = true;	
		} catch (SQLException e) {
	    	
	    	logger.info(e.toString());
	    	
	    } finally {
	       
	        if (rs != null) try {
	            if (!rs.isClosed()) rs.close();
	        } catch (Exception e) {}
	        if (stmt != null) try {
	            if (!stmt.isClosed()) stmt.close();
	        } catch (Exception e) {}
	        if (cn != null) try {
	            if (!cn.isClosed()) cn.close();
	        } catch (Exception e) {}
	    }
		return newPasswordSucceded;
	}
	
	//Source: stackoverflow.com
	private String hashPassword(String pw) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pw.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			logger.info(e.toString());
			return pw;			
		}		
	} 
	
	//Validation login
	public boolean check(String userName, String password) {
		
		Connection cn = dbcn.serverConnection();
        boolean loginSuccessful = false;
        
        
        try {
            String query = "SELECT COUNT(*) FROM jassDB.userData WHERE username = ? AND password = ?";
            stmt = cn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1,  userName);
            stmt.setString(2,  hashPassword(password));
            rs = stmt.executeQuery();
            rs.next();

            loginSuccessful = (rs.getInt(1) > 0);          
        	logger.info("Login successful: " + loginSuccessful);            	
           
        } catch (SQLException e) {
        	
        	logger.info(e.toString());
        	
        } finally {
           
            if (rs != null) try {
                if (!rs.isClosed()) rs.close();
            } catch (Exception e) {}
            if (stmt != null) try {
                if (!stmt.isClosed()) stmt.close();
            } catch (Exception e) {}
            if (cn != null) try {
                if (!cn.isClosed()) cn.close();
            } catch (Exception e) {}
        }
        return loginSuccessful;
		
	}

}
