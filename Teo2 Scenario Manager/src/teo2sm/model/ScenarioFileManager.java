package teo2sm.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

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
	public ScenarioFileManager(ScenarioData scenario, String filePath) {
		this.scenario = scenario;
		this.filePath = filePath;
		mode = Constants.WRITE;
	}
	
	//WARNING: call this method only in LOAD mode
	/**
	 * Load a scenario from a file
	 * @return the loaded scenario, or null if an exception was caught
	 * (Catch FileNotFoundException if file doesn't exists, file exists but some of the scenario folders don't exist
	 * Catch IOException if there's an error reading a line of file)
	 */
	public ScenarioData getScenario() {
		//loading code
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(filePath, "r");
			//check
			String in = file.readLine();
			if(!in.equals(Constants.FILE_EXTENSION_SCENARIO)) {
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
				sc.setSeqNumber(i);
				if(i<10)
					fl = new File(filePath+"_data/scene0"+i);
				else
					fl = new File(filePath+"_data/scene"+i);
				if(!fl.exists())
					throw new FileNotFoundException();
				setupSceneFiles(sc, i);
				i++;
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			System.err.println("Errore lettura stringa in caricamento scenario");
			e.printStackTrace();
			return null;
		}
		return scenario;
	}
	
	//WARNING: call this method only in WRITE mode
	/**
	 * Write the class attribute scenario to a file (class attribute filePath)
	 * @return true if no exception are caught, false otherwise
	 */
	public boolean saveFile() {
		//saving code
		File file = new File(filePath);
        try {
        	FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Constants.FILE_EXTENSION_SCENARIO);
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
		for(SceneData scene : scenario.getScenes()) {
			//scene folder
			if(i<10)
				folder = new File(filePath+"_data/scene0"+i);
			else
				folder = new File(filePath+"_data/scene"+i);
			if(!folder.exists()) {
				created = folder.mkdir();
				outcome = outcome && created;
			}
			if(!createSceneFiles(scene, i-1))
				throw new IOException("Error in copying scene files.");
			i++;
		}
		return outcome;
	}
	
	private boolean createSceneFiles(SceneData scene, int i) {
		boolean outcome = true;
		if(i<10) {
			try {
				Files.copy(Paths.get(scenario.getScenes().get(i).getStoryPath()), 
						Paths.get(filePath+"_data/scene0"+Integer.toString(i+1)+"/"+Constants.SCENE_STORY+"."+Constants.FILE_EXTENSION_STORY), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getBackgroundMusicPath()), 
						Paths.get(filePath+"_data/scene0"+Integer.toString(i+1)+"/"+Constants.SCENE_BACKGROUND_MUSIC+"."+Constants.FILE_EXTENSION_BACKGROUND_MUSIC), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getProjectedContentPath()), 
						Paths.get(filePath+"_data/scene0"+Integer.toString(i+1)+"/"+Constants.SCENE_PROJECTED_CONTENT+"."+Constants.FILE_EXTENSION_PROJECTED_CONTENT), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getObjectImagePath()), 
						Paths.get(filePath+"_data/scene0"+Integer.toString(i+1)+"/"+Constants.SCENE_OBJECT_IMAGE+"."+Constants.FILE_EXTENSION_OBJECT_IMAGE), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				outcome = false;
				e.printStackTrace();
			}
		} else {
			try {
				Files.copy(Paths.get(scenario.getScenes().get(i).getStoryPath()), 
						Paths.get(filePath+"_data/scene"+Integer.toString(i+1)+"/"+Constants.SCENE_STORY+"."+Constants.FILE_EXTENSION_STORY), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getBackgroundMusicPath()), 
						Paths.get(filePath+"_data/scene"+Integer.toString(i+1)+"/"+Constants.SCENE_BACKGROUND_MUSIC+"."+Constants.FILE_EXTENSION_BACKGROUND_MUSIC), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getProjectedContentPath()), 
						Paths.get(filePath+"_data/scene"+Integer.toString(i+1)+"/"+Constants.SCENE_PROJECTED_CONTENT+"."+Constants.FILE_EXTENSION_PROJECTED_CONTENT), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
				Files.copy(Paths.get(scenario.getScenes().get(i).getObjectImagePath()), 
						Paths.get(filePath+"_data/scene"+Integer.toString(i+1)+"/"+Constants.SCENE_OBJECT_IMAGE+"."+Constants.FILE_EXTENSION_OBJECT_IMAGE), 
						(CopyOption) StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				outcome = false;
				e.printStackTrace();
			}
		}
		
		return outcome;
	}

	private void setupSceneFiles(SceneData scene, int i) {
		if(i<10) {
			scene.setStoryPath(filePath+"_data/scene0"+i+"/"+Constants.SCENE_STORY+"."+Constants.FILE_EXTENSION_STORY);
			scene.setBackgroundMusicPath(filePath+"_data/scene0"+i+"/"+Constants.SCENE_BACKGROUND_MUSIC+"."+Constants.FILE_EXTENSION_BACKGROUND_MUSIC);
			scene.setProjectedContentPath(filePath+"_data/scene0"+i+"/"+Constants.SCENE_PROJECTED_CONTENT+"."+Constants.FILE_EXTENSION_PROJECTED_CONTENT);
			scene.setObjectImagePath(filePath+"_data/scene0"+i+"/"+Constants.SCENE_OBJECT_IMAGE+"."+Constants.FILE_EXTENSION_OBJECT_IMAGE);
		} else {
			scene.setStoryPath(filePath+"_data/scene"+i+"/"+Constants.SCENE_STORY+"."+Constants.FILE_EXTENSION_STORY);
			scene.setBackgroundMusicPath(filePath+"_data/scene"+i+"/"+Constants.SCENE_BACKGROUND_MUSIC+"."+Constants.FILE_EXTENSION_BACKGROUND_MUSIC);
			scene.setProjectedContentPath(filePath+"_data/scene"+i+"/"+Constants.SCENE_PROJECTED_CONTENT+"."+Constants.FILE_EXTENSION_PROJECTED_CONTENT);
			scene.setObjectImagePath(filePath+"_data/scene"+i+"/"+Constants.SCENE_OBJECT_IMAGE+"."+Constants.FILE_EXTENSION_OBJECT_IMAGE);
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
