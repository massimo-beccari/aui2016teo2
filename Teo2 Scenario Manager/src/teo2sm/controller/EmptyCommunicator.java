package teo2sm.controller;

public class EmptyCommunicator implements CommInterface {

	@Override
	public int waitChildInteraction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String waitRfidObject() {
		// TODO Auto-generated method stub
		return "1234";
	}

	@Override
	public void openConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTeoMood(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendTeoMovement(String code) {
		// TODO Auto-generated method stub
		
	}
}
