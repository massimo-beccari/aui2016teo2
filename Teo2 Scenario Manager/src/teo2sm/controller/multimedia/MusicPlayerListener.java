package teo2sm.controller.multimedia;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import teo2sm.model.SceneData;

public class MusicPlayerListener extends Thread implements BasicPlayerListener {
	private SceneData currentScene;
	private BasicPlayer musicPlayer;
	private BasicController musicController;
	private boolean opened;
	
	public MusicPlayerListener(SceneData scene, BasicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		this.musicController = (BasicController) musicPlayer;
		this.musicPlayer.addBasicPlayerListener(this);
	    currentScene = scene;
		opened = true;
	    init();
	}

	private void init() {
		try {
			musicController.open(new File(currentScene.getReinforcementContentPath()));
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(opened) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.interrupt();
	}

	@Override
	public void opened(Object arg0, @SuppressWarnings("rawtypes") Map prop) { }

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map prop) { }

	@Override
	public void setController(BasicController arg0) { }

	@Override
	public void stateUpdated(BasicPlayerEvent e) {
		if(e.getCode() == BasicPlayerEvent.EOM) {
			try {
				musicController.stop();
				musicController.play();
			} catch (BasicPlayerException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public void setCurrentScene(SceneData currentScene) {
		this.currentScene = currentScene;
		init();
	}
}
