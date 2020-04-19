package Commons;

/**
 * Message to Login a User on the Server
 * @author mibe1
 *
 */
public class Message_Login extends Message{
	private String password;
	private String loginName;
	/**
	 * Mainconstructor getting a Username and a Password from the client 
	 */
	public Message_Login(String loginName, String password) {
		super();
		this.loginName = loginName;
		this.password =password;
	}
	//Getters no cahnges on the variables needed after creation!
	public String getPassword() {
		return password;
	}

	public String getLoginName() {
		return loginName;
	}
	
	
}
