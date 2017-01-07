package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.ScenarioData;

public class AppCore {
	private AppRefs app;
	
	public AppCore(AppRefs app) {
		this.app = app;
	}
	
	public void startApplication() {
		mainRoutine();
	}
	
	private void mainRoutine() {
		int action;
		action = app.getUI().waitForUserAction();
		switch(action) {
			case Constants.LOAD_SCENARIO_ACTION:
				loadScenario();
				break;
		}
	}
	
	private void loadScenario() {
		String filePath = app.getUI().askFile();
		ScenarioFileManager sfm = new ScenarioFileManager(app, filePath);
		ScenarioData loadedScenario = sfm.getScenario();
		ScenarioManager manager = new ScenarioManager(app, loadedScenario);
		manager.manageScenario();
	}
}
