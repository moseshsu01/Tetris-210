package ui;

import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppNewGameMenu extends JFrame {
    private JPanel panelNewGameMenu;
    private JButton buttonRegular;
    private JButton buttonSurvival;
    private JButton buttonArcade;
    private JButton buttonBack;
    private Runner2 runner;
    private Player player;

    // MODIFIES: this
    public AppNewGameMenu(Player p) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = p;
        runner = new Runner2(0);
        add(panelNewGameMenu);
        setSize(400, 500);
        newGame();
        buttonBack.addActionListener(new ActionListener() {
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

    // EFFECTS: starts a new game with given state
    public void newGame() {
        buttonRegular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TetrisApp app = new TetrisApp(player, "Regular", false);
            }
        });
        buttonSurvival.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TetrisApp app = new TetrisApp(player, "Survival", false);
            }
        });
        buttonArcade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TetrisApp app = new TetrisApp(player, "Arcade", false);
            }
        });
    }
}
