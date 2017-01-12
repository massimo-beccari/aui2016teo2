package teo2sm.view;

public interface UserInterface {
	
	public void showFileNotFound(String filePath);
	
	public void showCannotCreateFile(String filePath);
	
	public int waitForUserAction();
	
	public String askFile();
	
	public void showScene(int number);
	
	public void setPlayableScenario(int code);
	
	public void setTitle(String title);
}
