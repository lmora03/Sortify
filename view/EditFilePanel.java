/*
 * Course: TCSS 360 - Software Development and QA Techniques
 * Project Name: Sortify
 * File Name: EditFilePanel.java
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import model.EnhancedFile;
import model.FileTags;

/**
 * This class acts as JPanel that holds the necessary components when the user wants to add
 * or edit details regarding a file.
 *
 * @author Luis Mora, lmora@uw.edu
 * @version v1.0
 */
public class EditFilePanel extends JPanel implements ItemListener {


    /** JTextArea that reserves the section where users can see the name of the chosen file. */
    private final JTextArea myFileNameLabel;

    /** JTextArea that reserves the section where users can add a description to the chosen file. */
    private final JTextArea myDescription;

    /** A List of JCheckBoxes that correspond with FileTags tags. */
    private final List<JCheckBox> myCheckBoxes;

    /** EnhancedFile that is the current EnhancedFile that is being dealt with. */
    private EnhancedFile myEnhancedFile;

    /** ImagePanel object that holds the image connected to the chosen file. */
    private ImagePanel myImageSection;

    /** An instance of FileTags used for holding tags. */
    private FileTags myFileTags;

    /** JPanel that holds components for File Tags. */
    private JPanel myTagListPanel;


    /**
     * Constructor for EditFilePanel objects. The method instantiates the instance variables directly
     * or indirectly with the methods that are called.
     */
    public EditFilePanel() {
        super();

        myCheckBoxes = new ArrayList<>();
        myFileNameLabel = new JTextArea();
        myImageSection = new ImagePanel();
        myDescription = new JTextArea(5, 15);
        myFileTags = new FileTags();

        setupFileNameLabel();
        setupDescriptionArea();
        setupPanels();
    }

    /**
     * Sets up the FileNameLabel to the desired specifications.
     */
    public void setupFileNameLabel() {
        myFileNameLabel.setLineWrap(true);
        myFileNameLabel.setLineWrap(true);
        myFileNameLabel.setEditable(false);
        myFileNameLabel.setOpaque(true);
        myFileNameLabel.setBackground(Color.WHITE);
        myFileNameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    /**
     * Sets up myDescription to the desired specifications.
     */
    public void setupDescriptionArea() {
        myDescription.setMaximumSize(new Dimension(5, 15));
        myDescription.setPreferredSize(new Dimension(5, 15));
        myDescription.setLineWrap(true);
        myDescription.setWrapStyleWord(true);
    }

    /**
     * Sets up the panel with the necessary components.
     */
    public void setupPanels() {
        setBackground(SortifyFrame.PROGRAM_COLOR);
        setPreferredSize(new Dimension(SortifyFrame.FRAME_SIZE.width / 2, SortifyFrame.FRAME_SIZE.height / 2));
        setLayout(new BorderLayout());

        final JPanel detailsSection = new JPanel();
        detailsSection.setBackground(new Color(229, 227, 220));

        final JPanel componentPanel = new JPanel();

        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.PAGE_AXIS));
        componentPanel.setBackground(new Color(229, 227, 220));

        setupDescriptionPanel(componentPanel);

        setupTagsPanelComponents(componentPanel);

        detailsSection.add(componentPanel);

        add(myImageSection, BorderLayout.CENTER);

