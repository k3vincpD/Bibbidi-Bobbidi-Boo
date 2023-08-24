package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ballom extends Enemy {

    private BufferedImage[][] sprites;
    private int direction;
    private boolean dead;
    private int spriteTimer;
    private int spriteIndex;
    private Random random = new Random();

    public Ballom(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        this.direction = random.nextInt(4);
        this.sprites = spriteMap;
        this.moveSpeed = 1.0F;
        this.dead = false;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
    }

    @Override
    public void update() {
        updateCollider();

        if (!this.dead) {
            move();
            updateSprite();
        } else {
            updateDeadSprite();
        }
    }

    @Override
    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
            this.destroy();
        }
    }

    @Override
    public void handleCollision(Wall collidingObj) {
        super.handleCollision(collidingObj);
        changeDirection();
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        super.handleCollision(collidingObj);
        changeDirection();
    }

    private void updateCollider() {
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
    }

    private void updateSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex = (spriteIndex + 1) % sprites[direction].length;
        }
        this.sprite = sprites[direction][spriteIndex];
    }

    private void updateDeadSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex++;
            if (spriteIndex >= sprites[3].length) {
                this.destroy();
            }
        }
        this.sprite = sprites[3][spriteIndex];
    }

    private void changeDirection() {
        int newDirection = random.nextInt(4);
        while (newDirection == direction) {
            newDirection = random.nextInt(4);
        }
        direction = newDirection;
    }

    @Override
    public void move() {
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
    }

    private void moveUp() {
        position.setLocation(position.x, position.y - moveSpeed);
    }

    private void moveDown() {
        position.setLocation(position.x, position.y + moveSpeed);
    }

    private void moveLeft() {
        position.setLocation(position.x - moveSpeed, position.y);
    }

    private void moveRight() {
        position.setLocation(position.x + moveSpeed, position.y);
    }
}
