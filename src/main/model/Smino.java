package model;

import java.awt.*;

// represents the S piece
public class Smino extends Tetromino {

    // MODIFIES: this
    public Smino() {
        colour = Color.green;
        position = new Position(3, 1, 4, 0, 4, 1, 5, 0);
        piece = 5;
    }

    // EFFECTS: returns rotation of piece
    @Override
    public int getRotation() {
        if (position.getCell1().x < position.getCell3().x) {
            return 1;
        } else if (position.getCell1().y < position.getCell3().y) {
            return 2;
        } else if (position.getCell1().y == position.getCell3().y) {
            return 3;
        } else {
            return 4;
        }
    }
}
