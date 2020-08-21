package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class playerTest {

    Player Bob;

    @BeforeEach
    void runBefore() {
        Bob = new Player("abc", "cba", "Bob");
    }

    @Test
    void testConstructor() {
        assertEquals(0, Bob.getArcadeHighScore());
        assertEquals(0, Bob.getRegularHighScore());
        assertEquals(0, Bob.getSurvivalHighScore());
        assertEquals("Bob", Bob.getName());
        assertEquals("abc", Bob.getUsername());
        assertEquals("cba", Bob.getPassword());
        assertEquals(-1, Bob.getArcadeScore());
        assertEquals(-1, Bob.getRegularLevel());
        assertEquals(-1, Bob.getRegularLines());
        assertEquals(-1, Bob.getSurvivalScore());
        assertEquals("", Bob.getArcadeMatrixData());
        assertEquals("", Bob.getRegularMatrixData());
        assertEquals("", Bob.getSurvivalMatrixData());
    }

    @Test
    void testChangeUsername() {
        Bob.changeUsername("abg");
        assertEquals(Bob.getUsername(), "abg");
    }

    @Test
    void testChangePassword() {
        Bob.changePassword("abg");
        assertEquals(Bob.getPassword(), "abg");
    }

    @Test
    void testChangeName() {
        Bob.changeName("abg");
        assertEquals(Bob.getName(), "abg");
    }

    @Test
    void testUpdateRegularHighScore() {
        Bob.updateRegularHighScore(123);
        assertEquals(Bob.getRegularHighScore(), 123);
    }

    @Test
    void testUpdateSurvivalHighScore() {
        Bob.updateSurvivalHighScore(123);
        assertEquals(Bob.getSurvivalHighScore(), 123);
    }

    @Test
    void testUpdateArcadeHighScore() {
        Bob.updateArcadeHighScore(123);
        assertEquals(Bob.getArcadeHighScore(), 123);
    }

    @Test
    void testUpdateRegularSave() {
        String s = "";
        for (int k = 0; k < 200; k++) {
            s = s + "7";
        }
        Bob.updateRegularSave(s, 7, 7);
        assertEquals("7", Bob.getRegularMatrixData().substring(4, 5));
        assertEquals(7, Bob.getRegularLevel());
        assertEquals(7, Bob.getRegularLines());
    }

    @Test
    void testUpdateSurvivalSave() {
        String s = "";
        for (int k = 0; k < 200; k++) {
            s = s + "7";
        }
        Bob.updateSurvivalSave(s, 7);
        assertEquals("7", Bob.getSurvivalMatrixData().substring(4, 5));
        assertEquals(7, Bob.getSurvivalScore());
    }

    @Test
    void testUpdateArcadeSave() {
        String s = "";
        for (int k = 0; k < 200; k++) {
            s = s + "7";
        }
        Bob.updateArcadeSave(s, 7);
        assertEquals("7", Bob.getArcadeMatrixData().substring(4, 5));
        assertEquals(7, Bob.getArcadeScore());
    }


    //  DIDNT WRITE A TEST FOR SAVE BUT HOW
}
