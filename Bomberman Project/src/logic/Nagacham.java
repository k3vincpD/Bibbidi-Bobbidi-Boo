package logic;

public class Nagacham extends Enemy {
    public Nagacham(int speedValue, Map map, Player player) {
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
