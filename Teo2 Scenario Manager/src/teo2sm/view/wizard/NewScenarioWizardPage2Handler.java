package teo2sm.view.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NewScenarioWizardPage2Handler implements ActionListener {
	private NewScenarioWizardPage2 page2;
	private JButton storyButton;
	private JButton musicButton;
	private JButton videoButton;
	
	public NewScenarioWizardPage2Handler(NewScenarioWizardPage2 page2, JButton storyButton, 
			JButton musicButton, JButton videoButton) {
		this.page2 = page2;
		this.storyButton = storyButton;
		this.musicButton = musicButton;
		this.videoButton = videoButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(storyButton)) {
        	String s = page2.askFile();
        	page2.setStoryText(s);
        	page2.updateUI();
        } else if (e.getSource().equals(musicButton)) {
        	String s = page2.askFile();
        	page2.setMusicText(s);
        	page2.updateUI();
        } else if (e.getSource().equals(videoButton)) {
        	String s = page2.askFile();
        	page2.setVideoText(s);
        	page2.updateUI();
        }
	}
}
