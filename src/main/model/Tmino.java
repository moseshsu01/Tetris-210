package model;

import java.awt.*;

// represents the T piece
public class Tmino extends Tetromino {

    // MODIFIES: this
    public Tmino() {
        colour = Color.MAGENTA;
        position = new Position(3, 1, 4, 0, 4, 1, 5, 1);
        piece = 7;
    }

    // EFFECTS: returns rotation of Tmino
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
