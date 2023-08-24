package presentation;

import java.awt.image.BufferedImage;


public enum Images {
    ICON,
    BACKGROUND,
    SOFT_WALL,
    POWER_BOMB,
    POWER_FIREUP,
    POWER_FIREMAX,
    POWER_SPEED,
    POWER_PIERCE,
    POWER_KICK,
    POWER_TIMER;

    public BufferedImage image = null;
    public BufferedImage[][] sprites = null;
    public BufferedImage getImage() {
        return this.image;
    }
    public BufferedImage[][] getSprites() {
        return this.sprites;
    }
}
