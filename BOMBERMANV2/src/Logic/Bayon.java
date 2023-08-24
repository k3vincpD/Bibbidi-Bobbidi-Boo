package Logic;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Bayon extends Enemy {

    private BufferedImage[][] sprites;

    public Bayon(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        updateCollider();
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
