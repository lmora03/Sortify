/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: FileTags.java
 */

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that creates FileTags objects which are used by EnhancedFiles. Within the
 * class, it stores the tags that exist in the program and the tags that are
 * associated with a file. When this object is instantiated, it gets the file with the stored tags
 * that are associated with all files. If this file doesn't exist, it gets the default tags
 * for an object and stores it right away.
 */
public class FileTags implements Serializable {

    /** Location where program tags are saved. */
    public final static String SAVE_LOCATION = "DefaultTags.bin";

    /** List containing all avaliable tags that can be used by an EnhancedFile. */
    private final List<String> myExistingTags;

    /** List containing all tags associated with an EnhancedFile. */
    private final List<String> mySelectedTags;

    /**
     * Constructor for FileTags objects. All instance variables are assigned to their values in this method.
     * The constructor retrieves the DefaultTags file and if the file doesn't exist then it calls
     * the defaultTags method to get default tags.
     */
    public FileTags() {
        myExistingTags = new ArrayList<>();
        SaveState.importData(myExistingTags, SAVE_LOCATION);
        if (myExistingTags.isEmpty()) {
            defaultTags();
        }
        mySelectedTags = new ArrayList<>();
    }

    /**
     * Adds the new tag to myExisting tags and saves the List to
     * DefaultTags.bin.
     *
     * @param theNewTag is the new tag for all Files.
     */
    public void addNewTag(final String theNewTag) {
        if (!myExistingTags.contains(theNewTag)) {
            myExistingTags.add(theNewTag);
            SaveState.exportData(myExistingTags, SAVE_LOCATION);
        }
    }

    /**
     * Adds a tag to mySelectedTags to represent that a tag is associated
     * with a File.
     *
     * @param theTag is the Tag that is being selected for a File.
     */
    public void selectTag(final String theTag) {
        if (myExistingTags.contains(theTag)) {
            mySelectedTags.add(theTag);
        }

    }

    /**
     * Removes a tag from mySelectedTags to represent that a tag is being
     * removed from an EnhancedFile.
     *
     * @param theTag is the Tag that is being removed from a File.
     */
    public void removeTag(final String theTag) {
        if (myExistingTags.contains(theTag)) {
            mySelectedTags.remove(theTag);
        }

    }

    /**
     * Returns myExistingTags.
     *
     * @return a List containing all tags that exist with all EnhancedFiles.
     */
    public List<String> getExistingTags() {
        return myExistingTags;
    }

    /**
     * Returns mySelectedTags.
     *
     * @return a List containing the tags that exist with a single EnhancedFile.
     */
    public List<String> getSelectedTags() {
        return mySelectedTags;
    }

    /**
     * Calculates a string with the instance variables of the object.
     *
     * @return a String representation of an EnhancedFile object.
     */
    @Override
    public String toString() {
        return "FileTags{" +
                "mySelectedTags=" + Arrays.toString(mySelectedTags.toArray()) +
                '}';
    }

    @Override
    public boolean equals(Object theOther) {
        boolean result = false;
        if (theOther != null && theOther.getClass().equals(getClass())) {
            final FileTags otherTags = (FileTags) theOther;
            if (mySelectedTags.equals(otherTags.mySelectedTags) && myExistingTags.equals(otherTags.myExistingTags)) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Adds the default tags for the program to myExistingTag. This method is only called when
     * DefaultTags.bin doesn't exist.
     */
    private void defaultTags() {
        final String[] defaultTags = { "Vacation", "Camping", "Mountains", "Research", "Document",
                "School", "Personal" };
        Collections.addAll(myExistingTags, defaultTags);
        SaveState.exportData(myExistingTags, SAVE_LOCATION);
    }
}