package model;

import persistence.Saveable;
import persistence.Reader;

import java.io.PrintWriter;

// represents a player/ an account, stores player info & account data
public class Player implements Saveable {

    //player profile
    private String username;
    private String password;
    private String name;        // screen name
    private int regularHighScore;
    private int survivalHighScore;
    private int arcadeHighScore;
    private String regularMatrixData;
    private int regularLevel;
    private int regularLines;
    private String survivalMatrixData;
    private int survivalScore;
    private String arcadeMatrixData;
    private int arcadeScore;
    //make an avatar option?

    // MODIFIES: this
    public Player(String u, String p, String n) {
        username = u;
        password = p;
        name = n;
        regularHighScore = 0;
        survivalHighScore = 0;
        arcadeHighScore = 0;
        regularMatrixData = "";
        regularLevel = -1;                  // these are for checking
        regularLines = -1;
        survivalMatrixData = "";
        survivalScore = -1;                 // these are for checking
        arcadeMatrixData = "";
        arcadeScore = -1;                   // these are for checking
    }

    public String getRegularMatrixData() {
        return regularMatrixData;
    }

    public int getRegularLevel() {
        return regularLevel;
    }

    public int getRegularLines() {
        return regularLines;
    }

    public String getSurvivalMatrixData() {
        return survivalMatrixData;
    }

    public int getSurvivalScore() {
        return survivalScore;
    }

    public String getArcadeMatrixData() {
        return arcadeMatrixData;
    }

    public int getArcadeScore() {
        return arcadeScore;
    }

    // EFFECTS: returns username of player account
    public String getUsername() {
        return username;
    }

    // EFFECTS: returns password of player account
    public String getPassword() {
        return password;
    }

    // EFFECTS: returns name of player
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: changes the username of player
    public void changeUsername(String s) {
        username = s;
    }

    // MODIFIES: this
    // EFFECTS: changes the password of player
    public void changePassword(String s) {
        password = s;
    }

    // MODIFIES: this
    // EFFECTS: changes the name of player
    public void changeName(String s) {
        name = s;
    }

    // EFFECTS: returns player's high score in regular mode
    public int getRegularHighScore() {
        return (regularHighScore);
    }

    // EFFECTS: returns player's high score in survival mode
    public int getSurvivalHighScore() {
        return (survivalHighScore);
    }

    // EFFECTS: returns player's high score in regular mode
    public int getArcadeHighScore() {
        return (arcadeHighScore);
    }

    // REQUIRES: h is greater than regularHighScore
    // MODIFIES: this
    // EFFECTS: updates the high score of player in regular mode
    public void updateRegularHighScore(int h) {
        regularHighScore = h;
    }

    // REQUIRES: h is greater than survivalHighScore
    // MODIFIES: this
    // EFFECTS: updates the high score of player in survival mode
    public void updateSurvivalHighScore(int h) {
        survivalHighScore = h;
    }

    // REQUIRES: h is greater than arcadeHighScore
    // MODIFIES: this
    // EFFECTS: updates the high score of player in survival mode
    public void updateArcadeHighScore(int h) {
        arcadeHighScore = h;
    }

    // MODIFIES: this
    // EFFECTS: sets the regular mode save to input
    public void updateRegularSave(String md, int lvl, int lin) {
        regularMatrixData = md;
        regularLevel = lvl;
        regularLines = lin;
    }

    // MODIFIES: this
    // EFFECTS: sets the survival mode save to input
    public void updateSurvivalSave(String md, int score) {
        survivalMatrixData = md;
        survivalScore = score;
    }

    // MODIFIES: this
    // EFFECTS: sets the arcade mode save to input
    public void updateArcadeSave(String md, int score) {
        arcadeMatrixData = md;
        arcadeScore = score;
    }

    // MODIFIES: players.txt
    // EFFECTS: saves player data to text file
    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(username + Reader.DELIMITER);
        printWriter.print(password + Reader.DELIMITER);
        printWriter.print(name + Reader.DELIMITER);
        printWriter.print(regularHighScore + Reader.DELIMITER);
        printWriter.print(survivalHighScore + Reader.DELIMITER);
        printWriter.print(arcadeHighScore + Reader.DELIMITER);
        printWriter.print(regularMatrixData + Reader.DELIMITER);
        printWriter.print(regularLevel + Reader.DELIMITER);
        printWriter.print(regularLines + Reader.DELIMITER);
        printWriter.print(survivalMatrixData + Reader.DELIMITER);
        printWriter.print(survivalScore + Reader.DELIMITER);
        printWriter.print(arcadeMatrixData + Reader.DELIMITER);
        printWriter.print(arcadeScore + Reader.DELIMITER);
    }
}
