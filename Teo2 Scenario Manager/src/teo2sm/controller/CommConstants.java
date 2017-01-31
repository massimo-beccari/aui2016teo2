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
	public static final String COMM_RFID = "waitRfidObject";
	//movement
	public static final int MOV_OFF = 40;
	public static final int MOV_VIBRATE = 41;
	public static final int MOV_WALK_FRONT = 42;
	public static final int MOV_WALK_BACK = 43;
}
