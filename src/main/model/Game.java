package model;

import ui.Runner2;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

// represents the tetris game, has all the control methods, stores game state
public class Game {
    private String matrixData = "";
    private Matrix matrix;
    private Player player;
    private String gameMode;
    private int score = 0;
    private int level = 1;          // regular
    private int lines = 0;          // regular
    private Boolean bombs = false;  // survival
    private Boolean items = false;  // arcade
    private Boolean saveState = false;
    private Tetromino currentPiece;
    private int[][] wellMatrix = new int[10][20];
    private List<Tetromino> queue = new LinkedList<>(); // 3 pieces
    private Tetromino heldPiece;
    private Boolean isPaused = false;
    private Boolean justHeld;
    private Boolean isOver = false;
    private Runner2 runner;
    private Item currentItem;
    private int pieces;
    public static int itemTime = 7; // measured in pieces
    public static int piecesPerItem = 15;
    private int itemTimeSoFar = 0; // how many pieces has the items last through
    private Boolean drawItem = false;

    // 0 = empty
    // 1 = I
    // 2 = J
    // 3 = L
    // 4 = O
    // 5 = S
    // 6 = Z
    // 7 = T
    // 8 = garbage line
    // 9 = bomb
    // 10 = invisible

    // represets the 4 possible items in arcade mode
    public enum Item {
        INVISIMINO,         // insivible mode
        SPEEDIMINO,         // faster drop
        CLEARIMINO,         // clear matrix
        TWISTIMINO,         // activate cheese
    }

    // REMEMBER TO UPDATE AND SAVE MATRIX DATA WHEN GAME FINISHES

    // MODIFIES: this
    // EFFECTS: is basically the constructor for if saveState is false
    public void newGame() {
//        listItems.add(1);
//        listItems.add(2);
//        listItems.add(3);
//        listItems.add(0);
        switch (gameMode) {
            case "Regular":
                matrix = new Matrix();
                break;
            case "Survival":
                matrix = new Matrix();
                bombs = true;
                break;
            case "Arcade":
                matrix = new Matrix();
                items = true;
                break;
        }
        matrixData = matrix.getMatrixData();
        runGame();
    }


