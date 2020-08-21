package model;

import java.awt.*;

// represents the J piece
public class Jmino extends Tetromino {
    private Point origin;

    // MODIFIES: this
    public Jmino() {
        colour = Color.blue;
        position = new Position(3, 0, 3, 1, 4, 1, 5, 1);
        origin = position.getCell3();
        piece = 2;
    }

    // EFFECTS: returns the rotation number of piece
    @Override
    public int getRotation() {
        if (position.getCell1().x == position.getCell2().x && position.getCell1().y < position.getCell3().y) {
            return 1;
        } else if (position.getCell1().x > position.getCell2().x) {
            return 2;
        } else if (position.getCell1().x == position.getCell2().x) {
            return 3;
        } else {
            return 4;
        }
    }
}
