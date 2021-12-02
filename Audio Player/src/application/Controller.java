package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Controller implements Initializable {
	
	@FXML
	private Button playButton, pauseButton, resetButton, nextButton, previousButton;
	@FXML
	private ProgressBar timeProgressBar;
	@FXML
	private ComboBox<String> speedBox;
	@FXML
	private Slider volumeSlider;
	@FXML
	private Label myLabel;
	
	private File directory;
	private File[] files;
	
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private ArrayList<File> songs;
	private int songNumber;
	private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
	private Timer timer;
	private TimerTask task;
	private boolean running;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		songs = new ArrayList<File>();
		directory = new File("src/music");
		files = directory.listFiles();
		
		if(files != null)
		{
			for(File file : files)
			{
				songs.add(file);
			}
		}
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		myLabel.setText(songs.get(songNumber).getName());
		
		for(int i = 0; i < speeds.length; i++)
		{
			speedBox.getItems().add(Integer.toString(speeds[i]) + "%");
		}
		
		speedBox.setOnAction(this::changeSpeed);
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
			}
		});
		
		beginTimer();
		mediaPlayer.play();
		
	}
	
	public void playMusic() {

		beginTimer();
		changeSpeed(null);
		mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
		mediaPlayer.play();
		
	}
	
	public void pauseMusic() {
		
		cancelTimer();
		mediaPlayer.pause();
			
	}
	
	public void resetMusic() {
		
		timeProgressBar.setProgress(0);
		mediaPlayer.seek(Duration.seconds(0.0));
		
	}
	
	public void nextMusic() {
		
		if(songNumber < songs.size() - 1) 
		{
			songNumber += 1;
			
			mediaPlayer.stop();
			
			if(running)
			{
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			myLabel.setText(songs.get(songNumber).getName());
			
			playMusic();
		} 
		else 
		{
			songNumber = 0;
			
			mediaPlayer.stop();
			
			if(running)
			{
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			myLabel.setText(songs.get(songNumber).getName());
			
			playMusic();
		}
		
	}
	
	public void previousMusic() {
		
		if(songNumber > 0) 
		{
			songNumber -= 1;
			
			mediaPlayer.stop();
			
			if(running)
			{
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			myLabel.setText(songs.get(songNumber).getName());
			
			playMusic();
		}
		else 
		{
			songNumber = songs.size() - 1;
			
			mediaPlayer.stop();
			
			if(running)
			{
				cancelTimer();
			}
			
			media = new Media(songs.get(songNumber).toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			myLabel.setText(songs.get(songNumber).getName());
			
			playMusic();
		}
	}
	
	public void changeSpeed(ActionEvent event) {
		
		if(speedBox.getValue() == null)
		{
			mediaPlayer.setRate(1);
		} 
		else 
		{
			mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
		}	
	}
	
	public void beginTimer() 
	{
		timer = new Timer();
		task = new TimerTask() {
			
			@Override
			public void run() {
				running = true;
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				timeProgressBar.setProgress(current/end);
				
				if(current / end == 1)
				{
					cancelTimer();
				}
			}
		};
		timer.scheduleAtFixedRate(task, 0, 10);
	}
	
	public void cancelTimer() 
	{
		running = false;
		timer.cancel();
	}
}