package teo2sm.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import teo2sm.Constants;
import teo2sm.controller.ScenarioManager;
import teo2sm.model.ScenarioData;
import teo2sm.model.ScenarioFileManager;
import teo2sm.model.SceneData;
import teo2sm.model.TeoAction;
import teo2sm.view.wizard.NewScenarioWizardCallback;
import teo2sm.view.wizard.NewScenarioWizardModel;
import teo2sm.view.wizard.Wizard;

public class GUI implements UserInterface, Runnable {
	private boolean userBool;
	private int userInt;
	
	private JFrame mainFrame;
	private JScrollPane mainContainer;
	private JPanel mainPanel;
	private ArrayList<JPanel> scenarioPanels;
	private BoxLayout scenarioLayout;
	private SpringLayout layout;
	private JMenuBar menuBar;
	private MenuHandler menuHandler;
	private MainToolBar toolBar;
	private JMenu menuFile;
	private JMenu menuScenario;
	private TimeSlider timeSlider;
	private ArrayList<JLabel> sceneLabels;
	private int displayedSceneNumber;
	
	public GUI() {
		userBool = true;
		userInt = Constants.DEFAULT_USERINT_VALUE;
		mainFrame = new JFrame(Constants.WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout = new SpringLayout();
		mainPanel = new JPanel();		
		mainContainer = new JScrollPane(mainPanel);
		mainFrame.add(mainContainer);
		mainContainer.setPreferredSize(new Dimension(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT));
		mainContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		mainPanel.setLayout(layout);
		
		//scenario panel
		sceneLabels = new ArrayList<JLabel>();
		scenarioPanels = new ArrayList<JPanel>();
		displayedSceneNumber = 0;
		
		//menu bar
		setupMenuBar();
		
		//tool bar
		setupToolBar();		
		
		//mainFrame.pack();
		mainFrame.setSize(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
	
	private void setupScenarioPanel(int n) {
		JPanel newPanel = new JPanel();
		scenarioLayout = new BoxLayout(newPanel, BoxLayout.X_AXIS);
		newPanel.setLayout(scenarioLayout);
		if(n == 0) {
			layout.putConstraint(SpringLayout.WEST, newPanel, 0, SpringLayout.WEST, mainPanel);
			layout.putConstraint(SpringLayout.NORTH, newPanel, 48, SpringLayout.NORTH, mainPanel);
		} else {
			layout.putConstraint(SpringLayout.WEST, newPanel, 0, SpringLayout.WEST, mainPanel);
			layout.putConstraint(SpringLayout.NORTH, newPanel, 16, SpringLayout.SOUTH, scenarioPanels.get(n - 1));
		}
		mainPanel.add(newPanel);
		scenarioPanels.add(newPanel);
	}
	
	private void setupMenuBar() {
		menuBar = new JMenuBar();
		menuHandler = new MenuHandler(this);
		//file menu
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuFile.getAccessibleContext().setAccessibleDescription(
		        "File");
		menuBar.add(menuFile);
		
		//file/new
		JMenuItem menuItem = new JMenuItem("New Scenario",
                KeyEvent.VK_N);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Create a new scenario");
		menuFile.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		//file/load
		menuItem = new JMenuItem("Open Scenario...",
                KeyEvent.VK_O);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Open an existing scenario");
		menuFile.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		//file/save
		menuItem = new JMenuItem("Save Scenario...",
                KeyEvent.VK_S);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Save scenario...");
		menuFile.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		menuFile.addSeparator();
		
		//file/close
		menuItem = new JMenuItem("Close scenario",
                KeyEvent.VK_C);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Close scenario");
		menuFile.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		/*menuItem = new JMenuItem("Both text and icon",
		                new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuFile.add(menuItem);
		
		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuFile.add(menuItem);*/
		
		//scenario menu
		menuScenario = new JMenu("Play");
		menuScenario.setMnemonic(KeyEvent.VK_P);
		menuScenario.getAccessibleContext().setAccessibleDescription(
		        "Play");
		menuBar.add(menuScenario);
		
		//scenario/play
		menuItem = new JMenuItem("Play scenario",
                KeyEvent.VK_P);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Play scenario");
		menuScenario.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		//scenario/pause
		menuItem = new JMenuItem("Pause scenario",
                KeyEvent.VK_U);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Pause scenario");
		menuScenario.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		//scenario/stop
		menuItem = new JMenuItem("Stop scenario",
                KeyEvent.VK_S);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Stop scenario");
		menuScenario.add(menuItem);
		menuHandler.getMenuItems().add(menuItem);
		
		menuHandler.setup();
		mainFrame.setJMenuBar(menuBar);
	}
	
	private void setupToolBar() {
		toolBar = new MainToolBar(this);
		mainPanel.add(toolBar);
		layout.putConstraint(SpringLayout.NORTH, toolBar, 0, SpringLayout.NORTH, mainPanel);
	}
	
	private void waitUserInput() {
		while(!userBool) { 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/** 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
	}

	@Override
	public void showFileNotFound(String filePath) {
		JOptionPane.showMessageDialog(mainFrame, "File "+filePath+" not found.");
	}

	@Override
	public void showCannotCreateFile(String filePath) {
		JOptionPane.showMessageDialog(mainFrame, "Impossible to create the file.");
	}

	@Override
	public int waitForUserAction() {
		userBool = false;
		toolBar.setActive(true);
		menuHandler.setActive(true);
		waitUserInput();
		toolBar.setActive(false);
		menuHandler.setActive(false);
		userBool = true;
		return userInt;
	}
	
	@Override
	public String askFile() {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open...");
		fc.setFileFilter(new FileNameExtensionFilter("Teo2 Scenario file", "teo2s"));
		fc.setApproveButtonText("Open");
		int returnVal = fc.showOpenDialog(mainFrame);
		File file = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return file.getAbsolutePath();
        }
        
		return null;
	}

	@Override
	public String askSaveFile() {
		final JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save file...");
		fc.setFileFilter(new FileNameExtensionFilter("Teo2 Scenario file", "teo2s"));
		fc.setApproveButtonText("Save");
		int returnVal = fc.showSaveDialog(mainFrame);
		File file = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            String path = file.getAbsolutePath();
            if(path.endsWith(Constants.FILE_EXTENSION_SCENARIO))
            	return new String(file.getAbsolutePath());
            else
            	return new String(file.getAbsolutePath()+Constants.FILE_EXTENSION_SCENARIO);
        }
        
		return null;
	}

	@Override
	public void showScenes(ScenarioData scenario) {
		for(SceneData scene : scenario.getScenes()) {
			showScene(scene);
		}
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	private void showScene(SceneData scene) {
		JLabel sceneLabel = new JLabel("scene " + scene.getSeqNumber(), 
				new ImageIcon("res/images/scene.png", "scene"), JLabel.CENTER);
		sceneLabels.add(sceneLabel);
		sceneLabel.setVerticalTextPosition(JLabel.BOTTOM);
		sceneLabel.setHorizontalTextPosition(JLabel.CENTER);
		String actions = "";
		for(TeoAction action : scene.getActions())
			actions = actions + action.toString() + " ";
		sceneLabel.setToolTipText("RFID tag: " + scene.getRfidObjectTag() + " - Actions: " + actions);
		displayedSceneNumber = displayedSceneNumber + 1;
		final int MAX_SCENE_FOR_ROW = 8;
		if((displayedSceneNumber - 1) % MAX_SCENE_FOR_ROW == 0)
			setupScenarioPanel((displayedSceneNumber - 1) / MAX_SCENE_FOR_ROW);
		scenarioPanels.get(scenarioPanels.size() - 1).add(Box.createRigidArea(new Dimension(10, 0)));
		scenarioPanels.get(scenarioPanels.size() - 1).add(sceneLabel);
		scenarioPanels.get(scenarioPanels.size() - 1).add(Box.createRigidArea(new Dimension(5, 0)));
		scenarioPanels.get(scenarioPanels.size() - 1).revalidate();
	}
	
	@Override
	public void setPlayableScenario(int code) {
		toolBar.setPlayable(code);
		menuHandler.setPlayable(code);
	}

	@Override
	public void setOpenedScenario(int code) {
		toolBar.setOpened(code);
		menuHandler.setOpened(code);
	}

	@Override
	public void setTitle(String title) {
		mainFrame.setTitle(Constants.WINDOW_TITLE+title);
	}

	@Override
	public void hideClosedScenario() {
		for(JPanel panel : scenarioPanels)
			mainPanel.remove(panel);
		mainPanel.remove(timeSlider);
		displayedSceneNumber = 0;
		setupScenarioPanel(0);
		mainPanel.repaint();
	}

	@Override
	public ScenarioData createScenarioWizard() {
		NewScenarioWizardModel model = new NewScenarioWizardModel();
		NewScenarioWizardCallback callback = new NewScenarioWizardCallback();
		Wizard<ScenarioData> wizard = new Wizard<ScenarioData>(model, callback);
		wizard.startWizard();
		return callback.getScenario();
	}

	@Override
	public void showInvalidPlayOperation(int code) {
		switch(code) {
			case Constants.ACTION_PLAY_SCENARIO:
				JOptionPane.showMessageDialog(mainFrame, "Invalid operation: play.");
				break;
				
			case Constants.ACTION_PAUSE_SCENARIO:
				JOptionPane.showMessageDialog(mainFrame, "Invalid operation: pause.");
				break;
				
			case Constants.ACTION_STOP_SCENARIO:
				JOptionPane.showMessageDialog(mainFrame, "Invalid operation: stop.");
				break;
		}
	}

	@Override
	public void showTimeSlider(int maxTime) {
		timeSlider = new TimeSlider(maxTime);
		layout.putConstraint(SpringLayout.SOUTH, timeSlider, 0, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(timeSlider);
		mainPanel.updateUI();
	}
	
	@Override
	public void updateTimeSlider(int time) {
		//TODO
	}

	public void setUserBool(boolean userBool) {
		this.userBool = userBool;
	}

	public void setUserInt(int userInt) {
		this.userInt = userInt;
	}

	/*public static void main(String[] args) {
		ExecutorService e = Executors.newSingleThreadExecutor();
		GUI gui = new GUI();
		e.execute(gui);
	}*/
}
