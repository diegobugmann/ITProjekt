package server;

public enum GameType {
	TopsDown,
	BottomsUp,
	ShieldsOrSpades,
	RosesOrHearts,
	AcornsOrDiamonds,
	BellsOrClubs;
	
	public String toString() {
		String type = "";
        switch (this) {
        case BellsOrClubs: 
        	type = "Bells or Clubs"; break;
        case AcornsOrDiamonds: 
        	type = "Acorns or Diamonds"; break;
        case RosesOrHearts: 
        	type = "Roses or Hearts"; break;
        case ShieldsOrSpades: 
        	type = "Shields or Spades"; break;
        case TopsDown:
        	type = "Tops Down"; break;
        case BottomsUp:
        	type = "Bottoms Up"; break;
        }
        return type;
	}
	
}
