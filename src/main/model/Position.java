package model;

import java.awt.*;

// represents a position of a mino in terms of the coordinates of each square
public class Position {

    private Point cell1;
    private Point cell2;
    private Point cell3;
    private Point cell4;

    // MODIFIES: this
    public Position(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
        cell1 = new Point(a1, a2);
        cell2 = new Point(b1, b2);
        cell3 = new Point(c1, c2);
        cell4 = new Point(d1, d2);
    }

    // MODIFIES: this
    public Position(Point a, Point b, Point c, Point d) {
        cell1 = a;
        cell2 = b;
        cell3 = c;
        cell4 = d;
    }

    // MODIFIES: this
    // EFFECTS: sets position as given position
    public void setP(Position p) {
        cell1 = p.getCell1();
        cell2 = p.getCell2();
        cell3 = p.getCell3();
        cell4 = p.getCell4();
    }

    public Point getCell1() {
        return cell1;
    }

    public Point getCell2() {
        return cell2;
    }

    public Point getCell3() {
        return cell3;
    }

    public Point getCell4() {
        return cell4;
    }

    // MODIFIES: this
    // EFFECTS: moves all cells of position by i horizontally
    public void moveX(int i) {
        cell1.x += i;
        cell2.x += i;
        cell3.x += i;
        cell4.x += i;
    }

    // MODIFIES: this
    // EFFECTS: moves all cells of position by i vertically
    public void moveY() {
        cell1.y += 1;
        cell2.y += 1;
        cell3.y += 1;
        cell4.y += 1;
    }

    // EFFECTS: returns new position after moving by i horizontally
    public Position positionMoveX(int i) {
        Point c1 = new Point(cell1.x + i, cell1.y);
        Point c2 = new Point(cell2.x + i, cell2.y);
        Point c3 = new Point(cell3.x + i, cell3.y);
        Point c4 = new Point(cell4.x + i, cell4.y);
        return new Position(c1, c2, c3, c4);
    }

    // EFFECTS: returns new position after moving by 1 vertically
    public Position positionMoveDown() {
        return new Position(cell1.x, cell1.y + 1, cell2.x, cell2.y + 1, cell3.x, cell3.y + 1, cell4.x, cell4.y + 1);
    }

    // EFFECTS: returns new position after moving by i vertically
    public Position positionMoveDown(int y) {
        return new Position(cell1.x, cell1.y + y, cell2.x, cell2.y + y, cell3.x, cell3.y + y, cell4.x, cell4.y + y);
    }

    // EFFECTS: returns new position after rotating clockwise
    public Position positionRotateRight(int piece) {
        switch (piece) {
            case 1:
                return posRotateI();
            case 2:
                return posRotateJ();
            case 3:
                return posRotateL();
            case 4:
                return new Position(cell1, cell2, cell3, cell4);
            case 5:
                return posRotateS();
            case 6:
                return posRotateZ();
        }
        return posRotateT();
    }

    // EFFECTS: returns new position after rotating counterclockwise
    public Position positionRotateLeft(int piece) {
        switch (piece) {
            case 1:
                return posRotateI();
            case 2:
                return posRotateJLeft();
            case 3:
                return posRotateLLeft();
            case 4:
                return new Position(cell1, cell2, cell3, cell4);
            case 5:
                return posRotateS();
            case 6:
                return posRotateZ();
        }
        return posRotateTLeft();
    }

