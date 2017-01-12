package teo2sm.model;

public class SceneData {
	private int seqNumber;
	private String backgroundMusicPath;
	private String storyPath;
	private String projectedContentPath;
	private String rfidObjectTag;
	
	public SceneData() { }

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
