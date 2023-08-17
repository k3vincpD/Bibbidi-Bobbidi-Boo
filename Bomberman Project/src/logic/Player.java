package logic;

import presentation.KeyDetector;
import presentation.TypeOfSound;
import presentation.Sound;

import java.io.*;
import java.util.*;
public class Player extends Character {
    private transient KeyDetector keyDetector;
    private Key lastDirection;
    public int score, bonusForEnemyKilled, highScore;
    private boolean powerPellet;
    private final int POWER_PELLET_DURATION = calculateTime(5);
    private int powerUpCounter;
    public boolean isAlive;
    private int lives = 3;
    private int pacDotsEaten = 0;
    private boolean consumedPowerPellet;
    private Area area;

    private Sound sound;
    private transient FileManager fileManager;

    public Player(int speed, Map map) {
        this.map = map;
        //this.sound = new Sound();
        this.positionX = map.getPlayerSpawnCol();
        this.positionY = map.getPlayerSpawnRow();
        this.speed = speed;
        this.direction = Key.RIGHT;
        this.imageDirection = "arriba";
        this.lastDirection = null;
        this.powerPellet = false;
        this.powerUpCounter = 0;
        this.isAlive = true;
        this.consumedPowerPellet = false;
        this.fileManager = new FileManager(new File("C:\\Users\\USER\\projects\\Bibbidi-Bobbidi-Boo\\Bomberman Project\\src\\res\\puntuation.txt"));
        this.highScore = fileManager.getHighScore();
        this.waitTime = calculateTime(3);
    }

    public void setKeyDetector(KeyDetector controls) {
        this.keyDetector = controls;
    }

    /*
    Updates the X and Y position of the player on the window based
    on user input (pressed movement key - AWSD)
    */
    @Override
    public void update() {
        if (isPaused()) {
            return;
        }
        if (!isWaiting) {
            /*if (sound.isFinished()) {
                sound.playEffect(TypeOfSound.START_GAME);
            }*/
            if (!canTeleport()) {
                if (ateAllPacDots()) {
                    //sound.stop();
                    area.completeArea();
                    thread = false;
                    if (area.getNextArea() == null) {
                        if (fileManager != null) {
                            fileManager.saveScore(score);
                            return;
                        }
                        fileManager = new FileManager(new File("C:\\Users\\USER\\projects\\Bibbidi-Bobbidi-Boo\\Bomberman Project\\src\\res\\puntuation.txt"));
                        fileManager.saveScore(score);
                        return;
                    }
                    area.getNextArea().getPlayer().score += this.score;
                    return;
                }
                this.direction = getMovement();
                if (isAligned()) {
                    getItem();
                }
                if (hasPowerPellet()) {
                    activatePowerUp();
                }
            }
            move(direction, speed);
        } else {
            /*if (sound.isFinished()) {
                sound.playEffect(TypeOfSound.START_GAME);
            }*/
            isTimeUp(waitTime);
        }
    }

    public boolean isPaused() {
        if (keyDetector.key == Key.PAUSE) {
            keyDetector.key = null;
            this.paused = !this.paused;
            /*if (paused) {
                sound.stop();
            } else {
                sound.play();
            }*/
        }
        return this.paused;
    }

    private void getItem() {
        int itemIndex = map.changeValueTo0(getRowMap(), getColumnMap());
        if (itemIndex == Item.PAC_DOT.getCode()) {
            this.score += Item.PAC_DOT.getScore();
            pacDotsEaten += 1;
        } else if (itemIndex == Item.POWER_PELLET.getCode()) {
            this.score += Item.POWER_PELLET.getScore();
            this.powerPellet = true;
            this.powerUpCounter = 0;
        } else if (itemIndex == Item.FRUIT.getCode()) {
            this.score += Item.FRUIT.getScore();
        }
    }

    public boolean ateAllPacDots() {
        return pacDotsEaten == map.getNumberOfPacDots();
    }

    /*
    Get the player's movement direction at the given instant,
    considering whether a direction change can be performed
    */
    private Key getMovement() {
        updateLastDirection();
        if (this.lastDirection != null && (canMove(this.lastDirection)
                || isOppositeDirection(this.lastDirection, direction) || direction == null)) {
            keyDetector.key = lastDirection;
            lastDirection = null;
            return keyDetector.key;
        }
        return direction;
    }

    /*
    Changes the last direction assigned to the player if it's different from the current direction
    */
    private void updateLastDirection() {
        if (!Objects.equals(this.direction, keyDetector.key)) {
            this.lastDirection = keyDetector.key;
            keyDetector.key = this.direction;
        }
    }
/*
Can be use for Nagacham
*/
    public void reappear() {
        //sound.stop();
        //sound.playEffect(TypeOfSound.DEATH);
        powerPellet = false;
        powerUpCounter = 0;
        lives -= 1;
        if (lives == 0) {
            thread = false;
            //sound.playEffect(TypeOfSound.DEATH);
            //sound.stop();
            if (fileManager != null) {
                fileManager.saveScore(score);
                return;
            }
            fileManager = new FileManager(new File("C:\\Users\\USER\\projects\\Bibbidi-Bobbidi-Boo\\Bomberman Project\\src\\res\\puntuation.txt"));
            fileManager.saveScore(score);
        }
        this.positionX = map.getPlayerSpawnCol();
        this.positionY = map.getPlayerSpawnRow();
        this.isAlive = false;
        this.isWaiting = true;
        this.counter = 0;
    }
    /*
    Can be use for Power Ups
    */
    public void activatePowerUp() {
        if (powerUpCounter == 0) {
            /*sound.stop();
            sound.getSounds(TypeOfSound.POWER_UP);
            sound.loop();*/
            bonusForEnemyKilled = 1;
            consumedPowerPellet = true;
        }
        if (powerUpCounter == POWER_PELLET_DURATION) {
            //sound.stop();
            this.powerPellet = false;
            bonusForEnemyKilled = 1;
            powerUpCounter = 0;
        } else {
            powerUpCounter += 1;
        }
        if (powerUpCounter == 10) {
            consumedPowerPellet = false;
        }
    }

    @Override
    public boolean isTimeUp(int waitTime) {
        counter++;
        if (counter == waitTime) {
            isWaiting = false;
            counter = 0;
            //sound.stop();
            return true;
        }
        if (counter == 10) {
            isAlive = true;
        }
        return false;
    }

    /*
    Can be used for activating Power Up in Power Up class
    */
    public boolean activatedPowerPellet() {
        return this.consumedPowerPellet;
    }
    /*
    Can be used for activating Power Up in Power Up class
    */
    public boolean hasPowerPellet() {
        return this.powerPellet;
    }
    public int getLives() {
        return lives;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void stopSound() {
        sound.stop();
    }

    public Key getDirection() {
        return direction;
    }
}

