package logic;

public class Boyon extends Enemy {
    public Boyon(int speedValue, Map map, Player player) {
        super(speedValue,map, player, 1);
        this.spawnColumn=map.getBoyonDotCol();
        this.spawnRow=map.getBoyonDotRow();
    }

    @Override
    public void moveByPersonality() {
        if (!canTeleport()) {
            if (isAligned()) {
                previousDirection = direction;
                direction = getBestDirection(player.getColumnMap(), player.getRowMap());
            }
        }
        move(direction, speed);
    }

}
