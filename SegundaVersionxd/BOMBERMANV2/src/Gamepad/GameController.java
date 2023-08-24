package Gamepad;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Used to control the game
 */
class GameController implements KeyListener {

    private GamePanel gamePanel;

    /**
     * Construct a universal game controller key listener for the game.
     * @param gamePanel Attach game controller to this game panel
     */
    GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Key events for general game operations such as exit
     * @param e Keyboard key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Close game
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape key pressed: Closing game");
            this.gamePanel.exit();
        }

        // Display controls
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            System.out.println("F1 key pressed: Displaying help");

            String[] columnHeaders = { "", "White", "Black", "Red", "Blue" };
            Object[][] controls = {
                    {"Up", "Up", "W", "T", "I"},
                    {"Down", "Down", "S", "G", "K"},
                    {"Left", "Left", "A", "F", "J"},
                    {"Right", "Right", "D", "H", "L"},
                    {"Bomb", "b", "E", "Y", "O"},
                    {"", "", "", "", ""},
                    {"Help", "F2", "", "", ""},
                    {"Reset", "F5", "", "", ""},
                    {"Exit", "ESC", "", "", ""} };

            JTable controlsTable = new JTable(controls, columnHeaders);
            JTableHeader tableHeader = controlsTable.getTableHeader();

            // Wrap JTable inside JPanel to display
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(tableHeader, BorderLayout.NORTH);
            panel.add(controlsTable, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this.gamePanel, panel, "Controls", JOptionPane.PLAIN_MESSAGE);
        }

        // Reset game
        // Delay prevents resetting too fast which causes the game to crash
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (this.gamePanel.resetDelay >= 20) {
                System.out.println("F5 key pressed: Resetting game");
                this.gamePanel.resetGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
