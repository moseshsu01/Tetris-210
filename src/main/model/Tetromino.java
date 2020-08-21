package model;

import java.awt.*;

// represents a general 4-block mino
public abstract class Tetromino {

    protected Color colour;
    protected Position position;
    protected int piece;

    public Color getColor() {
        return colour;
    }

    public Position getPosition() {
        return position;
    }

    public int getPiece() {
        return piece;
    }

    public abstract int getRotation();
}
