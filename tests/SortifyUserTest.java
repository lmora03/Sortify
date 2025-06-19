package tests;

import model.SortifyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortifyUserTest {

    private SortifyUser myFirstUser;


    @BeforeEach
    void setUp() {
        myFirstUser = new SortifyUser();
    }

    @Test
    void setInfo() {
        SortifyUser expected = new SortifyUser("PersonName", "PersonEmail");
        myFirstUser.setInfo("PersonName", "PersonEmail");
        assertEquals(expected, myFirstUser);
    }


}