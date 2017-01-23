package teo2sm.model;

public class TeoAction {
	private String actionID;
	private ActionTime actionTime;
	
	public TeoAction(String actionID, ActionTime actionTime) {
		this.actionID = actionID;
		this.actionTime = actionTime;
	}

	public String getActionID() {
		return actionID;
	}

	public ActionTime getActionTime() {
		return actionTime;
	}
	
	@Override
	public String toString() {
		String action = actionTime + "_" + actionID;
		return action;
	}
}
