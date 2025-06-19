/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: Controller.java
 */

package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.EnhancedFile;
import model.SaveState;
import model.SortifyUser;
import view.SortifyFrame;

/**
 * This class is the controller and acts as the communication between the model
 * and view files.The class implements PropertyChangeListeners to hear
 * for events to determine what actions need to be done.
 * 
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class Controller implements PropertyChangeListener {

    /** A List of all the EnhancedFiles that the user created. */
    private final List<EnhancedFile> myEnhancedFiles;

    /** A list of all the SortifyUsers that have been created. */
    private final List<SortifyUser> myUsers;

    /** An instance of the model of the program. */
    private final SortifyFrame myProgram;

    /**
     * Constructor for Controller objects, and it instantiates all the instance
     * variables needed for the program.
     */
    public Controller() {
        myProgram = new SortifyFrame("Sortify: TCSS 360 Project");
        myProgram.addPropertyChangeListener(this);
        myUsers = new ArrayList<>();
        SaveState.importData(myUsers, SortifyUser.SAVE_LOCATION);

        myEnhancedFiles = new ArrayList<>();
    }

    /**
     * Method that listens for propertyChange events and does certain
     * actions based off the event. The reasoning behind this outline is so that
     * the model and view can communicate with each other.
     *
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final String propertyName = theEvent.getPropertyName();
        if ("userInfo".equals(propertyName)) {
            final String info = (String) theEvent.getNewValue();
            final String[] userInfo = info.split(",");
            if(!userInfo[0].isEmpty() && !userInfo[1].isEmpty()) {
                final SortifyUser user = new SortifyUser(userInfo[0], userInfo[1]);
                if (myUsers.contains(user)) {
                    myProgram.foundUser(true);
                } else {
                    myProgram.foundUser(false);
                    myUsers.add(user);
                }
            }
        } else if ("addedEnhancedFile".equals(propertyName)) {
            final EnhancedFile newFile = (EnhancedFile) theEvent.getNewValue();
            myEnhancedFiles.add(newFile);
        } else if ("saveAccountList".equals(propertyName)) {
            SaveState.exportData(myUsers, SortifyUser.SAVE_LOCATION);
        } else if ("saveEnhancedFileList".equals(propertyName)) {
            SaveState.exportData(myEnhancedFiles, EnhancedFile.SAVE_LOCATION);
        } else if ("editedEnhancedFile".equals(propertyName)) {
            final EnhancedFile newFile = (EnhancedFile) theEvent.getNewValue();
            final int index = myEnhancedFiles.indexOf(newFile);
            if (index != -1) {
                myEnhancedFiles.remove(index);
                myEnhancedFiles.add(index, newFile);
            }
        } else if ("removeDuplicates".equals(propertyName)) {
            final EnhancedFile newFile = (EnhancedFile) theEvent.getNewValue();
            final int index = myEnhancedFiles.indexOf(newFile);
            if (index != -1) {
                myEnhancedFiles.removeAll(Collections.singleton(newFile));
                myEnhancedFiles.add(index, newFile);
            }
            myProgram.notifyListChanges(myEnhancedFiles);
        } else if ("getImportedEnhancedFiles".equals(propertyName)) {
            final List<EnhancedFile> temp = new ArrayList<>();
            SaveState.importData(temp, EnhancedFile.SAVE_LOCATION);
            myEnhancedFiles.addAll(temp);
            myProgram.notifyListChanges(myEnhancedFiles);
        } else if ("clearFiles".equals(propertyName)) {
            myEnhancedFiles.clear();
            myProgram.notifyListChanges(myEnhancedFiles);
        }
    }

}
