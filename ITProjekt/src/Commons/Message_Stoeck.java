package Commons;

/**
 * @author digib
 * contains the name of the person who has the stoeck. Used to broadcast
 * */
public class Message_Stoeck extends Message {
	
	private String name;

	public Message_Stoeck(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
