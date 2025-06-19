/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SortifyMenuBar.java
 */

package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

import model.ProgramDetails;

/**
 * This class acts as the MenuBar for the SortifyFrame. All menubar components required for certain actions are
 * implemented in this class.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SortifyMenuBar extends JMenuBar {

    /** PropertyChange object that sends messages to items listening. Used to communicate between SortifyFrame. */
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /** JPanel that acts as the center panel for the JFrame. Used for centering JOptionPane. */
    private final JPanel myCenterPanel;

    /** JMenu containing the about menu of the program and other submenus. */
    private JMenu myAboutMenu;

    /** JMenu containing the sorting menu of the program and other submenus. */
    private JMenu mySortingMenu;

    /** Button group to hold all the sorting options for files. */
    private final ButtonGroup myButtonGroup;

    /** String containing the name of the user. */
    private String myName;

    /** String containing the email of the user. */
    private String myEmail;

    /** JTextField used for receiving the name of the user. */
    private JTextField myNameField;

    /** JTextField used for receiving the email of the user. */
    private JTextField myEmailField;

    /**
     * Creates instances of SortifyMenuBar with a given JPanel that acts as the center panel of the program.
     * The constructor assigns values to certain instance fields with the rest being implemented in methods
     * that are soon called.
     *
     * @param theCenterPanel is the center panel of the Program frame.
     */
    public SortifyMenuBar(JPanel theCenterPanel) {
        super();
        myCenterPanel = theCenterPanel;
        myName = "";
        myEmail = "";
        myButtonGroup = new ButtonGroup();
        setupAccountMenu();
        setupAboutMenu();
        setupSortingMenu();

    }

    /**
     * Sets up the account menu and the submenus that are associated with it.
     */
    public void setupAccountMenu() {
        final JMenu accountMenu = new JMenu("Account");
        final JPanel loginPanel = createLoginPanel();

        final JMenuItem login = new JMenuItem("Create/Login Account");
        final JMenuItem saveAccount = new JMenuItem("Save Account");
        final JMenuItem logout = new JMenuItem("Close Program");

        saveAccount.setEnabled(false);
        logout.setEnabled(false);

        login.addActionListener(_ -> {
            final int result = JOptionPane.showConfirmDialog(myCenterPanel, loginPanel, "Create/Login Account",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    SortifyFrame.SMALL_ICON);
            if (result == JOptionPane.OK_OPTION) {
                myName = myNameField.getText();
                myEmail = myEmailField.getText();
                final String results = myNameField.getText() + "," + myEmailField.getText();
                if (!results.equals(",")) {
                    if (!results.startsWith(",") && !results.endsWith(",")) {
                        myPCS.firePropertyChange("userInfo", null, results);
                        myNameField.setText(null);
                        myEmailField.setText(null);
                        login.setEnabled(false);
                        logout.setEnabled(true);
                        saveAccount.setEnabled(true);
                        myAboutMenu.setVisible(true);
                        mySortingMenu.setVisible(true);
                    }
                }
            }

        });

        saveAccount.addActionListener(_ -> {
            myPCS.firePropertyChange("saveAccount", false, true);
            saveAccount.setEnabled(false);
        });

        logout.addActionListener(_ -> {
            myPCS.firePropertyChange("closedProgram", false, true);
            login.setEnabled(true);
            logout.setEnabled(false);
            saveAccount.setEnabled(false);
            myAboutMenu.setVisible(false);
            mySortingMenu.setVisible(false);
            System.exit(0);
        });

        accountMenu.add(login);
        accountMenu.add(saveAccount);
        accountMenu.add(logout);

        add(accountMenu);
    }

    /**
     * Sets up the about menu for the menubar and all the submenus associated with it.
     */
    public void setupAboutMenu() {
        myAboutMenu = new JMenu("About");
        final JMenuItem aboutDetails = new JMenuItem("Details");
        aboutDetails.addActionListener(_ -> {
            String details = ProgramDetails.getProgramVersion();
            details += "This app is registered to: \n";
            details += "Name: " + myName + "\nEmail: " + myEmail + "\n";
            details += ProgramDetails.getProgramDetails();
            String finalDetails = details;
            JOptionPane.showMessageDialog(myCenterPanel, finalDetails, "About Program", JOptionPane.PLAIN_MESSAGE,
                    SortifyFrame.SMALL_ICON);
        });

        myAboutMenu.setVisible(false);
        myAboutMenu.add(aboutDetails);


        final JMenuItem helpDetails = new JMenuItem("Help");
        helpDetails.addActionListener(_ -> {
            JOptionPane.showMessageDialog(myCenterPanel, ProgramDetails.getProgramHelp(), "Sortify Help", JOptionPane.PLAIN_MESSAGE,
                    SortifyFrame.SMALL_ICON);
        });
        myAboutMenu.add(helpDetails);
        add(myAboutMenu);
    }

    public void setupSortingMenu() {
        mySortingMenu = new JMenu("Sorting");
        mySortingMenu.setVisible(false);
        final JRadioButton fileTypeRadio = new JRadioButton("File Type");
        fileTypeRadio.addActionListener(_ -> {
            myPCS.firePropertyChange("organizeByFiles", 0, 1);
        });
        final JRadioButton fileNameRadio = new JRadioButton("File Name");
        fileNameRadio.addActionListener(_ -> {
            myPCS.firePropertyChange("organizeByFiles", 0, 2);
        });
        myButtonGroup.add(fileTypeRadio);
        mySortingMenu.add(fileTypeRadio);

        myButtonGroup.add(fileNameRadio);
        mySortingMenu.add(fileNameRadio);
        add(mySortingMenu);
    }



    /**
     * Creates a JPanel that is used for JOptionPane in order to receive information
     * about the user.
     *
     * @return a JPanel containing components necessary for log into the program.
     */
    public JPanel createLoginPanel() {
        myNameField = new JTextField(SortifyFrame.FIELD_SIZE);
        myEmailField = new JTextField(SortifyFrame.FIELD_SIZE);
        final JPanel loginPanel = new JPanel();
        final JLabel nameLabel = new JLabel("First Name:");
        final JLabel emailLabel = new JLabel("Email");

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));

        loginPanel.add(nameLabel);
        nameLabel.setLabelFor(myNameField);
        loginPanel.add(myNameField);

        loginPanel.add(emailLabel);
        emailLabel.setLabelFor(myEmailField);
        loginPanel.add(myEmailField);

        return loginPanel;
    }

    /**
     * Adds a listener for property change events from this class.
     *
     * @param theListener a PropertyChangeListener to remove.
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
