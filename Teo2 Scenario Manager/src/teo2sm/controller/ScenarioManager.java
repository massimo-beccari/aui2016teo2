package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;

public class ScenarioManager {
	private boolean opened;
	private AppRefs app;
	private ScenarioData scenario;
	
	public ScenarioManager(AppRefs app, ScenarioData scenario) {
		opened = true;
		this.app = app;
		this.scenario = scenario;
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
		for(SceneData scene : scenario.getScenes()) {
			app.getUI().showScene(scene.getSeqNumber());
		}
		app.getUI().setTitle(scenario.getTitle());
		app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
	}
	
	//manage user actions
	private void manageAction(int action) {
		switch(action) {
			case Constants.ACTION_PLAY_SCENARIO:
				managePlay();
				break;
				
			case Constants.ACTION_PAUSE_SCENARIO:
				managePause();
				break;
				
			case Constants.ACTION_STOP_SCENARIO:
				manageStop();
				break;
				
			case Constants.ACTION_CLOSE_SCENARIO:
				opened = false;
				break;
			
			default:
				//return to AppCore
		}
	}
	
	//manage scenario play
	private void managePlay() {
		app.getUI().setPlayableScenario(Constants.SCENARIO_PLAYED);
		
	}
	
	//manage scenario pause
	private void managePause() {
		app.getUI().setPlayableScenario(Constants.SCENARIO_PAUSED);
			
	}
		
	//manage scenario stop
	private void manageStop() {
		app.getUI().setPlayableScenario(Constants.SCENARIO_STOPPED);
			
	}
}
