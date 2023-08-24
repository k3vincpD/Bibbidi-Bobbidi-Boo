package Gamepad;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameController implements KeyListener {

    private GamePanel gamePanel;
    GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        handleKeyInput(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void handleKeyInput(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE:
                System.out.println("Escape key pressed: Closing game");
                gamePanel.exit();
                break;
            case KeyEvent.VK_F1:
                System.out.println("F1 key pressed: Displaying help");
                displayControlsDialog();
                break;
            case KeyEvent.VK_F5:
                if (gamePanel.resetDelay >= 20) {
                    System.out.println("F5 key pressed: Resetting game");
                    gamePanel.resetGame();
                }
                break;
        }
    }

    private void displayControlsDialog() {
        String[] columnHeaders = {"", "White", "Black", "Red", "Blue"};
        Object[][] controls = {
                {"Up", "Up", "W", "T", "I"},
                {"Down", "Down", "S", "G", "K"},
                {"Left", "Left", "A", "F", "J"},
                {"Right", "Right", "D", "H", "L"},
                {"Bomb", "b", "E", "Y", "O"},
                {"", "", "", "", ""},
                {"Help", "F2", "", "", ""},
                {"Reset", "F5", "", "", ""},
                {"Exit", "ESC", "", "", ""}};

        JTable controlsTable = new JTable(controls, columnHeaders);
        JTableHeader tableHeader = controlsTable.getTableHeader();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(tableHeader, BorderLayout.NORTH);
        panel.add(controlsTable, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(gamePanel, panel, "Controls", JOptionPane.PLAIN_MESSAGE);
    }
}
