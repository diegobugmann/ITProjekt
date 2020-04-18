package client;

public class ClientModel {
	protected boolean userName;
	protected boolean password;
	protected String user;
	protected String pw;
	public static int cardStyle=0;
	
	
	
	public boolean validateUserName(String newValue) {
		userName = false;
		
		if(newValue.isEmpty() == false) {
			userName = true;
			//sendet an DB
			
		}
		return userName;
	}
	
	public boolean validatePassword(String newValue) {
		password = false;
		
		if(newValue.isEmpty() == false) {
			//sendet an DB
			password = true;
		}
		
		
		return password;
	}
	
	public boolean loginProcess(String user, String pw) {
		boolean done = false;
		this.user = user;
		this.pw = pw;
		
		//an DB senden
		done = true;
		return done;
		
	}
	
	
	public void newGame() {
		
	}
	
	public void setCardStyle(int style) {
		this.cardStyle = style;
	}
	
	public int getCardStyle() {
		return this.cardStyle;
	}


}


