package teo2sm.view.wizard;

import java.util.Map;

import javax.swing.event.ChangeListener;

import teo2sm.view.wizard.Wizard;
import teo2sm.view.wizard.WizardModelChangeEvent;
import teo2sm.view.wizard.WizardPage;

/**
 * A data model for a {@link Wizard}. The model is responsible for maintaining
 * both the data and the state of the wizard as it works and the pages it uses.
 * 
 * A properly implemented model must give each of it's possible states a String
 * identifier and a {@link WizardPage} to be displayed while it is in that
 * state.
 *
 * @param <T>
 *            the type of the result value
 */
public interface WizardModel<T> {
    /**
     * Returns true if the model can be "completed" and a usable result
     * retrieved from it.
     * 
     * @return true when model is finished
     */
    boolean completable();

    /**
     * Returns the result of finishing the Wizard.
     * 
     * This method can only be successfully called when completable() returns
     * true; otherwise an IllegalStateException will be thrown.
     * 
     * @return the result of the Wizard
     * @throws IllegalStateException
     *             if called while incomplete
     */
    T complete() throws IllegalStateException;

    /**
     * Tells the model to go directly to the given state and returns the identifier of 
     * that state
     * 
     * This is an optional operation; some models may wish to only allow their
     * state to change through the traversal order they establish with the forward()
     * and back() methods.
     * 
     * @param identifier
     *            the identifier
     * @return the identifier
     */
    default String goToState(String identifier) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an unmodifiable Map containing each of the {@link WizardPage} in
     * the model and the String identifiers of the states associated with them.
     * 
     * @return the pages and their states
     */
    Map<String, WizardPage> getAllPages();

    /**
     * Returns a collection containing all of the current states of the model.
     * 
     * @return the identifier of the current page
     */
    String getCurrentState();

    /**
     * Tells the model to advance to the next state, if able, and returns the
     * String identifier of the new current state.
     * 
     * @return the identifier of the new state
     */
    String forward();

    /**
     * Returns true if the model can advance it's state.
     * 
     * @return if the model can advance or not
     */
    boolean canGoForward();

    /**
     * Tells the model to return to the previous state, if able, and returns the
     * String identifier of the new current state.
     * 
     * @return the identifier of the new state
     */
    String back();

    /**
     * Returns true if the model can go back to a previous page.
     * 
     * @return if the model can go back or not
     */
    boolean canGoBack();

    /**
     * Returns the String identifier of the starting state of this model.
     * 
     * @return the first state
     */
    String getInitialState();

    /**
     * Registers a listener that wishes to receive
     * {@link WizardModelChangeEvent} from this model.
     * 
     * @param l
     *            the listener
     */
    void registerModelListener(ChangeListener l);

    /**
     * Unregisters a listener so that it will no longer receive events from this
     * model.
     * 
     * @param l
     *            the listener
     */
    void unregisterModelListener(ChangeListener l);
}