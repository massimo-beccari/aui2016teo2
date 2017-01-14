package teo2sm.view.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeListener;

import teo2sm.Constants;
import teo2sm.model.ScenarioData;

public class NewScenarioWizardData implements WizardModel<ScenarioData> {
	private Map<String, WizardPage> pages;
	private String currentState;
	private int currentStateNum;
	private int sceneNumber;
	private ScenarioData scenario;
	private ArrayList<ChangeListener> listeners;
	
	public NewScenarioWizardData() {
		pages = new HashMap<String, WizardPage>();
		currentState = Constants.WIZARD_PAGE_FIRST;
		currentStateNum = 0;
		sceneNumber = 0;
		scenario = new ScenarioData();
		listeners = new ArrayList<ChangeListener>();
		setupPages();
	}
	
	private void setupPages() {
		NewScenarioWizardPage1 page = new NewScenarioWizardPage1();
		pages.put(Constants.WIZARD_PAGE_FIRST, page);
		//TODO
		pages.get(currentState).onPageAboutToDisplay();
	}
	
	@Override
	public boolean completable() {
		if(currentState.equals(Constants.WIZARD_PAGE_SCENE+sceneNumber) && !pages.get(currentState).getError())
			return true;
		return false;
	}

	@Override
	public ScenarioData complete() throws IllegalStateException {
		if(!completable())
			throw new IllegalStateException();
		return scenario;
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
			scenario.setTitle(firstPage.getTitle());
			sceneNumber = firstPage.getSceneNumber();
		} else {
			loadSceneData(oldPage);
		}
		//get new state
		currentStateNum = currentStateNum + 1;
		String newState = Constants.WIZARD_PAGE_SCENE+currentStateNum;
		//prepare new page
		pages.get(newState).onPageAboutToDisplay();
		//inform listeners
		WizardModelChangeEvent event = new WizardModelChangeEvent(this, newState, currentState);
		for(ChangeListener l : listeners)
			l.stateChanged(event);
		return newState;
	}

	@Override
	public boolean canGoForward() {
		if(currentStateNum < sceneNumber)
			return true;
		return false;
	}

	@Override
	public String back() {
		//load data in scenario
		WizardPage oldPage = pages.get(currentState);
		oldPage.onPageHidden();
		loadSceneData(oldPage);
		//get new state
		currentStateNum = currentStateNum - 1;
		String newState;
		if(currentStateNum != 0)
			newState = Constants.WIZARD_PAGE_SCENE+currentStateNum;
		else
			newState = Constants.WIZARD_PAGE_FIRST;		
		//prepare new page
		pages.get(newState).onPageAboutToDisplay();
		//inform listeners
		WizardModelChangeEvent event = new WizardModelChangeEvent(this, newState, currentState);
		for(ChangeListener l : listeners)
			l.stateChanged(event);
		return newState;
	}

	@Override
	public boolean canGoBack() {
		if(currentStateNum > 0)
			return true;
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
	
	private void loadSceneData(WizardPage scenePage) {
		//TODO
	}
}
