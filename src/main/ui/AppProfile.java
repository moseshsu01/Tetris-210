package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppProfile extends JFrame {

    private JPanel panelProfile;
    private JTextArea textProfile;
    private JButton buttonBack;
    private JButton buttonViewSaveStates;
    private Runner2 runner;
    private Player player;

    // MODIFIES: this
    public AppProfile(Player p) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = p;
        runner = new Runner2(0);
        add(panelProfile);
        setSize(400, 500);
        constructor();
        buttonViewSaveStates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppProfileSaveStates myProfileSaveStates = new AppProfileSaveStates(player);
                        myProfileSaveStates.setVisible(true);
                    }
                });
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: acts as a conponent of the constructor
    public void constructor() {
        displayProfile();
        textProfile.setEnabled(false);
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppLoginMenu myLoginMenu = new AppLoginMenu(player);
                        myLoginMenu.setVisible(true);
                    }
                });
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: displays player profile data
    private void displayProfile() {
        textProfile.append(player.getName());
        textProfile.append("\nRegular high score: " + player.getRegularHighScore());
        textProfile.append("\nSurvival high score: " + player.getSurvivalHighScore());
        textProfile.append("\nArcade high score: " + player.getArcadeHighScore());
        textProfile.setEnabled(false);
    }
}
