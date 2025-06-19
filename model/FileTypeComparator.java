/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: FileTypeComparator.java
 */

package model;

import java.util.Comparator;

/**
 * Class compares files types from EnhancedFiles. Used for comparison and organization
 * of EnhancedFiles in the Program.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class FileTypeComparator implements Comparator<EnhancedFile> {

    /**
     * Compares to 2 EnhancedFile and based off their file type, the method returns an
     * integer with that representation.
     *
     * @param firstFile the first object to be compared.
     * @param secondFile the second object to be compared.
     * @return an integer representing if the first file is less, equal or greater than the second file
     */
    @Override
    public int compare(EnhancedFile firstFile, EnhancedFile secondFile) {
        return firstFile.getFileType().compareTo(secondFile.getFileType());
    }
}
