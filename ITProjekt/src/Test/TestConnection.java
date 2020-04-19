package Test;

import java.net.Socket;

import Commons.Simple_Message;
import client.CommunicationThread;





public class TestConnection {

	private String ip = "127.0.0.1";
	private int port = 8080;
	private client.CommunicationThread connection;
	public static void main(String[] args) {
		TestConnection  c = new TestConnection();
		c.connect();
		c.connection.sendMessage(new Simple_Message(Simple_Message.Msg.CheckConnection));
		System.out.println("Closing Connection");
		c.connection.closeConnection();
	}
	/**
	 * Connects to the Server and creates a connection object to send or receive data from the server
	 * @param c
	 */
	private void connect() {
	    Socket socket = null;
        try {
            socket = new Socket(ip, port);
            connection = new CommunicationThread(socket, null);
            connection.start();
            System.out.println("Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
