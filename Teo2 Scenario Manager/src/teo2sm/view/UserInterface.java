package teo2sm.view;

import java.util.ArrayList;

import teo2sm.model.ScenarioData;

public interface UserInterface {
	
	public void showFileNotFound(String filePath);
	
	public void showCannotCreateFile(String filePath);
	
	public int waitForUserAction();
	
	public String askFile();
	
	public String askSaveFile();
	
	public void showScenes(ScenarioData scenario);
	
	public void setPlayableScenario(int code);
	
	public void setOpenedScenario(int code);
	
	public void setTitle(String title);
	
	public void hideClosedScenario();
	
	public ScenarioData createScenarioWizard();
	
	public void showInvalidPlayOperation(int code);
	
	public void showTimeSlider(ArrayList<Integer> times);
	
	public void updateTimeSlider(int time, int sceneNumber);
	
	public void setStatusText(String text);
}
