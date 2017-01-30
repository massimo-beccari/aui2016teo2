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
	public ActionTime playVideo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionTime pauseVideo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionTime playStory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionTime pauseStory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionTime playMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionTime pauseMusic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int waitChildInteraction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String waitRfidObject(String tag) {
		// We need to know the current scene
		
		return null;
	}

	@Override
	public void setTeoMood(int code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTeoMovement(int code) {
		// TODO Auto-generated method stub

	}

}
