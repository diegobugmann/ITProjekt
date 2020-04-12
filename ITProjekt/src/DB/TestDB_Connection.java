
package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TestDB_Connection extends Application{
	
	
	//UI from "JDBC in a Nutshell"(B. Richards)
	
	Label lblIP = new Label("IP address");
    TextField txtIP = new TextField("127.0.0.1");
    Label lblPort = new Label("Port");
    TextField txtPort = new TextField("3306");
    Label lblUser = new Label("User");
    TextField txtUser = new TextField("root");
    Label lblPassword = new Label("Password");
    PasswordField txtPassword = new PasswordField();
    Button btnGo = new Button("Go");
    
    TextArea txtLog = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 GridPane gridControls = new GridPane();
	        gridControls.setHgap(20);
	        gridControls.setVgap(5);
	        gridControls.add(lblIP, 0, 0);
	        gridControls.add(txtIP, 1, 0);
	        gridControls.add(lblPort, 0, 1);
	        gridControls.add(txtPort, 1, 1);
	        gridControls.add(lblUser, 2, 0);
	        gridControls.add(txtUser, 3, 0);
	        gridControls.add(lblPassword, 2, 1);
	        gridControls.add(txtPassword, 3, 1);
	        gridControls.add(btnGo, 4, 0);
	        
	        
	        BorderPane root = new BorderPane();
	        root.setTop(gridControls);
	        root.setCenter(txtLog);
	        
	        btnGo.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                testDatabase();
	            }
	        });
	        
	        Scene scene = new Scene(root);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Hello, MySQL");
	        primaryStage.show();
		
	}
	
	private void testDatabase() {
		//nothing in yet, just remove error
	}


}
