/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: EnhancedFile.java
 */

package model;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * This class creates EnhancedFile objects which are essentially files with additional
 * details that the user add.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class EnhancedFile implements Serializable {

    /** File location where EnhancedFiles are saved and received. */
    public final static String SAVE_LOCATION = "EnhancedFiles.bin";

    /** Serial ID For EnhancedFile objects */
    @Serial
    private static final long serialVersionUID = -1519362649137534981L;

    /** File that is associated with EnhancedFile. */
    private final File myFile;

    /** String representing the type of file given. */
    private final String myFileType;

    /** String representing the name of the file. */
    private final String myFileName;

    /** String representing the description given to the file. */
    private String myFileDescription;

    /** FileTag object that is associated to an EnhancedFile and save tags. */
    private FileTags myTags;

    /**
     * Constructor for EnhancedFile objects when only a File is given. Instance variables
     * are either assigned null, specific values or calculated with the usage of methods.
     *
     * @param theFile is the file associated with the object.
     */
    public EnhancedFile(final File theFile) {
        myFile = theFile;
        myFileType = getFileType();
        myFileName = getFileName();
        myTags = new FileTags();
        myFileDescription = null;
    }

    /**
     * Constructor for EnhancedFile objects when an EnhancedFile is given. This constructor
     * essentially makes copies of the given EnhancedFile.
     *
     * @param theEnhancedFile is the EnhancedFile being copied.
     */
    public EnhancedFile(final EnhancedFile theEnhancedFile) {
        myFile = theEnhancedFile.getFile();
        myFileType = theEnhancedFile.getFileType();
        myFileName = theEnhancedFile.getFileName();
        myFileDescription = theEnhancedFile.getFileDescription();
        myTags = theEnhancedFile.getTags();
    }

    /**
     * Sets the description of an EnhancedFile to a String that is given.
     *
     * @param theDescription is the description given to the file.
     */
    public void setFileDescription(final String theDescription) {
        myFileDescription = theDescription;
    }

    /**
     * Sets the instance variable of myTags to the value that is given.
     *
     * @param theTags is the new FileTag object.
     */
    public void setTags(FileTags theTags) {
        myTags = theTags;
    }

    /**
     * Returns the File that is associated with this object.
     *
     * @return a File assigned to the object.
     */
    public File getFile() {
        return myFile;
    }

    /**
     * Calculates the file name and returns it as a String.
     *
     * @return a String that is the name of the file.
     */
    public String getFileName() {
        final Path path = Paths.get(myFile.getAbsolutePath());
        return path.getFileName().toString();
    }

    /**
     * Calculates the file type and returns it as a String.
     *
     * @return a String that is the file type of the EnhancedFile.
     */
    public String getFileType() {
        String path = myFile.getAbsolutePath();
        int result = path.lastIndexOf(".");
        if (result == -1) {
            path = "unknown";
        } else {
            path = path.substring(result + 1);
        }
        return path;
    }

    /**
     * Returns the given description of an EnhancedFile.
     *
     * @return a String representing the description of an EnhancedFile.
     */
    public String getFileDescription() {
        return myFileDescription;
    }

    /**
     * Returns the FileTag associated with the EnhancedFile.
     *
     * @return an FileTag object.
     */
    public FileTags getTags() {
        return myTags;
    }

    /**
     * Compares EnhancedFile objects to see if they are the same. If both objects are the same
     * type, then it compares if both have the same file name and file type.
     *
     * @param theOther is the other Object being compared to.
     * @return a boolean on whether the given Object is the same as this object.
     */
    @Override
    public boolean equals(Object theOther) {
        boolean result = false;
        if (theOther != null && theOther.getClass().equals(getClass())) {
            final EnhancedFile otherFile = (EnhancedFile) theOther;
            if (myFile.equals(otherFile.myFile) && myFileType.equals(otherFile.myFileType) &&
            myFileName.equals(otherFile.myFileName)) {
                result = true;
            }
        }
        return result;
    }

    /**
     *
     * @return an integer representing this object based of the file itself, the file name and file type.
     */
    @Override
    public int hashCode() {
        return Objects.hash(myFile, myFileName, myFileType);
    }

    @Override
    public String toString() {
        return "[" + myFile + ", " + myFileName + ", " + myFileType + ", " + hashCode() + "]";
    }



}
