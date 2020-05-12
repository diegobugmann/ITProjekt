package Test;

import Soundmodule.SoundModule;
import client.ChatBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TestChatView extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("GUI");
		StackPane root = new StackPane();
		BorderPane grid = new BorderPane();
		ChatBox cb = new ChatBox();
        SoundModule sm = new SoundModule();
		cb.getSend().setOnAction(event -> {
			cb.receiveChatMessage("michael", cb.getInput().getText());
			cb.getInput().setText("");
			 sm.playStock();
		});
		grid.setLeft(cb);
        root.getChildren().add(grid);
        primaryStage.setScene(new Scene(root, 900, 500));

       
        
        
        primaryStage.show();
	}
	
}
