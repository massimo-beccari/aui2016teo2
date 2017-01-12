package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.ScenarioData;
import teo2sm.model.ScenarioFileManager;

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
		while(true) {
			action = app.getUI().waitForUserAction();
			switch(action) {
				case Constants.LOAD_SCENARIO_ACTION:
					loadScenario();
					break;
				case Constants.NEW_SCENARIO_ACTION:
					newScenario();
					break;
			}
		}
	}
	
	private void loadScenario() {
		String filePath = app.getUI().askFile();
		//check file
		if(filePath != null) {
			ScenarioFileManager sfm = new ScenarioFileManager(filePath);
			ScenarioData loadedScenario = sfm.getScenario();
			//check loading
			if(loadedScenario != null) {
				ScenarioManager manager = new ScenarioManager(app, loadedScenario);
				manager.manageScenario();
			} else {
				app.getUI().showFileNotFound(filePath);
			}
		}
	}
	
	private void newScenario() {
		
	}
}
