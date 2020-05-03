package client;

import Commons.Wiis;

public class CardNameTranslator {
	
	public static String getBlattName(Wiis w) {
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
				blattName = "Fuenfblatt";
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
				blattName = "Vier Bauern";
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

}
