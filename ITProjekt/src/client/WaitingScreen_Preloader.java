package client;



import javafx.application.Preloader;
import javafx.application.Preloader.PreloaderNotification;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

/* Kopiert von SWE Modul von R. Bradley*/

public class WaitingScreen_Preloader extends Preloader {
	private Stage stage;
    private Label lblStatus = new Label();
    private Image gif; 

    @Override
    public void start(Stage splashStage) throws Exception {
        this.stage = splashStage;
        
        
        gif = new Image(WaitingScreen_Preloader.class.getResourceAsStream("Bilder/splashscreen.gif"));
        ImageView imv = new ImageView(gif);
       
        
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        root.setId("splash");
        root.setCenter(imv);
        root.setBottom(lblStatus);
        root.setAlignment(lblStatus, Pos.TOP_CENTER);
        
        lblStatus.setText("Es fehlen noch 3 von 4 Spielern");
        lblStatus.setStyle("-fx-text-fill: white");
        lblStatus.setStyle("-fx-text-size: 12px");
        
        Scene scene = new Scene(root, 200, 200);
        
        stage.setScene(scene);

        stage.show();
    }


    /**
     * Provide more detail progress reports
     * @param info Details can be passed in the parameter; the parameter
     *            implements the PreloaderNotification interface. The
     *            application must create a class containing the information it
     *            needs
     */
    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof InfoToSplashScreen) {
            InfoToSplashScreen myInfo = (InfoToSplashScreen) info;
            lblStatus.setText(myInfo.getDetails());
        }
    }

    /**
     * Respond to standard state changes of the main program. Typically, a
     * splash screen will hide itself when the main application starts
     * @param evt The event that is being reported; see possibilities in the
     *            StateChangeNotification documentation
     */
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }

    /**
     * If your program may report an error during initialization
     * @param info Details can be passed in the ErrorNotification object
     * @return true if the error has been displayed; false if Java needs to
     *         display its own error dialog
     */
    @Override
    public boolean handleErrorNotification(Preloader.ErrorNotification info) {
        lblStatus.setText(info.getDetails());
        return true;
    }

    /**
     * This class implements the PreloaderNotification interface, which is used
     * to pass application-notifications to the splash screen
     */
    public static class InfoToSplashScreen implements PreloaderNotification {
        private String details;

        public InfoToSplashScreen(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }

}
