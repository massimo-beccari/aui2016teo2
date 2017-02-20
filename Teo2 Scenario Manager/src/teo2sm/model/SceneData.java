package teo2sm.model;

import java.util.ArrayList;

import teo2sm.Constants;

public class SceneData {
	private int seqNumber;
	private String reinforcementContentPath;
	private String storyPath;
	private String projectedContentPath;
	private String objectImagePath;
	private String rfidObjectTag;
	private ArrayList<TeoAction> actions;
	
	public SceneData() {
		seqNumber = 0;
		storyPath = Constants.SCENE_DEFAULT_PATH_NAME;
		reinforcementContentPath = Constants.SCENE_DEFAULT_PATH_NAME;
		projectedContentPath = Constants.SCENE_DEFAULT_PATH_NAME;
		objectImagePath = Constants.SCENE_DEFAULT_PATH_NAME;
		rfidObjectTag = Constants.SCENE_DEFAULT_PATH_NAME;
		actions = new ArrayList<TeoAction>();
	}

	public String getReinforcementContentPath() {
		return reinforcementContentPath;
	}

	public void setReinforcementContentPath(String reinforcementContentPath) {
		this.reinforcementContentPath = reinforcementContentPath;
	}

	public String getStoryPath() {
		return storyPath;
	}

	public void setStoryPath(String storyPath) {
		this.storyPath = storyPath;
	}

	public String getProjectedContentPath() {
		return projectedContentPath;
	}

	public void setProjectedContentPath(String projectedContentPath) {
		this.projectedContentPath = projectedContentPath;
	}

	public String getObjectImagePath() {
		return objectImagePath;
	}

	public void setObjectImagePath(String objectImagePath) {
		this.objectImagePath = objectImagePath;
	}

	public String getRfidObjectTag() {
		return rfidObjectTag;
	}

	public void setRfidObjectTag(String rfidObjectTag) {
		this.rfidObjectTag = rfidObjectTag;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public ArrayList<TeoAction> getActions() {
		return actions;
	}
}
