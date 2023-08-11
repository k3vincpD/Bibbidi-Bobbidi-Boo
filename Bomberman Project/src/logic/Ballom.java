package logic;

public class Ballom extends Enemy {
    private final Map map;

    public Ballom(int speedValue, Map map, Player player) {
        super(speedValue, map, player, 0);
        this.map = map;
    }

    @Override
    public void moveByPersonality() {
        if (collidesWithWall()) {
            changeDirection();
        }
        move(direction, speed);
    }

    private boolean collidesWithWall() {
        int futureX = positionX + (direction == Key.LEFT ? -speed : (direction == Key.RIGHT ? speed : 0));
        int futureY = positionY + (direction == Key.UP ? -speed : (direction == Key.DOWN ? speed : 0));

        int mapValue = map.getMapValue(futureY / 24, futureX / 24);
        return mapValue == 1; // Assuming 1 represents a wall on the map
    }

    private void changeDirection() {
        Key newDirection;
        do {
            newDirection = getRandomValidDirection(direction);
        } while (newDirection == direction || isOppositeDirection(newDirection, direction));

        direction = newDirection;
    }

    @Override
    public void handlePausedState() {
        if (isPaused()) {
            return;
        }
    }
}

