package Gamepad;

import Logic.*;

import presentation.*;
import Gamepad.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains the main method to launch the game.
 */
public class GameLauncher {

    // The one and only window for the game to run
    public static GameWindow window;

    public static void main(String[] args) {
        ResourceCollection.readFiles();
        ResourceCollection.init();

        GamePanel game;
        try {
            game = new GamePanel(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e + ": Program args not given");
            game = new GamePanel(null);
        }

        game.init();
        window = new GameWindow(game);

        System.gc();
    }

}


