package presentation;

import logic.Area;
import logic.Character;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class responsible for drawing various graphical aspects of a level on the screen.
 * It has several specializations.
 */

public abstract class GraphicInterface {

    protected Area area;
    protected BufferedImage[] sprites, activeSprite;
    private int spriteNumber;
    protected Character character;

    public GraphicInterface() {
        sprites = new BufferedImage[8];
    }

    /**
     * Method that processes the logic of characters and then displays a graphical representation of them.
     */
    public void drawSprite(Graphics2D g2) {
        BufferedImage image = sprites[6];
        BufferedImage[] sprites = this.activeSprite;
        spriteNumber = this.character.spriteNumber;
        switch (this.character.getImageDirection()) {
            case "up":
                if (spriteNumber == 1) {
                    image = sprites[0];
                }
                if (spriteNumber == 2) {
                    image = sprites[1];
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = sprites[2];
                }
                if (spriteNumber == 2) {
                    image = sprites[3];
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = sprites[4];
                }
                if (spriteNumber == 2) {
                    image = sprites[5];
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = sprites[6];
                }
                if (spriteNumber == 2) {
                    image = sprites[7];
                }
                break;
        }
        g2.drawImage(image, this.character.getPositionX(), this.character.getPositionY(), area.getEntitySize(), area.getEntitySize(), null);
    }

    /**
     * This method is used to obtain an image (.jpg) with which the characters will be represented in the draw method.
     */
    public void getSprite(String imageDirection, BufferedImage[] sprites) {
        try {
            sprites[0] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_arriba_1.png"));
            sprites[1] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_arriba_2.png"));
            sprites[2] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_abajo_1.png"));
            sprites[3] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_abajo_2.png"));
            sprites[4] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_izquierda_1.png"));
            sprites[5] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_izquierda_2.png"));
            sprites[6] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_derecha_1.png"));
            sprites[7] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDirection + "/" + imageDirection + "_derecha_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void setArea(Area area);
}
