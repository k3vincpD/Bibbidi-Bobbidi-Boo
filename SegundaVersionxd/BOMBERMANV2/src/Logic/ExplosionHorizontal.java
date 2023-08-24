package Logic;
import presentation.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class ExplosionHorizontal extends Explosion {

    /**
     * Constructs a horizontal explosionContact that varies in length depending on firepower and pierce.
     * @param position Coordinates of this object in the game world
     * @param firepower Strength of this explosionContact
     * @param pierce Whether or not this explosionContact will pierce soft walls
     */
    ExplosionHorizontal(Point2D.Float position, int firepower, boolean pierce) {
        super(position);

        float leftX = this.checkHorizontal(this.position, firepower, pierce, -32);
        float rightX = this.checkHorizontal(this.position, firepower, pierce, 32);
        this.centerOffset = position.x - leftX; // The offset is used to draw the center explosionContact sprite

        Rectangle2D.Float recH = new Rectangle2D.Float(leftX, this.position.y, rightX - leftX + 32, 32);
        this.init(recH);

        this.animation = this.drawSprite((int) this.width, (int) this.height);

        this.sprite = this.animation[0];
    }

    /**
     * Check for walls to determine explosionContact range. Used for left and right.
     * @param position Original position of bomb prior to explosionContact
     * @param firepower Maximum range of explosionContact
     * @param blockWidth Size of each game object tile, negative for left, positive for right
     * @return Position of the explosionContact's maximum range in horizontal direction
     */
    private float checkHorizontal(Point2D.Float position, int firepower, boolean pierce, int blockWidth) {
        float value = position.x;   // Start at the origin tile

        outer: for (int i = 1; i <= firepower; i++) {
            // Expand one tile at a time
            value += blockWidth;

            // Check this tile for wall collision
            for (int index = 0; index < GameObjectCollection.tileObjects.size(); index++) {
                TileObject obj = GameObjectCollection.tileObjects.get(index);
                if (obj.collider.contains(value, position.y)) {
                    if (!obj.isBreakable()) {
                        // Hard wall found, move value back to the tile before
                        value -= blockWidth;
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

    /**
     * Draws the explosionContact sprite after determining its length and center.
     * @param width Explosion width
     * @param height Explosion height
     * @return Array of sprites for animation
     */
    private BufferedImage[] drawSprite(int width, int height) {
        // Initialize each image in the array to be drawn to
        BufferedImage[] spriteAnimation = new BufferedImage[ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
        for (int i = 0; i < spriteAnimation.length; i++) {
            spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        // Draw to each image in the array
        for (int i = 0; i < spriteAnimation.length; i++) {
            Graphics2D g2 = spriteAnimation[i].createGraphics();
            g2.setColor(new Color(0, 0, 0, 0));
            g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

            for (int j = 0; j < spriteAnimation[i].getWidth() / 32; j++) {
                if (spriteAnimation[i].getWidth() / 32 == 1 || this.centerOffset == j * 32) {
                    // Center sprite
                    g2.drawImage(this.sprites[0][i], j * 32, 0, null);
                } else if (j == 0) {
                    // Leftmost sprite
                    g2.drawImage(this.sprites[3][i], j * 32, 0, null);
                } else if (j == (spriteAnimation[i].getWidth() / 32) - 1) {
                    // Rightmost sprite
                    g2.drawImage(this.sprites[4][i], j * 32, 0, null);
                } else {
                    // Horizontal between sprite
                    g2.drawImage(this.sprites[1][i], j * 32, 0, null);
                }
            }

            g2.dispose();
        }

        return spriteAnimation;
    }

}