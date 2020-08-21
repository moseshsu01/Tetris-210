package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppPlayMenu extends JFrame {
    private JButton buttonLoadSaveState;
    private JButton buttonNewGame;
    private JButton buttonQuit;
    private JPanel panelPlayMenu;
    private Runner2 runner;
    private Player player;

    public AppPlayMenu(Player p) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = p;
        runner = new Runner2(0);
        add(panelPlayMenu);
        setSize(400, 500);
        buttonQuit.addActionListener(new ActionListener() {
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
        play();
    }

    // EFFECTS: allows the player to play a game by loading or starting a new game
    public void play() {
        buttonLoadSaveState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppNewGameMenu myNewGameMenu = new AppNewGameMenu(player);
                        myNewGameMenu.setVisible(true);
                    }
                });
            }
        });
    }

    // EFFECTS: constructs a new AppLoadState
    public void load() {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppLoadState myLoadState = new AppLoadState(player);
                myLoadState.setVisible(true);
            }
        });
    }

}
