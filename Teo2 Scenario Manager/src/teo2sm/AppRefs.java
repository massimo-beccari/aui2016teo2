package teo2sm;

import teo2sm.view.UserInterface;

public class AppRefs {
	private UserInterface userInterface;
	
	public AppRefs(UserInterface userInterface) {
		this.userInterface = userInterface;
	}
	
	public UserInterface getUI() {
		return userInterface;
	}
}
