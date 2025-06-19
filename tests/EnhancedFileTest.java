package tests;

import model.EnhancedFile;
import model.FileTags;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class EnhancedFileTest {

    private EnhancedFile myFirstEnhancedFile;

    private EnhancedFile mySecondEnhancedFile;

    private EnhancedFile myThirdEnhancedFile;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        myFirstEnhancedFile = new EnhancedFile(new File(".\\images\\sortifylogo.png"));
        mySecondEnhancedFile = new EnhancedFile(new File(".\\images\\zip.png"));
        myThirdEnhancedFile = new EnhancedFile(new File(".\\images\\sortifylogo.png"));

    }

    @org.junit.jupiter.api.Test
    void getFileName() {
        final String fileName = "sortifylogo.png";
        final String result = myFirstEnhancedFile.getFileName();
        assertEquals(fileName, result);
    }

    @org.junit.jupiter.api.Test
    void getFileType() {
        final String fileType = "png";
        assertEquals(fileType, myFirstEnhancedFile.getFileType());
    }

    @org.junit.jupiter.api.Test
    void getTags() {
        final FileTags tags = new FileTags();
        tags.selectTag("Personal");
        tags.selectTag("Document");
        myFirstEnhancedFile.setTags(tags);
        assertEquals(tags, myFirstEnhancedFile.getTags());
    }

    @org.junit.jupiter.api.Test
    void getTagsDefault() {
        final FileTags tags = new FileTags();
        myFirstEnhancedFile.setTags(tags);
        assertEquals(tags, myFirstEnhancedFile.getTags());
    }

    @org.junit.jupiter.api.Test
    void testEqualsTrue() {
        boolean result = myFirstEnhancedFile.equals(myThirdEnhancedFile);
        assertTrue(result);
    }

    @org.junit.jupiter.api.Test
    void testEqualsFalse() {
        boolean result = myFirstEnhancedFile.equals(mySecondEnhancedFile);
        assertFalse(result);
    }

}