package teo2sm.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import teo2sm.AppRefs;
import teo2sm.Constants;
import teo2sm.model.InvalidOperationException;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;
//import teo2sm.view.CLI;

public class ScenarioFileManager {
	private AppRefs app;
	private String filePath;
	private ScenarioData scenario;
	private final int mode;
	
	/**
	 * Constructor for loading mode
	 * @param filePath the string containing the file path to load
	 */
	public ScenarioFileManager(AppRefs app, String filePath) {
		this.app = app;
		this.filePath = filePath;
		scenario = new ScenarioData();
		mode = Constants.LOAD;
	}
	
	/**
	 * Constructor for writing mode
	 * @param scenario the scenario to save
	 * @param filePath the string containing the file path to save
	 */
	public ScenarioFileManager(AppRefs app, ScenarioData scenario, String filePath) {
		this.app = app;
		this.scenario = scenario;
		this.filePath = filePath;
		mode = Constants.WRITE;
	}
	
	public ScenarioData getScenario() throws InvalidOperationException {
		if(mode == Constants.WRITE)
			throw new InvalidOperationException();
		//loading code
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(filePath, "r");
			//title
			String in = file.readLine();
			scenario.setTitle(in);
			//scenes
			SceneData scene;
			Scanner s;
			in = file.readLine();
			while(in != null) {
				scene = new SceneData();
				s = new Scanner(in);
				scene.setStoryPath(s.next());
				scene.setBackgroundMusicPath(s.next());
				scene.setProjectedContentPath(s.next());
				scene.setRfidObjectTag(s.next());
				in = file.readLine();
				//TODO lettura azioni teo
				s.close();
				scenario.getScenes().add(scene);
				in = file.readLine();
			}
			file.close();
		} catch (FileNotFoundException e) {
			app.getUI().showFileNotFound(filePath);
		} catch (IOException e) {
			System.err.println("Errore lettura stringa in caricamento scenario");
			e.printStackTrace();
		}
		return scenario;
	}
	
	public boolean saveFile() throws InvalidOperationException {
		if(mode == Constants.LOAD)
			throw new InvalidOperationException();
		//saving code
		File file = new File(filePath);
        try {
        	FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);;
			bw.write(scenario.getTitle());
			bw.newLine();
			for(SceneData scene : scenario.getScenes()) {
				bw.write(scene.getStoryPath()+" ");
				bw.write(scene.getBackgroundMusicPath()+" ");
				bw.write(scene.getProjectedContentPath()+" ");
				bw.write(scene.getRfidObjectTag());
				bw.newLine();
				//TODO scrittura azioni teo
			}
			bw.flush();
			bw.close();
	    } catch (IOException e) {
	    	System.err.println("Errore scrittura stringa in salvataggio scenario");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*test main
	public static void main(String[] args) {
		AppRefs app = new AppRefs(new CLI());
		//test load
		ScenarioFileManager sfm = new ScenarioFileManager(app, "C:\\Users\\Max\\test.txt");
		try {
			ScenarioData sc = sfm.getScenario();
			SceneData scene = sc.getScenes().get(0);
			System.out.println(sc.getTitle());
			System.out.println(scene.getStoryPath());
			System.out.println(scene.getBackgroundMusicPath());
			System.out.println(scene.getProjectedContentPath());
			System.out.println(scene.getRfidObjectTag());
			scene = sc.getScenes().get(1);
			System.out.println(sc.getTitle());
			System.out.println(scene.getStoryPath());
			System.out.println(scene.getBackgroundMusicPath());
			System.out.println(scene.getProjectedContentPath());
			System.out.println(scene.getRfidObjectTag());
		} catch (InvalidOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test write
		ScenarioData sc2 = new ScenarioData();
		sc2.setTitle("prova_titolo");
		SceneData scene2 = new SceneData();
		scene2.setStoryPath("C:\\percorso\\story");
		scene2.setBackgroundMusicPath("C:\\percorso\\music");
		scene2.setProjectedContentPath("C:\\percorso\\multimedia");
		scene2.setRfidObjectTag("123456");
		sc2.getScenes().add(scene2);
		scene2 = new SceneData();
		scene2.setStoryPath("C:\\percorso\\story2");
		scene2.setBackgroundMusicPath("C:\\percorso\\music2");
		scene2.setProjectedContentPath("C:\\percorso\\multimedia2");
		scene2.setRfidObjectTag("987654");
		sc2.getScenes().add(scene2);
		sfm = new ScenarioFileManager(app, sc2, "C:\\Users\\Max\\test2.txt");
		try {
			sfm.saveFile();
		} catch (InvalidOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
