package teo2sm.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;

import teo2sm.Constants;

public class MenuHandler implements ActionListener {
	private boolean isActive;
    private GUI gui;
    private ArrayList<JMenuItem> menuItems;
    
    public MenuHandler(GUI gui) {
    	this.gui = gui;
    	isActive = false;
    	menuItems = new ArrayList<JMenuItem>();
    }
    
    public void setup() {
    	for(JMenuItem menuItem : menuItems) {
    		menuItem.addActionListener(this);
    	}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isActive)
	        if (e.getSource().equals(menuItems.get(0))) {
	        	gui.setUserInt(Constants.NEW_SCENARIO_ACTION);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(menuItems.get(1))) {
	        	gui.setUserInt(Constants.LOAD_SCENARIO_ACTION);
	            gui.setUserBool(true);
	        }
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public ArrayList<JMenuItem> getMenuItems() {
		return menuItems;
	}
}
