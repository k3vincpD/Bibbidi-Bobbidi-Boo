package logic;

public class Boyon extends Enemy {

    public Boyon(int speedValue, Map map, Player player) {
        super(speedValue, map, player, 0);
        moveByPersonality();
    }

    @Override
    public void moveByPersonality() {
        moveRandomly();
    }

    public void moveRandomly() {
        Key newDirection = getRandomValidDirection(direction);
        move(newDirection, speed);
    }

    @Override
    public void handlePausedState() {
    }

}
