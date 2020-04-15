package Commons;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Suit {	ShieldsOrSpades,
		RosesOrHearts,
		AcornsOrDiamonds,
		BellsOrClubs;
        @Override
        public String toString() {
            String suit = "";
            switch (this) {
            	case BellsOrClubs: suit = "Bells or Clubs"; break;
            	case AcornsOrDiamonds: suit = "Acorns or Diamonds"; break;
            	case RosesOrHearts: suit = "Roses or Hearts"; break;
            	case ShieldsOrSpades: suit = "Shields or Spades"; break;
            }
            return suit;
        }
    };
    public enum Rank {Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
        @Override
        public String toString() {
            String str = "ace";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 12
            int ordinal = this.ordinal();
            if (ordinal <= 4) {
                str = Integer.toString(ordinal+4);
            } else if (ordinal == 5) { // Jack
                str = "jack";
            } else if (ordinal == 6) { // Queen
                str = "queen";
            } else if (ordinal == 7) { // King
                str = "king";
            }
            return str;
        }
    };
    
    private final Suit suit;
    private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
    	this.suit = suit;
        this.rank = rank;
    }
    
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
    
    
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

	@Override
	public int compareTo(Card c) {
		return this.getRank().ordinal() - c.getRank().ordinal();
	}
}
