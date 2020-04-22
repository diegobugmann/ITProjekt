package Commons;

public class Wiis {
	
	public enum Blatt {
		dreiblatt(20),
		vierblatt(50),
		fuenfblatt(100),
		sechsblatt(100),
		siebenblatt(100),
		achtblatt(100),
		neunblatt(100),
		viergleiche(100),
		vierNeuner(150),
		vierBauern(200);
		
		private int points;

		private Blatt(int points) {
			this.points = points;
		}
		
		public int getPoints() {
			return this.points;
		}
		
	}
	
	private Card highestCard;
	private Blatt blatt;
	
	public Wiis(Blatt b, Card highestCard) {
		this.blatt = b;
		this.highestCard = highestCard;
		// TODO Auto-generated constructor stub
	}
	
	public Blatt getBlatt() {
		return this.blatt;
	}
	
}
