package teo2sm.view;

public class CLI implements UserInterface {
	
	@Override
	public void showFileNotFound(String filePath) {
		System.out.println("Error: file " + filePath + " not found");
	}

	@Override
	public void showCannotCreateFile(String filePath) {
		System.out.println("Error: file " + filePath + " cannot be created");
	}

	@Override
	public int waitForUserAction() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String askFile() {
		// TODO Auto-generated method stub
		return null;
	}
}
