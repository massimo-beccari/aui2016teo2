package teo2sm.view.wizard;

import java.awt.Dimension;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import teo2sm.Constants;

public class NewScenarioWizardPage2 extends WizardPage {
	private static final long serialVersionUID = 1L;
	private BoxLayout layout;
	private JLabel storyLabel;
	private JLabel reinforcementLabel;
	private JLabel videoLabel;
	private JLabel imageLabel;
	private JLabel tagLabel;
	private JLabel actionsLabel;
	private JTextField storyText;
	private JTextField reinforcementText;
	private JTextField videoText;
	private JTextField imageText;
	private JTextField tagText;
	private JTextField actionsText;
	private JButton storyButton;
	private JButton reinforcementButton;
	private JButton videoButton;
	private JButton imageButton;
	private String storyPath;
	private String reinforcementPath;
	private String videoPath;
	private String imagePath;
	private String rfidTag;
	private String actions;
	private boolean error;
	
	public NewScenarioWizardPage2() {
		error = false;
		storyPath = Constants.SCENE_DEFAULT_PATH_NAME;
		reinforcementPath = Constants.SCENE_DEFAULT_PATH_NAME;
		videoPath = Constants.SCENE_DEFAULT_PATH_NAME;
		imagePath = Constants.SCENE_DEFAULT_PATH_NAME;
		rfidTag = "";
		actions = "";
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
			actions = actionsText.getText();
		} catch(NullPointerException e) {
			error = true;
		} catch(NumberFormatException e) {
			error = true;
		}
		if(imagePath == null || storyPath == null || reinforcementPath == null || videoPath == null)
			error = true;
	}
	
	protected String askFile(String extension) {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file...");
		fc.setApproveButtonText("Open");
		fc.setFileFilter(new FileNameExtensionFilter("."+extension+" file", extension));
		int returnVal = fc.showOpenDialog(this);
		File file = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return new String(file.getAbsolutePath());
        }
        
		return Constants.SCENE_DEFAULT_PATH_NAME;
	}

	@Override
	public void onPageShown() {
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		storyLabel = new JLabel("Story audio file:");
		reinforcementLabel = new JLabel("Reinforcement video file:");
		videoLabel = new JLabel("Projected content video file:");
		imageLabel = new JLabel("Object image file:");
		tagLabel = new JLabel("RFID tag:");
		actionsLabel = new JLabel("Teo actions ( hour:min:sec:millis_mood, ... ) :");
		storyText = new JTextField();
		reinforcementText = new JTextField();
		videoText = new JTextField();
		imageText = new JTextField();
		tagText = new JTextField();
		actionsText = new JTextField();
		storyButton = new JButton("Choose file");
		reinforcementButton = new JButton("Choose file");
		videoButton = new JButton("Choose file");
		imageButton = new JButton("Choose file");
		storyText.setEnabled(false);
		reinforcementText.setEnabled(false);
		videoText.setEnabled(false);
		imageText.setEnabled(false);
		NewScenarioWizardPage2Handler handler = new NewScenarioWizardPage2Handler(this, storyButton, reinforcementButton, videoButton, imageButton);
		storyButton.addActionListener(handler);
		reinforcementButton.addActionListener(handler);
		videoButton.addActionListener(handler);
		imageButton.addActionListener(handler);
		
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(storyLabel);
		this.add(storyText);
		this.add(storyButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(reinforcementLabel);
		this.add(reinforcementText);
		this.add(reinforcementButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(videoLabel);
		this.add(videoText);
		this.add(videoButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(imageLabel);
		this.add(imageText);
		this.add(imageButton);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(tagLabel);
		this.add(tagText);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(actionsLabel);
		this.add(actionsText);
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

	protected void setReinforcementText(String reinforcementPath) {
		this.reinforcementPath = reinforcementPath;
		this.reinforcementText.setText(reinforcementPath);
	}

	protected void setVideoText(String videoPath) {
		this.videoPath = videoPath;
		this.videoText.setText(videoPath);
	}
	
	protected void setImageText(String imagePath) {
		this.imagePath = imagePath;
		this.imageText.setText(imagePath);
	}

	public String getStoryPath() {
		return storyPath;
	}

	public String getReinforcementPath() {
		return reinforcementPath;
	}

	public String getVideoPath() {
		return videoPath;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public String getRfidTag() {
		return rfidTag;
	}
	
	public String getActions() {
		return actions;
	}
}
