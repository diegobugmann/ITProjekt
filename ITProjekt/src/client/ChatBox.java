package client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
/**
 * VBox to process Chat messages from other players
 * @author mibe1
 *
 */
public class ChatBox extends VBox {
	private TextArea centerArea = new TextArea();
	private HBox controls = new HBox(10);
	private Button send = new Button(">");
	private TextField input = new TextField();
	
	/**
	 * Intiialze elemetns and set Styles
	 */
	public ChatBox() {
		super(15);
		
		this.getStylesheets().add(getClass().getResource("CSS/chat.css").toExternalForm());
		
		centerArea.setEditable(false);
		centerArea.setWrapText(true);
		
		//Stackoverflow code to scroll to the Bottom when the Text is changed
		centerArea.textProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldValue,
		            Object newValue) {
		    	centerArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
		    }
		});
		
		input.setMaxWidth(145);
		//Send message on Enter from Stackoverflow
		input.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	send.fire();
	            }
	        }
	    });
		controls.getChildren().addAll(input, send);
		this.getChildren().addAll(centerArea, controls);
		this.setVgrow(centerArea, Priority.ALWAYS);
		this.setPadding(new Insets(10,10,10,10));
	}
	
	/**
	 * New Messages get added to the centerArea
	 * @param sender
	 * @param message
	 */
	public void receiveChatMessage(String sender, String message) {
		centerArea.setText(centerArea.getText() + "\r\n"+ sender + ": "+message);
		centerArea.appendText(""); 
	}

	public Button getSend() {
		return send;
	}

	public TextField getInput() {
		return input;
	}
	
	
}
