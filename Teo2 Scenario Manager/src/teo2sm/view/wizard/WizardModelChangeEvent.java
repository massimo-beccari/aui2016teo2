package teo2sm.view.wizard;

import javax.swing.event.ChangeEvent;

import teo2sm.view.wizard.WizardModel;

/**
 * Event generated whenever the state of a {@link WizardModel} changes in a way
 * that external listeners should be informed of.
 * 
 * @author Damien
 *
 */
@SuppressWarnings("serial")
public class WizardModelChangeEvent extends ChangeEvent {
    private final String newState;
    private final String oldState;

    /**
     * Get the new state of the model.
     * 
     * @return the new state
     */
    public String getNewState() {
        return newState;
    }

    /**
     * Get the previous state of the model
     * 
     * @return the old state
     */
    public String getOldState() {
        return oldState;
    }

    /**
     * Public constructor.
     * 
     * @param source
     *            the model that generated the event
     * @param newState
     *            the state that the model entered
     * @param oldState
     *            the state that the model left
     */
    public WizardModelChangeEvent(WizardModel<?> source, String newState,
            String oldState) {
        super(source);
        this.newState = newState;
        this.oldState = oldState;
    }

    /**
     * Get the source WizardModel.
     * 
     * @return the event source
     */
    public WizardModel<?> getSource() {
        return (WizardModel<?>) super.getSource();
    }
}