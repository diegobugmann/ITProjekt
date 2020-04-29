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
	 * @return ArrayList<Wiis> containing ALL possible Wiis objects for players hand (regarding BIG-Wiis)
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
			
		//6 and 5-Blatt can be paired with vierlinge
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
				ArrayList<Card> vierlinge = removeVierlinge(secondWiis, handCopy);
				thirdWiis = isVierlinge(handCopy); //check for another vierlinge after removing the other ones
				if (thirdWiis != null) {
					wiis.add(thirdWiis);
				} else {
					addVierlinge(handCopy, vierlinge); //re add the vierlinge
					removeBlatt(firstWiis, handCopy); //and remove the other blatt
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
					//allenfalls remove, um weniger zu durchsuchen?
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
	 * @return Wiis
	 * highest blatt as a Wiis-object or null if no blatt is found
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
	 * @return ArrayList<Card>
	 * removes all the cards corresponding to wiis from the hand in order to search for more wiis of the same type
	 */
	private static ArrayList<Card> removeVierlinge(Wiis wiis, ArrayList<Card> handCopy) {
		ArrayList<Card> vierlinge = new ArrayList<Card>();
		Rank vierling = wiis.getHighestCard().getRank();
		for (int i = handCopy.size()-1; i >= 0; i--) {
			if (handCopy.get(i).getRank() == vierling) {
				vierlinge.add(handCopy.get(i));
				handCopy.remove(i);
			}
		}
		return vierlinge;
	}
	
	/**
	 * @author digib
	 * adds the vierlinge back to the hand
	 */
	private static void addVierlinge(ArrayList<Card> handCopy, ArrayList<Card> vierlinge) {
		for (Card c : vierlinge) {
			handCopy.add(c);
		}
	}
	
	public static Player validateWiisWinner(ArrayList<ArrayList<Wiis>> wiis, ArrayList<Player> players, GameType gameType) {
		
		//BEI GLEICH VIELEN PUNKTEN IST DER TIEFERE ORDINAL DER HÖHERE WEIS
		
		boolean isTrumpf = gameType != GameType.TopsDown && gameType != GameType.BottomsUp;
		Suit trumpf = null;
		if (isTrumpf) 
			trumpf = PlayValidation.getTrumpfAsSuit(gameType);
		Wiis highestWiis = wiis.get(0).get(0); //suppose the first wiis is the highest;
		Player winningPlayer = players.get(0);
		for (ArrayList<Wiis> wiisList : wiis) {
			for (Wiis w : wiisList) {
				if (highestWiis != w) { //don't compare the first wiis with itself
					if (w.getBlatt().getPoints() > highestWiis.getBlatt().getPoints()) {
						highestWiis = w;
						winningPlayer = (players.get(wiis.indexOf(wiisList)));
					} else if (w.getBlatt().getPoints() == highestWiis.getBlatt().getPoints()) {
						if (w.getBlatt() == highestWiis.getBlatt()) { //same blatt?
							
							
							
							if (w.getHighestCard().compareTo(highestWiis.getHighestCard()) > 0) {
								highestWiis = w;
								winningPlayer = (players.get(wiis.indexOf(wiisList)));
							} else if (w.getHighestCard().compareTo(highestWiis.getHighestCard()) == 0) { //same highest card
								if (isTrumpf) {
									if (w.getHighestCard().getSuit() == trumpf) { //does one have the wiis in trumpf suit?
										highestWiis = w;
										winningPlayer = (players.get(wiis.indexOf(wiisList)));
									} else if (highestWiis.getHighestCard().getSuit() != trumpf) {
										//TODO schauen, wer zuerst dran war
									}
								} else {
									//TODO hier direkt schauen, wer zuerst dran war
								}
							}
							

							
						} else {
							//same points but not same blatt: lower ordinal is stronger (according to rules)
							if (w.getBlatt().ordinal() < highestWiis.getBlatt().ordinal()) {
								highestWiis = w;
								winningPlayer = (players.get(wiis.indexOf(wiisList)));
							}
						}

					}
				}
				
			}
		}
		return null;
	}
	
}