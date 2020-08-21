package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class TetrominoTest {

    Tetromino a, b, c, d, e, f, g;

    @BeforeEach
    void RunBefore() {

        a = new Imino();
        b = new Jmino();
        c = new Lmino();
        d = new Omino();
        e = new Smino();
        f = new Tmino();
        g = new Zmino();
    }

    @Test
    void testConstructor() {
        assertEquals(Color.cyan, a.getColor());
        assertEquals(Color.blue, b.getColor());
        assertEquals(Color.orange, c.getColor());
        assertEquals(Color.YELLOW, d.getColor());
        assertEquals(Color.green, e.getColor());
        assertEquals(Color.MAGENTA, f.getColor());
        assertEquals(Color.red, g.getColor());
    }

}
