package teo2sm.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GUI implements Runnable {
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItem;
	
	public GUI() {
		mainFrame = new JFrame("Teo2 Scenario Manager");
		menuBar = new JMenuBar();
		
		//file menu
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_A);
		menuFile.getAccessibleContext().setAccessibleDescription(
		        "File");
		menuBar.add(menuFile);
		
		//file/new
		menuItem = new JMenuItem("New Scenario",
                KeyEvent.VK_T);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Create a new scenario");
		menuFile.add(menuItem);
		
		//file/load
		menuItem = new JMenuItem("Open Scenario...",
                KeyEvent.VK_T);
		/*menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));*/
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Open an existing scenario");
		menuFile.add(menuItem);
		
		/*menuItem = new JMenuItem("Both text and icon",
		                new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menuFile.add(menuItem);
		
		menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuFile.add(menuItem);*/
		

		mainFrame.setJMenuBar(menuBar);
		
		mainFrame.setSize(900, 700);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		/*ExecutorService e = Executors.newSingleThreadExecutor();
		GUI gui = new GUI();
		e.execute(gui);*/
		
	}

	/** 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
	}
}
