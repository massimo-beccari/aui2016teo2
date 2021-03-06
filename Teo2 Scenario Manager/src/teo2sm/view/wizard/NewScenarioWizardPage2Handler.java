package teo2sm.view.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import teo2sm.Constants;

public class NewScenarioWizardPage2Handler implements ActionListener {
	private NewScenarioWizardPage2 page2;
	private JButton storyButton;
	private JButton reinforcementButton;
	private JButton videoButton;
	private JButton imageButton;
	
	public NewScenarioWizardPage2Handler(NewScenarioWizardPage2 page2, JButton storyButton, 
			JButton reinforcementButton, JButton videoButton,  JButton imageButton) {
		this.page2 = page2;
		this.storyButton = storyButton;
		this.reinforcementButton = reinforcementButton;
		this.videoButton = videoButton;
		this.imageButton = imageButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(storyButton)) {
        	String s = page2.askFile(Constants.FILE_EXTENSION_STORY);
        	page2.setStoryText(s);
        	page2.updateUI();
        } else if (e.getSource().equals(reinforcementButton)) {
        	String s = page2.askFile(Constants.FILE_EXTENSION_REINFORCEMENT_CONTENT);
        	page2.setReinforcementText(s);
        	page2.updateUI();
        } else if (e.getSource().equals(videoButton)) {
        	String s = page2.askFile(Constants.FILE_EXTENSION_PROJECTED_CONTENT);
        	page2.setVideoText(s);
        	page2.updateUI();
        } else if (e.getSource().equals(imageButton)) {
        	String s = page2.askFile(Constants.FILE_EXTENSION_OBJECT_IMAGE);
        	page2.setImageText(s);
        	page2.updateUI();
        }
	}
}
