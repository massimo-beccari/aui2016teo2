package teo2sm.view.wizard;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NewScenarioWizardPage2 extends WizardPage {
	private static final long serialVersionUID = 1L;
	private BoxLayout layout;
	private JLabel titleLabel;
	private JLabel sceneNumberLabel;
	private JTextField titleText;
	private JTextField sceneNumberText;
	private String title;
	private int sceneNumber;
	private boolean error;
	
	public NewScenarioWizardPage2() {
		error = false;
	}
	
	@Override
	public void onPageAboutToDisplay() {
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		titleLabel = new JLabel("Scenario title:");
		sceneNumberLabel = new JLabel("Number of scenes in this scenario:");
		titleText = new JTextField();
		sceneNumberText = new JTextField();
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(titleLabel);
		this.add(titleText);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(sceneNumberLabel);
		this.add(sceneNumberText);
	}

	@Override
	public void onPageHidden() {
		try {
			title = titleText.getText();
			sceneNumber = Integer.parseInt(sceneNumberText.getText());
		} catch(NullPointerException e) {
			error = true;
		} catch(NumberFormatException e) {
			error = true;
		}
	}

	@Override
	public void onPageShown() {
		this.updateUI();
	}

	@Override
	public boolean getError() {
		return error;
	}

	public String getTitle() {
		return title;
	}

	public int getSceneNumber() {
		return sceneNumber;
	}
}
