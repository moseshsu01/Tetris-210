package ui;

import model.Player;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

// the runner that runs the game
public class Runner2 {

    private static final String PLAYERS_FILE = "./data/players.txt";
    private List<Player> players;

    public Runner2(int difference) {
        // for creating a runner withing calling runGame but just accessing methods
    }

    public Runner2() {
        runGame();
    }

    // EFFECTS: runs the game, starting with the option screen
    private void runGame() {
        loadPlayers();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppMenu myMenu = new AppMenu();
                myMenu.setVisible(true);
            }
        });
    }

    // EFFECTS: returns true if username does not already exist
    public Boolean goodUsername(String username) {
        for (String i: getUsernames()) {
            if (username.equals(i)) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: return true if given name is not found in players.txt
    public Boolean goodName(String name) {
        for (String i: getNames()) {
            if (name.equals(i)) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns player with given username
    public Player getPlayerWithUsername(String username) {
        Player p = null;
        for (Player i: players) {
            if (i.getUsername().equals(username)) {
                p = i;
            }
        }
        return p;
    }

    // EFFECTS: returns true if username is found in players.txt
    public Boolean usernameExists(String username) {
        for (String i: getUsernames()) {
            if (username.equals(i)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if password matches username
    public Boolean goodPassword(String username, String password) {
        if (password.equals(getPlayerWithUsername(username).getPassword())) {
            return true;
        }
        return false;
    }

    // EFFECTS: terminates the program by stopping to run the game
    public void quitGame() {
        System.exit(0);
    }

    // EFFECTS: saves player data to PLAYERS_FILE
    public void savePlayer(Player player1) {
        try {
            Writer writer = new Writer(new File(PLAYERS_FILE));
            for (Player p: players) {
                if (!p.getUsername().equals(player1.getUsername())) {
                    writer.write(p);
                    writer.write(System.getProperty("line.separator"));
                }
            }
            writer.write(player1);
            writer.close();
            //System.out.println("Player saved to file " + PLAYERS_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save player to " + PLAYERS_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes players
    private void init() {
        players = new ArrayList<Player>();
    }


    // MODIFIES: this
    // EFFECTS: loads accounts from PLAYERS_FILE, if that file exists;
    // otherwise initializes accounts with default values
    public void loadPlayers() {
        try {
            players = Reader.readPlayers(new File(PLAYERS_FILE));
        } catch (IOException e) {
            System.out.println("BIG TIME ERROR");
            init();
        }
    }

    //EFFECTS: returns a list of all usernames from all players stored
    private List<String> getUsernames() {
        ArrayList<String> usernames = new ArrayList();
        for (Player p: players) {
            usernames.add(p.getUsername());
        }
        return usernames;
    }

    //EFFECTS: returns a list of all screen names from all players stored
    private List<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Player p: players) {
            names.add(p.getUsername());
        }
        return names;
    }

    // REQUIRES: players have the required matrix data saved in players.txt
    // EFFECTS: returns appropriate matrix data of given player
    public String getMatrixData(Player p, String gameMode) {
        Player temp = null;
        for (Player p1: players) {
            if (p1.getUsername().equals(p.getUsername())) {
                temp = p1;
            }
        }
        switch (gameMode) {
            case "Regular":
                assert temp != null;
                return temp.getRegularMatrixData();
            case "Survival":
                assert temp != null;
                return temp.getSurvivalMatrixData();
            case "Arcade":
                assert temp != null;
                return temp.getArcadeMatrixData();
        }
        assert temp != null;
        return temp.getArcadeMatrixData(); // should not get here
    }

}

