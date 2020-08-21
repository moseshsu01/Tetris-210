package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class matrixTest {

    Matrix m1, m2, m3;
    Tetromino a, b, c, d, e, f, g;


    @BeforeEach
    void runBefore() {
        m1 = new Matrix();
        m2 = new Matrix();
        m3 = new Matrix();
        String s = "";
        for (int k = 0; k < 200; k++) {
            s = s + "6";
        }
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
        assertEquals(20, m1.getHeight());
        assertEquals(10, m1.getWidth());
        assertEquals(20, m2.getHeight());
        assertEquals(10, m2.getWidth());
        assertEquals(20, m3.getHeight());
        assertEquals(10, m3.getWidth());
        assertEquals(200, m1.getMatrixData().length());
        assertEquals(200, m2.getMatrixData().length());
        assertEquals(200, m3.getMatrixData().length());
        assertEquals(0, m1.getMatrix()[3][4]);
        assertEquals(0, m2.getMatrix()[3][4]);
        assertEquals(0, m2.getMatrix()[3][4]);
    }
}