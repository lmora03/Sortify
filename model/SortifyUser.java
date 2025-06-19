/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SortifyUser.java
 */

package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class creates instance of users for Sortify program. The object holds
 * records of the user's first name and email to create messages based off the
 * inputs. The class also implements Serializable to store all users.
 * 
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SortifyUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -5544847507957975897L;

    /**
     * A static string that holds the location/name of the file for saving
     * SortifyUser objects.
     */
    public final static String SAVE_LOCATION = "SortifyUsers.bin";

    /** A string containing the first name of the SortifyUser. */
    private String myFirstName;

    /** A string containing the email of the SortifyUser. */
    private String myEmail;


    /**
     * Contructor for SortifyUser objects. The method doesn't require any parameters
     * to be creates and initially assigns all instance variables to null or false.
     */
    public SortifyUser() {
        this(null, null);
    }

    public SortifyUser(final String theFirstName, final String theEmail) {
        myFirstName = theFirstName;
        myEmail = theEmail;
    }

    /**
     * Sets the given information of a new first name and new email to the instance
     * variables that corralate. isInfoSet is changed to true to indicate that the
     * user has gave their name and email for the object.
     * 
     * @param theNewName  is a String containing the new name of the SortifyUser
     *                    object.
     * @param theNewEmail is a String containing the new email fo the SortifyUser
     *                    object.
     */
    public void setInfo(final String theNewName, final String theNewEmail) {
        myFirstName = theNewName;
        myEmail = theNewEmail;
    }

    /**
     * Returns a message that uses the first name and email of the SortifyUser which
     * contains the details about the user.
     * 
     * @return a String representation of information about a user.
     */
    public String getUserDetails() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append("This app is registered to ");
        builder.append(myFirstName);
        builder.append("\nEmail: ");
        builder.append(myEmail);
        return builder.toString();
    }

    @Override
    public String toString() {
        return "SortifyUser [myFirstName=" + myFirstName + ", myEmail=" + myEmail + ", isInfoSet=" + ", hashCode()=" + hashCode() + "]";
    }

    @Override
    public boolean equals(Object anotherObject) {
        boolean result = false;
        if (anotherObject.getClass() == getClass() && anotherObject != null) {
            SortifyUser user2 = (SortifyUser) anotherObject;
            if (myFirstName.equals(user2.myFirstName) && myEmail.equals(user2.myEmail)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myFirstName, myEmail);
    }
}
