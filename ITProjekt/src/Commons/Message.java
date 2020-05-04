package Commons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 * Basic Class for all Messages, contains client, messageid and timestamp
 * @author mibe1
 *
 */
public class Message implements Serializable {

	// Data included in a message
	//The first Message sent from a client to a server is always 0 from the seccond message the messages get an individual ID
	//IDs are not always unique!!!!!!!! This is not required but special
    private long id;
    private long timestamp;
    // contains the Sending client if message gets send from client to Server
    //Or the acting client if message gets send from Server to all clients (Example turn)
    private String client;
    
    
    // Generator for a unique message ID
    private static long messageID = 0;

    /**
     * Increment the global messageID
     * Code from Bradley Richards
     * @return the next valid ID
     * TODO think if this works when programm is exicuted on multible physical clients and what it is needed for
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
        	//System.out.println("Sending: "+ this.toString() + " Socket " + socket.toString());
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
    	return "Message " + this.id +" Type "+MessageType.getType(this)+"  Client " + this.client + " SendingTime  " + this.timestamp;
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
    	if(messageID < msg.getId())
    	messageID = msg.getId();
    	//System.out.println("Read new Message "+ msg.toString());
        return msg;
	}
}
