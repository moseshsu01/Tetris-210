package persistence;

import model.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A reader that can read account data from a file
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of players parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Player> readPlayers(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of players parsed from list of strings
    // where each string contains data for one player
    private static List<Player> parseContent(List<String> fileContent) {
        List<Player> players = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            players.add(parsePlayer(lineComponents));
        }
        return players;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 6 where element 0 represents the
    // username of the next player to be constructed, element 1 represents
    // the password, elements 2 represents the name, element 3 represents
    // the regular high score, element 4 represents the survival high score,
    // and element 5 represents the arcade high score of the player ot be constructed
    // element 6 is the regular matrix data, element 7 is the regular level,
    // 8 is the regular score, 9 is the survival matrix data, 10 is the survival score,
    // 11 is the arcade matrix data, and 12 is the arcade score
    // EFFECTS: returns an player constructed from components
    private static Player parsePlayer(List<String> components) {
        String username = components.get(0);
        String password = components.get(1);
        String name = components.get(2);
        int regularHighScore = Integer.parseInt(components.get(3));
        int survivalHighScore = Integer.parseInt(components.get(4));
        int arcadeHighScore = Integer.parseInt(components.get(5));
        String regularMD = components.get(6);
        int regularLvl = Integer.parseInt(components.get(7));
        int regularLines = Integer.parseInt(components.get(8));
        String survivalMD = components.get(9);
        int survivalScore = Integer.parseInt(components.get(10));
        String arcadeMD = components.get(11);
        int arcadeScore = Integer.parseInt(components.get(12));
        Player temp = new Player(username, password, name);
        temp.updateRegularHighScore(regularHighScore);
        temp.updateSurvivalHighScore(survivalHighScore);
        temp.updateArcadeHighScore(arcadeHighScore);
        temp.updateRegularSave(regularMD, regularLvl, regularLines);
        temp.updateSurvivalSave(survivalMD, survivalScore);
        temp.updateArcadeSave(arcadeMD, arcadeScore);
        return temp;
    }
}