        add(detailsSection, BorderLayout.EAST);
    }

    /**
     * Sets up the JCheckboxes which represent tags avaliable for adding. All these tags
     * are added to the GUI.
     */
    public void setTags() {
        final List<String> tags = myFileTags.getExistingTags();
        for (final String single : tags) {
            JCheckBox temp = new JCheckBox(single);
            temp.setBackground(Color.WHITE);
            temp.addItemListener(this);
            myTagListPanel.add(temp);
            myCheckBoxes.add(temp);
        }
    }

    /**
     * Sets the EnhancedFile to the one that is given. After, the method sets up the panel to be
     * based off the given EnhancedFile and it's details.
     *
     * @param theFile is the current EnhancedFile.
     */
    public void editFile(final EnhancedFile theFile) {
        myEnhancedFile = theFile;
        myFileNameLabel.setText(theFile.getFileName());
        myDescription.setText(theFile.getFileDescription());
        myImageSection.setImage(theFile);
        List<String> selected = myEnhancedFile.getTags().getSelectedTags();
        for (JCheckBox c : myCheckBoxes) {
            if (selected.contains(c.getActionCommand())) {
                c.setSelected(true);
            }
        }
    }

    /**
     * Returns the text in Jtextarea for the description.
     * 
     * @return a string representing the description given to the file.
     */
    public String getDescription() {
        return myDescription.getText();
    }

    /**
     * Clears out the textarea.
     */
    public void clear() {
        myDescription.setText("");
        myImageSection = new ImagePanel();
        myFileNameLabel.setText("");
        myFileTags = new FileTags();

        for (JCheckBox box : myCheckBoxes) {

            box.setSelected(false);
        }
        repaint();
    }

    /**
     * Goes throughout all the checkboxes to determined if they are checked. If
     * it is selected, the action command is added to a list which is returned.
     *
     * @return a List containing all the selected tags.
     */
    public List<String> getSelected() {
        final List<String> temp = new ArrayList<>();
        for (final JCheckBox each : myCheckBoxes) {
            if (each.isSelected()){
                temp.add(each.getActionCommand());
            }
        }
        return temp;
    }

    /**
     * Sets the JPanel to be based off the characteristics of the current EnhancedFile
     *
     * @param theFile is the new EnhancedFile being used.
     */
    public void setFile(final EnhancedFile theFile) {
        myEnhancedFile = theFile;
        myImageSection.setImage(myEnhancedFile);
        myFileNameLabel.setText(myEnhancedFile.getFileName());
        myDescription.setText(myEnhancedFile.getFileDescription());
        myFileTags = myEnhancedFile.getTags();
    }

    /**
     * Based off the list of JCheckboxes representing tags, if one is
     * select or unselected, it will add or remove it from myFileTags.
     *
     * @param theEvent the event to be processed.
     */
    @Override
    public void itemStateChanged(final ItemEvent theEvent) {
        final JCheckBox local = (JCheckBox) theEvent.getSource();
        String state = local.getActionCommand();
        if (theEvent.getStateChange() == ItemEvent.SELECTED) {
            myFileTags.selectTag(state);
        } else {
            myFileTags.removeTag(state);
        }
    }

    /**
     * Sets up the description panel section.
     *
     * @param componentPanel is the parent JPanel.
     */
    private void setupDescriptionPanel(JPanel componentPanel) {
        final JScrollPane scrollSection = new JScrollPane(myDescription);
        final JLabel fileLabel = new JLabel("File Name");
        final JLabel label = new JLabel("File Description");

        componentPanel.add(fileLabel);
        componentPanel.add(myFileNameLabel);
        componentPanel.add(label);
        label.setLabelFor(myDescription);

        fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        myFileNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        final Dimension size = new Dimension(320, 120);

        setupScrollPane(scrollSection, size);

        componentPanel.add(scrollSection);
    }

    /**
     * Sets up the tags panel section components.
     *
     * @param componentPanel is the parent JPanel.
     */
    private void setupTagsPanelComponents(JPanel componentPanel) {
        final JLabel selectTags = new JLabel("Select Tags");
        componentPanel.add(selectTags);
        selectTags.setAlignmentX(Component.LEFT_ALIGNMENT);

        setupTagPanel();

        setTags();

        final JScrollPane tagScrollPane = new JScrollPane(myTagListPanel);
        final Dimension updateSize = new Dimension(320, 250);

        setupScrollPane(tagScrollPane, updateSize);

        final JButton addNewTag = new JButton("Add New Tag");
        addNewTag.setAlignmentX(Component.LEFT_ALIGNMENT);
        componentPanel.add(tagScrollPane);
        componentPanel.add(addNewTag);
        addNewTag.addActionListener(_ -> {
            String result = JOptionPane.showInputDialog("Enter New Tag: ") ;
            myFileTags.addNewTag(result);
            myTagListPanel.removeAll();
            setTags();
            myTagListPanel.revalidate();
            myTagListPanel.repaint();
        });
    }

    /**
     * Setups up myTagListPanel.
     */
    private void setupTagPanel() {
        myTagListPanel = new JPanel();
        myTagListPanel.setBackground(Color.WHITE);
        myTagListPanel.setLayout(new BoxLayout(myTagListPanel, BoxLayout.PAGE_AXIS));
        myTagListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Sets up the scroll pane to the given size.
     *
     * @param theComponent is the JScrollPane being altered.
     * @param theSize is the dimension for the scroll pane.
     */
    private void setupScrollPane(final JScrollPane theComponent, final Dimension theSize) {
        theComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
        theComponent.setPreferredSize(theSize);
        theComponent.setMaximumSize(theSize);
        theComponent.setMinimumSize(theSize);
        theComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    }
}
