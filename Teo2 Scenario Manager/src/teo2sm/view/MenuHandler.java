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
    	setPlayable(0);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isActive)
	        if (e.getSource().equals(menuItems.get(0))) {
	        	gui.setUserInt(Constants.ACTION_NEW_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(menuItems.get(1))) {
	        	gui.setUserInt(Constants.ACTION_LOAD_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(menuItems.get(2))) {
	        	gui.setUserInt(Constants.ACTION_PLAY_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(menuItems.get(3))) {
	        	gui.setUserInt(Constants.ACTION_PAUSE_SCENARIO);
	            gui.setUserBool(true);
	        } else if (e.getSource().equals(menuItems.get(4))) {
	        	gui.setUserInt(Constants.ACTION_STOP_SCENARIO);
	            gui.setUserBool(true);
	        }
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public ArrayList<JMenuItem> getMenuItems() {
		return menuItems;
	}
	
	public void setPlayable(int code) {
		switch(code) {
			case Constants.SCENARIO_STOPPED:
				menuItems.get(2).setEnabled(true);
	        	menuItems.get(3).setEnabled(false);
	        	menuItems.get(4).setEnabled(false);
	        	break;
		
			case Constants.SCENARIO_PLAYED:
				menuItems.get(2).setEnabled(false);
				menuItems.get(3).setEnabled(true);
				menuItems.get(4).setEnabled(true);
	        	break;
	        
			case Constants.SCENARIO_PAUSED:
				menuItems.get(2).setEnabled(true);
				menuItems.get(3).setEnabled(false);
				menuItems.get(4).setEnabled(true);
	        	break;
	        
	        default:
	        	menuItems.get(2).setEnabled(false);
	        	menuItems.get(3).setEnabled(false);
	        	menuItems.get(4).setEnabled(false);
		}
	}
}
