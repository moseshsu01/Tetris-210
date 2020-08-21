package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AppLogin extends JFrame {
    private JPanel panelLogin;
    private JTextField textUsernameMessage;
    private JTextField textEnterUsername;
    private JTextField textPasswordMessage;
    private JTextField textEnterPassword;
    private JButton buttonLogin;
    private Runner2 runner;

    // MODIFIES: this
    public AppLogin() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        runner = new Runner2(0);
        add(panelLogin);
        setSize(400, 500);
        textUsernameMessage.setText("Please enter your username below");
        textUsernameMessage.setEnabled(false);
        textPasswordMessage.setText("Please enter you password below");
        textPasswordMessage.setEnabled(false);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runner.loadPlayers();
                textEnterUsername.setForeground(Color.black);
                textEnterPassword.setForeground(Color.black);
                String username = textEnterUsername.getText();
                String password = textEnterPassword.getText();
                error(username, password);
            }
        });
        retry();
    }

    // MODIFIES: this
    // EFFECTS: allows the user to retry to login
    public void retry() {
        textEnterPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textEnterPassword.setForeground(Color.black);
            }
        });
        textEnterUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textEnterUsername.setForeground(Color.black);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: attempts to log the player in
    public void error(String username, String password) {
        if (username.equals("")) {
            textEnterUsername.setText("Oops, you did not enter a username");
            textEnterUsername.setForeground(Color.red);
        } else if (password.equals("")) {
            textEnterPassword.setText("Oops, you did not enter a password");
            textEnterPassword.setForeground(Color.red);
        } else if (!runner.usernameExists(textEnterUsername.getText())) {
            textEnterUsername.setText("Oops, the username you entered does not exists");
            textEnterUsername.setForeground(Color.red);
        } else if (!runner.goodPassword(textEnterUsername.getText(), textEnterPassword.getText())) {
            textEnterPassword.setText("Oops, wrong password");
            textEnterPassword.setForeground(Color.red);
        } else {
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    AppLoginMenu myLoginMenu = new AppLoginMenu(runner.getPlayerWithUsername(username));
                    myLoginMenu.setVisible(true);
                }
            });
        }
    }
}
