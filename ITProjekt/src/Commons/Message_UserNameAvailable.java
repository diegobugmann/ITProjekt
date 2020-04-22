package Commons;

public class Message_UserNameAvailable extends Message{
	
	
	private String userName;
	
	/**
	 * Mainconstructor getting a Username from the client 
	 */
	public Message_UserNameAvailable(String userName) {
		super();
		this.userName = userName;		
	}
	
	//Getters no cahnges on the variables needed after creation!
	
	public String getLoginName() {
		return userName;
	}

}
