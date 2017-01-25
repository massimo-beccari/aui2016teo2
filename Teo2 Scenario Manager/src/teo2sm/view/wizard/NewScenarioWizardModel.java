package teo2sm.view.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.event.ChangeListener;

import teo2sm.Constants;
import teo2sm.model.ActionTime;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;
import teo2sm.model.TeoAction;

public class NewScenarioWizardModel implements WizardModel<ScenarioData> {
	private Map<String, WizardPage> pages;
	private String currentState;
	private int currentStateNum;
	private int sceneNumber;
	private ArrayList<ChangeListener> listeners;
	private ScenarioData result;
	private Map<Integer, SceneData> rawResult;
	
	public NewScenarioWizardModel() {
		pages = new HashMap<String, WizardPage>();
		currentState = Constants.WIZARD_PAGE_FIRST;
		currentStateNum = 0;
		sceneNumber = 0;
		listeners = new ArrayList<ChangeListener>();
		result = new ScenarioData();
		rawResult = new HashMap<Integer, SceneData>();
		setupPages();
	}
	
	private void setupPages() {
		NewScenarioWizardPage1 page1 = new NewScenarioWizardPage1();
		NewScenarioWizardPage2 page2 = new NewScenarioWizardPage2();
		pages.put(Constants.WIZARD_PAGE_FIRST, page1);
		pages.put(Constants.WIZARD_PAGE_SCENE, page2);
		pages.get(currentState).onPageShown();
	}
	
	@Override
	public boolean completable() {
		if(currentStateNum == sceneNumber && !pages.get(currentState).getError())
			return true;
		return false;
	}

	@Override
	public ScenarioData complete() throws IllegalStateException {
		if(!completable())
			throw new IllegalStateException();
		NewScenarioWizardPage2 lastPage = (NewScenarioWizardPage2) pages.get(currentState);
		lastPage.onPageHidden();
		loadSceneData(lastPage, currentStateNum);
		for(int i = 1; i <= sceneNumber; i++)
			result.getScenes().add(rawResult.get(i));
		return result;
	}

	@Override
	public Map<String, WizardPage> getAllPages() {
		return pages;
	}

	@Override
	public String getCurrentState() {
		return currentState;
	}

	@Override
	public String forward() {
		//load data in scenario
		WizardPage oldPage = pages.get(currentState);
		oldPage.onPageHidden();
		if(currentStateNum == 0) {
			NewScenarioWizardPage1 firstPage = (NewScenarioWizardPage1) oldPage;
			sceneNumber = firstPage.getSceneNumber();
			result.setTitle(firstPage.getTitle());
		} else
			loadSceneData((NewScenarioWizardPage2) oldPage, currentStateNum);
		//update state num
		String oldState = currentState;
		currentStateNum = currentStateNum + 1;
		currentState = Constants.WIZARD_PAGE_SCENE;
		//prepare new page
		pages.get(currentState).onPageAboutToDisplay();
		pages.get(currentState).onPageShown();
		/*if(currentStateNum == 0 || rawResult.containsKey(currentStateNum))
			reloadPage(currentStateNum);*/
		//inform listeners
		WizardModelChangeEvent event = new WizardModelChangeEvent(this, currentState, oldState);
		for(ChangeListener l : listeners)
			l.stateChanged(event);
		return currentState;
	}

	@Override
	public boolean canGoForward() {
		if(completable() || currentStateNum < sceneNumber)
			return true;
		return false;
	}

	@Override
	public String back() {
		//load data in scenario
		WizardPage oldPage = pages.get(currentState);
		oldPage.onPageHidden();
		loadSceneData((NewScenarioWizardPage2) oldPage, currentStateNum);
		//get new state
		String oldState = currentState;
		currentStateNum = currentStateNum - 1;
		if(currentStateNum == 0)
			currentState = Constants.WIZARD_PAGE_FIRST;
		//prepare new page
		pages.get(currentState).onPageAboutToDisplay();
		pages.get(currentState).onPageShown();
		/*if(currentStateNum == 0 || rawResult.containsKey(currentStateNum))
			reloadPage(currentStateNum);*/
		//inform listeners
		WizardModelChangeEvent event = new WizardModelChangeEvent(this, currentState, oldState);
		for(ChangeListener l : listeners)
			l.stateChanged(event);
		return currentState;
	}

	@Override
	public boolean canGoBack() {
		/*if(currentStateNum > 0)
			return true;*/
		return false;
	}

	@Override
	public String getInitialState() {
		return Constants.WIZARD_PAGE_FIRST;
	}

	@Override
	public void registerModelListener(ChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void unregisterModelListener(ChangeListener l) {
		listeners.remove(l);
	}
	
	private void loadSceneData(NewScenarioWizardPage2 page, int n) {
		SceneData scene = new SceneData();
		scene.setSeqNumber(n);
		scene.setStoryPath(page.getStoryPath());
		scene.setBackgroundMusicPath(page.getMusicPath());
		scene.setProjectedContentPath(page.getVideoPath());
		scene.setObjectImagePath(page.getImagePath());
		scene.setRfidObjectTag(page.getRfidTag());
		try {
			setActions(page, scene);
		} catch (Exception e) {
			System.err.println("Non dovrei mai finire qui O.O");
			e.printStackTrace();
		}
		rawResult.put(n, scene);
	}
	
	private void setActions(NewScenarioWizardPage2 page, SceneData scene) throws Exception {
		String actionsString = page.getActions();
		Scanner sc1 = new Scanner(actionsString);
		sc1.useDelimiter(" ");
		String actionString = sc1.next();
		boolean ended = false;
		while(!ended) {
			Scanner sc2 = new Scanner(actionString);
			sc2.useDelimiter("_");
			String s = sc2.next();
			ActionTime actionTime = new ActionTime(s);
			sc2.skip("_");
			sc2.useDelimiter(" ");
			s = sc2.next();
			TeoAction action = new TeoAction(s, actionTime);
			sc2.close();
			scene.getActions().add(action);
			try {
				actionString = sc1.next();
			} catch(NoSuchElementException e) {
				ended = true;
			}
		}
		sc1.close();
	}
	
	/*private void reloadPage(int n) {
		if(n == 0) {
			NewScenarioWizardPage1 page = (NewScenarioWizardPage1) pages.get(n);
			page.setSceneNumberText(sceneNumber+"");
			page.setTitleText(result.getTitle());
			page.updateUI();
		} else {
			SceneData oldScene = rawResult.get(n);
			NewScenarioWizardPage2 page = (NewScenarioWizardPage2) pages.get(n);
			page.setStoryText(oldScene.getStoryPath());
			page.setMusicText(oldScene.getBackgroundMusicPath());
			page.setVideoText(oldScene.getProjectedContentPath());
			page.setRfidTag(oldScene.getRfidObjectTag());
			page.updateUI();
		}
	}*/
}