    // EFFECTS: returns position of Imino rotated clockwise
    public Position posRotateI() {
        Point newCell1;
        Point newCell2;
        Point newCell3;
        Point newCell4;
        if (cell1.y == cell2.y) {
            newCell1 = new Point(cell1.x + 2, cell1.y - 1);
            newCell2 = new Point(cell2.x + 1, cell2.y);
            newCell3 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell4.x - 1, cell4.y + 2);
        } else {
            newCell1 = new Point(cell1.x - 2, cell1.y + 1);
            newCell2 = new Point(cell2.x - 1, cell2.y);
            newCell3 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell4.x + 1, cell4.y - 2);
        }
        return new Position(newCell1, newCell2, newCell3, newCell4);
    }

    // EFFECTS: returns position of Jmino rotated clockwise
    public Position posRotateJ() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.y < cell2.y) {
            newCell1 = new Point(cell3.x + 1, cell3.y - 1);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x, cell3.y + 1);
        } else if (cell1.x > cell2.x) {
            newCell1 = new Point(cell3.x + 1, cell3.y + 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x - 1, cell3.y);
        } else if (cell1.y > cell2.y) {
            newCell1 = new Point(cell3.x - 1, cell3.y + 1);
            newCell2 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else {
            newCell1 = new Point(cell3.x - 1, cell3.y - 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x + 1, cell3.y);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }

    // EFFECTS: returns position of Imino rotated counter-clockwise
    public Position posRotateJLeft() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.y < cell2.y) {
            newCell1 = new Point(cell3.x - 1, cell3.y + 1);
            newCell2 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else if (cell1.x > cell2.x) {
            newCell1 = new Point(cell3.x - 1, cell3.y - 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x + 1, cell3.y);

        } else if (cell1.y > cell2.y) {
            newCell1 = new Point(cell3.x + 1, cell3.y - 1);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x, cell3.y + 1);
        } else {
            newCell1 = new Point(cell3.x + 1, cell3.y + 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x - 1, cell3.y);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }

    // EFFECTS: returns position of Lmino rotated clockwise
    public Position posRotateL() {
        Point newCell1;
        Point newCell3;
        Point newCell4;
        if (cell1.x < cell2.x) {
            newCell1 = new Point(cell2.x, cell2.y - 1);
            newCell3 = new Point(cell2.x, cell2.y + 1);
            newCell4 = new Point(cell2.x + 1, cell2.y + 1);
        } else if (cell1.y < cell2.y) {
            newCell1 = new Point(cell2.x + 1, cell2.y);
            newCell3 = new Point(cell2.x - 1, cell2.y);
            newCell4 = new Point(cell2.x - 1, cell2.y + 1);
        } else if (cell1.x > cell2.x) {
            newCell1 = new Point(cell2.x, cell2.y + 1);
            newCell3 = new Point(cell2.x, cell2.y - 1);
            newCell4 = new Point(cell2.x - 1, cell2.y - 1);
        } else {
            newCell1 = new Point(cell2.x - 1, cell2.y);
            newCell3 = new Point(cell2.x + 1, cell2.y);
            newCell4 = new Point(cell2.x + 1, cell2.y - 1);
        }
        return new Position(newCell1, cell2, newCell3, newCell4);
    }

    // EFFECTS: returns position of Lmino rotated counter-clockwise
    public Position posRotateLLeft() {
        Point newCell1;
        Point newCell3;
        Point newCell4;
        if (cell1.x < cell2.x) {
            newCell1 = new Point(cell2.x, cell2.y + 1);
            newCell3 = new Point(cell2.x, cell2.y - 1);
            newCell4 = new Point(cell2.x - 1, cell2.y - 1);
        } else if (cell1.y < cell2.y) {
            newCell1 = new Point(cell2.x - 1, cell2.y);
            newCell3 = new Point(cell2.x + 1, cell2.y);
            newCell4 = new Point(cell2.x + 1, cell2.y - 1);
        } else if (cell1.x > cell2.x) {
            newCell1 = new Point(cell2.x, cell2.y - 1);
            newCell3 = new Point(cell2.x, cell2.y + 1);
            newCell4 = new Point(cell2.x + 1, cell2.y + 1);
        } else {
            newCell1 = new Point(cell2.x + 1, cell2.y);
            newCell3 = new Point(cell2.x - 1, cell2.y);
            newCell4 = new Point(cell2.x - 1, cell2.y + 1);
        }
        return new Position(newCell1, cell2, newCell3, newCell4);
    }

    // EFFECTS: returns position of Smino rotated clockwise
    public Position posRotateS() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.x < cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y - 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x + 1, cell3.y + 1);
        } else if (cell1.y > cell3.y) {
            newCell1 = new Point(cell3.x + 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell3.x - 1, cell3.y + 1);
        } else if (cell1.x > cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y + 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x - 1, cell3.y - 1);
        } else {
            newCell1 = new Point(cell3.x - 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x + 1, cell3.y - 1);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }

    // EFFECTS: returns position of Zmino rotated clockwise
    public Position posRotateZ() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.x < cell2.x) {
            newCell1 = new Point(cell3.x + 1, cell3.y - 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y + 1);
        } else if (cell1.y > cell2.y) {
            newCell1 = new Point(cell3.x + 1, cell3.y + 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else if (cell1.x > cell2.x) {
            newCell1 = new Point(cell3.x - 1, cell3.y + 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else {
            newCell1 = new Point(cell3.x - 1, cell3.y - 1);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x + 1, cell3.y);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }

    // EFFECTS: returns position of Tmino rotated clockwise
    public Position posRotateT() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.x < cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y - 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y + 1);
        } else if (cell1.y < cell3.y) {
            newCell1 = new Point(cell3.x + 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell3.x - 1, cell3.y);
        } else if (cell1.x > cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y + 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else {
            newCell1 = new Point(cell3.x - 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x + 1, cell3.y);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }

    // EFFECTS: returns position of Tmino rotated counter-clockwise
    public Position posRotateTLeft() {
        Point newCell1;
        Point newCell2;
        Point newCell4;
        if (cell1.x < cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y + 1);
            newCell2 = new Point(cell3.x - 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y - 1);
        } else if (cell1.y < cell3.y) {
            newCell1 = new Point(cell3.x - 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y - 1);
            newCell4 = new Point(cell3.x + 1, cell3.y);
        } else if (cell1.x > cell3.x) {
            newCell1 = new Point(cell3.x, cell3.y - 1);
            newCell2 = new Point(cell3.x + 1, cell3.y);
            newCell4 = new Point(cell3.x, cell3.y + 1);
        } else {
            newCell1 = new Point(cell3.x + 1, cell3.y);
            newCell2 = new Point(cell3.x, cell3.y + 1);
            newCell4 = new Point(cell3.x - 1, cell3.y);
        }
        return new Position(newCell1, newCell2, cell3, newCell4);
    }
}
