
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
	//Players
	private MediaPlayer backgroundPlayer;
	private MediaPlayer waitingPlayer;
	private MediaPlayer drawPlayer;
	private MediaPlayer mixPlayer;
	private MediaPlayer msgPlayer;
	//FileLocation
	private String location;
	/**
	 * Volumes
	 */
	private double waitingVolume = 0.0;
	private double backgroundvolume = 0.0;
	private double gameVolume = 0.5;

	public SoundModule() {
		//Initalizing all the Mediaplayers and sets the defaultsound
		location = Paths.get("").toUri()+ "src/Soundmodule/";
		location.replace(" ", "%20");
		try {
			URL background = new URL(location+"background.mp3");
			Media backgroundMedia = new Media(background.toString());
			backgroundPlayer = new MediaPlayer(backgroundMedia);
			backgroundPlayer.setVolume(backgroundvolume);
			
			URL waiting = new URL(location+"waiting.mp3");
			Media waitingMedia = new Media(waiting.toString());
			waitingPlayer = new MediaPlayer(waitingMedia);
			waitingPlayer.setVolume(waitingVolume);
			
			URL draw = new URL(location + "draw.mp3");
			Media drawMedia = new Media(draw.toString());
			drawPlayer = new MediaPlayer(drawMedia);
			drawPlayer.setVolume(gameVolume);
			
			URL mix = new URL(location+"mix.mp3");
			Media mixMedia = new Media(mix.toString());
			mixPlayer = new MediaPlayer(mixMedia);
			mixPlayer.setVolume(gameVolume);
			
			URL msg = new URL(location+"newMessage.mp3");
			Media msgMedia = new Media(msg.toString());
			msgPlayer = new MediaPlayer(msgMedia);
			msgPlayer.setVolume(gameVolume);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * Play waiting song when ended mediaplayer get reloaded and plays again
	 */
	public void playWaitingSound() {
		waitingPlayer.play();
		waitingPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
				try {
					URL waiting = new URL(location+"waiting.mp3");
					Media waitingMedia = new Media(waiting.toString());
					waitingPlayer = new MediaPlayer(waitingMedia);
					waitingPlayer.setVolume(waitingVolume);
					playWaitingSound();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	
	public void pauseWaitingSound() {
		waitingPlayer.pause();
	}
	/**
	 * Play background song when ended mediaplayer get reloaded and plays again
	 */
	public void playBackgroundSound() {
		backgroundPlayer.play();
		backgroundPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
				try {
					URL background = new URL(location+"background.mp3");
					Media backgroundMedia = new Media(background.toString());
					backgroundPlayer = new MediaPlayer(backgroundMedia);
					backgroundPlayer.setVolume(backgroundvolume);
					playBackgroundSound();
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	
	public void pauseBackgroundSound() {
		backgroundPlayer.pause();
	}
/**
 * Play and relaod draw sound
 * @param e
 */
	
	public void playDraw(ActionEvent e) {
		drawPlayer.play();
		drawPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
	        	backgroundPlayer.play();
				
				try {
					URL draw = new URL(location + "draw.mp3");
					Media drawMedia = new Media(draw.toString());
					drawPlayer = new MediaPlayer(drawMedia);
					drawPlayer.setVolume(gameVolume);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	/**
	 * Play and reload mix card sound
	 * @param e
	 */
	public void playMix(ActionEvent e) {
		mixPlayer.play();
		mixPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
	        	backgroundPlayer.play();
				
				try {
					URL mix = new URL(location+"mix.mp3");
					Media mixMedia = new Media(mix.toString());
					mixPlayer = new MediaPlayer(mixMedia);
					mixPlayer.setVolume(gameVolume);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	/**
	 * play and reload new Message sound 
	 * @param e
	 */
	public void playNewMesage(ActionEvent e) {
		msgPlayer.play();
		msgPlayer.onEndOfMediaProperty().set(new Runnable(){
	        public void run(){
	        	backgroundPlayer.play();
				
				try {
					URL msg = new URL(location+"newMessage.mp3");
					Media msgMedia = new Media(msg.toString());
					msgPlayer = new MediaPlayer(msgMedia);
					msgPlayer.setVolume(gameVolume);
				} catch (MalformedURLException e) {}
	        }
		});
	}
	
	//Getters and setters--------------------------------------------------------------------------------------
	public double getWaitingVolume() {
		return waitingVolume;
	}

	public void setWaitingVolume(double waitingVolume) {
		this.waitingVolume = waitingVolume;
		//Update the Playervolume
		waitingPlayer.setVolume(waitingVolume);
	}

	public double getBackgroundvolume() {
		return backgroundvolume;
	}

	public void setBackgroundvolume(double backgroundvolume) {
		this.backgroundvolume = backgroundvolume;
		backgroundPlayer.setVolume(backgroundvolume);
	}

	public double getGameVolume() {
		return gameVolume;
	}

	public void setGameVolume(double gameVolume) {
		this.gameVolume = gameVolume;
		mixPlayer.setVolume(gameVolume);
		drawPlayer.setVolume(gameVolume);
	}
	//------------------------------------------------------------------------------------------------------------------
}
