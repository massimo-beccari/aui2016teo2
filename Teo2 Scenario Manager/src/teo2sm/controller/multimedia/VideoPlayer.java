package teo2sm.controller.multimedia;

import javax.swing.JFrame;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class VideoPlayer extends Thread {
	private final JFrame frame = new JFrame();
	private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	private String videoPath;
	
	public VideoPlayer(String videoPath) {
		this.videoPath = videoPath;
        
        frame.setLocation(50, 50);
        frame.setSize(800, 600);
        frame.setContentPane(mediaPlayerComponent);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public void playVideo() {
		mediaPlayerComponent.getMediaPlayer().playMedia(videoPath);
		mediaPlayerComponent.getMediaPlayer().setRepeat(true);
	}
	
	public void resumeVideo() {
		mediaPlayerComponent.getMediaPlayer().play();
	}
	
	public void pauseVideo() {
		mediaPlayerComponent.getMediaPlayer().pause();
	}
	
	public void stopVideo() {
		mediaPlayerComponent.getMediaPlayer().stop();
	}
	
	public void destroyWindow() {
		frame.dispose();
	}
	
	@Override
	public void start() {
		
	}
	
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
}
