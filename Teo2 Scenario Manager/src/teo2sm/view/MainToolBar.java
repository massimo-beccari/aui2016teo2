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
	protected String newline = "\n";

    public MainToolBar(GUI gui) {
    	super(new BorderLayout());
    	isActive = false;
    	this.gui = gui;

        //Create the toolbar.
        JToolBar toolBar = new JToolBar("Tool bar");
        addButtons(toolBar);


        //Lay out the main panel.
        setPreferredSize(new Dimension(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_ICONS_SIZE+16));
        add(toolBar, BorderLayout.PAGE_START);
        toolBar.setFloatable(false);
    }

    protected void addButtons(JToolBar toolBar) {
        //new file button
    	newFileButton = makeButton("res/images/new_file.png",
                                      "Back to previous something-or-other",
                                      "Previous");
        toolBar.add(newFileButton);
        
      //open file button
        loadFileButton = makeButton("res/images/open_file.png",
                                      "Back to previous something-or-other",
                                      "Previous");
        toolBar.add(loadFileButton);
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
	        	gui.setUserInt(Constants.LOAD_SCENARIO_ACTION);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(newFileButton)) {
	        	gui.setUserInt(Constants.NEW_SCENARIO_ACTION);
	            gui.setUserBool(true);
	        }
    }

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
