/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SortifyOptionsPanel.java
 */

package view;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 * The class acts as a JPanel that holds all the avaliable actions that can be done with the
 * file. This class uses propertyChange in order to send messages to SortifyFrame, all of which
 * represent the different actions.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SortifyOptionsPanel extends JPanel {

    /** PropertyChange object that sends messages to items listening. Used to communicate between SortifyFrame. */
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /** JPanel that acts as the center panel for the JFrame. Used for centering JOptionPane. */
    private final JPanel myCenterPanel;

    /** JFileChooser that allows users to choose their desired files for the program. */
    private final JFileChooser myFileChooser;

    /** JPanel that holds all the buttons. */
    private final JPanel myButtonPanel;

    /** JButton that allows the user to add files through JFileChooser. */
    private final JButton myFileChooserButton;

    /** JButton that allows the user to export all EnhancedFile objects. */
    private final JButton myExportButton;

    /** JButton that allows the user to import all EnhancedFile objects that have been created before. */
    private final JButton myImportButton;

    /** JButton that allows the user to edit an EnhancedFile. */
    private final JButton myEditButton;

    private final JButton myClearAll;

    /** JButton that allows the user to delete all duplicate EnhancedFiles. */
    private final JButton myDeleteDuplicateButton;

    /**
     * Constructor that creates SortifyOptionsPanel with a given center panel. The method assigns certain instance
     * methods to values and call other methods to instantiate the rest of the variables.
     *
     * @param theCenterPanel is the center panel of the Program frame.
     */
    public SortifyOptionsPanel(final JPanel theCenterPanel) {
        super();
        myCenterPanel = theCenterPanel;

        myButtonPanel = new JPanel();
        myFileChooserButton = new JButton("Add New File");
        myEditButton = new JButton("Edit File");
        myExportButton = new JButton("Export Files");
        myImportButton = new JButton("Import Files");
        myDeleteDuplicateButton = new JButton("Duplicates");
        myClearAll = new JButton("Clear All");
        myFileChooser = new JFileChooser();

        setupComponents();

        setupListeners();
    }

    /**
     * Sets up the components inside the class.
     */
    public void setupComponents() {
        setLayout(new BorderLayout());
        myEditButton.setEnabled(false);
        myButtonPanel.setBackground(new Color(229, 227, 220));

        final GridLayout experiment = new GridLayout(6, 0, 0, 20);
        myButtonPanel.setLayout(experiment);
        final JButton[] myButtons = { myFileChooserButton, myImportButton, myExportButton, myEditButton,
                myDeleteDuplicateButton, myClearAll };
        for (final JButton single : myButtons) {
            setupButton(single);
        }

        add(myButtonPanel, BorderLayout.CENTER);
    }

    /**
     * Sets up the JButton to a specific way.
     *
     * @param theButton is the JButton that is being altered.
     */
    public void setupButton(final JButton theButton) {
        theButton.setBackground(new Color(221,221,221));
        theButton.setPreferredSize(new Dimension(125, 5));
        theButton.setMinimumSize(new Dimension(100, 5));
        theButton.setMaximumSize(new Dimension(100, 5));
        myButtonPanel.add(theButton);
        theButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Sets up the listeners for each of the JButtons.
     */
    public void setupListeners() {

        myFileChooserButton.addActionListener(_ -> {
            final int result = myFileChooser.showOpenDialog(myCenterPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                myPCS.firePropertyChange("openEditFilePanel", null, myFileChooser.getSelectedFile());
            }
        });

        myImportButton.addActionListener(_ -> myPCS.firePropertyChange("getImportedEnhancedFiles",
                false, true));

        myExportButton.addActionListener(_ -> myPCS.firePropertyChange("saveEnhancedFiles",
                false, true));

        myEditButton.addActionListener(_ -> myPCS.firePropertyChange("getSelectedFile",
                false, true));

        myDeleteDuplicateButton.addActionListener(_ -> myPCS.firePropertyChange("removeDuplicates",
                false, true));
        myClearAll.addActionListener(_ -> {
            myPCS.firePropertyChange("clearFiles", false, true);
        });
    }

    /**
     * Changes whether myEditButton is enabled or not.
     *
     * @param theValue is the value for the status of myEditButton.
     */
    public void openEdit(final boolean theValue) {
        myEditButton.setEnabled(theValue);
    }

    /**
     * Changes whether myImportButton is enabled or not
     *
     * @param theValue is the value for the status of myImportButton.
     */
    public void openImport(final boolean theValue) {
        myImportButton.setEnabled(theValue);
    }

    /**
     * Adds a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to add.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes a listener for property change events from this class.
     * 
     * @param theListener a PropertyChangeListener to remove.
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }
}
