package presentation;

import logic.Area;

/*
 Specialization of a Graphic Interface responsible for drawing the player,
 only has one type of sprites
*/

public class PlayerGraphicInterface extends GraphicInterface {

    public PlayerGraphicInterface() {
        super();
        getSprite("pacman", sprites);
    }

    @Override
    public void setArea(Area area) {
        activeSprite = sprites;
        character = area.getPlayer();
    }
}

