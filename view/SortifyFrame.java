/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SortifyFrame.java
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.EnhancedFile;
import model.FileNameComparator;
import model.FileTags;
import model.FileTypeComparator;

/**
 * This class is the main JFrame of the program. Here all instances necessary to the GUI are instantiated
 * and used as needed. The class also contains the necessary operations to alter the view of the Frame and uses/listens
 * to propertyChange to communicate to the model and listen to certain events.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SortifyFrame implements PropertyChangeListener {

    /** Dimensions of the JFrame */
    protected final static Dimension FRAME_SIZE = new Dimension(1500, 1000);

    /** ImageIcon of the Sortify Logo */
    protected final static ImageIcon FRAME_ICON = new ImageIcon(Objects.requireNonNull(
            SortifyFrame.class.getResource("/images/sortifylogo.jpg")));

    /** A Small version of FRAME_ICON, often used for JOptionPane. */
    protected final static ImageIcon SMALL_ICON = new ImageIcon(
            SortifyFrame.FRAME_ICON.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

    /** Integer containing the required size of text fields. */
    protected final static int FIELD_SIZE = 15;

    /** Color containing the main color of the program. */
    protected final static Color PROGRAM_COLOR = new Color(229, 227, 220);

    /** PropertyChange object that sends messages to items listening. Used to communicate between Controller. */
    private final PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    /** Instance field containing the JFrame of the program. */
    private final JFrame mySortifyFrame;

    /** Instance field containing the MenuBar of the program. */
    private final SortifyMenuBar myMenuBar;

    /** Instance field containing the JPanel that is used to hold the list of all uploaded files. */
    private final SortifyFileList mySortifyFileList;

    /** Instance field containing the JPanel for all operations possible in the program. */
    private final SortifyOptionsPanel mySortifyOptionsPanel;

    /** Instance field containing the JPanel that is used when the user wants to add or edit a file. */
    private EditFilePanel myEditFilePanel;

    /** Instance field of JPanel that is used for the center panel. */
    private final JPanel myCenterPanel;

    /**
     * Constructor of SortifyFrame.java that creates instances of the class with a given string. This is used
     * for the title of the JFrame. The constructor assigns all instance fields to their appropriate values and
     * call the necessary Objects for the Frame.
     *
     * @param theName is the name of JFrame.
     */
    public SortifyFrame(final String theName) {

        mySortifyFrame = new JFrame(theName);

        myCenterPanel = new JPanel();
        myCenterPanel.setLayout(new BorderLayout());

        myMenuBar = new SortifyMenuBar(myCenterPanel);

        mySortifyFileList = new SortifyFileList();

        mySortifyOptionsPanel = new SortifyOptionsPanel(myCenterPanel);

        myEditFilePanel = new EditFilePanel();

        setupFrame();
        addComponents();

        mySortifyFrame.setVisible(true);
    }

    /**
     * Sets up the JFrame of the program by calling certain methods that alter the way mySortifyFrame
     * look and act.
     */
    private void setupFrame() {
        final int ratio = 2;
        mySortifyFrame.getContentPane().setBackground(PROGRAM_COLOR);
        mySortifyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mySortifyFrame.setSize(FRAME_SIZE.width / ratio, FRAME_SIZE.height / ratio);
        mySortifyFrame.setLocationRelativeTo(null);
        mySortifyFrame.setIconImage(FRAME_ICON.getImage());
    }

    /**
     *  Adds all the instance variables, which are extends from common Swing instances, to the Frame
     *  to add the necessary functions. The class also connects the appropriate listeners so messages are
     *  sent and heard.
     */
    public void addComponents() {
        myCenterPanel.setVisible(false);
        mySortifyOptionsPanel.setVisible(false);

        mySortifyFrame.setJMenuBar(myMenuBar);
        myMenuBar.addPropertyChangeListener(this);
        mySortifyFrame.add(mySortifyOptionsPanel, BorderLayout.EAST);
        mySortifyFrame.add(myCenterPanel, BorderLayout.CENTER);
        myCenterPanel.add(mySortifyFileList.getList(), BorderLayout.CENTER);
        mySortifyOptionsPanel.addPropertyChangeListener(this);
    }

    /**
     * Method that listens for specific firedMessages usually from the menubar or SortifyOptionsPanel.
     * Depending on the event that was fired, this method sends messages to the controller in order to
     * interact with the model.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();
        if ("userInfo".equals(propertyName)) {
            myPCS.firePropertyChange("userInfo", null, theEvent.getNewValue());
            setVisibleComponents(true);
        } else if ("openEditFilePanel".equals(propertyName)) {
            final EnhancedFile chosenFile = new EnhancedFile((File) theEvent.getNewValue());
            myEditFilePanel = new EditFilePanel();
            myEditFilePanel.setFile(chosenFile);
            final int result = JOptionPane.showConfirmDialog(mySortifyFrame, myEditFilePanel, "Photo Details",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                final String description = myEditFilePanel.getDescription();
                final List<String> selected = myEditFilePanel.getSelected();
                final FileTags tags = new FileTags();
                for (final String element : selected) {
                    tags.selectTag(element);
                }
                chosenFile.setFileDescription(description);
                chosenFile.setTags(tags);

                myPCS.firePropertyChange("addedEnhancedFile", null, chosenFile);
                mySortifyFileList.addFile(chosenFile);
                mySortifyOptionsPanel.openEdit(true);
                myEditFilePanel.clear();
            }
        } else if ("saveAccount".equals(propertyName)) {
            myPCS.firePropertyChange("saveAccountList", false, true);
            showMessage("Saved Account");
        } else if ("saveEnhancedFiles".equals(propertyName)) {
            myPCS.firePropertyChange("saveEnhancedFileList", false, true);
            showMessage("Saved All Enhanced Files");
        } else if ("getSelectedFile".equals(propertyName)) {
            final EnhancedFile selectedFile = mySortifyFileList.getSelectedFile();
            if (selectedFile == null) {
                showMessage("Choose a file that you would like to edit");
            } else {
                myEditFilePanel = new EditFilePanel();
                myEditFilePanel.editFile(selectedFile);
                final int result = JOptionPane.showConfirmDialog(mySortifyFrame, myEditFilePanel, "Photo Details",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    final String description = myEditFilePanel.getDescription();
                    selectedFile.setFileDescription(description);
                    final List<String> selected = myEditFilePanel.getSelected();
                    final FileTags tags = new FileTags();
                    for (String s : selected) {
                        tags.selectTag(s);
                    }
                    myPCS.firePropertyChange("editedEnhancedFile", null, selectedFile);
                    myEditFilePanel.clear();
                }
            }
        } else if ("removeDuplicates".equals(propertyName)) {
            final EnhancedFile deleteCopies = mySortifyFileList.getSelectedFile();
            if (deleteCopies == null) {
                showMessage("Choose a file that you would like to save to remove all others");
            } else {
                myPCS.firePropertyChange("removeDuplicates", null, deleteCopies);
            }
        } else if ("getImportedEnhancedFiles".equals(propertyName)) {
            myPCS.firePropertyChange("getImportedEnhancedFiles", false, true);
            mySortifyOptionsPanel.openEdit(true);
            mySortifyOptionsPanel.openImport(false);
        } else if ("organizeByFiles".equals(propertyName)) {
            Comparator<EnhancedFile> comparator = null;
            final int selection = (int)theEvent.getNewValue();
            if (selection == 1) {
                comparator = new FileTypeComparator();
            } else if (selection == 2) {
                comparator = new FileNameComparator();
            }
            mySortifyFileList.organize(comparator);
        } else if ("clearFiles".equals(propertyName)) {
            mySortifyOptionsPanel.openImport(true);
            myPCS.firePropertyChange("clearFiles", false, true);

        }
    }

    /**
     * Changes the visibility of the components within mySortifyFrame. This is usually called by
     * propertyChange when the user signs in or out.
     *
     * @param theValue is a boolean determining whether components are to be shown.
     */
    public void setVisibleComponents(final boolean theValue) {
        mySortifyOptionsPanel.setEnabled(theValue);
        myCenterPanel.setVisible(theValue);
        mySortifyOptionsPanel.setVisible(theValue);
    }



    /**
     * Displays a message based off whether the account given was new or already existing.
     *
     * @param theFoundUser is boolean on whether a new account was made.
     */
    public void foundUser(final boolean theFoundUser) {
        String message;
        if (theFoundUser) {
            message = "Found Account";
        } else {
            message = "New Account";
        }
        showMessage(message);
    }

    /**
     * Notifies mySortifyFileList of changes to the list of EnhancedFiles.
     *
     * @param allFiles is a List of the current enhanced files.
     */
    public void notifyListChanges(final List<EnhancedFile> allFiles) {
        mySortifyFileList.updateList(allFiles);
    }

    /**
     * Shows a JOptionPane that uses the program logo and a message that was given.
     *
     * @param theMessage is the message for the JOptionPane.
     */
    public void showMessage(final String theMessage) {
        JOptionPane.showMessageDialog(myCenterPanel, theMessage, "Program Message", JOptionPane.PLAIN_MESSAGE,
                SMALL_ICON);
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
