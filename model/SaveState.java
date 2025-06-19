/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: SaveState.java
 */

package model;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * This class imports and exports data from chosen lists. These lists
 * are generic to allow the ability for different types to be saved and loaded
 * to reuse code.
 * 
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class SaveState {

    /**
     * A static method that allows for saving items inside a list to a given
     * location. The method allows any type allowing for different usages
     * from different classes.
     * 
     * @param <T>         is the type of item being used.
     * @param theList     is a list representing items that will be saved.
     * @param theLocation is a String representing the location where items will be
     *                    saved.
     */
    public static <T> void exportData(final List<T> theList, final String theLocation) {
        try {
            final FileOutputStream fos = new FileOutputStream(theLocation);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (final T item : theList) {
                oos.writeObject(item);
            }
            oos.close();
        } catch (IOException theException) {
            theException.printStackTrace();
        }
    }

    /**
     * A static method that allows for retrieve items and places them into a list.
     * Using theLocation, method reads all objects from the file. The method
     * allows any type allowing for different usages from different classes.
     * 
     * @param <T>         is the type of items being used.
     * @param theList     is a list that will store items that are being read from
     *                    the file.
     * @param theLocation is a String representing the location where items are
     *                    being stored so they can be read.
     */
    @SuppressWarnings("unchecked")
    public static <T> void importData(final List<T> theList, final String theLocation) {
        try {
            final FileInputStream fin = new FileInputStream(theLocation);
            final ObjectInputStream ois = new ObjectInputStream(fin);
            T item;
            while (true) {
                try {
                    item = (T) ois.readObject();
                    if (item instanceof T) {
                        theList.add((T) item);
                    }
                } catch (EOFException theException) {
                    break;
                }
            }
            ois.close();
        } catch (IOException | ClassNotFoundException theException) {
            if (!(theException instanceof FileNotFoundException)) {
                theException.printStackTrace();
            }

        }
    }
}
