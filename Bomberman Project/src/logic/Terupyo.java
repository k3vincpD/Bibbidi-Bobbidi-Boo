package logic;

public class Terupyo extends Enemy {
    public Terupyo(int speedValue, Map map, Player player) {
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
        if (isPaused()) {
            return;
        }
    }
}
