/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: ImagePanel.java
 */

package view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.EnhancedFile;

/**
 * This class holds the image that is related for an EnhancedFile. Much of this class is based off
 * Cody Henrichsen and his "Resized Image in Java Code" with some alterations for this program.
 * <a href="https://www.youtube.com/watch?v=IdR33nRolPs&t=154s">...</a>
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class ImagePanel extends JPanel {

    /** JLabel holding the image itself. */
    private final JLabel myImage;

    /**
     * Constructor for ImagePanel objects. The method sets up all instance methods
     * and their looks.
     */
    public ImagePanel() {
        super();
        myImage = new JLabel();
        setLayout(new BorderLayout());
        add(myImage, BorderLayout.CENTER);
        myImage.setHorizontalAlignment(JLabel.CENTER);
        myImage.setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * Sets the image of the given File to the Jpanel. The method tries to read the image of the file
     * if the file type is based off an image. For other file types, the method uses images on the local
     * program to at least show what type of file is being dealt with.
     *
     * @param theFile is the EnhancedFile being dealt with.
     */
    public void setImage(EnhancedFile theFile) {
        try {
            BufferedImage source = ImageIO.read(new File(theFile.getFile().getAbsolutePath()));
            if (source == null) {
                final String typePath = "/images/" + theFile.getFileType() + ".png";
                var resource = getClass().getResource(typePath);
                if (resource != null) {
                    source = ImageIO.read(resource);
                } else {
                    source = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/unknown.png")));
                }
            }
                final BufferedImage resize = resizeImage(source, 400);
                myImage.setIcon(new ImageIcon(resize));
            } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Changes the size of the given image to a different size.
     *
     * @param source is the BufferedImage that is being altered.
     * @param maxPixelSize is the max size of the image.
     * @return a BufferedImage that is altered to a different size.
     */
    public BufferedImage resizeImage(final BufferedImage source, final int maxPixelSize) {
        BufferedImage resize = null;
        final Image toolkitImage = source.getScaledInstance(maxPixelSize, -1, Image.SCALE_SMOOTH);
        final int width = toolkitImage.getWidth(this);
        final int height = toolkitImage.getHeight(this);
        resize = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2d = resize.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(toolkitImage, 0, 0, null);
        g2d.dispose();
        return resize;
    }
}
