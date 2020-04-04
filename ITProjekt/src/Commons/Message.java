package Commons;

import java.io.Serializable;

public class Message implements Serializable{

	// Data included in a message
	private String client;
	private long id;
    private long timestamp;
    
    // Generator for a unique message ID
    private static long messageID = 0;

    /**
     * Increment the global messageID
     * Code from Bradley Richards
     * @return the next valid ID
     */
    private static long nextMessageID() {
        return messageID++;
    }
    
    public Message() {
    	this.id = nextMessageID();
    }
    
    
    
    public String toString() {
    	return "Message" + this.id + " Client" + this.client + "SendingTime " + this.timestamp;
    }
    
    
    
}
