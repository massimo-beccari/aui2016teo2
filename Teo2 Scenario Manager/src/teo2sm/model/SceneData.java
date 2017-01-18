package teo2sm.model;

import teo2sm.Constants;

public class SceneData {
	private int seqNumber;
	private String backgroundMusicPath;
	private String storyPath;
	private String projectedContentPath;
	private String objectImagePath;
	private String rfidObjectTag;
	
	public SceneData() {
		seqNumber = 0;
		storyPath = Constants.SCENE_DEFAULT_PATH_NAME;
		backgroundMusicPath = Constants.SCENE_DEFAULT_PATH_NAME;
		projectedContentPath = Constants.SCENE_DEFAULT_PATH_NAME;
		objectImagePath = Constants.SCENE_DEFAULT_PATH_NAME;
		rfidObjectTag = Constants.SCENE_DEFAULT_PATH_NAME;
	}

	public String getBackgroundMusicPath() {
		return backgroundMusicPath;
	}

	public void setBackgroundMusicPath(String backgroundMusicPath) {
		this.backgroundMusicPath = backgroundMusicPath;
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
}
