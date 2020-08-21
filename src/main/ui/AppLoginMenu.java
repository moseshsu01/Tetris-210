package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppLoginMenu extends JFrame {
    private JPanel panelLoginMenu;
    private JButton buttonProfile;
    private JButton buttonPlay;
    private JButton buttonLogout;
    private Runner2 runner;
    private Player player;

    // MODIFIES: this
    public AppLoginMenu(Player p) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = p;
        runner = new Runner2(0);
        add(panelLoginMenu);
        setSize(400, 500);
        profileButton();
        logoutButton();
        buttonPlay.addActionListener(new ActionListener() {
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

    // EFFECTS: constructs a new AppMenu
    public void logoutButton() {
        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppMenu myMenu = new AppMenu();
                        myMenu.setVisible(true);
                    }
                });
            }
        });
    }

    // EFFECTS: constructs a new AppProfile
    public void profileButton() {
        buttonProfile.addActionListener(new ActionListener() {
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
