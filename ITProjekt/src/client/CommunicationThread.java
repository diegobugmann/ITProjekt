package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Commons.Message;
import Commons.Simple_Message;

public class CommunicationThread extends Thread{
	private Socket socket;
	private ClientController controller;
	
	public CommunicationThread(Socket s, ClientController controller) throws IOException {
		//run the Constructor of Thread
		super();
		this.socket = s;
		this.controller = controller;
	}
	
	
    /**
     * Handles all incoming Messages by just receiving them and then giving them to the processMessage method 
     * If the answer of the message is not null, this method also response to the server and starts listening again 
     */
    @Override
    public void run() {
    	 try {
    		// Read a message from the client
    		 Message msgIn = Message.receive(socket);
				processMessage(msgIn);
 			
 			Message msgOut = processMessage(msgIn);
 			if(msgOut != null) {
 				msgOut.send(socket);
 			}
			this.run();
         } catch (Exception e) {
         }    

    }
	
	private Message processMessage(Message msgIn) {
		
		return null;
	}


	public void sendMessage(Message msg) {
		msg.send(this.socket);
	}
	
	public void sendReceived() {
		Simple_Message msg = new Simple_Message(Simple_Message.Msg.Received);
		msg.send(this.socket);
		
	}
	
	
	/**
	 * Closes the connection to the Server on shutting down the program
	 */
	public void closeConnection() {
		try { if (socket != null) socket.close(); } catch (IOException e) {}
	}
}
