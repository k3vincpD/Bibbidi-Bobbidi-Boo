package Gamepad;

import presentation.*;


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
            game = new GamePanel(null); //poner un mapo
        }

        game.init();
        window = new GameWindow(game);

        System.gc();
    }

}


