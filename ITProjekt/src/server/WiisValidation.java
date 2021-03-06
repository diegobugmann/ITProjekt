package server;

import java.util.ArrayList;

import Commons.Card;
import Commons.Wiis;
import Commons.Card.Rank;
import Commons.Card.Suit;
import Commons.GameType;
import Commons.Wiis.Blatt;

/**
 * @author digib
 */
public class WiisValidation {
	
	/**
	 * @author digib
	 * @param hand
	 * @return ArrayList<Wiis> containing ALL possible Wiis objects from players hand
	 */
	public static ArrayList<Wiis> validateWiis(ArrayList<Card> hand) {
		ArrayList<Wiis> wiis = new ArrayList<Wiis>();
		ArrayList<Card> handCopy = (ArrayList<Card>) hand.clone();
		Wiis firstWiis = isBlatt(handCopy);
		Wiis secondWiis;
		Wiis thirdWiis;
		
		//no blatt, but maybe 1 or even 2 vierlinge?
		if (firstWiis == null) {
			firstWiis = isVierlinge(handCopy);
			if (firstWiis != null) {
				wiis.add(firstWiis);
				removeVierlinge(firstWiis, handCopy);
				secondWiis = isVierlinge(handCopy); //check for another vierlinge after removing the other ones
				if (secondWiis != null)
					wiis.add(secondWiis);
			}
		
		//7, 8 and 9-Blatt are only possible on its own
		} else if (firstWiis.getBlatt() == Blatt.siebenblatt || firstWiis.getBlatt() == Blatt.achtblatt ||
				firstWiis.getBlatt() == Blatt.neunblatt) {
			wiis.add(firstWiis);
			
		//6 and 5-Blatt can be paired with vierlinge or another blatt
		} else if (firstWiis.getBlatt() == Blatt.sechsblatt || firstWiis.getBlatt() == Blatt.fuenfblatt) {
			wiis.add(firstWiis);
			secondWiis = isVierlinge(handCopy); //check for vierlinge
			if (secondWiis != null)
				wiis.add(secondWiis);
			else { //check for another blatt, therefore removing the one already found
				removeBlatt(firstWiis, handCopy);
				secondWiis = isBlatt(handCopy);
				if (secondWiis != null)
					wiis.add(secondWiis);
			}
			
		//4-Blatt can be paired with vierlinge and/or another blatt
		} else if (firstWiis.getBlatt() == Blatt.vierblatt) {
			wiis.add(firstWiis);
			secondWiis = isVierlinge(handCopy); //check for vierlinge
			if (secondWiis != null)
				wiis.add(secondWiis);
			removeBlatt(firstWiis, handCopy); //check for another blatt, therefore removing the one already found
			secondWiis = isBlatt(handCopy);
			if (secondWiis != null)
				wiis.add(secondWiis);
			
		//3-Blatt can be paired with multiple vierlinge or blätter in different combinations
		} else if (firstWiis.getBlatt() == Blatt.dreiblatt) {
			wiis.add(firstWiis);
			secondWiis = isVierlinge(handCopy); //check for vierlinge
			if (secondWiis != null) {
				wiis.add(secondWiis);
				ArrayList<Card> handCopy2 = (ArrayList<Card>) handCopy.clone();
				removeVierlinge(secondWiis, handCopy2);
				thirdWiis = isVierlinge(handCopy2); //check for another vierlinge after removing the other ones
				if (thirdWiis != null) {
					wiis.add(thirdWiis);
				} else {
					removeBlatt(firstWiis, handCopy); //remove the other blatt
					thirdWiis = isBlatt(handCopy); //to check for another blatt
					if (thirdWiis != null)
						wiis.add(thirdWiis);
				}
			} else { //no vierlinge
				removeBlatt(firstWiis, handCopy);
				secondWiis = isBlatt(handCopy); //look for a second 3blatt
				if (secondWiis != null) {
					wiis.add(secondWiis);
					removeBlatt(secondWiis, handCopy);
					thirdWiis = isBlatt(handCopy); //look for a third 3blatt
					if (thirdWiis != null)
						wiis.add(thirdWiis);
				}
			}
		}
		return wiis;
	}
	

