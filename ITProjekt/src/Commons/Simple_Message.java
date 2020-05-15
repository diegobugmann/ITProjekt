package Commons;

/**
 * Simple Messages are used to send nonData Messages. Messages only carrying one information about the programm status
 * often used to request a date message or to quit a communication with a accepted or received message.
 * @author mibe1
 *
 */
public class Simple_Message extends Message{
	/**
	 * Type of the simple message
	 * @author mibe1
	 *
	 */
	public enum Msg{
		//Quited with a received message it checks the possibilty to send messages between server and client
		CheckConnection,
		//Most used message quits a receive of any massage
		Received,
		//Server to clients start of the Game
		Game_Start,
		//Server to client waits for client to reutrn Message ansage Points
		Ansage_Points,
		//Server to Client game has ended
		GameEnded,
		//Server to client waits for client to reutrn Message ansage Trumpf
		Ansage_Trumpf,
		Schiebe,
		Schiebe_Trumpf,
		//info that a new round starts
		nextRound,
		//Fragt GameListe des Servers ab
		Get_GameList,
		//Canncel on the Splashscreen
		CancelWaiting,
		//server to client if a match was played
		Match,
		//Login related Messages
		Login_accepted,
		Registration_accepted,
		Username_accepted,
		Username_declined, 
		Registration_invalidPW, 
		Registration_failed;
	}
	
	private Msg type;
	/**
	 * Defaultconstructor
	 * @param type
	 */
	public Simple_Message(Msg type) {
		this.type = type;
	}
	//Getters and Setters
	public Msg getType() {
		return type;
	}
	public void setType(Msg type) {
		this.type = type;
	}
	//---------------------------------
	//ToString for simplemessages
	@Override
	public String toString() {
		return super.toString() + " Simplemessage Type: "+ this.type;
	}
	
	

}
