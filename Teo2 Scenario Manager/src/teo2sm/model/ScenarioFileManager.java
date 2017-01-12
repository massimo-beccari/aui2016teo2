package teo2sm.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import teo2sm.AppRefs;
import teo2sm.Constants;

public class ScenarioFileManager {
	private String filePath;
	private ScenarioData scenario;
	private final int mode;
	
	/**
	 * Constructor for loading mode
	 * @param filePath the string containing the file path to load
	 */
	public ScenarioFileManager(String filePath) {
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
		this.scenario = scenario;
		this.filePath = filePath;
		mode = Constants.WRITE;
	}
	
	//WARNING: call this method only in LOAD mode
	public ScenarioData getScenario() {
		//loading code
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(filePath, "r");
			//check
			String in = file.readLine();
			if(!in.equals(Constants.FILE_EXTENSION)) {
				file.close();
				return null;
			}
			//title
			in = file.readLine();
			scenario.setTitle(in);
			//scenes
			SceneData scene;
			Scanner s;
			in = file.readLine();
			while(in != null) {
				scene = new SceneData();
				s = new Scanner(in);
				scene.setRfidObjectTag(s.next());
				in = file.readLine();
				//TODO lettura azioni teo
				s.close();
				scenario.getScenes().add(scene);
				in = file.readLine();
			}
			file.close();
			//check directories and setup paths in scene data
			File fl = new File(filePath+"_data");
			if(!fl.exists())
				throw new FileNotFoundException();
			int i = 1;
			for(SceneData sc : scenario.getScenes()) {
				if(i<10)
					fl = new File(filePath+"_data/scene0"+i);
				else
					fl = new File(filePath+"_data/scene"+i);
				if(!fl.exists())
					throw new FileNotFoundException();
				if(!checkDirectories(i)) {
					throw new FileNotFoundException();
				}
				setupSceneFolders(sc, i);
				i++;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			System.err.println("Errore lettura stringa in caricamento scenario");
			e.printStackTrace();
		}
		return scenario;
	}
	
	//WARNING: call this method only in WRITE mode
	public boolean saveFile() {
		//saving code
		File file = new File(filePath);
        try {
        	FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Constants.FILE_EXTENSION);
			bw.newLine();
			bw.write(scenario.getTitle());
			bw.newLine();
			for(SceneData scene : scenario.getScenes()) {
				bw.write(scene.getRfidObjectTag());
				bw.newLine();
				//TODO scrittura azioni teo
			}
			bw.flush();
			bw.close();
			if(!createFolders())
				throw new IOException("Error in creating scenario folders.");
	    } catch (IOException e) {
	    	String message = e.getMessage();
	    	if(message != null)
	    		System.err.println(message);
	    	else
	    		System.err.println("Errore scrittura stringa in salvataggio scenario");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean createFolders() throws IOException {
		File folder;
		boolean created, outcome = true;
		//main folder
		folder = new File(filePath+"_data");
		if(!folder.exists())
			folder.mkdir();
		int i = 1;
		for(@SuppressWarnings("unused") SceneData scene : scenario.getScenes()) {
			//scene folder
			if(i<10)
				folder = new File(filePath+"_data/scene0"+i);
			else
				folder = new File(filePath+"_data/scene"+i);
			if(!folder.exists()) {
				created = folder.mkdir();
				outcome = outcome && created;
			}
			if(!createSceneFolders(i))
				throw new IOException("Error in creating scene folders.");
			i++;
		}
		return outcome;
	}
	
	private boolean createSceneFolders(int i) {
		File folder;
		boolean created, outcome = true;
		//story folder
		if(i<10)
			folder = new File(filePath+"_data/scene0"+i+"/story");
		else
			folder = new File(filePath+"_data/scene"+i+"/story");
		if(!folder.exists()) {
			created = folder.mkdir();
			outcome = outcome && created;
		}
		//music folder
		if(i<10)
			folder = new File(filePath+"_data/scene0"+i+"/music");
		else
			folder = new File(filePath+"_data/scene"+i+"/music");
		if(!folder.exists()) {
			created = folder.mkdir();
			outcome = outcome && created;
		}
		//video folder
		if(i<10)
			folder = new File(filePath+"_data/scene0"+i+"/video");
		else
			folder = new File(filePath+"_data/scene"+i+"/video");
		if(!folder.exists()) {
			created = folder.mkdir();
			outcome = outcome && created;
		}
		return outcome;
	}
	
	private boolean checkDirectories(int i) {
		boolean outcome = true;
		File folder;
		if(i<10) {
			folder = new File(filePath+"_data/scene0"+i+"/story");
			outcome = outcome && folder.exists();
			folder = new File(filePath+"_data/scene0"+i+"/music");
			outcome = outcome && folder.exists();
			folder = new File(filePath+"_data/scene0"+i+"/video");
			outcome = outcome && folder.exists();
		} else {
			folder = new File(filePath+"_data/scene"+i+"/story");
			outcome = outcome && folder.exists();
			folder = new File(filePath+"_data/scene"+i+"/music");
			outcome = outcome && folder.exists();
			folder = new File(filePath+"_data/scene"+i+"/video");
			outcome = outcome && folder.exists();
		}
		return outcome;
	}

	private void setupSceneFolders(SceneData scene, int i) {
		if(i<10) {
			scene.setStoryPath(filePath+"_data/scene0"+i+"/story");
			scene.setBackgroundMusicPath(filePath+"_data/scene0"+i+"/music");
			scene.setProjectedContentPath(filePath+"_data/scene0"+i+"/video");
		} else {
			scene.setStoryPath(filePath+"_data/scene"+i+"/story");
			scene.setBackgroundMusicPath(filePath+"_data/scene"+i+"/music");
			scene.setProjectedContentPath(filePath+"_data/scene"+i+"/video");
		}
	}
	
	public int getMode() {
		return mode;
	}
	
	/*test main
	public static void main(String[] args) {
		AppRefs app = new AppRefs(new CLI());
		//test load
		ScenarioFileManager sfm = new ScenarioFileManager("C:/Users/Max/test.txt");
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
		//test write
		sfm = new ScenarioFileManager("C:/Users/Max/test2.txt");
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
		sfm.saveFile();
	}*/
}
