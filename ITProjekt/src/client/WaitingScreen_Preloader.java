package client;



import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 * @author Luca Meyer
 * Copied from SWE Modul von R. Bradley
 */

public class WaitingScreen_Preloader extends Preloader {
	private Stage stage;
    private Label lblStatus;
    private Image gif;
    protected int anzahlPers;
    protected Button abbruchBtn;
    protected VBox vBox;
    

    @Override
    public void start(Stage splashStage) throws Exception {
        this.stage = splashStage;
        lblStatus = new Label();
        lblStatus.setId("statuslbl");
        
        vBox = new VBox();
        abbruchBtn = new Button("Abbruch");
        Region spacer1 = new Region();
        spacer1.setPrefHeight(30);
        
        vBox.getChildren().addAll(lblStatus, spacer1, abbruchBtn);
        vBox.setAlignment(Pos.CENTER);
        
        gif = new Image(WaitingScreen_Preloader.class.getResourceAsStream("Bilder/splashscreen.gif"));
        ImageView imv = new ImageView(gif);
       
        
        BorderPane root = new BorderPane();
        BorderPane.setAlignment(vBox, Pos.BASELINE_CENTER);
        BorderPane.setMargin(vBox, new Insets(0, 0, 50, 0));
        
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setId("splash");
        root.setCenter(imv); 
        
        root.setBottom(vBox);
       
        anzahlPers = 1;
        lblStatus.setText("Es "+((4-anzahlPers == 1) ? "fehlt noch" : "fehlen noch")+ " " +(4-anzahlPers)+ " "+
        ((4-anzahlPers == 1) ? "Person" : "Personen"));
        
       
        Scene scene = new Scene(root, 200, 200);
        scene.getStylesheets().add(getClass().getResource("CSS/splash.css").toExternalForm());
         
        stage.setScene(scene);

        stage.show();
        
    }

	public void setMissingPers(int anzahlPers) {
		this.anzahlPers = anzahlPers;
		
	}
    
	/**
	 * updates the number of missing players
	 */
	public void updateAnzahlPers(int numOfPers) {
        anzahlPers = numOfPers;
        lblStatus.setText("Es "+((4-anzahlPers == 1) ? "fehlt noch" : "fehlen noch")+ " " +(4-anzahlPers)+ " "+
        ((4-anzahlPers == 1) ? "Person" : "Personen"));
	}

}
