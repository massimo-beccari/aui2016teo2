package teo2sm.controller;

import teo2sm.model.ActionTime;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;


public class Communicator implements CommInterface {

    private String hc05Url = "btspp://" + CommConstants.BLUETOOTH_ADDRESS + 
    		":1;authenticate=false;encrypt=false;master=false";
    
    StreamConnection streamConnection;
    OutputStream os;
    InputStream is;
    
    private void openConnection() throws Exception {
    	streamConnection = (StreamConnection) Connector.open(hc05Url);
        os = streamConnection.openOutputStream();
        is = streamConnection.openInputStream();

        //os.write("prova1\r".getBytes()); //just send '1' to the device
    }
    
    private void closeConnection() throws Exception {
    	os.close();
        is.close();
        streamConnection.close();
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
		/*// We need to know the current scene
		byte[] buffer = new byte[256]; 
		openConnection();
		// sending expected RFID tag name
		os.write((ScenarioManager.currentScene+CommConstants.COMMAND_EOL).getBytes());
		// reading result
		int length = is.read(buffer);
		closeConnection();
		return new String(buffer, 1, length);*/
		return null;
	}

	@Override
	public void setTeoMood(int code) {
		try {
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
		}
	}

	@Override
	public void sendTeoMovement(int code) {
		try {
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
		}
	}

}
