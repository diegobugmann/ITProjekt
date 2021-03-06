package Commons;

import java.io.Serializable;

import Commons.Card.Rank;
import Commons.Card.Suit;
import server.PlayValidation;

public class Wiis implements Serializable, Comparable<Wiis> {
	
	public enum Blatt {
		dreiblatt(20),
		vierblatt(50),
		fuenfblatt(100),
		sechsblatt(150),
		siebenblatt(200),
		achtblatt(250),
		neunblatt(300),
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

	@Override
	public String toString() {
		String text ="";
		text += this.blatt.toString()+"_";
		text+= this.highestCard.toString();
		return text;
	}
	
	public Card getHighestCard() {
		return this.highestCard;
	}

	@Override
	public int compareTo(Wiis o) {
		return this.blatt.compareTo(o.blatt);		
	}
	
	/**
	 * @author digib
	 * @param gameType
	 * @return boolean if wiis contains stoeck
	 */
	public boolean containsStoeck(GameType gameType) {
		Suit trumpf = PlayValidation.getTrumpfAsSuit(gameType);
		if (blatt != Blatt.viergleiche && blatt != Blatt.vierBauern && blatt != Blatt.vierNeuner) {
			if (highestCard.getRank() == Rank.Ace || highestCard.getRank() == Rank.King) {
				if (highestCard.getSuit() == trumpf) {
					return true;
				}
			}
		}
		return false;
	}
	
}
