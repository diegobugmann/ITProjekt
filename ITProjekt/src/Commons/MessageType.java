package Commons;

/**
 * This class defines all available message types. We provide static functions
 * to map between tye types and the value of the enumeration.
 */
public enum MessageType {
	login,
	register,
	gamelist,
	simple_Message,
	createGame,
	joinGame,
	players,
	hand,
	turn,
	ansage,
	wiis,
	wiisInfo,
	stich,
	points,
	cancel,
	trumpf,
	endResults,
	error,
	newUserName,
	yourTurn,
	chat
	;
	
    public static MessageType parseType(String typeName) {
    	MessageType type = MessageType.simple_Message;
    	for (MessageType value : MessageType.values()) {
    		if (value.toString().equals(typeName)) type = value;
    	}
    	return type;
    }
	
    /**
     * Determine the message type from the actual class of this object
     */
    public static MessageType getType(Message msg) {
    	
    	MessageType type = simple_Message;
    	if (msg instanceof Simple_Message) type = simple_Message;
    	else if (msg instanceof Message_Login) type = login;
    	else if (msg instanceof Message_Register) type = register;
    	else if (msg instanceof Message_GameList) type = gamelist;
    	else if (msg instanceof Message_CreateGame) type = createGame;
    	else if (msg instanceof Message_JoinGame) type = joinGame;
    	else if (msg instanceof Message_Players) type = players;
    	else if (msg instanceof Message_Hand) type = hand;
    	else if (msg instanceof Message_Turn) type = turn;
    	else if (msg instanceof Message_Ansage) type = ansage;
    	else if (msg instanceof Message_Wiis) type = wiis;
    	else if (msg instanceof Message_Stich) type = stich;
    	else if (msg instanceof Message_Points) type = points;
    	else if (msg instanceof Message_Cancel) type = cancel;
    	else if (msg instanceof Message_Trumpf) type = trumpf;
    	else if (msg instanceof Message_EndResult) type = endResults;
    	else if (msg instanceof Message_Error) type = error;
    	else if (msg instanceof Message_UserNameAvailable) type = newUserName;
    	else if (msg instanceof Message_YourTurn) type = yourTurn;
    	else if (msg instanceof Message_WiisInfo) type = wiisInfo;
    	else if (msg instanceof Message_Chat) type = chat;
    	return type;
    }	
	
}