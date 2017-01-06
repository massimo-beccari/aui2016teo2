package teo2sm.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import teo2sm.AppRefs;
import teo2sm.model.Constants;
import teo2sm.model.InvalidOperationException;
import teo2sm.model.ScenarioData;
import teo2sm.model.SceneData;
import teo2sm.view.CLI;

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
				scenario.getScenes().add(scene);
				in = file.readLine();
			}
			file.close();
		} catch (FileNotFoundException e) {
			app.getUI().showFileNotFound(filePath);
		} catch (IOException e) {
			System.err.println("Errore lettura stringa in caricamento scenari");
			e.printStackTrace();
		}
		return scenario;
	}
	
	public RandomAccessFile getFileSaved() throws InvalidOperationException {
		if(mode == Constants.LOAD)
			throw new InvalidOperationException();
		//saving code
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(filePath, "w");
			if (file != null) {
				
			} else if (file == null) {
	        	
	        } else
	            System.out.println("Il file non può essere creato");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return file;
	}
	
	/*test main*/
	public static void main(String[] args) {
		AppRefs app = new AppRefs(new CLI());
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
	}
}
