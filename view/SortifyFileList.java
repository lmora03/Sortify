/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SortifyFileList.java
 */

package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;

import model.EnhancedFile;

/**
 * Class that uses a JList to represent the EnhancedFiles that were added to the program.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SortifyFileList {

    /** DefaultListModel instance holding the name of EnhancedFiles. Used for adding and removing from JList. */
    private final DefaultListModel<String> myModel;

    /** List of all the EnhancedFiles that exist in the program. */
    private final List<EnhancedFile> myFileList;

    /** JList of all the EnhancedFile file names that exist in the program. Used for the GUI*/
    private final JList<String> myFileNameList;

    /**
     * Constructor for SortifyFileList objects. This method instantiates all instance variables
     * and sets up the list needed in the GUI.
     */
    public SortifyFileList() {
        myFileList = new ArrayList<>();
        myFileNameList = new JList<>();
        myModel = new DefaultListModel<>();
        setupComponents();
    }

    /**
     * Sets up the components necessary to the program.
     */
    public void setupComponents() {
        myFileNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myFileNameList.setLayoutOrientation(JList.VERTICAL);
        myFileNameList.setModel(myModel);
    }


    /**
     * Adds the given file to all list such as myModel and myFileList.
     *
     * @param theFile is the EnhancedFile that is being added to the lists.
     */
    public void addFile(final EnhancedFile theFile) {
        myFileList.add(theFile);
        myModel.addElement(theFile.getFileName());
    }

    /**
     * Updates all instance variables to match the given EnhancedFile. This
     * is done when there is an update such as a removal or adding new files.
     *
     * @param allFiles is the current List of all EnhancedFiles for the program.
     */
    public void updateList(List<EnhancedFile> allFiles) {
        myFileList.clear();
        myFileNameList.removeAll();
        myModel.clear();
        for (final EnhancedFile file : allFiles) {
            myFileList.add(new EnhancedFile(file));
            myModel.addElement(file.getFileName());
        }
    }

    public void organize(Comparator<EnhancedFile> theComparator) {
        myFileList.sort(theComparator);
        myFileNameList.removeAll();
        myModel.clear();
        for (final EnhancedFile file : myFileList) {
            myModel.addElement(file.getFileName());
        }
    }

    /**
     * Returns the selected file within the JList, if one is selected.
     *
     * @return EnhancedFile that is currently selected from JList.
     */
    public EnhancedFile getSelectedFile() {
        EnhancedFile temp = null;
        if (!myFileNameList.isSelectionEmpty()) {
            temp = myFileList.get(myFileNameList.getSelectedIndex());
        }
        return temp;
    }

    /**
     *  Returns the JList of myFileNameList to be used and shown in the GUI.
     *
     * @return JList of myFileNameList.
     */
    public JList<String> getList() {
        return myFileNameList;
    }



}
