package Commons;

import java.io.Serializable;

public class Message implements Serializable {

	// Data included in a message
    private long id;
    private long timestamp;
    private String client;
    
    
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
    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
    public String toString() {
    	return "Message" + this.id + " Client" + this.client + "SendingTime " + this.timestamp;
    }
}
