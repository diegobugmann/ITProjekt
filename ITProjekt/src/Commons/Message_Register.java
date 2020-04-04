/**
 * 
 */
package Commons;

/**
 * @author mibe1
 *
 */
public class Message_Register extends Message{

	private String password;
	private String loginName;
	
	/**
	 * 
	 */
	public Message_Register() {
		super();
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
