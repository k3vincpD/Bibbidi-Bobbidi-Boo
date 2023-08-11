package logic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class Character implements Runnable, Serializable {
    private static final long serialVersionUID = 1L; // Add a serialVersionUID for Serializable

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
                handlePausedState();
                checkTime();
            }
        }
    }

    public static void checkTime() {
        double drawInterval = 1000000000.0 / Area.getFPS(); // Use decimal point for double division
        double nextDrawTime = System.nanoTime() + drawInterval;
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

    public abstract void handlePausedState(); // Renamed from isPaused()

    public abstract void update();

    public boolean isColliding(Key key) {
        int newRowMap = getRowMapAfterMove(key);
        int newColumnMap = getColumnMapAfterMove(key);
        return blockValues.contains(map.getMapValue(newRowMap, newColumnMap));
    }

    private int getRowMapAfterMove(Key key) {
        int newRowMap = getRowMap();
        switch (key) {
            case UP:
                newRowMap--;
                break;
            case DOWN:
                newRowMap++;
                break;
        }
        return newRowMap;
    }

    private int getColumnMapAfterMove(Key key) {
        int newColumnMap = getColumnMap();
        switch (key) {
            case RIGHT:
                newColumnMap++;
                break;
            case LEFT:
                newColumnMap--;
                break;
        }
        return newColumnMap;
    }

    public boolean canMove(Key newKey) {
        return isAligned() && !isColliding(newKey);
    }

    public boolean isOppositeDirection(Key currentKey, Key newKey) {
        return (currentKey == Key.DOWN && newKey == Key.UP) ||
                (currentKey == Key.UP && newKey == Key.DOWN) ||
                (currentKey == Key.RIGHT && newKey == Key.LEFT) ||
                (currentKey == Key.LEFT && newKey == Key.RIGHT);
    }

    public void move(Key key, int speed) {
        if (!isColliding(key)) {
            updatePosition(key, speed);
            updateImageDirection(key);
        }
        updateSprite();
    }

    private void updatePosition(Key key, int speed) {
        switch (key) {
            case UP:
                positionY -= speed;
                break;
            case DOWN:
                positionY += speed;
                break;
            case RIGHT:
                positionX += speed;
                break;
            case LEFT:
                positionX -= speed;
                break;
        }
    }

    private void updateImageDirection(Key key) {
        switch (key) {
            case UP:
                imageDirection = "up";
                break;
            case DOWN:
                imageDirection = "down";
                break;
            case RIGHT:
                imageDirection = "right";
                break;
            case LEFT:
                imageDirection = "left";
                break;
        }
    }

    private void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNumber = (spriteNumber == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public boolean isAligned() {
        int entitySize = Area.getEntitySize();
        return positionX % entitySize == 0 && positionY % entitySize == 0;
    }

    public boolean canTeleport() {
        return teleportToRight() || teleportToLeft();
    }

    private boolean teleportToRight() {
        int rightTeleportValue = 6;
        return map.getMapValue(getRowMap(), (positionX + 22) / 24) == rightTeleportValue;
    }

    private boolean teleportToLeft() {
        int leftTeleportValue = 5;
        return map.getMapValue(getRowMap(), getColumnMap()) == leftTeleportValue;
    }

    public boolean isTimeUp(int waitTime) {
        counter++;
        if (counter >= waitTime) { // Use greater or equal sign for accuracy
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

    /*public boolean isPaused() {
        return paused;
    }*/

}

