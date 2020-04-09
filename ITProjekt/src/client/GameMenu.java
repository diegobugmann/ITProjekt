package client;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class GameMenu extends MenuBar {
	
	protected Menu einstellungen;
	protected MenuItem karten;
	protected MenuItem sprache;
	
	protected Menu regeln;
	protected Menu statistik;
	protected Menu about;
	protected Menu exit;
	
	
	public GameMenu() {
		super();
		
		einstellungen = new Menu("Einstellngen");
		karten = new MenuItem("Karten");
		sprache = new MenuItem("Sprache");
		
		einstellungen.getItems().addAll(karten, sprache);
		
		this.getMenus().addAll(regeln, einstellungen, statistik, about, exit);
		
		
		
		
	}

}
