package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppSaveStateDisplay extends JFrame {
    private JPanel panelSaveStateDisplay;
    private JTextArea textSaveState;
    private JButton backButton;
    private Player player;
    private String gameMode;

    // MODIFIES: this
    public AppSaveStateDisplay(Player player, String gameMode) {
        this.player = player;
        this.gameMode = gameMode;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelSaveStateDisplay);
        setSize(400, 500);
        displaySave();
        textSaveState.setEnabled(false);
        backButton.addActionListener(new ActionListener() {
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

    // MODIFIES: textSaveState
    // EFFECTS: displays the save data
    public void displaySave() {
        if (gameMode.equals("Regular")) {
            textSaveState.append("\nLines: " + player.getRegularLines());
            textSaveState.append("\nLevel: " + player.getRegularLevel() % 100);
            textSaveState.append("\nScore: " + (player.getRegularLevel() - (player.getRegularLevel() % 100)));
        } else if (gameMode.equals("Survival")) {
            textSaveState.append("\nScore: " + player.getSurvivalScore());
        } else {
            textSaveState.append("\nScore: " + player.getArcadeScore());
        }
    }
}
