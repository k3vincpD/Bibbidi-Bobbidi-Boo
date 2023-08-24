package Logic;

import presentation.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
public class Ballom extends Enemy{
    private BufferedImage[][] sprites;
    private int direction;
    private double moveSpeed;


    public Ballom(Point2D.Float position, BufferedImage[][] sprite) {
        super(position, sprite[1][0]);
        this.direction = 1;
        this.moveSpeed = 1.0F;
    }

    @Override
    public void update() {
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        move();
    }

    @Override
    public boolean isDead() {
        return super.isDead();
    }

    @Override
    public void onCollisionEnter(GameObject var1) {
        super.onCollisionEnter(var1);
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        move();
    }

    @Override
    public void handleCollision(Bomber collidingObj) {
        super.handleCollision(collidingObj);
    }

    @Override
    public void move() {
        direction = (int) (Math.random() * 4);
        switch (direction) {
            case 0: // Up
                moveUp();
                break;
            case 1: // Down
                moveDown();
                break;
            case 2: // Left
                moveLeft();
                break;
            case 3: // Right
                moveRight();
                break;
        }
    }


    private void moveLeft() {
        this.direction = 2;
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }
    private void moveDown() {
        this.direction = 1;
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }
    private void moveUp() {
        this.direction = 0;
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }
    private void moveRight() {
        this.direction = 3;
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

}




