package teo2sm.controller.multimedia;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.player.AudioDevice;
import uk.co.caprica.vlcj.player.AudioOutput;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;

public class VideoPlayer extends Thread {
	private final JFrame frame = new JFrame();
	private final MediaPlayerFactory factory;
	private final EmbeddedMediaPlayer mediaPlayer;
	private final CanvasVideoSurface videoSurface;
	private final JPanel contentPane;
	private final List<AudioOutput> outputs;
	private final AudioOutput pcOut;
	private final AudioDevice pcSpeakers;
	private final Canvas canvas;
	
	private String videoPath;
	private String imagePath;
	private JLabel reinforcementImage;
	private FullScreenStrategy fullScreenStrategy;
	
	public VideoPlayer(String videoPath, String imagePath) {
		this.videoPath = videoPath;
		this.imagePath = imagePath;
		reinforcementImage = new JLabel(new ImageIcon(imagePath));
		
		canvas = new Canvas();
		canvas.setBackground(Color.black);
		
		factory = new MediaPlayerFactory();
        mediaPlayer = factory.newEmbeddedMediaPlayer();
        outputs = factory.getAudioOutputs();
        pcOut = outputs.get(5);
        pcSpeakers = pcOut.getDevices().get(2);
        mediaPlayer.setAudioOutput(pcOut.getName());
        mediaPlayer.setAudioOutputDevice(null, pcSpeakers.getDeviceId());
        videoSurface = factory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        contentPane.setLayout(new BorderLayout(8, 8));
        contentPane.setBackground(Color.black);
        contentPane.add(canvas, BorderLayout.CENTER);
		
		fullScreenStrategy = new Win32FullScreenStrategy(frame);
		mediaPlayer.setFullScreenStrategy(fullScreenStrategy);

		/*int i = 0;
		for(AudioOutput ao : outputs) {
			System.out.println(i + " + " + ao.toString());
			int j = 0;
			for(AudioDevice ad : ao.getDevices()) {
				System.out.println("   " + j + " - " + ad.getDeviceId());
				j++;
			}
			i++;
		}*/
        
        frame.setLocation(50, 50);
        frame.setSize(800, 600);
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
	}
	
	public void playVideo() {
		mediaPlayer.setRepeat(true);
		mediaPlayer.setFullScreen(true);
		mediaPlayer.playMedia(videoPath);
	}
	
	public void resumeVideo() {
		mediaPlayer.play();
	}
	
	public void pauseVideo() {
		mediaPlayer.pause();
	}
	
	public void stopVideo() {
		mediaPlayer.stop();
	}
	
	public void closeWindow() {
		mediaPlayer.setFullScreen(false);
		mediaPlayer.release();
		factory.release();
		frame.dispose();
	}
	
	@Override
	public void start() {
		
	}
	
	public void setPaths(String videoPath, String imagePath) {
		this.videoPath = videoPath;
		this.imagePath = imagePath;
		reinforcementImage = new JLabel(new ImageIcon(this.imagePath));
	}
	
	public synchronized void switchToImage() {
		frame.setContentPane(reinforcementImage);
		reinforcementImage.updateUI();
	}
	
	public void switchToVideo() {
		frame.setContentPane(contentPane);
	}
}
