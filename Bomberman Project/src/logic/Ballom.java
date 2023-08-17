package logic;


public class Ballom extends Enemy {
    public Ballom(int speed, Map map, Player player) {
        super(speed, map, player, 3);
        this.spawnColumn = map.getBallomDotCol();
        this.spawnRow = map.getBallomDotRow();
    }

    @Override
    public void moveByPersonality() {
        if (!canTeleport()) {
            if (isAligned()) {
                previousDirection = direction;
                direction = getRandomDirection(previousDirection);
            }
        }
        move(direction, speed);
    }
}
