package tests;

import model.FileTags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileTagsTest {

    private FileTags myFirst;

    private FileTags mySecond;

    @BeforeEach
    void setUp() {
        myFirst = new FileTags();
        mySecond = new FileTags();
    }

    @Test
    void addNewTag() {
        final String[] defaultTags = { "Vacation", "Camping", "Mountains", "Research", "Document",
                "School", "Personal" , "Recipes", "Cooking"};
        List<String> expected = new ArrayList<>(List.of(defaultTags));
        myFirst.addNewTag("Recipes");
        myFirst.addNewTag("Cooking");
        List<String> result = myFirst.getExistingTags();
        assertEquals(expected, result);
    }

    @Test
    void selectTag() {
        final String[] defaultTags = {"Camping", "Mountains"};
        List<String> expected = new ArrayList<>(List.of(defaultTags));

        myFirst.selectTag("Camping");
        myFirst.selectTag("Mountains");


        List<String> result = myFirst.getSelectedTags();
        assertEquals(expected, result);


    }

    @Test
    void removeTag() {
        final String[] defaultTags = {"Camping"};
        List<String> expected = new ArrayList<>(List.of(defaultTags));

        myFirst.selectTag("Camping");
        myFirst.selectTag("Mountains");
        myFirst.removeTag("Camping");

        List<String> result = myFirst.getSelectedTags();

        assertEquals(expected, result);
    }
}