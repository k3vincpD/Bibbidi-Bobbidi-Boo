package logic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Character implements Runnable, Serializable {
    private List<Integer> blockValues = Arrays.asList(1, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
    protected int positionX, positionY, speed, waitTime, counter;

    protected Map map;
    protected Key direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    protected String imageDirection;
    public boolean thread = true;
    protected boolean isWaiting = true;
    protected boolean paused = false;

    @Override
    public void run() {
        while (thread) {
            if (!paused) {
                update();
                checkTime();
            } else {
                isPaused();
                checkTime();
            }
        }
    }

    public static void checkTime() {
        double drawInterval = 1000000000 / Area.getFPS(); // 60 frames per second
        double nextDrawTime = System.nanoTime() + drawInterval; // interval in nanoseconds
        try {
            double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
            if (remainingTime < 0) {
                remainingTime = 0;
            }
            Thread.sleep((long) remainingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean isPaused();

    public abstract void update();

    /*
     Method that returns a boolean defining whether the character collides with a maze wall.
    */

    public boolean isColliding(Key key) {
        switch (key) {
            case UP:
                return blockValues.contains(map.getMapValue((positionY - speed) / 24, getColumnMap()));
            case DOWN:
                return blockValues.contains(map.getMapValue(getRowMap() + 1, getColumnMap()));
            case LEFT:
                return blockValues.contains(map.getMapValue(getRowMap(), (positionX - speed) / 24));
            case RIGHT:
                return blockValues.contains(map.getMapValue(getRowMap(), getColumnMap() + 1));
            default:
                return false;
        }
    }

    /**
     * Returns true if the direction change of the entity does not conflict with the alignment of the blocks
     * or with a wall. If any of these conditions are not met, it returns false.
     */
    public boolean canMove(Key newKey) {
        if (!isAligned()) {
            return false;
        }
        return !isColliding(newKey);
    }

    public boolean isOppositeDirection(Key currentKey, Key newKey) {
        return currentKey == Key.DOWN && newKey == Key.UP ||
                currentKey == Key.UP && newKey == Key.DOWN ||
                currentKey == Key.RIGHT && newKey == Key.LEFT ||
                currentKey == Key.LEFT && newKey == Key.RIGHT;
    }

    /*
    Moves the character in the maze, if it collides the character maintains its position.
    key    : Direction of movement
    speed  : Magnitude of character displacement (must be a multiple of the entity size handled in a level)
    */

    public void move(Key key, int speed) {
        if (Objects.equals(key, Key.UP) && !isColliding(key)) {
            imageDirection = "arriba";
            positionY -= speed;
        } else if (Objects.equals(key, Key.DOWN) && !isColliding(key)) {
            imageDirection = "abajo";
            positionY += speed;
        } else if (Objects.equals(key, Key.RIGHT) && !isColliding(key)) {
            imageDirection = "derecha";
            positionX += speed;
        } else if (Objects.equals(key, Key.LEFT) && !isColliding(key)) {
            imageDirection = "izquierda";
            positionX -= speed;
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 1) {
                spriteNumber = 2;
            } else if (spriteNumber == 2) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean isAligned() {
        return positionX % Area.getEntitySize() == 0 && positionY % Area.getEntitySize() == 0;
    }

    /*
    Returns a boolean defining whether the character is on a teleport, in which case,
    it changes its position to the other side of it.
    */
    public boolean canTeleport() {
        if (map.getMapValue(getRowMap(), (positionX + 22) / 24) == 6) {
            positionX = map.getTpRightCol() * 24 - 12;
            positionY = map.getTpRightRow() * 24;
            return true;
        }
        if (map.getMapValue(getRowMap(), getColumnMap()) == 5) {
            this.positionX = map.getTpLeftCol() * 24 + 12;
            this.positionY = map.getTpLeftRow() * 24;
            return true;
        }
        return false;
    }

    public boolean isTimeUp(int waitTime) {
        counter++;
        if (counter == waitTime) {
            isWaiting = false;
            counter = 0;
            return true;
        }
        return false;
    }

    public int getRowMap() {
        return positionY / Area.getEntitySize();
    }

    public int getColumnMap() {
        return positionX / Area.getEntitySize();
    }

    public int calculateTime(int seconds) {
        return Area.getFPS() * seconds;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public Map getMap() {
        return map;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public boolean isItPaused() {
        return paused;
    }

}

