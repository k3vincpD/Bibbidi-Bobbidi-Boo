package presentation;

import logic.Area;
import logic.Bomberman;
import logic.Difficulty;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Bomberman bomberman = new Bomberman(
                new Area(Difficulty.EASY, "/res/mapa3.txt"),
                new Area(Difficulty.EASY, "/res/mapa2.txt"),
                new Area(Difficulty.INTERMEDIATE, "/res/mapa1.txt")
//                new Area(Difficulty.INTERMEDIATE, "/res/mapa4.txt"),
//                new Area(Difficulty.HARD, "/res/mapa5.txt"),
//                new Area(Difficulty.HARD, "/res/mapa6.txt")
        );
        GamePanel menu = new GamePanel(bomberman, new Dimension(716, 440));
    }
}