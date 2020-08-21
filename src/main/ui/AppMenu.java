package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppMenu extends JFrame {

    private JButton buttonCreateNewAccount;
    private JPanel panelMenu;
    private JButton buttonLogin;
    private JButton buttonQuit;
    private Runner2 runner;

    // MODIFIES: this
    public AppMenu() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        runner = new Runner2(0);
        add(panelMenu);
        setTitle("Welcome to Tetris 210!");
        setSize(400, 500);
        createNewAccountOrQuit();
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppLogin myLogin = new AppLogin();
                        myLogin.setVisible(true);
                    }
                });
            }
        });
    }

    // EFFECTS: allows user to create new account or quit
    public void createNewAccountOrQuit() {
        buttonCreateNewAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AppCreateNewAccount myCreateNewAccount = new AppCreateNewAccount();
                        myCreateNewAccount.setVisible(true);
                    }
                });
            }
        });
        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                runner.quitGame();
            }
        });
    }

}
