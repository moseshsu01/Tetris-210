package model;

import java.awt.*;

// represents the I piece
public class Imino extends Tetromino {

    // MODIFIES: this
    public Imino() {
        colour = Color.cyan;
        position = new Position(3, 1, 4, 1, 5, 1, 6, 1);
        // so you can rotate it right away
        piece = 1;
    }

    // EFFECTS: returns the rotation of Imino
    @Override
    public int getRotation() {
        if (position.getCell1().x == position.getCell2().x) {
            return 2; // straight
        }
        return 1; // flat
    }
}
