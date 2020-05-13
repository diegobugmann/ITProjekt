 package server;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author digib
 */
public class ServerView {
	
	private ServerModel model;
	protected Stage primaryStage;
	private Label lblPort = new Label("Port");
	protected TextField txtPort = new TextField("6666");
	protected Button btnStart = new Button("Start");
	
	/**
	 * @author digib
	 */
	public ServerView(ServerModel mode, Stage primaryStage) {
		this.model = model;
		this.primaryStage = primaryStage;
		
		HBox root = new HBox(lblPort, txtPort, btnStart);
		root.getStyleClass().add("root");
		
		Scene scene = new Scene(root,300,70);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setTitle("Server CodingKittens");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}