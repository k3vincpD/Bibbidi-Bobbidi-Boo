package logic;

import javax.swing.text.Position;
import java.util.List;

public class Bomb {
    private int timeToExplode;
    private int explosionRadius;
    private Map map;
    private int positionY;
    private int positionX;

    public Bomb() {
        this.timeToExplode = 0;
        this.explosionRadius = 1;
    }

    public void explode() {
        if (isTimeToExplode()) {
            createExplosion(map, explosionRadius);
            map.clearBombAtPosition(this);
            handleExplosionEffects();
            timeToExplode = 0;
            explosionRadius = 1;
        } else {
            timeToExplode--;
        }
    }

    private boolean isTimeToExplode() {
        return timeToExplode <= 0;
    }

    private void createExplosion(Map map, int radius) {
        int bombRow = map.getRowForPosition(this);
        int bombCol = map.getColumnForPosition(this);
        map.setExplosionAtPosition(bombRow, bombCol);

        for (int r = 1; r <= radius; r++) {
            if (isTileInBounds(map, bombRow - r, bombCol)) {
                map.setExplosionAtPosition(bombRow - r, bombCol);
            }
            // Similar checks for other directions
        }
    }

    private boolean isTileInBounds(Map map, int row, int col) {
        return row >= 0 && row < map.getRows() && col >= 0 && col < map.getColumns();
    }

    private void handleExplosionEffects() {
        List<Position> explosionPositions = map.getExplosionPositions();
        for (Position position : explosionPositions) {
            handlePositionEffects(position);
        }
    }

    private void handlePositionEffects(Position position) {
        // TODO: Implement explosion effects for the given position
    }

    public void setTimeToExplode(int time) {
        this.timeToExplode = time;
    }

    public void setExplosionRadius(int radio) {
        this.explosionRadius = radio;
    }

    public int getRowMap() {
        return positionY / 24;
    }

    public int getColumnMap() {
        return positionX / 24;
    }

}

