package Logic;
import presentation.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Bomber extends Player {
    private Bomb bomb;
    private boolean dead;
    private BufferedImage[][] sprites;
    private int direction;
    private int spriteIndex;
    private int spriteTimer;
    private float moveSpeed;
    private int firepower;
    private int maxBombs;
    private int bombAmmo;
    private int bombTimer;
    private boolean pierce;
    private boolean kick;

    public Bomber(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        this.sprites = spriteMap;
        this.direction = 1;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.moveSpeed = 1.0F;
        this.firepower = 1;
        this.maxBombs = 1;
        this.bombAmmo = this.maxBombs;
        this.bombTimer = 250;
        this.pierce = false;
        this.kick = false;
    }

    private void moveUp() {
        this.direction = 0;
        this.position.setLocation(this.position.x, this.position.y - this.moveSpeed);
    }

    private void moveDown() {
        this.direction = 1;
        this.position.setLocation(this.position.x, this.position.y + this.moveSpeed);
    }

    private void moveLeft() {
        this.direction = 2;
        this.position.setLocation(this.position.x - this.moveSpeed, this.position.y);
    }

    private void moveRight() {
        this.direction = 3;
        this.position.setLocation(this.position.x + this.moveSpeed, this.position.y);
    }

    private void plantBomb() {
        float x = (float)(Math.round(this.position.getX() / 32.0) * 32L);
        float y = (float)(Math.round((this.position.getY() + 16.0) / 32.0) * 32L);
        Point2D.Float spawnLocation = new Point2D.Float(x, y);

        for(int i = 0; i < GameObjectCollection.tileObjects.size(); ++i) {
            GameObject obj = (GameObject)GameObjectCollection.tileObjects.get(i);
            if (obj.collider.contains(spawnLocation)) {
                return;
            }
        }

        this.bomb = new Bomb(spawnLocation, this.firepower, this.pierce, this.bombTimer, this);
        GameObjectCollection.spawn(this.bomb);
        --this.bombAmmo;
    }

    public void restoreAmmo() {
        this.bombAmmo = Math.min(this.maxBombs, this.bombAmmo + 1);
    }

    public void addAmmo(int value) {
        System.out.print("Bombs set from " + this.maxBombs);
        this.maxBombs = Math.min(6, this.maxBombs + value);
        this.restoreAmmo();
        System.out.println(" to " + this.maxBombs);
    }

    public void addFirepower(int value) {
        System.out.print("Firepower set from " + this.firepower);
        this.firepower = Math.min(6, this.firepower + value);
        System.out.println(" to " + this.firepower);
    }

    public void addSpeed(float value) {
        System.out.print("Move Speed set from " + this.moveSpeed);
        this.moveSpeed = Math.min(4.0F, this.moveSpeed + value);
        System.out.println(" to " + this.moveSpeed);
    }

    public void setPierce(boolean value) {
        System.out.print("Pierce set from " + this.pierce);
        this.pierce = value;
        System.out.println(" to " + this.pierce);
    }

    public void setKick(boolean value) {
        System.out.print("Kick set from " + this.kick);
        this.kick = value;
        System.out.println(" to " + this.kick);
    }

    public void reduceTimer(int value) {
        System.out.print("Bomb Timer set from " + this.bombTimer);
        this.bombTimer = Math.max(160, this.bombTimer - value);
        System.out.println(" to " + this.bombTimer);
    }

    public BufferedImage getBaseSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.dead;
    }

    public void update() {
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        if (!this.dead) {
            if ((this.spriteTimer = (int)((float)this.spriteTimer + this.moveSpeed)) >= 12) {
                ++this.spriteIndex;
                this.spriteTimer = 0;
            }

            if (!this.UpPressed && !this.DownPressed && !this.LeftPressed && !this.RightPressed || this.spriteIndex >= this.sprites[0].length) {
                this.spriteIndex = 0;
            }

            this.sprite = this.sprites[this.direction][this.spriteIndex];
            if (this.UpPressed) {
                this.moveUp();
            }

            if (this.DownPressed) {
                this.moveDown();
            }

            if (this.LeftPressed) {
                this.moveLeft();
            }

            if (this.RightPressed) {
                this.moveRight();
            }

            if (this.ActionPressed && this.bombAmmo > 0) {
                this.plantBomb();
            }
        } else if (this.spriteTimer++ >= 30) {
            ++this.spriteIndex;
            if (this.spriteIndex < this.sprites[4].length) {
                this.sprite = this.sprites[4][this.spriteIndex];
                this.spriteTimer = 0;
            } else if (this.spriteTimer >= 250) {
                this.destroy();
            }
        }

    }

    public void onCollisionEnter(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    public void handleCollision(Wall collidingObj) {
        this.solidCollision(collidingObj);
    }

    public void handleCollision(Enemy collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }
    }

    public void handleCollision(Explosion collidingObj) {
        if (!this.dead) {
            this.dead = true;
            this.spriteIndex = 0;
        }

    }

    public void handleCollision(Bomb collidingObj) {
        Rectangle2D intersection = this.collider.createIntersection(collidingObj.collider);
        if (intersection.getWidth() >= intersection.getHeight() && intersection.getHeight() <= 6.0 && Math.abs(this.collider.getCenterX() - collidingObj.collider.getCenterX()) <= 8.0) {
            if (this.kick && !collidingObj.isKicked()) {
                if (intersection.getMaxY() >= this.collider.getMaxY() && this.DownPressed) {
                    collidingObj.setKicked(true, KickDirection.FromTop);
                }

                if (intersection.getMaxY() >= collidingObj.collider.getMaxY() && this.UpPressed) {
                    collidingObj.setKicked(true, KickDirection.FromBottom);
                }
            }

            this.solidCollision(collidingObj);
        }

        if (intersection.getHeight() >= intersection.getWidth() && intersection.getWidth() <= 6.0 && Math.abs(this.collider.getCenterY() - collidingObj.collider.getCenterY()) <= 8.0) {
            if (this.kick && !collidingObj.isKicked()) {
                if (intersection.getMaxX() >= this.collider.getMaxX() && this.RightPressed) {
                    collidingObj.setKicked(true, KickDirection.FromLeft);
                }

                if (intersection.getMaxX() >= collidingObj.collider.getMaxX() && this.LeftPressed) {
                    collidingObj.setKicked(true, KickDirection.FromRight);
                }
            }

            this.solidCollision(collidingObj);
        }

    }

    public void handleCollision(Powerup collidingObj) {
        collidingObj.grantBonus(this);
        collidingObj.destroy();
    }
}
