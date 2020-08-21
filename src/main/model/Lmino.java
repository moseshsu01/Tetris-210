package model;

import java.awt.*;

// represents the L piece
public class Lmino extends Tetromino {
    private Point origin;

    // MODIFIES: this
    public Lmino() {
        colour = Color.orange;
        position = new Position(3, 1, 4, 1, 5, 1, 5, 0);
        origin = position.getCell2();
        piece = 3;
    }

    // EFFECTS: returns the rotation of piece
    @Override
    public int getRotation() {
        return 1;
    }

}