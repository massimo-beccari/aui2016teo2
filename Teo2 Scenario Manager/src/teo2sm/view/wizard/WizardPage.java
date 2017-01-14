package teo2sm.view.wizard;

import javax.swing.JPanel;

/**
 * Abstract class for a page to be displayed by a {@link Wizard}.
 * 
 * @author Damien
 *
 */
@SuppressWarnings("serial")
public abstract class WizardPage extends JPanel {
	private boolean error;

    /**
     * Method called before the page is about to be displayed.
     * 
     * Override this method to "set up" the page.
     */
    public void onPageAboutToDisplay() {

    };

    /**
     * Method called when the page is hidden.
     * 
     * Override this method for whatever is needed if a page is finished.
     */
    public void onPageHidden() {

    };

    /**
     * Method called when the page is made visible.
     */
    public void onPageShown() {

    }
    
    public boolean getError() {
    	return error;
    }
}