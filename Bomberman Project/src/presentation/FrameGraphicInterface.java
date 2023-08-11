package presentation;

import logic.Player;
import logic.Map;
import logic.Area;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Specialization of InterfazGrafica that handles drawing the player's current score, highscore, lives, and text on the screen.
 */
public class FrameGraphicInterface extends GraphicInterface {
    private Player player;
    private Map map;
    private BufferedImage bombermanSkinImage;
    private Font bombermanFont;

    public FrameGraphicInterface() {
        super();
        loadImages();
        loadFont();
    }

    private void loadImages() {
        try {
            bombermanSkinImage = ImageIO.read(getClass().getResourceAsStream("/res/pacman/pacman_right_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFont() {
        try {
            bombermanFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/BOMBERMA.TTF"));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawSprite(Graphics2D g2) {
        g2.setFont(bombermanFont.deriveFont(18f));
        g2.setColor(Color.white);
        g2.drawString(String.valueOf(player.score), 12, 40);
        g2.drawString("SCHOLARSHIP NOTE", ((map.getColumns() - 6) * 24) / 2, 20);
        g2.drawString(String.valueOf(player.highScore), ((map.getColumns() - 2) * 24) / 2, 40);
        if (player.isWaiting() && player.getLives() > 0) {
            g2.setColor(Color.YELLOW);
            g2.drawString("SEMESTER STARTS", map.getReadyMessageCol() - 94, map.getReadyMessageRow());
        } else if (player.getLives() == 0) {
            g2.setColor(Color.RED);
            g2.drawString("YOU FAILED THE SEMESTER", map.getReadyMessageCol() - 94, map.getReadyMessageRow());
        } else if (player.ateAllPacDots()) {
            g2.setColor(Color.pink);
            g2.drawString("PASSED THE SEMESTER", map.getReadyMessageCol() - 86, map.getReadyMessageRow());
        } else if (player.isPaused() && !player.isWaiting()) {
            g2.setColor(Color.GRAY);
            g2.drawString("TOOK A BREAK", map.getReadyMessageCol() - 60, map.getReadyMessageRow());
        }
        for (int i = 1; i < player.getLives(); i++) {
            g2.drawImage(bombermanSkinImage, 24 * i, (map.getRows() * 24) - 24, null);
        }
    }

    @Override
    public void setArea(Area area) {
        this.area = area;
        player = area.getPlayer();
        character = player;
        map = area.getMap();
    }
}