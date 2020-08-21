package ui;

import model.Game;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;

public class TetrisApp extends JFrame {
    private Game game;
    private TetrisGameRenderer renderer;
    private static final int WIDTH_MATRIX = 10;
    private static final int HEIGHT_MATRIX = 20;
    private static final int INTERVAL = 1000;
    private static final int PIXELS = 30;
    private static final int SPACE_UP = 30 * 3;
    private static final int SPACE_RIGHT = 30 * 7;
    private static final int SPACE_LEFT = 30 * 7;
    private static final int SPACE_DOWN = 30 * 2;
    private Boolean saved = false;
    private BufferedImage bf;
    private Player player;

    // MODIFIES: this
    // EFFECTS: sets up window in which tetris game will be played
    public TetrisApp(Player p, String gameMode, Boolean saveState) {
        super("Tetris");
        player = p;
        int width = 720; // WIDTH_MATRIX * PIXELS + SPACE_LEFT + SPACE_RIGHT;
        int height = 780; // HEIGHT_MATRIX * PIXELS + 30 + SPACE_UP + SPACE_DOWN
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game = new Game(p, gameMode, saveState);
        renderer = new TetrisGameRenderer(game);
        // Keyboard controls
        this.addKeyListener(new KeyHandler());
        centreOnScreen();
        this.addTimer();
        this.setVisible(true);
        bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    // MODIFIES: this
    // EFFECTS:  frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: Represents a key handler, responds to keyboard events
    private class KeyHandler extends KeyAdapter {
        @Override
        // MODIFIES: this
        // EFFECTS:  updates game in response to a keyboard event
        public void keyPressed(KeyEvent e) {
            if (!game.isOver() && !game.isPaused()) {
                pressKey(e);
            } else {
                if (!game.isOver()) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        game.changePause();
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_S) {
                        game.saveState();
                        saved = true;
                        repaint(); // draw saved
                    } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                        quitGame();
                    }
                }
            }
        }
    }

    // EFFECTS: disposes the current game, opens up new game menu
    public void quitGame() {
        game.saveState();
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppNewGameMenu myNewGameMenu = new AppNewGameMenu(player);
                myNewGameMenu.setVisible(true);
            }
        });
    }

    // MODIFIES: game
    // EFFECTS: updates game state based on key pressed
    public void pressKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            game.rotateRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            game.move(-1);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            game.move(+1);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.hardDrop();
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            game.hold();
        } else if (e.getKeyCode() == KeyEvent.VK_Z) {
            game.rotateLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            game.changePause();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            game.softDrop();
        }
        game.updateMatrix();
        repaint();
    }

    @Override
    // MODIFIES: graphics
    // EFFECTS:  clears screen and paints game onto graphics
    public void paint(Graphics g) {
        if (game.isPaused()) {
            renderer.drawGamePaused(g);
            if (saved) {
                renderer.drawSaved(g);
                saved = false;
            }
        } else {
            if (game.isOver()) {
                renderer.draw(bf.getGraphics()); //bf is the BufferedImage object
                g.drawImage(bf,0,0,null);
                renderer.drawGameOver(g);
            } else {
                renderer.draw(bf.getGraphics()); //bf is the BufferedImage object
                g.drawImage(bf,0,0,null);
            }
        }
    }

    // EFFECTS: initializes a timer that updates game each
    //          INTERVAL milliseconds
    private void addTimer() {
        final Timer t = new Timer(INTERVAL, null);
        t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!game.isPaused()) {
                    if (game.isOver()) {
                        repaint(); // paint game over
                        t.stop();
                    } else {
                        updateTimer(t);
                    }
                } else {
                    repaint(); // paint game paused
                }
            }
        });
        t.start();
    }

    // MODIFIES: t, game
    // EFFECTS: updates timer based on game state
    public void updateTimer(Timer t) {
        if (!game.getGameMode().equals("Regular")) { // if game mode is survival or arcade
            if (game.getCurrentItem() != null && game.getCurrentItem() == Game.Item.SPEEDIMINO) {
                t.setDelay(70);                // if SPEEDIMINO IS ACTIVE
            } else {
                if (game.getGameMode().equals("Survival")) {
                    t.setDelay(2000);
                    game.addScore(2); // how many seconds
                } else {                // gameMode must be arcade
                    t.setDelay(500);
                    game.addScore(1); // how many half seconds haha
                }
            }
        } else {
            t.setDelay(INTERVAL - (55 * game.getLevel()) - 50);
        }
        game.update();
        repaint();
    }
}