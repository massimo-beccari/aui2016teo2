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
import teo2sm.controller.CommConstants;
import teo2sm.controller.ScenarioManager;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;
import teo2sm.model.TeoAction;

public class PlayManager extends Thread implements BasicPlayerListener {
	private AppRefs app;
	private ScenarioManager scenarioManager;
	private ScenarioData scenario;
	private Iterator<SceneData> sceneIterator;
	private Iterator<TeoAction> actionIterator;
	private SceneData currentScene;
	private TeoAction currentAction;
	private BasicController storyController;
	private VideoPlayer videoPlayer;
	private boolean opened;
	private int duration;
	private int basePosition;
	private int position;
	private boolean flagEndOfReproduction;
	private boolean flagVideo;
	private boolean flagReinforcement;
	
	public PlayManager(AppRefs app, ScenarioManager sm, ScenarioData scenario, BasicController storyController) {
		this.app = app;
		scenarioManager = sm;
		//story
		this.storyController = storyController;
	    ((BasicPlayer) storyController).addBasicPlayerListener(this);
	    //scenario, current scene, current action
		this.scenario = scenario;
		sceneIterator = scenario.getScenes().iterator();
		currentScene = sceneIterator.next();
		actionIterator = currentScene.getActions().iterator();
		if(actionIterator.hasNext())
			currentAction = actionIterator.next();
		else
			currentAction = null;
		//video player
		videoPlayer = new VideoPlayer(currentScene.getProjectedContentPath(), currentScene.getObjectImagePath());
		videoPlayer.start();
		if(currentScene.getProjectedContentPath().equals(Constants.SCENE_DEFAULT_PATH_NAME))
			flagVideo = false;
		else
			flagVideo = true;
		//
		opened = true;
	    loadMp3();
		setDuration();
		position = 0;
		basePosition = 0;
		flagEndOfReproduction = false;
	}

