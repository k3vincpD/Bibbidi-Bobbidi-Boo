package presentation;

import logic.Area;
import logic.Enemy;

import java.awt.image.BufferedImage;

/**
 * Specialization of GraphicsInterface responsible for drawing a Ghost based on its state (chase or scatter),
 * therefore it has two types of sprites
 */
public class EnemyGraphicInterface extends GraphicInterface {

    protected Enemy enemy;
    protected BufferedImage[] fleeingSprites;
    private boolean updateSprites;

    public EnemyGraphicInterface(String image) {
        super();
        this.fleeingSprites = new BufferedImage[8];
        getSprite(image, sprites);
        getSprite("vulnerableP1", fleeingSprites);
    }

    public void update() {
        if (enemy.isFleeing() && updateSprites) {
            this.activeSprite = fleeingSprites;
            updateSprites = false;
        }
        if (!enemy.isFleeing() && !updateSprites) {
            this.activeSprite = sprites;
            updateSprites = true;
        }
    }

    public void setEnemy(Enemy enemy) {
        this.updateSprites = true;
        activeSprite = sprites;
        this.enemy = enemy;
        character = this.enemy;
    }

    @Override
    public void setArea(Area area) {
    }
}
