package teo2sm.controller.multimedia;

import java.io.File;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class ReinforcementPlayerListener extends Thread implements BasicPlayerListener {
	private PlayManager manager;
	private BasicPlayer musicPlayer;
	private BasicController musicController;
	private boolean opened;
	
	public ReinforcementPlayerListener(BasicPlayer musicPlayer, PlayManager manager) {
		this.musicPlayer = musicPlayer;
		this.musicController = (BasicController) musicPlayer;
		this.musicPlayer.addBasicPlayerListener(this);
		this.manager = manager;
		opened = true;
	    init();
	}

	private void init() {
		try {
			musicController.open(new File("res/00_oh_no_cerca_ancora.MP3"));
		} catch (BasicPlayerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			musicController.play();
		} catch (BasicPlayerException e1) {
			System.err.println("Error in playing reinforcement audio.");
			e1.printStackTrace();
		}
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
			manager.setReinforcement(false);
		}
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}
}
