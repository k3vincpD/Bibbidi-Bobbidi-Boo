package presentation;

import java.awt.image.BufferedImage;

 public enum SpriteMaps {
    PLAYER_1,//jugador
    PLAYER_2,//jugador
    PLAYER_3,
    PLAYER_4,
    BALLOM,//movimiento normal
    BAYON, //movimiento aleaotrio
    PASS, //el que persigue
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