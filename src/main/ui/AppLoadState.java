package ui;

import model.Game;
import model.Player;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppLoadState extends JFrame {
    private JButton buttonRegularSaveState;
    private JButton buttonSurvivalSaveState;
    private JButton buttonArcadeSaveState;
    private JButton buttonQuit;
    private JPanel panelLoadState;
    private Runner2 runner;
    private Player player;

    // MODIFIES: this
    public AppLoadState(Player p) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = p;
        runner = new Runner2(0);
        add(panelLoadState);
        setSize(400, 500);
        quitButton();
        regularSaveButton();
        buttonSurvivalSaveState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                survivalSaveState();
            }
        });
        buttonArcadeSaveState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arcadeSaveState();
            }
        });
    }

    // EFFECTS: creates a new AppPlayMenu
    public void quitButton() {
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppPlayMenu myPlayMenu = new AppPlayMenu(player);
                        myPlayMenu.setVisible(true);
                    }
                });
            }
        });
    }

    public void regularSaveButton() {
        buttonRegularSaveState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "Oops, it looks like you don't have a save state.";
                if (player.getRegularMatrixData().equals("")) {
                    JOptionPane.showMessageDialog(null, s, "Oops!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            dispose();
                            TetrisApp app = new TetrisApp(player, "Regular", true);
                        }
                    });
                }
            }
        });
    }

    // EFFECTS: loads the player's survival save state
    public void survivalSaveState() {
        String s = "Oops, it looks like you don't have a save state.";
        if (player.getSurvivalMatrixData().equals("")) {
            JOptionPane.showMessageDialog(null, s, "Oops!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    TetrisApp app = new TetrisApp(player, "Survival", true);
                }
            });
        }
    }

    // EFFECTS: loads the player's arcade save state
    public void arcadeSaveState() {
        String s = "Oops, it looks like you don't have a save state.";
        if (player.getArcadeMatrixData().equals("")) {
            JOptionPane.showMessageDialog(null, s, "Oops!", JOptionPane.INFORMATION_MESSAGE);
        } else {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                    TetrisApp app = new TetrisApp(player, "Arcade", true);
                }
            });
        }
    }
}
