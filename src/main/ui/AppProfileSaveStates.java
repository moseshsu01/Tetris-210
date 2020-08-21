package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppProfileSaveStates extends JFrame {
    private JButton regularSaveStateButton;
    private JButton survivalSaveStateButton;
    private JButton arcadeSaveStateButton;
    private JPanel panelProfileSaveStates;
    private JButton backButton;
    private Player player;

    // MODIFIES: this
    public AppProfileSaveStates(Player player) {
        this.player = player;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(panelProfileSaveStates);
        setSize(400, 500);
        back();
        arcadeSaveStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppSaveStateDisplay mySaveStateDisplay = new AppSaveStateDisplay(player, "Arcade");
                        mySaveStateDisplay.setVisible(true);
                    }
                });
            }
        });
        survivalOrRegular();
    }

    // EFFECTS: displays survival save data
    public void survivalSave() {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppSaveStateDisplay mySaveStateDisplay = new AppSaveStateDisplay(player, "Survival");
                mySaveStateDisplay.setVisible(true);
            }
        });
    }

    // EFFECTS: displays survival or regular save data
    public void survivalOrRegular() {
        survivalSaveStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                survivalSave();
            }
        });
        regularSaveStateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppSaveStateDisplay mySaveStateDisplay = new AppSaveStateDisplay(player, "Regular");
                        mySaveStateDisplay.setVisible(true);
                    }
                });
            }
        });
    }

    // EFFECTS: constructs a new AppProfile
    public void back() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppProfile myProfile = new AppProfile(player);
                        myProfile.setVisible(true);
                    }
                });
            }
        });
    }
}
