package Gamepad;

import presentation.ResourceCollection;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameWindow extends JFrame {

    /**
     * Screen width and height is determined by the map size. Map size is set when loading the map in
     * the GamePanel class. For best results, do not use a map that is smaller than the default map
     * provided in resources.
     */

    static final int HUD_HEIGHT = 48;   // Size of the HUD. The HUD displays score.
    static final String TITLE = "Bomberman by Team CopyPaste";

    /**
     * Constructs a game window with the necessary configurations.
     * @param game Game panel that will be contained inside the game window
     */
    GameWindow(GamePanel game) {
        this.setTitle(TITLE);
        this.setIconImage(ResourceCollection.Images.ICON.getImage());
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Called every second. Updates the FPS and Ticks counters and prints them to the console with the current time.
     * @param fps FPS counter
     * @param ticks Ticks counter
     */
    public void update(int fps, int ticks) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        System.out.println("[" + dtf.format(time) + "]" + " FPS: " + fps + ", Ticks: " + ticks);
        GameLauncher.window.setTitle(GameWindow.TITLE);
    }

}