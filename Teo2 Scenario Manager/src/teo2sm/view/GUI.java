package teo2sm.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpringLayout;

import teo2sm.Constants;

public class GUI implements UserInterface, Runnable {
	private boolean userBool;
	private int userInt;
	
	private JFrame mainFrame;
	private Container contentPane;
	private SpringLayout layout;
	private JMenuBar menuBar;
	private MenuHandler menuHandler;
	private MainToolBar toolBar;
	private JMenu menuFile;
	
	public GUI() {
		userBool = true;
		userInt = Constants.DEFAULT_USERINT_VALUE;
		mainFrame = new JFrame("Teo2 Scenario Manager");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layout = new SpringLayout();
		contentPane = mainFrame.getContentPane();
		contentPane.setLayout(layout);
		
		//menu bar
		setupMenuBar();
		
		//tool bar
		setupToolBar();
		
		//TEST
		
		//END TEST
		
		mainFrame.pack();
		mainFrame.setSize(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
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
		
		/*menuItem = new JMenuItem("Both text and icon",
		                new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuFile.add(menuItem);
		
		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuFile.add(menuItem);*/
		
		menuHandler.setup();
		mainFrame.setJMenuBar(menuBar);
	}
	
	private void setupToolBar() {
		toolBar = new MainToolBar(this);
		contentPane.add(toolBar);
		layout.putConstraint(SpringLayout.NORTH, toolBar, 0, SpringLayout.NORTH, contentPane);
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
		JOptionPane.showMessageDialog(mainFrame, "File non trovato.");
	}

	@Override
	public void showCannotCreateFile(String filePath) {
		JOptionPane.showMessageDialog(mainFrame, "Impossibile creare il file.");
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
		int returnVal = fc.showOpenDialog(mainFrame);
		File file = null;
		
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            return file.getAbsolutePath();
        }
        
		return null;
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
