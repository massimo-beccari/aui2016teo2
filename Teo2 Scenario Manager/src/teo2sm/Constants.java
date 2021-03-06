package teo2sm;

public class Constants {
	//file manager modes
	public static final int LOAD = 1;
	public static final int WRITE = 2;
	//dimensions
	public static final int DEFAULT_WINDOW_HEIGHT = 600;
	public static final int DEFAULT_WINDOW_WIDTH = 900;
	public static final int DEFAULT_ICONS_SIZE = 24;
	public static final int DEFAULT_SCENE_IMAGE_SIZE = 96;
	//user actions
	public static final int ACTION_LOAD_SCENARIO = 1;
	public static final int ACTION_NEW_SCENARIO = 2;
	public static final int ACTION_SAVE_SCENARIO = 3;
	public static final int ACTION_CLOSE_SCENARIO = 4;
	public static final int ACTION_PLAY_SCENARIO = 5;
	public static final int ACTION_PAUSE_SCENARIO = 6;
	public static final int ACTION_STOP_SCENARIO = 7;
	//scenario playing status
	public static final int SCENARIO_PLAYED = 1;
	public static final int SCENARIO_PAUSED = 2;
	public static final int SCENARIO_STOPPED = 3;
	//scenario file status
	public static final int SCENARIO_OPENED = 4;
	public static final int SCENARIO_CLOSED = 5;
	//scene file names
	public static final String SCENE_DEFAULT_PATH_NAME = "default";
	public static final String SCENE_STORY = "story";
	public static final String SCENE_REINFORCEMENT_CONTENT = "reinforcement";
	public static final String SCENE_PROJECTED_CONTENT = "video";
	public static final String SCENE_OBJECT_IMAGE = "image";
	public static final String SCENE_RFID_EMPTY = "no_object";
	//wizard page ids
	public static final String WIZARD_PAGE_FIRST = "wizard_1";
	public static final String WIZARD_PAGE_SCENE = "wizard_2";
	//application
	public static final int DEFAULT_USERINT_VALUE = 0;
	public static final int MAX_SCENE_NUMBER = 16;
	public static final String FILE_EXTENSION_SCENARIO = ".teo2s";
	public static final String FILE_EXTENSION_STORY = "mp3";
	public static final String FILE_EXTENSION_REINFORCEMENT_CONTENT = "mp4";
	public static final String FILE_EXTENSION_PROJECTED_CONTENT = "mp4";
	public static final String FILE_EXTENSION_OBJECT_IMAGE = "png";
	public static final String WINDOW_TITLE = "Teo2 Scenario Manager";
	public static final String REINFORCEMENT_AUDIO_FILE = "res/00_oh_no_cerca_ancora.MP3";
	public static final String REINFORCEMENT_VIDEO_FILE = "res/00_Fantastico.mp4";
	public static final String FINAL_VIDEO_FILE = "res/00_Grande.mp4";
}
