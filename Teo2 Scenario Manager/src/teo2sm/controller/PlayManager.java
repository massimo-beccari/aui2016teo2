package teo2sm.controller;

import teo2sm.AppRefs;
import teo2sm.model.ActionTime;

public class PlayManager extends Thread {
	private AppRefs app;
	private ActionTime startTime, currentTime;
	
	public PlayManager(AppRefs app) {
		this.app = app;
	}
	
	public void playScenario() {
		startTime = new ActionTime(System.currentTimeMillis());
	}
	
	public void pauseScenario() {
		currentTime = new ActionTime(System.currentTimeMillis());
		try {
			currentTime = currentTime.sub(startTime);
		} catch (Exception e) {
			System.err.println("Non dovrei mai finire qui O.O");
			e.printStackTrace();
		}
		
	}
	
	public void stopScenario() {
		
	}

	@Override
	public void run() {
		
	}
}
