package Logic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Enemy extends GameObject {
    protected boolean dead;
    protected boolean kill;
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private float moveSpeed;

    public Enemy(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        this.dead = false;
        this.kill = false;
        this.moveSpeed = 1.0F; // Be consistent with the float type
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this); // Corrected variable name
    }

    public abstract void move();

    public void handleCollision(Wall collidingObj) {
        solidCollision(collidingObj);
    }

    public void handleCollision(Explosion collidingObj) {
        if (!isDead()) {
            dead = true;
            spriteIndex = 0;
            this.destroy();
        }
    }

    public void handleCollision(Bomb collidingObj) {
        solidCollision(collidingObj);
    }

    public void handleCollision(Bomber collidingObj) {
        collidingObj.destroy();
    }
    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }


}
