package logic;

public class Terupyo extends Enemy {
    public Terupyo(int speed, Map map, Player player) {
        super(speed, map, player, 4);
        this.spawnColumn = map.getTerupyoDotCol();
        this.spawnRow = map.getTerupyoDotRow();
    }

    @Override
    public void moveByPersonality() {
        if (!canTeleport()) {
            if (isAligned()) {
                previousDirection = direction;
                direction = getMovement();
            }
        }
        move(direction, speed);
    }

    public Key getMovement() {
        int extraDistanceToTravel = 4;
        switch (player.getDirection()) {
            case UP:
                return getBestDirectionTerupyo(-extraDistanceToTravel, 0);
            case DOWN:
                return getBestDirectionTerupyo(extraDistanceToTravel, 0);
            case RIGHT:
                return getBestDirectionTerupyo(0, extraDistanceToTravel);
            case LEFT:
                return getBestDirectionTerupyo(0, -extraDistanceToTravel);
            default:
                return getRandomDirection(previousDirection);
        }
    }

    public Key getBestDirectionTerupyo(int extraColumns, int extraRows) {
        Key movement = null;
        double minDistance = 10000;
        double tempDistance = calculateDistanceTerupyo(extraColumns, extraRows, 0, -MOVE_VALUE);
        if (canMove(Key.UP)) {
            minDistance = tempDistance;
            movement = Key.UP;
        }
        tempDistance = calculateDistanceTerupyo(extraColumns, extraRows, 0, MOVE_VALUE);
        if (tempDistance < minDistance && canMove(Key.DOWN)) {
            minDistance = tempDistance;
            movement = Key.DOWN;
        }
        tempDistance = calculateDistanceTerupyo(extraColumns, extraRows, -MOVE_VALUE, 0);
        if (tempDistance < minDistance && canMove(Key.LEFT)) {
            minDistance = tempDistance;
            movement = Key.LEFT;
        }
        tempDistance = calculateDistanceTerupyo(extraColumns, extraRows, MOVE_VALUE, 0);
        if (tempDistance < minDistance && canMove(Key.RIGHT)) {
            minDistance = tempDistance;
            movement = Key.RIGHT;
        }
        return movement;
    }

    public double calculateDistanceTerupyo(int extraColumns, int extraRows, int extraGhostColumns, int extraGhostRows) {
        return Math.sqrt(Math.pow(getColumnMap() + extraGhostColumns - (player.getColumnMap() + extraColumns), 2) + Math.pow(getRowMap() + extraGhostRows - (player.getRowMap() + extraRows), 2));
    }
}

