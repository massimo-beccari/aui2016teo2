package teo2sm.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;


public class Communicator implements CommInterface {

    private String hc05Url = "btspp://" + CommConstants.BLUETOOTH_ADDRESS + 
    		":1;authenticate=false;encrypt=false;master=false";
    private StreamConnection streamConnection;
    private OutputStream os;
    private InputStream is;
    private BufferedReader br;
    
    public Communicator() {
    	
    }
    
    public void openConnection() {
    	try {
			streamConnection = (StreamConnection) Connector.open(hc05Url);
			os = streamConnection.openOutputStream();
	        is = streamConnection.openInputStream();
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        //os.write("prova1\r".getBytes()); //just send '1' to the device
    }
    
    public void closeConnection() {
    	try {
	    	os.close();
	        is.close();
	        streamConnection.close();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

	@Override
	public int waitChildInteraction() {
		try {
			openConnection();
			int interaction = is.read();
			closeConnection();
			switch (interaction) {
			case CommConstants.COMM_FSR_HUG:
				return CommConstants.COMM_FSR_HUG;
			case CommConstants.COMM_FSR_PUNCH:
				return CommConstants.COMM_FSR_PUNCH;
			case CommConstants.COMM_FSR_CARESS:
				return CommConstants.COMM_FSR_CARESS;
			default: 
				return 0;
			}
		} catch (Exception e) {
			//TODO
		}
		return 0;
	}

	@Override
	public String waitRfidObject() {
		try {
			os.write((CommConstants.COMM_RFID+CommConstants.COMMAND_EOL).getBytes());
			String tag = br.readLine();
			closeConnection();
			return tag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setTeoMood(String code) {
		/*try {
			openConnection();
			switch (code) {
			case CommConstants.COMM_MOOD_NEUTRAL:
				os.write(("moodNeutral"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.COMM_MOOD_SAD:
				os.write(("moodSad"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.COMM_MOOD_ANGRY:
				os.write(("moodAngry"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.COMM_MOOD_SCARED:
				os.write(("moodScared"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.COMM_MOOD_HAPPY:
				os.write(("moodHappy"+CommConstants.COMMAND_EOL).getBytes());
				break;
			default: 
				os.write(("moodNeutral"+CommConstants.COMMAND_EOL).getBytes());
				break;
			}
			closeConnection();
		} catch (Exception e) {
			//TODO
		}*/
	}

	@Override
	public void sendTeoMovement(String code) {
		/*try {
			openConnection();
			switch (code) {
			case CommConstants.MOV_OFF:
				os.write(("moveOff"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.MOV_VIBRATE:
				os.write(("moveVibrate"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.MOV_WALK_FRONT:
				os.write(("moveFront"+CommConstants.COMMAND_EOL).getBytes());
				break;
			case CommConstants.MOV_WALK_BACK:
				os.write(("moveBack"+CommConstants.COMMAND_EOL).getBytes());
				break;
			default: 
				os.write(("moveOff"+CommConstants.COMMAND_EOL).getBytes());
				break;
			}
			closeConnection();
		} catch (Exception e) {
			//TODO
		}*/
	}
	
	public static void main(String[] args) {
		Communicator comm = new Communicator();
		comm.openConnection();
		System.out.println(comm.waitRfidObject());
		comm.closeConnection();
	}
}
