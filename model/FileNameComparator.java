/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: FileNameComparator.java
 */

package model;

import java.util.Comparator;

/**
 * Class compares files names from EnhancedFiles. Used for comparison and organization
 * of EnhancedFiles in the Program.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class FileNameComparator implements Comparator<EnhancedFile> {
    /**
     * Compares to 2 EnhancedFile and based off their file name, the method returns an
     * integer with that representation.
     *
     * @param firstFile the first object to be compared.
     * @param secondFile the second object to be compared.
     * @return an integer representing if the first file is less, equal or greater than the second file
     */
    @Override
    public int compare(final EnhancedFile firstFile, final EnhancedFile secondFile) {
        return firstFile.getFileName().toLowerCase().compareTo(secondFile.getFileName().toLowerCase());
    }
}
