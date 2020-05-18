package client;

import javafx.scene.control.Label;
/**
 * @author Luca Meyer
 * @author sarah
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;

import Commons.GameType;

public abstract class InfoView extends VBox{

	protected Label trumpflbl;
	protected Label picTrump;
	protected Label popUplbl;
	protected Label popUp;
	
	public InfoView() {
		trumpflbl = new Label("Trumpf");
		picTrump = new Label("");
		
		popUplbl = new Label("Info");
		popUp = new Label("");
		this.setMinWidth(200);
	}
	
	public void updateTrump(int trump, int cardStyle) {		
		if((Integer) trump != null && (Integer) trump != 7) {		
			String cStyle;			
			if(cardStyle==0) {
				cStyle= "franz";
			} else {
				cStyle = "deutsch";
			}
			
			InputStream is1 = getClass().getResourceAsStream("Trumpf_"+ cStyle +"/"+ GameType.values()[trump]+".png");
			Image image = new Image(is1);
			ImageView imv = new ImageView(image);
			imv.setFitWidth(40);
			imv.setFitHeight(40);
			imv.setPreserveRatio(true);
			picTrump.setGraphic(imv);
		} else if((Integer) trump == 7) {
			picTrump.setGraphic(null);
		}else {
			picTrump.setGraphic(null);		}
		
	}
}
