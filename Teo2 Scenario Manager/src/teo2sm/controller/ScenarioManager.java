package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.ScenarioData;
import teo2sm.model.ScenarioFileManager;

public class ScenarioManager {
	private boolean opened;
	private AppRefs app;
	private ScenarioData scenario;
	private boolean isPlaying;
	private PlayManager playManager;
	
	public ScenarioManager(AppRefs app, ScenarioData scenario) {
		opened = true;
		this.app = app;
		this.scenario = scenario;
		isPlaying = false;
		playManager = null;
	}
	
	public void manageScenario() {
		loadScenario();
		int action;
		do {
			action = app.getUI().waitForUserAction();
			manageAction(action);
		} while(opened);
	}
	
	//load scenario in view
	private void loadScenario() {
		app.getUI().showScenes(scenario);
		app.getUI().setTitle(" - "+scenario.getTitle());
		app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
		app.getUI().setOpenedScenario(Constants.SCENARIO_OPENED);
		playManager = new PlayManager(app, this, scenario);
		playManager.start();
	}
	
	//manage user actions
	private void manageAction(int action) {
		switch(action) {
			case Constants.ACTION_PLAY_SCENARIO:
				try {
					managePlay();
				} catch (Exception e) {
					app.getUI().showInvalidPlayOperation(Constants.ACTION_PLAY_SCENARIO);
				}
				break;
				
			case Constants.ACTION_PAUSE_SCENARIO:
			try {
				managePause();
			} catch (Exception e) {
				app.getUI().showInvalidPlayOperation(Constants.ACTION_PAUSE_SCENARIO);
			}
				break;
				
			case Constants.ACTION_STOP_SCENARIO:
			try {
				manageStop();
			} catch (Exception e) {
				app.getUI().showInvalidPlayOperation(Constants.ACTION_STOP_SCENARIO);
			}
				break;
			
			case Constants.ACTION_SAVE_SCENARIO:
				manageSave();
				break;
				
			case Constants.ACTION_CLOSE_SCENARIO:
				opened = false;
				playManager.setOpened(false);
				app.getUI().setOpenedScenario(Constants.SCENARIO_CLOSED);
				app.getUI().setTitle("");
				app.getUI().hideClosedScenario();
				break;
			
			default:
				//return to AppCore
		}
	}
	
	//manage scenario play
	private void managePlay() throws Exception {
		if(isPlaying)
			throw new Exception();
		isPlaying = true;
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
		playManager.playScenario();
	}
	
	//manage scenario pause
	private void managePause() throws Exception {
		if(!isPlaying)
			throw new Exception();
		isPlaying = false;
		app.getUI().setPlayableScenario(Constants.SCENARIO_PAUSED);
		playManager.pauseScenario();
	}
		
	//manage scenario stop
	private void manageStop() throws Exception {
		if(!isPlaying)
			throw new Exception();
		isPlaying = false;
		app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
		playManager.stopScenario();
	}
	
	//manage scenario save
	private void manageSave() {
		String filePath = app.getUI().askSaveFile();
		//check file
		if(filePath != null) {
			ScenarioFileManager sfm = new ScenarioFileManager(scenario, filePath);
			boolean outcome = sfm.saveFile();
			if(!outcome)
				app.getUI().showCannotCreateFile(filePath);
		}
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}