	private void loadMp3() {
		try {
	    	storyController.open(new File(currentScene.getStoryPath()));
		} catch (BasicPlayerException e) {
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
				e.printStackTrace();
			}
		}
		videoPlayer.closeWindow();
		this.interrupt();
	}

	@Override
	public void opened(Object arg0, @SuppressWarnings("rawtypes") Map prop) { }

	@Override
	public void progress(int arg0, long arg1, byte[] arg2, @SuppressWarnings("rawtypes") Map prop) {
		int relativePosition = ((int) (((long) prop.get("mp3.position.microseconds")) / 1000));
		//position in milliseconds
		position = (basePosition*1000) + relativePosition;
		app.getUI().updateTimeSlider(position/1000, currentScene.getSeqNumber());
		//update Teo mood
		if((currentAction != null) && ((int)(currentAction.getActionTime().toLong()) < relativePosition)) {
			changeTeoMood(currentAction.getActionID());
			if(actionIterator.hasNext())
				currentAction = actionIterator.next();
			else
				currentAction = null;
		}
	}

	@Override
	public void setController(BasicController arg0) { }

	@Override
	public void stateUpdated(BasicPlayerEvent e) {
		if(e.getCode() == BasicPlayerEvent.PLAYING) {
			changeTeoMood(CommConstants.COMM_CMD_MOOD_HAPPY);
			videoPlayer.setFullscreen(true);
			if(currentScene.getSeqNumber() >= 3)
				videoPlayer.setRepeat(true);
			if(flagVideo)
				videoPlayer.playVideo();
		} else if(e.getCode() == BasicPlayerEvent.PAUSED) {
			if(flagVideo)
				videoPlayer.pauseVideo();
		} else if(e.getCode() == BasicPlayerEvent.RESUMED) {
			if(flagVideo)
				videoPlayer.resumeVideo();
		} else if(e.getCode() == BasicPlayerEvent.STOPPED) {
			reinitialize();
		} else if(e.getCode() == BasicPlayerEvent.EOM) {
			flagEndOfReproduction = true;
		}
	}
	
	private void reinitialize() {
		//i pushed button stop
		if(!flagEndOfReproduction) {
			//video player settings
			videoPlayer.setFullscreen(false);
			if(flagVideo)
				videoPlayer.stopVideo();
			app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
			app.getUI().setOpenedScenario(Constants.SCENARIO_OPENED);
			scenarioManager.setPlaying(false);
			basePosition = 0;
			position = 0;
			sceneIterator = scenario.getScenes().iterator();
			currentScene = sceneIterator.next();
			app.getUI().updateTimeSlider(position/1000, currentScene.getSeqNumber());
			loadMp3();
			videoPlayer.setPaths(currentScene.getProjectedContentPath(), currentScene.getObjectImagePath());
			//manage if there is a video file or not
			if(currentScene.getProjectedContentPath().equals(Constants.SCENE_DEFAULT_PATH_NAME))
				flagVideo = false;
			else
				flagVideo = true;
		} else {
			//the reproduction of current scene ended
			flagEndOfReproduction = false;
			if(sceneIterator.hasNext()) {
				System.out.println("Scene "+currentScene.getSeqNumber());
				basePosition = basePosition + calculateSceneDuration(currentScene);
				//in the first scene Teo waits for button interaction
				if(currentScene.getSeqNumber() == 1)
					manageButtonInteraction();
				//in the other scenes, choose if wait for tag o go on directly with next scene
				else if(!currentScene.getRfidObjectTag().equals(Constants.SCENE_RFID_EMPTY))
					manageObjectInteraction();
				if(flagVideo && videoPlayer.isPlaying())
					videoPlayer.stopVideo();
				//set next scene
				currentScene = sceneIterator.next();
				actionIterator = currentScene.getActions().iterator();
				if(actionIterator.hasNext())
					currentAction = actionIterator.next();
				else
					currentAction = null;
				videoPlayer.setPaths(currentScene.getProjectedContentPath(), currentScene.getObjectImagePath());
				loadMp3();
				//manage if there is a video file or not
				if(currentScene.getProjectedContentPath().equals(Constants.SCENE_DEFAULT_PATH_NAME))
					flagVideo = false;
				else
					flagVideo = true;
				//play next scene
				app.getUI().highlightsCurrentScene(currentScene.getSeqNumber());
				try {
					storyController.play();
				} catch (BasicPlayerException e1) {
					e1.printStackTrace();
				}
			} else {
				videoPlayer.setRepeat(false);
				manageHughInteraction();
				playVideoCongrats(Constants.FINAL_VIDEO_FILE);
				//re initialization
				reinitialize();
			}
		}
	}
	
	private void manageObjectInteraction() {
		app.getUI().setPlayableScenario(Constants.SCENARIO_CLOSED);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - RFID: sending waitRfidObject command...");
		String objectTag = app.getCommunicator().waitRfidObject();
		System.out.println("??? "+objectTag);
		if(flagVideo)
			videoPlayer.stopVideo();
		while(!objectTag.equals(currentScene.getRfidObjectTag())) {
			System.out.println("Scene "+currentScene.getSeqNumber()+" - RFID: received WRONG RFID tag: "+objectTag);
			changeTeoMood(CommConstants.COMM_CMD_MOOD_SAD);
			if(!videoPlayer.isPlaying()) {
				videoPlayer.setPaths(currentScene.getReinforcementContentPath(), currentScene.getObjectImagePath());
				videoPlayer.playVideo();
			}
			playAudioReinforcement();
			System.out.println("Scene "+currentScene.getSeqNumber()+" - RFID: sending waitRfidObject command...");
			objectTag = app.getCommunicator().waitRfidObject();
		}
		System.out.println("Scene "+currentScene.getSeqNumber()+" - RFID: received RFID tag: "+objectTag);
		changeTeoMood(CommConstants.COMM_CMD_MOOD_HAPPY);
		playVideoCongrats(Constants.REINFORCEMENT_VIDEO_FILE);
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
	}
	
	private void manageButtonInteraction() {
		int interaction;
		app.getUI().setPlayableScenario(Constants.SCENARIO_CLOSED);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - Button: sending waitButtonInteraction command...");
		do {
			interaction = app.getCommunicator().waitButtonInteraction();
		} while (interaction != CommConstants.COMM_BUTTON_GREY);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - Button: received button interaction: "+interaction);
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
	}
	
	private void manageHughInteraction() {
		int interaction;
		app.getUI().setPlayableScenario(Constants.SCENARIO_CLOSED);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - FSR: sending waitFsrInteraction command...");
		do {
			interaction = app.getCommunicator().waitFsrInteraction();
		} while (/*interaction != CommConstants.COMM_FSR_HUG*/false);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - FSR: received FSR interaction: "+interaction);
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
	}
	
	private void changeTeoMood(String mood) {
		System.out.println("Scene "+currentScene.getSeqNumber()+" - Mood: sending "+mood+" command...");
		app.getCommunicator().setTeoMood(mood);
		System.out.println("Scene "+currentScene.getSeqNumber()+" - Mood: "+mood+" command sent.");
	}
	
	private void playAudioReinforcement() {
		BasicPlayer reinforcementPlayer = new BasicPlayer();
		ReinforcementPlayerListener reinforcementListener = new ReinforcementPlayerListener(reinforcementPlayer, this);
		flagReinforcement = true;
		app.getUI().setPlayableScenario(Constants.SCENARIO_CLOSED);
		reinforcementListener.start();
		while(flagReinforcement) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Error in thread sleep.");
				e.printStackTrace();
			}
		}
		reinforcementListener.setOpened(false);
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
	}
	
	private void playVideoCongrats(String file) {
		if(videoPlayer.isPlaying())
			videoPlayer.stopVideo();
		videoPlayer.setPaths(file, currentScene.getObjectImagePath());
		videoPlayer.playVideo();
		try {
			Thread.sleep(5500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		videoPlayer.stopVideo();
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public void setReinforcement(boolean flag) {
		flagReinforcement = flag;
	}

	public int getPosition() {
		return position;
	}
}
