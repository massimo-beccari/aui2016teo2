package teo2sm;

import teo2sm.controller.CommInterface;
import teo2sm.view.UserInterface;

public class AppRefs {
	private UserInterface userInterface;
	private CommInterface communicator;
	
	public AppRefs(UserInterface userInterface, CommInterface communicator) {
		this.userInterface = userInterface;
		this.communicator = communicator;
	}
	
	public UserInterface getUI() {
		return userInterface;
	}

	public CommInterface getCommunicator() {
		return communicator;
	}
}
