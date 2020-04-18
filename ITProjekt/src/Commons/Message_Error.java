package Commons;

public class Message_Error extends Message{
	
	public enum ErrorType{
		not_loggedin,
		logginfalied;
		
		
	}
	
	private String errorMessage;
	private ErrorType type;
	public Message_Error(String errorMessage, ErrorType type) {
		super();
		this.errorMessage = errorMessage;
		this.type = type;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public ErrorType getType() {
		return type;
	}
	public void setType(ErrorType type) {
		this.type = type;
	}
	
	
	
}
