package Soundmodule;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Settingsview for Game sounds including 3 sliders and 2 Buttons as controll elements
 * Volumes can be set between 0 and 100%
 * @author mibe1
 *
 */
public class SoundSettingsView extends Stage{
	private SoundModule sm;
	
	private Scene scene;

	HBox root = new HBox(15);
	VBox labels = new VBox(25);
	VBox sliders = new VBox(25);
	VBox volumes = new VBox(25);
	
	private Slider waitingSoundSlider;
	private Label waitingSoundLabel = new Label("Warte Musik");
	private Label WaitingSoundVolume;
	

	private Slider backgroundSoundSlider;
	private Label backgroundSoundLabel = new Label("hintergrund Musik");
	private Label backgroundSoundVolume;
	

	private Slider gameSoundSoundSlider;
	private Label gameSoundLabel = new Label("Spiel Sounds");
	private Label gameSoundVolume;
	
	private Button apply = new Button("Speichern");
	private Region spacer = new Region();
	private Button cancel = new Button("Verwerfen");
	
	public SoundSettingsView(SoundModule sm) {
		super();
		this.sm = sm;
		
		waitingSoundSlider = new Slider(0.0, 1.0, sm.getWaitingVolume());
		waitingSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				int val = (int) (waitingSoundSlider.getValue()*100);
            	WaitingSoundVolume.setText(val+" %");
			}
		});
		WaitingSoundVolume = new Label((int)(waitingSoundSlider.getValue()*100)+" %");
		
		backgroundSoundSlider = new Slider(0.0, 1.0, sm.getBackgroundvolume());
		backgroundSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	int val = (int) (backgroundSoundSlider.getValue()*100);
            	backgroundSoundVolume.setText(val+" %");
			}
		});
		backgroundSoundVolume = new Label((int)(backgroundSoundSlider.getValue()*100)+"%");
		
		gameSoundSoundSlider = new Slider(0.0, 1.0, sm.getGameVolume());
		gameSoundSoundSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	int val = (int) (gameSoundSoundSlider.getValue()*100);
            	gameSoundVolume.setText( val+" %");
			}
		});
		gameSoundVolume = new Label((int)(gameSoundSoundSlider.getValue()*100)+"%");
		
		apply.setOnAction(event -> {
			applySettings();
			this.close();
		});
		
		cancel.setOnAction(event -> {
			this.close();
		});
		
		
		labels.getChildren().addAll(gameSoundLabel, backgroundSoundLabel, waitingSoundLabel, apply);
		sliders.getChildren().addAll(gameSoundSoundSlider, backgroundSoundSlider, waitingSoundSlider);
		volumes.getChildren().addAll(gameSoundVolume, backgroundSoundVolume, WaitingSoundVolume, cancel);
		root.getChildren().addAll(labels, sliders, volumes);
		root.setPadding(new Insets(20,0,0,20));
		
		scene  = new Scene(root);
		this.setScene(scene);
		this.setHeight(220);
		this.setWidth(400);
	}

	private void applySettings() {
		sm.setBackgroundvolume(backgroundSoundSlider.getValue());
		sm.setGameVolume(gameSoundSoundSlider.getValue());
		sm.setWaitingVolume(waitingSoundSlider.getValue());
	}

}
