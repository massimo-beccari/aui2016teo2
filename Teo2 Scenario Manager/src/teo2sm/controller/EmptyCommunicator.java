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
	public void setTeoMood(int code) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTeoMovement(int code) {
		// TODO Auto-generated method stub

	}
}
