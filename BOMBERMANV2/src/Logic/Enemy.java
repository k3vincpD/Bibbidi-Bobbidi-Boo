package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Enemy extends GameObject {

    protected float moveSpeed;
    protected boolean dead;
    protected int spriteIndex;
    protected int spriteTimer;
    protected int direction;
    protected Random random = new Random();

    public Enemy(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        moveSpeed = 1.0f;
        direction = random.nextInt(4);
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.dead = false;
    }

    protected void updateCollider() {
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
    }

    protected void move() {
        switch (direction) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
        }

        float deltaX = 0, deltaY = 0;
        switch (direction) {
            case 0:
                deltaY = -moveSpeed;
                break;
            case 1:
                deltaY = moveSpeed;
                break;
            case 2:
                deltaX = -moveSpeed;
                break;
            case 3:
                deltaX = moveSpeed;
                break;
        }
    }

    protected float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    @Override
    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
        changeDirection();
    }

    public void handleCollision(Enemy collidingObj) {
        // Do nothing for collision with other enemies
    }

    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
            this.destroy();
        }
    }

    public void handleCollision(Bomb collidingObj) {
        this.solidCollision(collidingObj);
        changeDirection();
    }

    public void handleCollision(Bomber collidingObj) {
        collidingObj.handleCollision(this);
    }

    public void handleCollision(Powerup collidingObj) {
        collidingObj.destroy();
    }

    protected void changeDirection() {
        int newDirection = random.nextInt(4);
        while (newDirection == direction) {
            newDirection = random.nextInt(4);
        }
        direction = newDirection;
    }

    protected void moveUp() {
        position.setLocation(position.x, position.y - moveSpeed);
    }

    protected void moveDown() {
        position.setLocation(position.x, position.y + moveSpeed);
    }

    protected void moveLeft() {
        position.setLocation(position.x - moveSpeed, position.y);
    }

    protected void moveRight() {
        position.setLocation(position.x + moveSpeed, position.y);
    }

    public boolean isDead() {
        return this.dead;
    }
}
