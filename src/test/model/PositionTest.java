package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position x;

    @BeforeEach
    void RunBefore() {
        x = new Position(1, 2, 3, 4, 5, 6, 7, 8);
    }

    @Test
    void testConstructor() {
    }

}
