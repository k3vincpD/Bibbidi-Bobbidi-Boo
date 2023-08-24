package Logic;

import presentation.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Base class for two types of explosions: horizontal and vertical.
 */
public abstract class Explosion extends GameObject {

    // --- BASE CLASS ---

    protected BufferedImage[][] sprites;
    protected BufferedImage[] animation;
    protected float centerOffset;
    private int spriteIndex;
    private int spriteTimer;

    /**
     * Constructor called in horizontal and vertical constructors.
     * @param position Coordinates of this object in the game world
     */
    Explosion(Point2D.Float position) {
        super(position);
        this.sprites = ResourceCollection.SpriteMaps.EXPLOSION_SPRITEMAP.getSprites();
        this.centerOffset = 0;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
    }

    /**
     * Called later in the constructor to set collider.
     * @param collider Collider for this to be set to
     */
    protected void init(Rectangle2D.Float collider) {
        this.collider = collider;
        this.width = this.collider.width;
        this.height = this.collider.height;
        this.sprite = new BufferedImage((int) this.width, (int) this.height, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Controls animation and destroy when it finishes
     */
    @Override
    public void update() {
        // Animate sprite
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.animation.length) {
            this.destroy();
        } else {
            this.sprite = this.animation[this.spriteIndex];
        }
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    /**
     * Draw based on the collider's position instead of this object's own position.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.collider.x, this.collider.y);
        rotation.rotate(Math.toRadians(this.rotation), this.collider.width / 2.0, this.collider.height / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

}