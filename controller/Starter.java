/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: Starter.java
 */

package controller;

import javax.swing.*;
import java.awt.EventQueue;

/**
 * This class contains the main method and starts an instance of Controller,
 * which allows the program to start.
 * 
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class Starter {
    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException theException) {
            throw new RuntimeException(theException);
        }
    }

    /**
     * The start point for the PowerPaint application.
     * 
     * @param theArgs command line arguments - ignored in this program
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLookAndFeel();
                new Controller();
            }

        });
    }
}
