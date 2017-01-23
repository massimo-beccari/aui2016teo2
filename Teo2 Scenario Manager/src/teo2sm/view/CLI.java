package teo2sm.view;

import teo2sm.model.ScenarioData;

public class CLI implements UserInterface {
	
	@Override
	public void showFileNotFound(String filePath) {
		System.out.println("Error: file " + filePath + " not found");
	}

	@Override
	public void showCannotCreateFile(String filePath) {
		System.out.println("Error: file " + filePath + " cannot be created");
	}

	@Override
	public int waitForUserAction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String askFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showScenes(ScenarioData scenario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayableScenario(int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOpenedScenario(int code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String askSaveFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hideClosedScenario() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScenarioData createScenarioWizard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showInvalidPlayOperation(int code) {
		// TODO Auto-generated method stub
		
	}
}
