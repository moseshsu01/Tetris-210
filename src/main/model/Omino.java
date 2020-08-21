package model;

import java.awt.*;

// represents the O piece
public class Omino extends Tetromino {

    // MODIFIES: this
    public Omino() {
        colour = Color.YELLOW;
        position = new Position(4, 0, 4, 1, 5, 0, 5, 1);
        piece = 4;
    }

    // EFFECTS: returns rotation number of piece
    @Override
    public int getRotation() {
        return 1;
    }

}
