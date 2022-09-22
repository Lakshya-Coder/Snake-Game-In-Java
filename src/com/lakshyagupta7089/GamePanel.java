package com.lakshyagupta7089;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    JFrame frame = new JFrame();

    static final int SCREEN_WIDTH = 850;
    static final int SCREEN_HEIGHT = 850;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int DELAY = 50;
    static final int[] x = new int[GAME_UNITS];
    static final int[] y = new int[GAME_UNITS];

    private boolean isPrevXBelowZero = false, isPrevYBelowZero = false;

    String pathString = "assets/images/right.png";

    ImageIcon appleIcon = new ImageIcon("assets/images/apple.png");
    ImageIcon headIcon = new ImageIcon("assets/images/right.png");
    ImageIcon bodyIcon = new ImageIcon("assets/images/body.png");
    int bodyParts = 3;
    int applesEaten = 0;

    int appleX;
    int appleY;

    char direction = 'R';
    boolean running = true;

    private Timer timer;
    private final Random random;

    GamePanel(String name, int x) {
        DELAY = x;

        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        frame.setTitle(name + " Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        startGame();
    }

    public void startGame() {
        newApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.drawImage(appleIcon.getImage(), appleX, appleY, null);// body.png

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.drawImage(headIcon.getImage(), x[i], y[i], null);
                } else {
                    g.drawImage(bodyIcon.getImage(), x[i], y[i], null);
                }
            }

            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));

            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }

        if (x[0] < 0) {
            x[0] = SCREEN_WIDTH;
            isPrevXBelowZero = true;
        }

        if (x[0] >= SCREEN_WIDTH && !isPrevXBelowZero) {
            x[0] = 0;
        }

        if (y[0] < 0) {
            y[0] = SCREEN_HEIGHT;
            isPrevYBelowZero = true;
        }

        if (y[0] >= SCREEN_HEIGHT && !isPrevYBelowZero) {
            y[0] = 0;
        }

        isPrevXBelowZero = isPrevYBelowZero = false;

        if (!running) {
            timer.stop();
        }


    }

    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));

        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        g.drawString("Score is: " + applesEaten, 220, 530);

        g.setFont(new Font("Ink Free", Font.BOLD, 20));

        g.drawString("Press space for reset the game!", 220, 460);
    }

    public void check() {
        String list[] = { "Yes", "No" };

        int x = JOptionPane.showOptionDialog(null, "Would you like to play more?", "Message",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, list, 0);

        if (x == 0) {
            restartGame();
            frame.dispose();
            timer.stop();
            new GameFrame();
        } else {
            if (!running)
                System.exit(0);
        }
    }

    public void restartGame() {
        if (!running) {
            for (int i = 0; i < x.length; i++) {
                x[i] = 0;
                y[i] = 0;
            }

            running = true;
            direction = 'R';
            bodyParts = 3;
            applesEaten = 0;
            move();
            checkApple();
            checkCollisions();
            timer.start();
            repaint();
            headIcon = new ImageIcon(pathString);
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
            repaint();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        headIcon = new ImageIcon("assets/images/left.png");
                        direction = 'L';
                    }

                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        headIcon = new ImageIcon("assets/images/right.png");
                        direction = 'R';
                    }

                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                        headIcon = new ImageIcon("assets/images/up.png");
                    }

                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        headIcon = new ImageIcon("assets/images/down.png");
                        direction = 'D';
                    }

                    break;
                case KeyEvent.VK_SPACE:
                    if (!running)
                        check();
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
    }
}
