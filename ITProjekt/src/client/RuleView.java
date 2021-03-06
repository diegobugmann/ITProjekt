package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
/**
 * 
 * @author Luca Meyer
 * Shows the txt file jassHelp in a new ScrollPane
 */
public class RuleView extends ScrollPane {
	private Scanner fileScanner;
	protected Label content;
	
	public RuleView() {
		super();
		content = new Label("");
		content.setStyle("-fx-font-family: \"Arial\";");
		String text = "";
		try {
			fileScanner = new Scanner(new File(System.getProperty("user.dir")+"/src/client/jassHelp.txt"), "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(fileScanner.hasNextLine()) {
			text += fileScanner.nextLine()+"\n";
		}
		content.setText(text);
		this.setContent(content);
		this.setPadding(new Insets(10, 10, 10, 10));
		
	}

}
