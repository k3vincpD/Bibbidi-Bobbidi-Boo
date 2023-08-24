package presentation;
import Logic.*;

import java.awt.image.BufferedImage;

 public enum SpriteMaps {
    PLAYER_1,//jugador
    BALLOM,//movimiento normal
    BAYON,//movimiento aleaotrio
    PASS,//el que persigue
    HARD_WALLS,
    BOMB,
    BOMB_PIERCE,
    EXPLOSION_SPRITEMAP;

    public BufferedImage image = null;
    public BufferedImage[][] sprites = null;

    public BufferedImage getImage() {
        return this.image;
    }

    public BufferedImage[][] getSprites() {
        return this.sprites;
    }
}