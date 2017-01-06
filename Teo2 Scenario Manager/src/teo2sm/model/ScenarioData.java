package teo2sm.model;

import java.util.ArrayList;

public class ScenarioData {
	private String title;
	private ArrayList<SceneData> scenes;
	
	public ScenarioData() {
		scenes = new ArrayList<SceneData>();
	}
	
	public ArrayList<SceneData> getScenes() {
		return scenes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
