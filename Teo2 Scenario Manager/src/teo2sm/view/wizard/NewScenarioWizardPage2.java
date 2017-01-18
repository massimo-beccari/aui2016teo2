package teo2sm.view.wizard;

import java.awt.Dimension;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import teo2sm.Constants;

public class NewScenarioWizardPage2 extends WizardPage {
	private static final long serialVersionUID = 1L;
	private BoxLayout layout;
	private JLabel storyLabel;
	private JLabel musicLabel;
	private JLabel videoLabel;
	private JLabel tagLabel;
	private JTextField storyText;
	private JTextField musicText;
	private JTextField videoText;
	private JTextField tagText;
	private JButton storyButton;
	private JButton musicButton;
	private JButton videoButton;
	private String storyPath;
	private String musicPath;
	private String videoPath;
	private String rfidTag;
	private boolean error;
	
	public NewScenarioWizardPage2() {
		error = false;
	}
	
	@Override
	public void onPageAboutToDisplay() {
		error = false;
		removeAll();
	}

	@Override
	public void onPageHidden() {
		try {
			rfidTag = tagText.getText();
		} catch(NullPointerException e) {
			error = true;
		} catch(NumberFormatException e) {
			error = true;
		}
		if(rfidTag == null || storyPath == null || musicPath == null || videoPath == null)
			error = true;
	}
	
	protected String askFile() {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file...");
		fc.setApproveButtonText("Open");
		int returnVal = fc.showOpenDialog(this);
		File file = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return new String(file.getAbsolutePath()+Constants.FILE_EXTENSION);
        }
        
		return null;
	}

	@Override
	public void onPageShown() {
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		storyLabel = new JLabel("Story audio file:");
		musicLabel = new JLabel("Background music audio file:");
		videoLabel = new JLabel("Projected content video file:");
		tagLabel = new JLabel("RFID tag:");
		storyText = new JTextField();
		musicText = new JTextField();
		videoText = new JTextField();
		tagText = new JTextField();
		storyButton = new JButton("Choose file");
		musicButton = new JButton("Choose file");
		videoButton = new JButton("Choose file");
		storyText.setEnabled(false);
		musicText.setEnabled(false);
		videoText.setEnabled(false);
		NewScenarioWizardPage2Handler handler = new NewScenarioWizardPage2Handler(this, storyButton, musicButton, videoButton);
		storyButton.addActionListener(handler);
		musicButton.addActionListener(handler);
		videoButton.addActionListener(handler);
		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(storyLabel);
		this.add(storyText);
		this.add(storyButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(musicLabel);
		this.add(musicText);
		this.add(musicButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(videoLabel);
		this.add(videoText);
		this.add(videoButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(tagLabel);
		this.add(tagText);
		this.updateUI();
	}

	@Override
	public boolean getError() {
		return error;
	}

	protected void setStoryText(String storyPath) {
		this.storyPath = storyPath;
		this.storyText.setText(storyPath);
	}

	protected void setMusicText(String musicPath) {
		this.musicPath = musicPath;
		this.musicText.setText(musicPath);
	}

	protected void setVideoText(String videoPath) {
		this.videoPath = videoPath;
		this.videoText.setText(videoPath);
	}

	protected void setRfidTag(String rfidTag) {
		this.tagText.setText(rfidTag);
		this.rfidTag = rfidTag;
	}

	public String getStoryPath() {
		return storyPath;
	}

	public String getMusicPath() {
		return musicPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public String getRfidTag() {
		return rfidTag;
	}
}