    // MODIFIES: this
    public Game(Player player, String gameMode, Boolean saveState) {
        runner = new Runner2(0);
        this.player = player;
        this.gameMode = gameMode;
        if (!saveState) {
            newGame();
        } else {
            createGame();
            runGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates new game
    public void createGame() {
        this.saveState = true;
        runner.loadPlayers();
        if (gameMode.equals("Regular")) {
            // parse level and score from level
            matrix = new Matrix();
            level = player.getRegularLevel() % 100;
            score = player.getRegularLevel() - (player.getRegularLevel() % 100);
            lines = player.getRegularLines();
            loadSaveOntoWell(runner.getMatrixData(player, "Regular"));
        } else if (gameMode.equals("Survival")) {
            matrix = new Matrix();
            score = player.getSurvivalScore();
            loadSaveOntoWell(runner.getMatrixData(player, "Survival"));
            bombs = true;
        } else if (gameMode.equals("Arcade")) {
            matrix = new Matrix();
            score = player.getArcadeScore();
            loadSaveOntoWell(runner.getMatrixData(player, "Arcade"));
            items = true;
        }
    }

    // REQUIRES saveData is matrixData
    // MODIFIES: this
    // EFFECTS: loads given string as values into wellMatrix
    public void loadSaveOntoWell(String saveData) {
        for (int k = 0; k < 20; k++) {
            for (int j = 0; j < 10; j++) {
                int index = (10 * k) + j;
                String data = Character.toString(saveData.charAt(index));
                wellMatrix[j][k] = Integer.parseInt(data);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: fills in wellMatrix and updates the piece queue, starts the game
    public void runGame() {
        if (!saveState) {
            fillWith0(wellMatrix);
        }
        for (int k = 0; k < 3; k++) {
            addToQueue();
        }
        pieces += 3;
        nextPiece();
        updateMatrix();
    }

    // MODIFIES: this
    // EFFECTS: adds a piece to the queue
    public void addToQueue() {
        int choose = (int) (Math.random() * 7 + 1);
        Tetromino t = null;
        if (choose == 1) {
            t = new Imino();
        } else if (choose == 2) {
            t = new Jmino();
        } else if (choose == 3) {
            t = new Lmino();
        } else if (choose == 4) {
            t = new Omino();
        } else if (choose == 5) {
            t = new Smino();
        } else if (choose == 6) {
            t = new Zmino();
        } else if (choose == 7) {
            t = new Tmino();
        }
        queue.add(t);
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public String getGameMode() {
        return gameMode;
    }

    // REQUIRES: !isOver()
    // MODIFIES: this
    // EFFECTS:  move current piece down
    public void update() {
        softDrop();
        if (bombs) {
            newGarbageLine();
        }
        updateMatrix();
    }

    public Boolean isOver() {
        return isOver;
    }

    public Tetromino getCurrentPiece() {
        return currentPiece;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public int getScore() {
        return score;
    }

    // REQUIRES: i > 0
    // MODIFIES: this
    // EFFECTS: adds an integer to the score
    public void addScore(int i) {
        score += i;
    }

    public int getLevel() {
        return level;
    }

    public int getLines() {
        return lines;
    }

    public Player getPlayer() {
        return player;
    }

    // MODIFIES: this
    // EFFECTS: Rotate the piece clockwise
    public void rotateRight() {
        if (!collides(currentPiece.getPosition().positionRotateRight(currentPiece.getPiece()))) {
            currentPiece.getPosition().setP(currentPiece.getPosition().positionRotateRight(currentPiece.getPiece()));
        }
    }

    // MODIFIES: this
    // EFFECTS: Rotate the piece counterClockwise
    public void rotateLeft() {
        if (!collides(currentPiece.getPosition().positionRotateLeft(currentPiece.getPiece()))) {
            currentPiece.getPosition().setP(currentPiece.getPosition().positionRotateLeft(currentPiece.getPiece()));
        }
    }

    // EFFECTS: helper that fills given matrix with Os
    public int[][] fillWith0(int[][] mat) {
        for (int k = 0; k < mat.length; k++) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[k][j] = 0;
            }
        }
        return mat;
    }

    // REQUIRES: given wellMatrix
    // MODIFIES: this
    // EFFECTS: randomly rearrange every block in each row
    public void activateCheese(int[][] mat) {
        int[] order = new int[10];
        for (int k = 0; k < 20; k++) {
            for (int j = 0; j < 10; j++) {
                order[j] = mat[j][k];
            }
            order = getRandomOrder(order);
            for (int j = 0; j < 10; j++) {
                mat[j][k] = order[j];
            }
        }
    }

    // REQUIRES: order is of size 10
    // EFFECTS: helper for activateCheese, returns an array with int 0-9 shuffled
    public int[] getRandomOrder(int[] order) {
        for (int k = 0; k < 10; k++) {
            int index = (int) (Math.random() * 10);
            int index2 = (int) (Math.random() * 10);
            int temp = order[index];
            order[index] = order[index2];
            order[index2] = temp;
        }
        return order;
    }

    // REQUIRES: i is 1 or -1
    // MODIFIES: this
    // EFFECTS: Move the current piece left or right
    public void move(int i) {
        if (!collides(currentPiece.getPosition().positionMoveX(i))) {
            currentPiece.getPosition().moveX(i);
            updateMatrix();
        }
    }

    // MODIFIES: this
    // EFFECTS: sticks the current piece to well by dropping it all the way down
    public void hardDrop() {
        fixToWell();
    }

    // MODIFIES: this
    // EFFECTS: Drops the piece one line or fixes it to the well if it can't drop
    public void softDrop() {
        int pieceRow = getPieceRow();
        // so the bomb doesnt become a hole
        if (bombs && (pieceRow + 1 == getBombRow().y || pieceRow + 2 == getBombRow().y)) {
            deleteRow(getBombRow().y);
            fixToWell();
        } else {
            if (!collides(currentPiece.getPosition().positionMoveDown())) {
                currentPiece.getPosition().moveY();
            } else {
                fixToWell();
            }
        }
    }

    // EFFECT: returns the largest y position of any cell in currentPiece
    public int getPieceRow() {
        int y1 = currentPiece.getPosition().getCell1().y;
        int y2 = currentPiece.getPosition().getCell2().y;
        int y3 = currentPiece.getPosition().getCell3().y;
        int y4 = currentPiece.getPosition().getCell4().y;
        int pieceRow = 0;   // should be changed
        if (y1 <= y2 && y1 <= y3 && y1 <= y4) {
            pieceRow = y1;
        } else if (y2 <= y1 && y2 <= y3 && y2 <= y4) {
            pieceRow = y2;
        } else if (y3 <= y1 && y3 <= y2 && y3 <= y4) {
            pieceRow = y3;
        } else {
            pieceRow = y4;
        }
        return pieceRow;
    }

    // EFFECTS: Make the dropping piece part of the well, so it is available for
    // collision detection.
    public void fixToWell() {
        while (!collides(currentPiece.getPosition().positionMoveDown())) {
            currentPiece.getPosition().moveY();
        }
        updateWellMatrix();
        clearRows();
        if (hitABomb()) {               // if hit a bomb, delete the row
            deleteRow(getBombRow().y);
        }
        nextPiece();
        justHeld = false;   // piece is dropped
    }

    // REQUIRES: current piece is fixed to well
    // MODIFIES: this
    // EFFECTS: Put a new, random piece into the dropping position, updates queue,
    // generates an item if per 15 pieces if in arcade mode
    public void nextPiece() {
        drawItem = false;
        currentPiece = queue.get(0);
        isOver = collides(currentPiece.getPosition());
        if (isOver) {
            saveEndedGame();
        }
        updateQueue();
        if (items == true) {
            if (pieces % (piecesPerItem + itemTime) == (piecesPerItem + itemTime - 1)) {
                drawItem = true;
            }
            nextPieceArcade();
        }
    }

    // REQUIRES: is in arcade mode
    // MODIFIES: this
    // EFFECTS: manages the current item in arcade mode
    public void nextPieceArcade() {
        if (itemTimeSoFar == itemTime) {
            currentItem = null;
            itemTimeSoFar = 0;
        } else if (currentItem != null) {
            itemTimeSoFar++;
        } else if (pieces % (piecesPerItem + itemTime) == 0) { // since you dont count the pieces during item
//            int item = listItems.get(0);
//            listItems.remove(0);
            int item = (int) (Math.random() * 4);
            if (item == 0) {
                currentItem = Item.INVISIMINO;
                itemTimeSoFar = 0;
            } else if (item == 1) {
                currentItem = Item.SPEEDIMINO;
                itemTimeSoFar = 0;
            } else if (item == 2) {
                currentItem = Item.CLEARIMINO;
                itemTimeSoFar = itemTime;           // the clearimino is a one time use
                fillWith0(wellMatrix);
            } else {
                currentItem = Item.TWISTIMINO;
                itemTimeSoFar = itemTime;           // the twistimino is a one time use
                activateCheese(wellMatrix);
            }
        }
    }

    // REQUIRES: isOver is true
    // MODIFIES: players.txt
    // EFFECTS: saves the new high school (if applicable) to player data
    public void saveEndedGame() {
        if (gameMode.equals("Regular") && score > player.getRegularHighScore()) {
            player.updateRegularHighScore(score);
        } else if (bombs && score > player.getSurvivalHighScore()) {
            player.updateSurvivalHighScore(score);
        } else if (items == true && score > player.getArcadeHighScore()) {
            player.updateArcadeHighScore(score);
        }
        runner.loadPlayers();
        runner.savePlayer(player);
    }

    // EFFECTS: generates a random tetromino and adds it to queue, removes first mino in queue
    public void updateQueue() {
        int choose = (int) (Math.random() * 7 + 1);
        Tetromino t = null;
        // 1 = I
        // 2 = J
        // 3 = L
        // 4 = O
        // 5 = S
        // 6 = Z
        // 7 = T
        if (choose == 1) {
            t = new Imino();
        } else if (choose == 2) {
            t = new Jmino();
        } else if (choose == 3) {
            t = new Lmino();
        } else if (choose == 4) {
            t = new Omino();
        } else if (choose == 5) {
            t = new Smino();
        } else if (choose == 6) {
            t = new Zmino();
        } else if (choose == 7) {
            t = new Tmino();
        }
        queue.remove(0);
        queue.add(t);
        pieces++;
    }

    // MODIFIES: this
    // EFFECTS: Clear completed rows from the field and award score according to
    // the number of simultaneously cleared rows.
    public void clearRows() {
        boolean gap;
        int linesCleared = 0;

        for (int k = 19; k > -1; k--) {
            gap = false;
            for (int j = 0; j < 10; j++) {
                if (wellMatrix[j][k] == 0 || wellMatrix[j][k] >= 8) {
                    gap = true;
                    break;
                }
            }
            if (!gap) {
                deleteRow(k);
                k += 1;
                linesCleared += 1;
            }
        }
        if (gameMode.equals("Regular")) {
            awardPoints(linesCleared);
            lines += linesCleared;
            updateLevel();
        }
    }

    // EFFECTS: returns true if current piece is touches a bomb
    public Boolean hitABomb() {
        int cell1x = currentPiece.getPosition().getCell1().x;
        int cell1y = currentPiece.getPosition().getCell1().y + 1;
        int cell2x = currentPiece.getPosition().getCell2().x;
        int cell2y = currentPiece.getPosition().getCell2().y + 1;
        int cell3x = currentPiece.getPosition().getCell3().x;
        int cell3y = currentPiece.getPosition().getCell3().y + 1;
        int cell4x = currentPiece.getPosition().getCell4().x;
        int cell4y = currentPiece.getPosition().getCell4().y + 1;
        int bombx = getBombRow().x;
        int bomby = getBombRow().y;
        if (cell1x == bombx && cell1y == bomby) {
            return true;
        } else if (cell2x == bombx && cell2y == bomby) {
            return true;
        } else if (cell3x == bombx && cell3y == bomby) {
            return true;
        } else if (cell4x == bombx && cell4y == bomby) {
            return true;
        }
        return false;
    }

    // REQUIRES: there is a bomb
    // EFFECTS: returns the smallest y coordinate of bomb lines
    public Point getBombRow() {
        for (int k = 0; k < 19; k++) {
            for (int j = 0; j < 10; j++) {
                if (wellMatrix[j][k] == 9) {
                    return new Point(j, k);
                }
            }
        }
        return new Point(0, 0); // if there is no bomb
    }

    // REQUIRES: gameMode is "Regular"
    // MODIFIES: this
    // EFFECTS: updates the level based on lines cleared
    public void updateLevel() {
        if (level != 15) {
            level = lines / 10 + 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: awards points based on lines cleared
    public void awardPoints(int linesCleared) {
        switch (linesCleared) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes given row from well matrix
    public void deleteRow(int row) {
        for (int k = row; k > 0; k--) {
            for (int j = 0; j < 10; j++) {
                wellMatrix[j][k] = wellMatrix[j][k - 1];
            }
        }
        for (int j = 0; j < 10; j++) {
            wellMatrix[j][0] = 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: generates new garbage bomb line by pushing well matrix upwards
    public void newGarbageLine() {

        int pieceRow = getPieceRow();
        // so the bomb doesnt become a hole
        if (bombs && (pieceRow + 1 == getBombRow().y || pieceRow + 2 == getBombRow().y)) {
            deleteRow(getBombRow().y);
            fixToWell();
        }

        for (int k = 0; k < 19; k++) {
            for (int j = 0; j < 10; j++) {
                wellMatrix[j][k] = wellMatrix[j][k + 1];
            }
        }
        int posBomb = (int) (Math.random() * 10);
        for (int p = 0; p < 10; p++) {
            wellMatrix[p][19] = 8; // 8 represents a garbage line
        }
        wellMatrix[posBomb][19] = 9; // 9 represents a bomb
    }

    // MODIFIES: this
    // EFFECTS: sets current piece as part of well matrix
    public void updateWellMatrix() {
        // puts current piece on well matrix
        int piece = currentPiece.getPiece();
        int posX1 = currentPiece.getPosition().getCell1().x;
        int posY1 = currentPiece.getPosition().getCell1().y;
        wellMatrix[posX1][posY1] = piece;
        int posX2 = currentPiece.getPosition().getCell2().x;
        int posY2 = currentPiece.getPosition().getCell2().y;
        wellMatrix[posX2][posY2] = piece;
        int posX3 = currentPiece.getPosition().getCell3().x;
        int posY3 = currentPiece.getPosition().getCell3().y;
        wellMatrix[posX3][posY3] = piece;
        int posX4 = currentPiece.getPosition().getCell4().x;
        int posY4 = currentPiece.getPosition().getCell4().y;
        wellMatrix[posX4][posY4] = piece;
    }

    // MODIFIES: this
    // EFFECTS: puts wellmatrix and current piece onto a single matrix for drawing
    public void updateMatrix() {
        // put well matrix on matrix (includes invisimino case)
        if (currentItem != null && currentItem == Item.INVISIMINO) {
            for (int k = 0; k < 20; k++) {
                for (int j = 0; j < 10; j++) {
                    if (wellMatrix[j][k] >= 1 && wellMatrix[j][k] <= 7) {
                        matrix.getMatrix()[j][k] = 10;
                    } else {
                        matrix.getMatrix()[j][k] = wellMatrix[j][k];
                    }
                }
            }
        } else {
            for (int k = 0; k < 20; k++) {
                for (int j = 0; j < 10; j++) {
                    matrix.getMatrix()[j][k] = wellMatrix[j][k];
                }
            }
        }

        putCurrentPieceOnMatrix();
    }

    // MODIFIES: this
    // EFFECTS: put current piece on matrix
    public void putCurrentPieceOnMatrix() {
        // put current piece on matrix
        int piece = currentPiece.getPiece();
        // if need to draw white / question mark to represent incoming item
        if (drawItem) {
            piece = 11;
        }
        int posX1 = currentPiece.getPosition().getCell1().x;
        int posY1 = currentPiece.getPosition().getCell1().y;
        matrix.getMatrix()[posX1][posY1] = piece;
        int posX2 = currentPiece.getPosition().getCell2().x;
        int posY2 = currentPiece.getPosition().getCell2().y;
        matrix.getMatrix()[posX2][posY2] = piece;
        int posX3 = currentPiece.getPosition().getCell3().x;
        int posY3 = currentPiece.getPosition().getCell3().y;
        matrix.getMatrix()[posX3][posY3] = piece;
        int posX4 = currentPiece.getPosition().getCell4().x;
        int posY4 = currentPiece.getPosition().getCell4().y;
        matrix.getMatrix()[posX4][posY4] = piece;
    }

    // EFFECTS: returns true if a given position overlaps with any in well matrix
    public Boolean collides(Position p) {
        if (outOfBounds(p)) {
            return true;
        } else if (wellMatrix[p.getCell1().x][p.getCell1().y] != 0) {
            return true;
        } else if (wellMatrix[p.getCell2().x][p.getCell2().y] != 0) {
            return true;
        } else if (wellMatrix[p.getCell3().x][p.getCell3().y] != 0) {
            return true;
        } else if (wellMatrix[p.getCell4().x][p.getCell4().y] != 0) {
            return true;
        }
        return false;
    }

    // EFFECTS: returns true if given position is out of the matrix
    public Boolean outOfBounds(Position p) {
        if (p.getCell1().x > 9 || p.getCell2().x > 9 || p.getCell3().x > 9 || p.getCell4().x > 9) {
            return true;
        } else if (p.getCell1().x < 0 || p.getCell2().x < 0 || p.getCell3().x < 0 || p.getCell4().x < 0) {
            return true;
        } else if (p.getCell1().y > 19 || p.getCell2().y > 19 || p.getCell3().y > 19 || p.getCell4().y > 19) {
            return true;
        } else if (p.getCell1().y < 0 || p.getCell2().y < 0 || p.getCell3().y < 0 || p.getCell4().y < 0) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes the state of isPaused
    public void changePause() {
        if (isPaused) {
            isPaused = false;
        } else {
            isPaused = true;
        }
    }

    public Boolean isPaused() {
        return isPaused;
    }

    // MODIFIES: this
    // EFFECTS: switches the held piece with the current piece if allowed
    public void hold() {
        if (heldPiece == null) {
            heldPiece = newPiece(currentPiece);
            nextPiece();
            justHeld = true;
        }
        if (!justHeld) {
            Tetromino temp = heldPiece;
            heldPiece = newPiece(currentPiece);
            currentPiece = newPiece(temp);
            justHeld = true;
        }
    }

    // MODIFIES: t
    // EFFECTS: returns a new mino of same type of given mino
    public Tetromino newPiece(Tetromino t) {
        switch (t.getPiece()) {
            case 1:
                return new Imino();
            case 2:
                return new Jmino();
            case 3:
                return new Lmino();
            case 4:
                return new Omino();
            case 5:
                return new Smino();
            case 6:
                return new Zmino();
        }
        return new Tmino();
    }

    // EFFECTS: returns a NEW tetromino of same type as held piece
    public Tetromino getHeldPiece() {
        if (heldPiece == null) {
            return null;
        }
        return newPiece(heldPiece);
    }

    // EFFECTS: returns a NEW tetromino of same type as held piece
    public LinkedList<Tetromino> getNextPieces() {
        List<Tetromino> queuePieces = new LinkedList<>();
        for (int k = 0; k < 3; k++) {
            if (queue.get(k).getPiece() == 1) {
                queuePieces.add(new Imino());
            } else if (queue.get(k).getPiece() == 2) {
                queuePieces.add(new Jmino());
            } else if (queue.get(k).getPiece() == 3) {
                queuePieces.add(new Lmino());
            } else if (queue.get(k).getPiece() == 4) {
                queuePieces.add(new Omino());
            } else if (queue.get(k).getPiece() == 5) {
                queuePieces.add(new Smino());
            } else if (queue.get(k).getPiece() == 6) {
                queuePieces.add(new Zmino());
            } else if (queue.get(k).getPiece() == 7) {
                queuePieces.add(new Tmino());
            }
        }
        return (LinkedList) queuePieces;
    }

    // MODIFIES: players.txt
    // EFFECTS: saves current game state to player file, overwrites previous save
    public void saveState() {
        matrixData = "";
        for (int k = 0; k < 20; k++) {
            for (int j = 0; j < 10; j++) {
                matrixData = matrixData + ("" + wellMatrix[j][k]);
            }
        }
        switch (gameMode) {
            case "Regular":
                player.updateRegularSave(matrixData, level + score, lines); // level stores both level and score
                break;
            case "Survival":
                player.updateSurvivalSave(matrixData, score);
                break;
            case "Arcade":
                player.updateArcadeSave(matrixData, score);
        }
        runner.loadPlayers();
        runner.savePlayer(player);
    }
}
