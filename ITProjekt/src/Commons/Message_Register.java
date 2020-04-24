/**
 * 
 */
package Commons;

/**
 * @author mibe1
 * Registration of a new User with a Username and a Password after creation Username and Password can not be changed
 */
public class Message_Register extends Message{

	private String password;
	private String userName;
	
	/**
	 * Mainconstructor getting a Username and a Password from the client 
	 */
	public Message_Register(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	//Getters no cahnges on the variables needed after creation!
	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}
}
