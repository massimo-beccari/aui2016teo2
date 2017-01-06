package teo2sm.view;

public class CLI implements UserInterface {
	
	@Override
	public void showFileNotFound(String filePath) {
		System.out.println("Error: file " + filePath + " not found");
	}

}
