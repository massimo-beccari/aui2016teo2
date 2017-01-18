package teo2sm;

public class Constants {
	//file manager modes
	public static final int LOAD = 1;
	public static final int WRITE = 2;
	//dimensions
	public static final int DEFAULT_WINDOW_HEIGHT = 700;
	public static final int DEFAULT_WINDOW_WIDTH = 900;
	public static final int DEFAULT_ICONS_SIZE = 24;
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
	//wizard page ids
	public static final String WIZARD_PAGE_FIRST = "wizard_1";
	public static final String WIZARD_PAGE_SCENE = "wizard_2";
	//application
	public static final int DEFAULT_USERINT_VALUE = 0;
	public static final String FILE_EXTENSION = ".teo2s";
	public static final String WINDOW_TITLE = "Teo2 Scenario Manager";
}
