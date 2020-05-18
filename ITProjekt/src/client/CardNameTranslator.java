package client;

import Commons.Wiis;

/**
 * 
 * @author sarah
 *
 */

public class CardNameTranslator {
	
	public static String getBlattName(Wiis w, int isDeutsch) {
		String blattName = "";
		switch (w.getBlatt()){
			case dreiblatt :{
				blattName = "Dreiblatt";
				break; 
			}
			case vierblatt :{
				blattName = "Vierblatt";
				break; 
			}
			case fuenfblatt :{
				blattName = "Fünfblatt";
				break; 
			}
			case sechsblatt :{
				blattName = "Sechsblatt";
				break; 
			}
			case siebenblatt :{
				blattName = "Siebenblatt";
				break; 
			}
			case achtblatt :{
				blattName = "Achtblatt";
				break; 
			}
			case neunblatt :{
				blattName = "Neunblatt";
				break; 
			}
			case viergleiche :{
				blattName = "Vier Gleiche";
				break;
			}
			case vierNeuner :{
				blattName = "Vier Neuner";
				break;
			}
			case vierBauern :{
				if(isDeutsch == 1) {
					blattName = "Vier Under";					
				} else {
					blattName = "Vier Buure";					
				}
				break; 
			}
		}
		return blattName;
	}
	
	public static String getSuitName(Wiis w, int isDeutsch) {
		String suitName = "";
		if(isDeutsch == 1) {
			switch (w.getHighestCard().getSuit()){
				case BellsOrClubs :{
					suitName = "Schellen";
					break; 
				}
				case AcornsOrDiamonds :{
					suitName = "Eicheln";
					break; 
				}
				case RosesOrHearts :{
					suitName = "Rosen";
					break; 
				}
				case ShieldsOrSpades :{
					suitName = "Schilten";
					break; 
				}
			}
		} else {
			switch (w.getHighestCard().getSuit()){
			case BellsOrClubs :{
				suitName = "Kreuz";
				break; 
			}
			case AcornsOrDiamonds :{
				suitName = "Egge";
				break; 
			}
			case RosesOrHearts :{
				suitName = "Herz";
				break; 
			}
			case ShieldsOrSpades :{
				suitName = "Schaufel";
				break; 
			}
		}
		}
		return suitName;
	}
	
	public static String getRankName(Wiis w, int isDeutsch) {
		String rankName = "";
		
		switch (w.getHighestCard().getRank()){
			case Six :{
				rankName = "Sechs";
				break; 
			}
			case Seven :{
				rankName = "Sieben";
				break; 
			}
			case Eight :{
				rankName = "Acht";
				break; 
			}
			case Nine :{
				rankName = "Neun";
				break; 
			}
			case Ten :{
				rankName = "Zehn";
				break; 
			}
			case Jack :{
				if(isDeutsch == 1) {
					rankName = "Under";					
				} else {
					rankName = "Buur";					
				}
				break; 
			}
			case Queen :{
				if(isDeutsch == 1) {
					rankName = "Ober";					
				} else {
					rankName = "Dame";					
				}
				break; 
			}
			case King :{
				rankName = "König";
				break; 
			}
			case Ace :{
				rankName = "Ass";
				break; 
			}
		}
		return rankName;
		
	}

}
