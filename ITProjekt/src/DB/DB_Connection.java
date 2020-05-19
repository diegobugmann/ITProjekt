package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author sarah
 * inspired by example code from Bradley Richards
 */

public class DB_Connection {
	
	private String ipAdress = "127.0.0.1";
	private int port = 3306;
	private String userName = "jassUser";
	private String userPassword = "newPassword_12345";
	private final Logger logger = Logger.getLogger("");
	
	
	public DB_Connection() {
		
		// Source: stackoverflow.com for reading XML
		
		try {
			File file = new File("DBsettings.xml");
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbfactory.newDocumentBuilder();
			Document document = db.parse(file);			
			NodeList nList = document.getElementsByTagName("DBSettings"); 
			Node node = nList.item(0);
		    Element element = (Element) node;
		    ipAdress = element.getElementsByTagName("ipAdress").item(0).getTextContent();
		    port = Integer.parseInt(element.getElementsByTagName("port").item(0).getTextContent());
		    userName = element.getElementsByTagName("username").item(0).getTextContent();
		    userPassword = element.getElementsByTagName("password").item(0).getTextContent();

		} catch (Exception e) {

			logger.info(e.toString());
		}
		
		Connection cn = this.serverConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'jassdb'";
		stmt = cn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		rs = stmt.executeQuery();
        rs.next();
        
        if(rs.getInt(1) == 0) {
        	createDB(cn);
        }
        
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
	}
	
	public Connection serverConnection() {
		
	
		Connection cn = null;
  
        String serverInfo = "jdbc:mysql://" + ipAdress + ":" + port + "/";
        String optionInfo = "?connectTimeout=5000&serverTimezone=Europe/Zurich";
        String username = userName;
        String password = userPassword;
        
        try {
        	
        	cn = DriverManager.getConnection(serverInfo + optionInfo, username, password);
            
		 } catch (SQLException e) {
			 
			 logger.info(e.toString());
		 }
        
        return cn;
	}
	

	private void createDB(Connection cn) {
		
		Statement stmt = null;

	    try {
	    	
	        stmt = cn.createStatement();
	        stmt.execute("CREATE DATABASE jassdb");
	        stmt.execute("USE jassdb");
	        stmt.execute("CREATE TABLE userData " +
	                        "(ID INT NOT NULL AUTO_INCREMENT, " +
	                        "username VARCHAR(255) NOT NULL, " +
	                        "password VARCHAR(64) NOT NULL, " + 
	                        "PRIMARY KEY (`ID`))");
	        
	    } catch (SQLException e) {
	    	
	    	logger.info(e.toString());
	        
	    } finally {
	        if (stmt != null) try {
	            if (!stmt.isClosed()) stmt.close();
	        } catch (Exception e) {}
	        
	    } 
	}
	
	public void disconnectDB(Connection cn) {
		
		if(cn != null) 
		try {
			if(!cn.isClosed()) {
				cn.close();
			}
		} catch (Exception e){

			logger.info(e.toString());
		}
	}

}
