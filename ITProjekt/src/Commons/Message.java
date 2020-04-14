package Commons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;


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
    	this.timestamp = System.currentTimeMillis();
    }
    
    /**
     * Sends the Message on the given Socket
     * @param socket
     */
    public void send(Socket socket) {
    	try{
        	ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
        	this.timestamp = System.currentTimeMillis();
        	System.out.println("Sending: "+ this.toString());
        	writer.writeObject(this);
			writer.flush();
    	}
    	catch(Exception e) {
    		System.out.println(e.toString());
    	}
    }
    
    //Geters and Setters -----------------------------------------------------------------------------
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
	//--------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
    	return "Message " + this.id + " Client " + this.client + "SendingTime  " + this.timestamp;
    }
 
    /**
     * Reades a received Message and casts it into a Message Object
     * @param clientSocket
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
	public static Message receive(Socket clientSocket) throws IOException, ClassNotFoundException {
    	ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
    	Message msg = (Message) in.readObject();
    	System.out.println("Read new Message "+ msg.toString());
        return msg;
	}
}
