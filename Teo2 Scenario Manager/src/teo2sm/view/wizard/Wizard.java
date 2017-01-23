package teo2sm.view.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import teo2sm.view.wizard.WizardModel;
import teo2sm.view.wizard.WizardModelChangeEvent;

    /**
     * Class for simple JDialog-based wizard.
     * 
     * This class handles the "outside facing" functionality of displaying and
     * traversing the pages and passing the final result out of itself.
     *
     * Custom functionality is provided by passing an implementation of {@link
     * WizardModel} to the constructor.
     * 
     * This class provides the following functionality: <br>
     * - Provides buttons to advance or retreat the wizard, which are disabled or
     * enabled based on whether the model can go forward or back at any point, and a
     * cancel button to end the wizard without completing it. <br>
     * - Change the displayed {@link WizardPage} when the model changes state.
     * 
     * @param <T>
     *            the type of the result the wizard will generate
     *
     */
    public class Wizard<T> implements ActionListener, ChangeListener {
        private final WizardModel<T> model;
        private final JDialog dialog = new JDialog();
        private final JPanel contentPanel = new JPanel();
        private final CardLayout layout;
        private final JButton nextButton;
        private final JButton backButton;
        private final Consumer<T> completionCallback;
        private boolean started = false;

        /**
         * Public constructor.
         * 
         * 
         * @param model
         *            the WizardModel to use
         * @param completionCallback
         *            called when the wizard completes
         */
        public Wizard(WizardModel<T> model, Consumer<T> completionCallback) {
            dialog.setBounds(100, 100, 450, 500);
            dialog.setResizable(false);
            dialog.getContentPane().setLayout(new BorderLayout());
            this.layout = new CardLayout();
            contentPanel.setLayout(layout);
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.model = model;
            model.registerModelListener(this);
            dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
            {
                JPanel buttonPane = new JPanel();
                buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                dialog.getContentPane().add(buttonPane, BorderLayout.SOUTH);
                {
                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setActionCommand("Cancel");
                    buttonPane.add(cancelButton);
                    cancelButton.addActionListener(this);
                }
                {
                    backButton = new JButton("Back");
                    backButton.setActionCommand("Back");
                    backButton.setEnabled(false);
                    buttonPane.add(backButton);
                    dialog.getRootPane().setDefaultButton(backButton);
                    backButton.addActionListener(this);
                }
                {
                    nextButton = new JButton("Next");
                    nextButton.setActionCommand("Next");
                    buttonPane.add(nextButton);
                    dialog.getRootPane().setDefaultButton(nextButton);
                    nextButton.addActionListener(this);
                }
            }
            dialog.setModal(true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            this.completionCallback = completionCallback;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
            case "Next":
                model.forward();
                break;
            case "Cancel":
            	completionCallback.accept(null);
                dialog.dispose();
                break;
            case "Back":
                model.back();
                break;
            case "Finish":
                complete();
                break;
            }
        }


    /**
     * Starts the wizard and displays it.
     * 
     * A wizard may only be started once.
     */
    public void startWizard() {
        if (started) {
            throw new IllegalStateException("Tried to start a wizard that had already started.");
        }
        for (Entry<String, WizardPage> entry : model.getAllPages().entrySet()) {
            layout.addLayoutComponent(entry.getValue(), entry.getKey());
            contentPanel.add(entry.getValue());
        }
        layout.show(contentPanel, model.getInitialState());
        dialog.setVisible(true);
        started = true;
    }

    /**
     * Returns whether or not this wizard can be completed.
     * 
     * @return true when completable
     */
    public boolean isComplete() {
        return model.completable();
    }

    /**
     * Finish the wizard. The callback method is applied and the
     * JDialog is disposed.
     */
    private void complete() {
        if (!model.completable()) {
            throw new IllegalStateException("Attempted to complete an unfinished model.");
        }
        completionCallback.accept(model.complete());
        dialog.dispose();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e instanceof WizardModelChangeEvent) {
            if (model.canGoForward()) {
                nextButton.setEnabled(true);
            } else {
                nextButton.setEnabled(false);
            }

            if (model.canGoBack()) {
                backButton.setEnabled(true);
            } else {
                backButton.setEnabled(false);
            }

            if (model.completable()) {
                nextButton.setText("Finish");
            } else {
                nextButton.setText("Next");
            }
            nextButton.setActionCommand(nextButton.getText());
            layout.show(contentPanel, ((WizardModelChangeEvent) e).getNewState());
        }
    }
}