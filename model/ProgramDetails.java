/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: ProgramDetails.java
 */

package model;

/**
 * Class that provides information regarding information about
 * the program.
 * 
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class ProgramDetails {

    /**
     * A static method that gets the details about the program.
     * 
     * @return a String containing information about the program developers.
     */
    public static String getProgramDetails() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append("This app provided by\n");
        builder.append("Jeffery Weiss: Product Owner\n");
        builder.append("Lead Developer: Luis Mora");
        return builder.toString();
    }

    /**
     * Returns the current program version.
     * 
     * @return a String containing information about the program version.
     */
    public static String getProgramVersion() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append("Application Version: v1.0\n");
        return builder.toString();
    }

    public static String getProgramHelp() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Welcome to the Help Page\n\n");
        builder.append("On the right side of the screen are options that you can use\n\n");
        builder.append("'Add a New File' will prompt a file chooser to add files to the program.\n\n");
        builder.append("'Import Files' will open and load any saved files from this program.\n\n");
        builder.append("'Export Files' will save the current files and their details. \n\n");
        builder.append("'Edit File' allows for file editing. Select the file first and press the option.\n\n");
        builder.append("'Duplicates' will remove similar files of your choosing. \nSelect the file to save and" +
                "all others will be removed. \n\n");
        builder.append("'Clear All' will remove all files from screen but they are still saved locally.\n\n");
        builder.append("In our Sorting Menu, you are provided a series of choices for sorting");

        return builder.toString();
    }
}
