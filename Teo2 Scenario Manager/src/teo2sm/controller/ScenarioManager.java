package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;

public class ScenarioManager {
	private AppRefs app;
	private ScenarioData scenario;
	
	public ScenarioManager(AppRefs app, ScenarioData scenario) {
		this.app = app;
		this.scenario = scenario;
	}
	
	public void manageScenario() {
		//load scenario in view
		for(SceneData scene : scenario.getScenes()) {
			
		}
	}
}
