package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame();

        window.setResizable(false);
        window.setTitle("One Man Army");
        window.setUndecorated(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();
    }
}

