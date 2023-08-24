package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ballom extends Enemy {

    private BufferedImage[][] sprites;


    public Ballom(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.collider.setRect(this.position.x + 3.0F, this.position.y + 16.0F + 3.0F, this.width - 6.0F, this.height - 16.0F - 6.0F);
        this.sprites = spriteMap;

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

    private void updateSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex = (spriteIndex + 1) % sprites[direction].length;
            this.sprite = sprites[direction][spriteIndex];
        }
    }

    private void updateDeadSprite() {
        spriteTimer++;
        if (spriteTimer >= 10) {
            spriteTimer = 0;
            spriteIndex++;
            if (spriteIndex >= sprites[3].length) {
                this.destroy();
            } else {
                this.sprite = sprites[3][spriteIndex];
            }
        }
    }

}
