
package Soundmodule;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
/**
 * Plays game Sounds on deman every sound is implemented as own Mediaplayer so sounds are preloaded an can just be played on demand
 * @author mibe1
 *
 */
public class SoundModule {

	private MediaPlayer backgroundPlayer;
	private MediaPlayer drawPlayer;
	private MediaPlayer mixPlayer;
	private String location;
	
	public SoundModule() {
		
		location = Paths.get("").toUri()+ "src/Soundmodule/";
		location.replace(" ", "%20");
		try {
			URL background = new URL(location+"background.mp3");
			Media backgroundMedia = new Media(background.toString());
			backgroundPlayer = new MediaPlayer(backgroundMedia);
			URL draw = new URL(location + "draw.mp3");
			Media drawMedia = new Media(draw.toString());
			drawPlayer = new MediaPlayer(drawMedia);
			URL mix = new URL(location+"mix.mp3");
			Media mixMedia = new Media(mix.toString());
			mixPlayer = new MediaPlayer(mixMedia);
			
			
			//TrainingsCode
			backgroundPlayer.setVolume(0);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void playBackgroundSound() {
		backgroundPlayer.play();
		backgroundPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
				try {
					URL background = new URL(location+"background.mp3");
					Media backgroundMedia = new Media(background.toString());
					backgroundPlayer = new MediaPlayer(backgroundMedia);
					backgroundPlayer.play();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	
	public void pauseBackgroundSound() {
		backgroundPlayer.pause();
		backgroundPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
				try {
					URL background = new URL(location+"background.mp3");
					Media backgroundMedia = new Media(background.toString());
					backgroundPlayer = new MediaPlayer(backgroundMedia);
					backgroundPlayer.play();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}

	public void playDraw(ActionEvent e) {
		backgroundPlayer.pause();
		drawPlayer.play();
		drawPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
	        	backgroundPlayer.play();
				
				try {
					URL draw = new URL(location + "draw.mp3");
					Media drawMedia = new Media(draw.toString());
					drawPlayer = new MediaPlayer(drawMedia);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	public void playMix(ActionEvent e) {
		backgroundPlayer.pause();
		mixPlayer.play();
		mixPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
	        	backgroundPlayer.play();
				
				try {
					URL mix = new URL(location+"mix.mp3");
					Media mixMedia = new Media(mix.toString());
					mixPlayer = new MediaPlayer(mixMedia);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}

}
