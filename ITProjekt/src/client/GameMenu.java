package client;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
/**
 * 
 * @author Luca Meyer
 *MenuBar for the Lobby and the GameView
 */
public class GameMenu extends MenuBar {
	
	
	protected Menu spiel;
	protected MenuItem exit;
	
	protected Menu einstellungen;
	protected MenuItem karten;
	protected MenuItem sound;
	
	protected Menu help;
	protected MenuItem regeln;
	protected MenuItem about;
	
	
	public GameMenu() {
		super();
		
		spiel = new Menu("Spiel");
		exit = new MenuItem("Beenden");
		spiel.getItems().add(exit);
		
		einstellungen = new Menu("Einstellungen");
		karten = new MenuItem("Karten");
		sound = new MenuItem("Soundeinstellungen");
		einstellungen.getItems().addAll(karten, sound);
		
		help = new Menu("Hilfe");
		regeln = new MenuItem("Regeln");
		about = new MenuItem("About");
		help.getItems().addAll(regeln, about);
		
		
		this.getStylesheets().add(getClass().getResource("CSS/menu.css").toExternalForm());
		
		this.getMenus().addAll(spiel, einstellungen, help);
		
		
		
		
	}

}
