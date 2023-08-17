package logic;

public class Nagacham extends Enemy {
    private int counter2;

    public Nagacham(int speed, Map map, Player player) {
        super(speed, map, player, 2);
        this.counter2 = 0;
        this.spawnColumn = map.getNagachamDotCol();
        this.spawnRow = map.getNagachamDotRow();
    }

    @Override
    public void moveByPersonality() {
        if (!canTeleport()) {
            if (isAligned() && this.counter2 < 25) {
                previousDirection = direction;
                direction = getRandomDirection(previousDirection);
                this.counter2++;
            } else if (isAligned() && counter2 >= 25) {
                previousDirection = direction;
                direction = getBestDirection(player.getColumnMap(), player.getRowMap());
                this.counter2++;
            }
            if (this.counter2 == 60) {
                this.counter2 = 0;
            }
        }
        move(direction, speed);
    }
}
