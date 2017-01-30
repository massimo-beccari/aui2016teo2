package teo2sm.controller.multimedia;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.controller.ScenarioManager;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;

public class PlayManager extends Thread implements BasicPlayerListener {
	private AppRefs app;
	private ScenarioManager scenarioManager;
	private ScenarioData scenario;
	private Iterator<SceneData> sceneIterator;
	private SceneData currentScene;
	private BasicController storyController;
	private VideoPlayer videoPlayer;
	private boolean opened;
	private int duration;
	private int basePosition;
	private int position;
	
	public PlayManager(AppRefs app, ScenarioManager sm, ScenarioData scenario, BasicController storyController) {
		this.app = app;
		scenarioManager = sm;
		//story
		this.storyController = storyController;
	    ((BasicPlayer) storyController).addBasicPlayerListener(this);
	    //
		this.scenario = scenario;
		sceneIterator = scenario.getScenes().iterator();
		currentScene = sceneIterator.next();
		//video player
		videoPlayer = new VideoPlayer(currentScene.getProjectedContentPath());
		//
		opened = true;
	    loadMp3();
		setDuration();
		position = 0;
		basePosition = 0;
	}

	private void loadMp3() {
		try {
	    	storyController.open(new File(currentScene.getStoryPath()));
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setDuration() {
		duration = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(SceneData scene : scenario.getScenes()) {
			int sceneDuration = calculateSceneDuration(scene);
			duration = duration + sceneDuration;
			list.add(new Integer(sceneDuration));
		}
		list.add(0, new Integer(duration));
		app.getUI().showTimeSlider(list);
	}
	
	private int calculateSceneDuration(SceneData scene) {
		BasicPlayer player = new BasicPlayer();
		BasicController controller = (BasicController) player;
		MiniListener listener = new MiniListener();
		player.addBasicPlayerListener(listener);
		try {
			controller.open(new File(scene.getStoryPath()));
			int byteLength = (int) (listener.getProperties().get("mp3.length.bytes"));
			int bitRate = (int) (listener.getProperties().get("mp3.bitrate.nominal.bps"));
			int duration = byteLength * 8 / bitRate;
			return duration;
		} catch (BasicPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	class MiniListener implements BasicPlayerListener {
		private Map<?, ?> prop;

		@Override
		public void opened(Object arg0, @SuppressWarnings("rawtypes") Map p) {
			prop = p;
		}

		@Override
		public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map arg3) { }

		@Override
		public void setController(BasicController arg0) { }

		@Override
		public void stateUpdated(BasicPlayerEvent arg0) { }
		
		public Map<?, ?> getProperties() {
			return prop;
		}
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
		videoPlayer.destroyWindow();
		this.interrupt();
	}

	@Override
	public void opened(Object arg0, @SuppressWarnings("rawtypes") Map prop) { }

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map prop) {
		position = (basePosition*1000) + ((int) (((long) prop.get("mp3.position.microseconds")) / 1000));
		app.getUI().updateTimeSlider(position/1000, currentScene.getSeqNumber());
	}

	@Override
	public void setController(BasicController arg0) { }

	@Override
	public void stateUpdated(BasicPlayerEvent e) {
		if(e.getCode() == BasicPlayerEvent.PLAYING) {
			videoPlayer.playVideo();
		} else if(e.getCode() == BasicPlayerEvent.PAUSED) {
			videoPlayer.pauseVideo();
		} else if(e.getCode() == BasicPlayerEvent.RESUMED) {
			videoPlayer.resumeVideo();
		} else if(e.getCode() == BasicPlayerEvent.STOPPED) {
			videoPlayer.stopVideo();
			reinitialize();
		} else if(e.getCode() == BasicPlayerEvent.EOM) {
			if(sceneIterator.hasNext()) {
				basePosition = basePosition + calculateSceneDuration(currentScene);
				currentScene = sceneIterator.next();
				loadMp3();
				videoPlayer.stopVideo();
				videoPlayer.setVideoPath(currentScene.getProjectedContentPath());
			} else {
				//re initialization
				reinitialize();
			}
		}
	}
	
	private void reinitialize() {
		app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
		app.getUI().setOpenedScenario(Constants.SCENARIO_OPENED);
		scenarioManager.setPlaying(false);
		basePosition = 0;
		position = 0;
		sceneIterator = scenario.getScenes().iterator();
		currentScene = sceneIterator.next();
		app.getUI().updateTimeSlider(position/1000, currentScene.getSeqNumber());
		loadMp3();
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public int getPosition() {
		return position;
	}
}
