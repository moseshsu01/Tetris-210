package model;

import java.awt.*;

// represents the Z piece
public class Zmino extends Tetromino {

    // MODIFIES: this
    public Zmino() {
        colour = Color.red;
        position = new Position(3, 0, 4, 0, 4, 1, 5, 1);
        piece = 6;
    }

    // EFFECTS: returns rotation of Zmino
    public int getRotation() {
        if (position.getCell3().x < position.getCell4().x) {
            return 1;
        } else if (position.getCell3().y < position.getCell4().y) {
            return 2;
        } else if (position.getCell3().y == position.getCell4().y) {
            return 3;
        } else {
            return 4;
        }
    }
}
