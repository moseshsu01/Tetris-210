package ui;

import model.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.List;

// allows user to create an account
public class AppCreateNewAccount extends JFrame {
    private JPanel panelCreateNewAccount;
    private JTextField textUsernameMessage;
    private JTextField textPasswordMessage;
    private JTextField textEnterPassword;
    private JTextField textEnterUsername;
    private JButton buttonCreateAccount;
    private JTextField textNameMessage;
    private JTextField textEnterName;
    private List<String> usernames;
    private Runner2 runner;

    // MODIFIES: this
    public AppCreateNewAccount() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        runner = new Runner2(0);
        add(panelCreateNewAccount);
        setSize(400, 500);

        textUsernameMessage.setEnabled(false);
        textPasswordMessage.setEnabled(false);
        textNameMessage.setEnabled(false);
        buttonCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates account with username, password, and name
    public void createAccount() {
        runner.loadPlayers();
        textEnterUsername.setForeground(Color.black);
        textEnterPassword.setForeground(Color.black);
        textEnterName.setForeground(Color.black);
        String username = textEnterUsername.getText();
        String password = textEnterPassword.getText();
        String name = textEnterName.getText();
        if (!error()) {
            Player player1 = new Player(username, password, name);
            runner.savePlayer(player1);
            runner.loadPlayers();
            dispose();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    AppMenu myMenu = new AppMenu();
                    myMenu.setVisible(true);
                }
            });
        }
    }

    // EFFECTS: returns true if the entered username, password, and name are not passable
    public Boolean error() {
        String username = textEnterUsername.getText();
        String password = textEnterPassword.getText();
        String name = textEnterName.getText();
        if (username.equals("")) {
            textEnterUsername.setText("Oops, you did not enter a username");
            textEnterUsername.setForeground(Color.red);
            return true;
        } else if (password.equals("")) {
            textEnterPassword.setText("Oops, you did not enter a password");
            textEnterPassword.setForeground(Color.red);
            return true;
        } else if (name.equals("")) {
            textEnterName.setText("Oops, you did not enter a screen name");
            textEnterName.setForeground(Color.red);
            return true;
        } else if (error2()) {
            return true;
        }
        return false;
    }

    // EFFECTS: returns true if there is a create account error other than leaving a text blank
    public Boolean error2() {
        if (!runner.goodUsername(textEnterUsername.getText())) {
            textEnterUsername.setText("Oops, the username you entered already exists");
            return true;
        } else if (!runner.goodName(textEnterName.getText())) {
            textEnterName.setText("Oops, the screen name you entered already exists");
            return true;
        } else if (containsComma(textEnterUsername.getText())) {
            textEnterUsername.setText("Commas are not allowed");
            return true;
        } else if (containsComma(textEnterPassword.getText())) {
            textEnterPassword.setText("Commas are not allowed");
            return true;
        } else if (containsComma(textEnterName.getText())) {
            textEnterName.setText("Commas are not allowed");
            return true;
        }
        return false;
    }

    // EFFECTS: returns true if given s contains a comma
    public Boolean containsComma(String s) {
        String comma = ",";
        return s.contains(comma);
    }
}
