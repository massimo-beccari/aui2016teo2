package teo2sm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import teo2sm.controller.AppCore;
import teo2sm.view.GUI;

public class Main {

	public static void main(String[] args) {
		ExecutorService e = Executors.newSingleThreadExecutor();
		GUI gui = new GUI();
		AppRefs app = new AppRefs(gui);
		AppCore core = new AppCore(app);
		e.execute(gui);
		core.startApplication();
	}
}
