package teo2sm.controller;

public class CommConstants {
	//usiamo interi per la comunicazione che sono comodi da processare
	//buttons
	public static final int COMM_BUTTON_GREY = 10;
	//fsr
	public static final int COMM_FSR_HUG = 20;
	public static final int COMM_FSR_PUNCH = 21;
	public static final int COMM_FSR_CARESS = 22;
	//moods
	public static final int COMM_MOOD_NEUTRAL = 30;
	public static final int COMM_MOOD_SAD = 31;
	public static final int COMM_MOOD_ANGRY = 32;
	public static final int COMM_MOOD_SCARED = 33;
	public static final int COMM_MOOD_HAPPY = 34;
	//bluetooth
	public static final String BLUETOOTH_ADDRESS = "98D331FD381F";
	public static final String COMMAND_EOL = "\r";
	//rfid
	public static final String COMM_CMD_RFID = "waitRfidObject";
	public static final String COMM_CMD_BUTTON = "waitButtonInteraction";
	public static final String COMM_CMD_FSR = "waitFsrInteraction";
	public static final String COMM_CMD_MOOD_HAPPY = "setMoodHappy";
	public static final String COMM_CMD_MOOD_SCARED = "setMoodScared";
	public static final String COMM_CMD_MOOD_ANGRY = "setMoodAngry";
	public static final String COMM_CMD_MOOD_SAD = "setMoodSad";
	public static final String COMM_CMD_MOOD_NEUTRAL = "setMoodNeutral";
	//movement
	public static final int MOV_OFF = 40;
	public static final int MOV_VIBRATE = 41;
	public static final int MOV_WALK_FRONT = 42;
	public static final int MOV_WALK_BACK = 43;
}
