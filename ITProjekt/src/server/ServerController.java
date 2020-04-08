package server;

import Commons.Simple_Message;

public class ServerController {
	
	private ServerModel model;
	
	public ServerController(ServerModel model) {
		this.model = model;
		
		for (Game g : model.getGames()) {
			g.getNumOfPlayers().addListener( (obs, oV, nV) -> {
				if ((int) nV == 4) {
					//TODO Wenn 4 Spieler einem Spiel beigetreten sind, wird das Spiel gestartet
					model.broadcast(startGameMsg);
			}
			});
		}
		
	}

}
