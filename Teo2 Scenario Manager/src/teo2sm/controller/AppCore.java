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
		app.getUI().setOpenedScenario(Constants.SCENARIO_CLOSED);
		while(true) {
			action = app.getUI().waitForUserAction();
			switch(action) {
				case Constants.ACTION_LOAD_SCENARIO:
					loadScenario();
					break;
				case Constants.ACTION_NEW_SCENARIO:
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
		app.getUI().createScenarioWizard();
	}
}
