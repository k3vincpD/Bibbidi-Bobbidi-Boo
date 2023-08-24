package Logic;

import presentation.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Vertical explosion class.
 */
public class ExplosionVertical extends Explosion {

    /**
     * Constructs a vertical explosion that varies in length depending on firepower and pierce.
     * @param position Coordinates of this object in the game world
     * @param firepower Strength of this explosion
     * @param pierce Whether or not this explosion will pierce soft walls
     */
    public ExplosionVertical(Point2D.Float position, int firepower, boolean pierce) {
        super(position);

        try {
            float topY = checkVertical(this.position, firepower, pierce, -32);
            float bottomY = checkVertical(this.position, firepower, pierce, 32);
            this.centerOffset = position.y - topY;
            Rectangle2D.Float recV = new Rectangle2D.Float(this.position.x, topY, 32, bottomY - topY + 32);
            init(recV);
            this.animation = drawSprite((int) this.width, (int) this.height);
            this.sprite = this.animation[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage[] drawSprite(int width, int height) {
        BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];

        try {
            for (int i = 0; i < spriteAnimation.length; i++) {
                spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }

            for (int i = 0; i < spriteAnimation.length; i++) {
                BufferedImage spriteImage = spriteAnimation[i];
                Graphics2D g2 = spriteImage.createGraphics();
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(0, 0, spriteImage.getWidth(), spriteImage.getHeight());

                for (int j = 0; j < spriteImage.getHeight() / 32; j++) {
                    if (spriteImage.getHeight() / 32 == 1 || centerOffset == j * 32) {
                        g2.drawImage(sprites[0][i], 0, j * 32, null);
                    } else if (j == 0) {
                        g2.drawImage(sprites[5][i], 0, j * 32, null);
                    } else if (j == (spriteImage.getHeight() / 32) - 1) {
                        g2.drawImage(sprites[6][i], 0, j * 32, null);
                    } else {
                        g2.drawImage(sprites[2][i], 0, j * 32, null);
                    }
                }

                g2.dispose();
            }
        } catch (Exception e) {
            // Handle sprite drawing exceptions gracefully, e.g., log the error
            e.printStackTrace();
        }

        return spriteAnimation;
    }

    /**
     * Check for walls to determine explosion range. Used for top and bottom.
     * @param position Original position of bomb prior to explosion
     * @param firepower Maximum range of explosion
     * @param pierce Whether the explosion can pierce through walls
     * @param blockHeight Size of each game object tile, negative for top, positive for bottom
     * @return Position of the explosion's maximum range in the vertical direction
     */
    private float checkVertical(Point2D.Float position, int firepower, boolean pierce, int blockHeight) {
        float value = position.y;

        outer: for (int i = 1; i <= firepower; i++) {
            value += blockHeight;

            // Check this tile for wall collision
            for (TileObject obj : GameObjectCollection.tileObjects) {
                if (obj.collider.contains(position.x, value)) {
                    if (!obj.isBreakable()) {
                        // Hard wall found, move value back to the tile before
                        value -= blockHeight;
                    }

                    // Stop checking for tile objects after the first breakable is found
                    if (!pierce) {
                        break outer;
                    }
                }
            }
        }

        return value;
    }
}
