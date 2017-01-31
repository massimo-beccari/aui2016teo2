package teo2sm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import teo2sm.controller.AppCore;
import teo2sm.controller.EmptyCommunicator;
import teo2sm.view.GUI;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Main {

	public static void main(String[] args) {
		boolean found = new NativeDiscovery().discover();
        System.out.println(found);
        System.out.println(LibVlc.INSTANCE.libvlc_get_version());
        
		ExecutorService e = Executors.newSingleThreadExecutor();
		GUI gui = new GUI();
		EmptyCommunicator comm = new EmptyCommunicator();
		AppRefs app = new AppRefs(gui, comm);
		AppCore core = new AppCore(app);
		e.execute(gui);
		core.startApplication();
	}
}
