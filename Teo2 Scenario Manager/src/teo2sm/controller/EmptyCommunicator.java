package teo2sm.controller;

public class EmptyCommunicator implements CommInterface {

	@Override
	public int waitButtonInteraction() {
		return 0;
	}

	@Override
	public String waitRfidObject() {
		return "1234";
	}

	@Override
	public void openConnection() {
		
	}

	@Override
	public void closeConnection() {
		
	}

	@Override
	public void setTeoMood(String code) {
		
	}

	@Override
	public void sendTeoMovement(String code) {
		
	}

	@Override
	public int waitFsrInteraction() {
		// TODO Auto-generated method stub
		return 0;
	}
}
