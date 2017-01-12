package teo2sm.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import teo2sm.Constants;

public class MainToolBar extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private boolean isActive;
    private GUI gui;
    private JButton newFileButton;
    private JButton loadFileButton;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
	protected String newline = "\n";

    public MainToolBar(GUI gui) {
    	super(new BorderLayout());
    	isActive = false;
    	this.gui = gui;

        //Create the toolbar.
        JToolBar toolBar = new JToolBar("Tool bar");
        addButtons(toolBar);
        setPlayable(0);

        //Lay out the main panel.
        setPreferredSize(new Dimension(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_ICONS_SIZE+16));
        add(toolBar, BorderLayout.PAGE_START);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
    }

    protected void addButtons(JToolBar toolBar) {
        //new file button
    	newFileButton = makeButton("res/images/new_file.png",
                                      "New scenario",
                                      "New");
        toolBar.add(newFileButton);
        
        //open file button
        loadFileButton = makeButton("res/images/open_file.png",
                                      "Open scenario...",
                                      "Load");
        toolBar.add(loadFileButton);
        toolBar.addSeparator();
        
        //play scenario button
        playButton = makeButton("res/images/play.png",
                                      "Play scenario",
                                      "Play");
        toolBar.add(playButton);
        
        //pause scenario button
        pauseButton = makeButton("res/images/pause.png",
                                      "Pause scenario",
                                      "Pause");
        toolBar.add(pauseButton);
        
        //stop scenario button
        stopButton = makeButton("res/images/stop.png",
                                      "Stop scenario",
                                      "Stop");
        toolBar.add(stopButton);
    }

    protected JButton makeButton(String imagePath, String toolTipText, String altText) {
        //Create and initialize the button.
        JButton button = new JButton();
        //button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        button.setIcon(new ImageIcon(imagePath, altText));

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //String cmd = e.getActionCommand();

        // Handle each button
        if(isActive)
	        if (e.getSource().equals(loadFileButton)) {
	        	gui.setUserInt(Constants.ACTION_LOAD_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(newFileButton)) {
	        	gui.setUserInt(Constants.ACTION_NEW_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(playButton)) {
	        	gui.setUserInt(Constants.ACTION_PLAY_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(pauseButton)) {
	        	gui.setUserInt(Constants.ACTION_PAUSE_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(stopButton)) {
	        	gui.setUserInt(Constants.ACTION_STOP_SCENARIO);
	            gui.setUserBool(true);
	        }
    }

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void setPlayable(int code) {
		switch(code) {
			case Constants.SCENARIO_STOPPED:
				playButton.setEnabled(true);
	        	pauseButton.setEnabled(false);
	        	stopButton.setEnabled(false);
	        	break;
		
			case Constants.SCENARIO_PLAYED:
	        	playButton.setEnabled(false);
	        	pauseButton.setEnabled(true);
	        	stopButton.setEnabled(true);
	        	break;
	        
			case Constants.SCENARIO_PAUSED:
	        	playButton.setEnabled(true);
	        	pauseButton.setEnabled(false);
	        	stopButton.setEnabled(true);
	        	break;
	        
	        default:
	        	playButton.setEnabled(false);
	        	pauseButton.setEnabled(false);
	        	stopButton.setEnabled(false);
		}
	}
}
