 package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import client.LobbyView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private Label ipAdress;
	private Label localHost = new Label("Localhost: 127.0.0.1");
	
	/**
	 * @author digib
	 */
	public ServerView(ServerModel model, Stage primaryStage) {
		this.model = model;
		this.primaryStage = primaryStage;
		
		HBox controls = new HBox(lblPort, txtPort, btnStart);
		controls.getStyleClass().add("controls");
		
		//IP Adress from Michael
		VBox root = new VBox();
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAdress = new Label("IP Adresse: " + inetAddress.getHostAddress());
			
		} catch (UnknownHostException e) {
			ipAdress = new Label();
		}
		root.getChildren().addAll(controls, ipAdress,localHost);
		root.getStyleClass().add("root");
		
		Scene scene = new Scene(root,320,100);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		btnStart.getStyleClass().add("Button");
		primaryStage.setTitle("Server CodingKittens");
		primaryStage.getIcons().add(new Image(ServerView.class.getResourceAsStream("iconServer.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}