package teo2sm.controller;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;

public class PlayManager extends Thread implements BasicPlayerListener {
	private AppRefs app;
	ScenarioManager scenarioManager;
	private ScenarioData scenario;
	private Iterator<SceneData> sceneIterator;
	private SceneData currentScene;
	private Map<?, ?> currentSceneProperties;
	private BasicPlayer storyPlayer;
	private BasicController storyController;
	private BasicPlayer musicPlayer;
	private BasicController musicController;
	private boolean opened;
	private int duration;
	private int position;
	
	public PlayManager(AppRefs app, ScenarioManager sm, ScenarioData scenario) {
		this.app = app;
		scenarioManager = sm;
		this.scenario = scenario;
		sceneIterator = scenario.getScenes().iterator();
		currentScene = sceneIterator.next();
		opened = true;
		storyPlayer = new BasicPlayer();
		storyController = (BasicController) storyPlayer;
	    storyPlayer.addBasicPlayerListener(this);
	    musicPlayer = new BasicPlayer();
	    musicController = (BasicController) musicPlayer;
		musicPlayer.addBasicPlayerListener(this);
	    init();
		setDuration();
		position = 0;
	}

	private void init() {
		try {
	    	storyController.open(new File(currentScene.getStoryPath()));
	    	//musicController.open(new File(currentScene.getBackgroundMusicPath()));
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setDuration() {
		duration = 0;
		for(SceneData scene : scenario.getScenes()) {
			int sceneDuration = calculateSceneDuration(scene);
			duration = duration + sceneDuration;
		}
		app.getUI().showTimeSlider(duration);
	}
	
	private int calculateSceneDuration(SceneData scene) {
		int byteLength = (int) currentSceneProperties.get("mp3.length.bytes");
		int bitRate = (int) currentSceneProperties.get("mp3.bitrate.nominal.bps");
		int duration = byteLength * 8 / bitRate;
		return duration;
	}
	
	public void playScenario() throws BasicPlayerException {
		if(position < 1000) {
			storyController.play();
			//musicController.play();
		} else
			resumeScenario();
	}
	
	private void resumeScenario() throws BasicPlayerException {
		storyController.resume();
		//musicController.resume();
	}
	
	public void pauseScenario() throws BasicPlayerException {
		storyController.pause();
		//musicController.resume();
	}
	
	public void stopScenario() throws BasicPlayerException {
		
	}

	@Override
	public void run() {
		while(opened) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.interrupt();
	}

	@Override
	public void opened(Object arg0, @SuppressWarnings("rawtypes") Map prop) {
		currentSceneProperties = prop;
	}

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map prop) {
		position = (int) (((long) prop.get("mp3.position.microseconds")) / 1000);
	}

	@Override
	public void setController(BasicController arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stateUpdated(BasicPlayerEvent e) {
		if(e.equals(BasicPlayerEvent.EOM)) {
			if(sceneIterator.hasNext()) {
				currentScene = sceneIterator.next();
				init();
			} else {
				app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
				scenarioManager.setPlaying(false);
			}
		}
	}

	public synchronized void setOpened(boolean opened) {
		this.opened = opened;
	}
}