	/**
	 * @author digib
	 * @param hand
	 * @return Wiis
	 * vierlinge as a Wiis-object or null if no vierlinge are found
	 */
	private static Wiis isVierlinge(ArrayList<Card> hand) {
		int counter = 0;
		for (Rank r : Rank.values()) {
			counter = 0;
			for (Card c : hand) {
				if (c.getRank() == r) {
					counter++;
					if (counter == 4) {
						if (r == Rank.Nine) return new Wiis(Blatt.vierNeuner, c);
						else if (r == Rank.Jack) return new Wiis(Blatt.vierBauern, c);
						else return new Wiis(Blatt.viergleiche, c);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @author digib
	 * @param hand
	 * @return Wiis
	 * returns the longest possible blatt, if multiple blatt from same length: returns the most right one in the hand or null if no blatt is found
	 */
	private static Wiis isBlatt(ArrayList<Card> hand) {
		for (int diff = hand.size()-1; diff >= 2; diff--) {
			for (int highest = hand.size()-1; highest >= diff; highest--) {
				if (hand.get(highest).getSuit() == hand.get(highest-diff).getSuit() &&
					hand.get(highest).getRank().ordinal() == hand.get(highest-diff).getRank().ordinal()+diff) {
					for (Blatt b : Blatt.values()) {
						if (diff == b.ordinal()+2) 
							return new Wiis(b, hand.get(highest));
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @author digib
	 * @param wiis, handCopy
	 * removes all the cards corresponding to wiis from the hand
	 */
	private static void removeBlatt(Wiis wiis, ArrayList<Card> handCopy) {
		Card highestCard = wiis.getHighestCard();
		int numOfCards = wiis.getBlatt().ordinal()+3;
		int index = handCopy.indexOf(highestCard);
		for (int i = 0; i < numOfCards; i++) {
			handCopy.remove(index-i);
		}
	}
	
	/**
	 * @author digib
	 * @param wiis, handCopy
	 * @return ArrayList<Card>
	 * removes all the cards corresponding to wiis from the hand in order to search for more wiis of the same type
	 */
	private static void removeVierlinge(Wiis wiis, ArrayList<Card> handCopy) {
		Rank vierling = wiis.getHighestCard().getRank();
		for (int i = handCopy.size()-1; i >= 0; i--) {
			if (handCopy.get(i).getRank() == vierling)
				handCopy.remove(i);
		}
	}
	
	/**
	 * @author digib
	 * @param players, gameType
	 * @return Player with the highest Wiis
	 * compares all different Wiis-Objects from the players and validates the highest one, considering all special cases
	 */
	public static Player validateWiisWinner(ArrayList<Player> players, GameType gameType) {
		boolean isTrumpf = gameType != GameType.TopsDown && gameType != GameType.BottomsUp;
		Suit trumpf = null;
		if (isTrumpf) 
			trumpf = PlayValidation.getTrumpfAsSuit(gameType);
		Wiis highestWiis = players.get(0).getWiis().get(0); //suppose the first wiis is the highest;
		Player winningPlayer = players.get(0);
		for (Player p : players) {
			for (Wiis w : p.getWiis()) {
				if (highestWiis != w) { //don't compare the first wiis with itself
					if (w.getBlatt().getPoints() > highestWiis.getBlatt().getPoints()) {
						highestWiis = w;
						winningPlayer = p;
					} else if (w.getBlatt().getPoints() == highestWiis.getBlatt().getPoints()) {
						if (w.getBlatt() == highestWiis.getBlatt()) { //same blatt?
							if (w.getHighestCard().compareTo(highestWiis.getHighestCard()) < 0 && gameType == GameType.BottomsUp) {
								highestWiis = w;
								winningPlayer = p;
							} else if (w.getHighestCard().compareTo(highestWiis.getHighestCard()) > 0 && gameType != GameType.BottomsUp) { 
								highestWiis = w;
								winningPlayer = p;
							} else if (w.getHighestCard().compareTo(highestWiis.getHighestCard()) == 0) { //same highest card
								if (isTrumpf) {
									if (w.getHighestCard().getSuit() == trumpf) { //does one have the wiis in trumpf suit?
										highestWiis = w;
										winningPlayer = p;
									} else if (highestWiis.getHighestCard().getSuit() != trumpf) { //same blatt, both no trump suit: forehand wins
										if (p.getBeginningOrder() < winningPlayer.getBeginningOrder()) {
											highestWiis = w;
											winningPlayer = p;
										}
									}
								} else {
									if (p.getBeginningOrder() < winningPlayer.getBeginningOrder()) {
										highestWiis = w;
										winningPlayer = p;
									}
								}
							}
						} else {
							//same points but not same blatt: lower ordinal is stronger (according to rules and how we organized blatt-enum)
							if (w.getBlatt().ordinal() < highestWiis.getBlatt().ordinal()) {
								highestWiis = w;
								winningPlayer = p;
							}
						}
					}
				}
			}
		}
		return winningPlayer;
	}
	
}