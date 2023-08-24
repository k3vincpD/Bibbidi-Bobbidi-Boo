package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Enemy extends GameObject {

    protected float moveSpeed;

    public Enemy(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        moveSpeed = 1.0f;
    }

    public abstract void move();

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        this.solidCollision(collidingObj);
    }

    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    public void handleCollision(Enemy collidingObj) {
        // Do nothing for collision with other enemies
    }

    public void handleCollision(Explosion collidingObj) {
        // To be implemented by subclasses
    }

    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
    }

    public void handleCollision(Bomber collidingObj) {
        collidingObj.handleCollision(this);
    }

    public void handleCollision(Powerup collidingObj) {
        collidingObj.destroy();
    }


}
