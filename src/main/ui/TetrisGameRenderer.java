package ui;

import model.*;

import java.awt.*;

// Renderer for snake game
class TetrisGameRenderer {
    private Game game;
    public static final int PIXELS = 30;
    public static final int SPACE_UP = 30 * 3;
    public static final int SPACE_RIGHT = 30 * 7;
    public static final int SPACE_LEFT = 30 * 7; // this is literally wrong lol
    public static final int SPACE_DOWN = 30 * 2;
    public static final int WIDTH = 720;
    public static final int HEIGHT = 780;

    // EFFECTS: game to render is set to given game
    TetrisGameRenderer(Game game) {
        this.game = game;
    }

    // MODIFIES: graphics
    // EFFECTS:  draws tetris game onto graphics
    void draw(Graphics g) {
        drawBackground(g);
        drawMatrix(g);
        drawGhost(g);
        drawHeldPiece(g);
        drawNextPieces(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the background
    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 720, 780);
        g.setColor(new Color(255, 111, 45));
        g.fillRoundRect(20, 40, 680, 720, 50, 50);
    }

    // MODIFIES: graphics
    // EFFECTS: draw tetris matrix onto graphics
    private void drawMatrix(Graphics g) {
        // draw label
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 60));
        g.drawString("" + game.getScore(), SPACE_LEFT + 30, SPACE_UP + 15);

        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(SPACE_LEFT - 10, SPACE_UP + 20, 320, 620, 10, 10);

        for (int k = 0; k < 20; k++) {
            for (int j = 0; j < 10; j++) {
                Color color = getColor(game.getMatrix().getMatrix()[j][k]);
                g.setColor(color);
                g.fillRect(j * PIXELS + SPACE_LEFT, k * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
                g.setColor(Color.black);
                g.drawRect(j * PIXELS + SPACE_LEFT, k * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
            }
        }
        if (game.getGameMode().equals("Regular")) {
            drawLevel(g);
            drawLines(g);
        }
    }

    // MODIFIES: graphics
    // EFFECTS: draws ghost onto graphics if applicable
    private void drawGhost(Graphics g) {

        g.setColor(setGhostColour());
        int count = getGhostCount(1);
        int cell1x = game.getCurrentPiece().getPosition().getCell1().x;
        int cell1y = game.getCurrentPiece().getPosition().getCell1().y + count;
        g.setColor(setGhostColour());
        if (game.getMatrix().getMatrix()[cell1x][cell1y] == 0) {
            g.fillRect(cell1x * PIXELS + SPACE_LEFT, cell1y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
            g.setColor(Color.black);
            g.drawRect(cell1x * PIXELS + SPACE_LEFT, cell1y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
        }
        g.setColor(setGhostColour());
        int cell2x = game.getCurrentPiece().getPosition().getCell2().x;
        int cell2y = game.getCurrentPiece().getPosition().getCell2().y + count;
        if (game.getMatrix().getMatrix()[cell2x][cell2y] == 0) {
            g.fillRect(cell2x * PIXELS + SPACE_LEFT, cell2y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
            g.setColor(Color.black);
            g.drawRect(cell2x * PIXELS + SPACE_LEFT, cell2y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
        }
        fillRestOfGhost(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the last 2 cells of ghost, if applicable
    public void fillRestOfGhost(Graphics g) {
        int count = getGhostCount(1);
        g.setColor(setGhostColour());
        int cell3x = game.getCurrentPiece().getPosition().getCell3().x;
        int cell3y = game.getCurrentPiece().getPosition().getCell3().y + count;
        if (game.getMatrix().getMatrix()[cell3x][cell3y] == 0) {
            g.fillRect(cell3x * PIXELS + SPACE_LEFT, cell3y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
            g.setColor(Color.black);
            g.drawRect(cell3x * PIXELS + SPACE_LEFT, cell3y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
        }
        g.setColor(setGhostColour());
        int cell4x = game.getCurrentPiece().getPosition().getCell4().x;
        int cell4y = game.getCurrentPiece().getPosition().getCell4().y + count;
        if (game.getMatrix().getMatrix()[cell4x][cell4y] == 0) {
            g.fillRect(cell4x * PIXELS + SPACE_LEFT, cell4y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
            g.setColor(Color.black);
            g.drawRect(cell4x * PIXELS + SPACE_LEFT, cell4y * PIXELS + 30 + SPACE_UP, PIXELS, PIXELS);
        }
    }

    // EFFECTS: returns colour of current ghost
    private Color setGhostColour() {
        Color ghostColour;
        if (game.getCurrentPiece().getPiece() == 1) {
            ghostColour = new Color(92, 125, 125);
        } else if (game.getCurrentPiece().getPiece() == 2) {
            ghostColour = new Color(102, 104, 125);
        } else if (game.getCurrentPiece().getPiece() == 3) {
            ghostColour = new Color(125, 103, 88);
        } else if (game.getCurrentPiece().getPiece() == 4) {
            ghostColour = new Color(125, 123, 92);
        } else if (game.getCurrentPiece().getPiece() == 5) {
            ghostColour = new Color(106, 125, 106);
        } else if (game.getCurrentPiece().getPiece() == 6) {
            ghostColour = new Color(125, 97, 97);
        } else {
            ghostColour = new Color(114, 103, 125);
        }
        return ghostColour;
    }


    // EFFECTS: returns number of blocks down from current piece ghost is at
    private int getGhostCount(int count) {
        if (!game.collides(game.getCurrentPiece().getPosition().positionMoveDown(count))) {
            count++;
        } else {
            return count - 1;
        }
        return getGhostCount(count);
    }

    // EFFECTS: returns color given a int representing a piece, gray if not a piece
    public Color getColor(int piece) {
        switch (piece) {
            case 1:
                return new Imino().getColor();
            case 2:
                return new Jmino().getColor();
            case 3:
                return new Lmino().getColor();
            case 4:
                return new Omino().getColor();
            case 5:
                return new Smino().getColor();
            case 6:
                return new Zmino().getColor();
            case 7:
                return new Tmino().getColor();
            case 8:
                return Color.LIGHT_GRAY;
            case 9:
                return Color.ORANGE;
            case 11:
                return Color.white;         // incoming item
        }
        return Color.darkGray;              // case 10 also returns darkGray
    }

    public void drawGameOver(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("GAME OVER", 20 + SPACE_LEFT, 300 + SPACE_UP);
    }

    // MODIFIES: g
    // EFFECTS: draws the held piece onto graphics
    public void drawHeldPiece(Graphics g) {
        // draw label
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 36));
        g.drawString("HOLD", 69, 185);

        // draw board outline
        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(40, 190, 140, 140, 10, 10);

        // draw mini board
        g.setColor(Color.darkGray);
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                g.fillRect(j * PIXELS + 50, k * PIXELS + 30 + SPACE_UP + 80, PIXELS, PIXELS);
            }
        }

        if (!(game.getHeldPiece() == null)) {
            // draw piece
            drawPiece(g);
        }

        // draw outlines
        g.setColor(Color.black);
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                g.drawRect(j * PIXELS + 50, k * PIXELS + 30 + SPACE_UP + 80, PIXELS, PIXELS);
            }
        }
    }

    // MODIFIES: g
    // EFFECTS: draw the actual held mino onto top right of g
    public void drawPiece(Graphics g) {
        if (!(game.getHeldPiece() == null)) {
            // draw piece
            g.setColor(game.getHeldPiece().getColor());
            int cell1x = (game.getHeldPiece().getPosition().getCell1().x - 3) * PIXELS + 50;
            int cell1y = (game.getHeldPiece().getPosition().getCell1().y + 1) * PIXELS + 30 + SPACE_UP + 80;
            int cell2x = (game.getHeldPiece().getPosition().getCell2().x - 3) * PIXELS + 50;
            int cell2y = (game.getHeldPiece().getPosition().getCell2().y + 1) * PIXELS + 30 + SPACE_UP + 80;
            int cell3x = (game.getHeldPiece().getPosition().getCell3().x - 3) * PIXELS + 50;
            int cell3y = (game.getHeldPiece().getPosition().getCell3().y + 1) * PIXELS + 30 + SPACE_UP + 80;
            int cell4x = (game.getHeldPiece().getPosition().getCell4().x - 3) * PIXELS + 50;
            int cell4y = (game.getHeldPiece().getPosition().getCell4().y + 1) * PIXELS + 30 + SPACE_UP + 80;
            g.fillRect(cell1x, cell1y, PIXELS, PIXELS);
            g.fillRect(cell2x, cell2y, PIXELS, PIXELS);
            g.fillRect(cell3x, cell3y, PIXELS, PIXELS);
            g.fillRect(cell4x, cell4y, PIXELS, PIXELS);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws the display of next 3 pieces in queue
    public void drawNextPieces(Graphics g) {
        int board = 500;
        // draw label
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 36));
        g.drawString("NEXT", 71 + board, 185);

        // draw board outline
        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(40 + board, 190, 140, 140, 10, 10);

        // draw mini board
        g.setColor(Color.darkGray);
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                g.fillRect(j * PIXELS + 50 + board, k * PIXELS + 30 + SPACE_UP + 80, PIXELS, PIXELS);
            }
        }

        // draw piece
        drawNextMino(g);

        // draw outlines
        g.setColor(Color.black);
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                g.drawRect(j * PIXELS + 50 + board, k * PIXELS + 30 + SPACE_UP + 80, PIXELS, PIXELS);
            }
        }

        drawNextTwoPieces(g);
    }

    // MODIFIES: g
    // EFFECTS: draws the very next mino in queue onto graphics
    public void drawNextMino(Graphics g) {
        int board = 500;
        g.setColor(game.getNextPieces().get(0).getColor());
        int cell1x = (game.getNextPieces().get(0).getPosition().getCell1().x - 3) * PIXELS + 50 + board;
        int cell1y = (game.getNextPieces().get(0).getPosition().getCell1().y + 1) * PIXELS + 30 + SPACE_UP + 80;
        int cell2x = (game.getNextPieces().get(0).getPosition().getCell2().x - 3) * PIXELS + 50 + board;
        int cell2y = (game.getNextPieces().get(0).getPosition().getCell2().y + 1) * PIXELS + 30 + SPACE_UP + 80;
        int cell3x = (game.getNextPieces().get(0).getPosition().getCell3().x - 3) * PIXELS + 50 + board;
        int cell3y = (game.getNextPieces().get(0).getPosition().getCell3().y + 1) * PIXELS + 30 + SPACE_UP + 80;
        int cell4x = (game.getNextPieces().get(0).getPosition().getCell4().x - 3) * PIXELS + 50 + board;
        int cell4y = (game.getNextPieces().get(0).getPosition().getCell4().y + 1) * PIXELS + 30 + SPACE_UP + 80;
        g.fillRect(cell1x, cell1y, PIXELS, PIXELS);
        g.fillRect(cell2x, cell2y, PIXELS, PIXELS);
        g.fillRect(cell3x, cell3y, PIXELS, PIXELS);
        g.fillRect(cell4x, cell4y, PIXELS, PIXELS);
    }

    // MODIFIES: g
    // EFFECTS: draws the next 2 pieces in queue onto board
    public void drawNextTwoPieces(Graphics g) {
        int board = 500;

        // draw board outline
        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(40 + board, 190 + 150, 140, 260, 10, 10);

        for (int k = 1; k < 3; k++) {
            // draw mini board
            int moveDown = k * 150 - ((k - 1) * 30);

            g.setColor(Color.darkGray);
            for (int p = 0; p < 4; p++) {
                for (int j = 0; j < 4; j++) {
                    g.fillRect(j * PIXELS + 50 + board, p * PIXELS + 30 + SPACE_UP + 80 + moveDown, PIXELS, PIXELS);
                }
            }

            drawNextTwoMinos(g, k, board, moveDown);

            // draw outlines
            g.setColor(Color.black);
            for (int p = 0; p < 4; p++) {
                for (int j = 0; j < 4; j++) {
                    g.drawRect(j * PIXELS + 50 + board, p * PIXELS + 30 + SPACE_UP + 80 + moveDown, PIXELS, PIXELS);
                }
            }
        }
    }

    // MODIFIES: g
    // EFFECTS: draws piece in queue onto graphics
    public void drawNextTwoMinos(Graphics g, int k, int board, int moveDown) {
        // draw piece
        g.setColor(game.getNextPieces().get(k).getColor());
        int cell1x = (game.getNextPieces().get(k).getPosition().getCell1().x - 3) * PIXELS + 50 + board;
        // 200 = 30 + SPACE_UP + 80
        int cell1y = (game.getNextPieces().get(k).getPosition().getCell1().y + 1) * PIXELS + 200 + moveDown;
        int cell2x = (game.getNextPieces().get(k).getPosition().getCell2().x - 3) * PIXELS + 50 + board;
        int cell2y = (game.getNextPieces().get(k).getPosition().getCell2().y + 1) * PIXELS + 200 + moveDown;
        int cell3x = (game.getNextPieces().get(k).getPosition().getCell3().x - 3) * PIXELS + 50 + board;
        int cell3y = (game.getNextPieces().get(k).getPosition().getCell3().y + 1) * PIXELS + 200 + moveDown;
        int cell4x = (game.getNextPieces().get(k).getPosition().getCell4().x - 3) * PIXELS + 50 + board;
        int cell4y = (game.getNextPieces().get(k).getPosition().getCell4().y + 1) * PIXELS + 200 + moveDown;
        g.fillRect(cell1x, cell1y, PIXELS, PIXELS);
        g.fillRect(cell2x, cell2y, PIXELS, PIXELS);
        g.fillRect(cell3x, cell3y, PIXELS, PIXELS);
        g.fillRect(cell4x, cell4y, PIXELS, PIXELS);
    }

    // MODIFIES: graphics
    // EFFECTS: displays current level
    public void drawLevel(Graphics g) {
        int moveDownFromHold = 180;
        int moveRight = 15;
        // draw label
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 32));
        g.drawString("LEVEL", 63 + moveRight, 185 + moveDownFromHold);

        // draw board outline
        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(40 + moveRight, 190 + moveDownFromHold, 110, 110, 10, 10);

        // draw mini board
        g.setColor(Color.darkGray);
        g.fillRect(50 + moveRight, 30 + SPACE_UP + 80 + moveDownFromHold, PIXELS * 3, PIXELS * 3);

        // draw level number
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 50));
        g.drawString("" + game.getLevel(), 68 + moveRight, 185 + moveDownFromHold + 80);
    }

    // MODIFIES: graphics
    // EFFECTS: displays lines cleared
    public void drawLines(Graphics g) {
        int moveDownFromHold = 330;
        int moveRight = 15;
        // draw label
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 32));
        g.drawString("LINES", 63 + moveRight, 185 + moveDownFromHold);

        // draw board outline
        g.setColor(new Color(249, 187, 135));
        g.fillRoundRect(40 + moveRight, 190 + moveDownFromHold, 110, 110, 10, 10);

        // draw mini board
        g.setColor(Color.darkGray);
        g.fillRect(50 + moveRight, 30 + SPACE_UP + 80 + moveDownFromHold, PIXELS * 3, PIXELS * 3);

        // draw line number
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD + Font.ITALIC, 37));
        g.drawString("" + game.getLines(), 63 + moveRight, 185 + moveDownFromHold + 80);
    }

    public void drawGamePaused(Graphics g) {
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD, 40));
        g.drawString("PAUSED", 79 + SPACE_LEFT, 300 + SPACE_UP);
    }

    // MODIFIES: graphics
    // EFFECTS: displays the message that save was successful
    public void drawSaved(Graphics g) {
        g.setColor(new Color(255, 186, 34));
        g.setFont(new Font("Impact", Font.BOLD, 40));
        g.drawString("SAVE SUCCESSFUL", 220, 400 + SPACE_UP);
    }
}
