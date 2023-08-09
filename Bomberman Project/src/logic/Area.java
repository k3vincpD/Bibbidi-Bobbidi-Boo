package logic;

import presentation.KeyDetector;
import java.io.Serializable;


/*
Logic class representing a Bomberman level,
consisting of: the player, the 4 basic Enemies, and a Map.
*/

public class Area {
    private Difficulty difficulty;
    private String mapDirection;
    private boolean completed;
    private Area nextArea;
    private Player player;
    private Enemy ballom, boyon, terupyo,nagacham;
    private Map map;
    private transient Thread playerLogicThread, ballomLogicThread, boyonLogicThread, terupyoLogicThread, nagachamLogicThread;
    private static final int FPS = 60;
    private static final int ENTITY_SIZE = 24;

    public Area(Difficulty difficulty, String mapDirection) {
        this.difficulty = difficulty;
        this.mapDirection = mapDirection;
        map = new Map(mapDirection);

        //Characters
        player = new Player(difficulty.getSpeedValue(), map);
        ballom = new Ballom(difficulty.getSpeedValue(), map, player);
        boyon = new Boyon(difficulty.getSpeedValue(), map, player);
        terupyo = new Terupyo(difficulty.getSpeedValue(), map, player);
        nagacham = new Nagacham(difficulty.getSpeedValue(), map, player);
    }

    public void startArea(KeyDetector controls) {
        player.thread = true;
        ballom.thread = true;
        boyon.thread = true;
        terupyo.thread = true;
        nagacham.thread = true;
        playerLogicThread = new Thread(player);
        ballomLogicThread = new Thread(ballom);
        boyonLogicThread = new Thread(boyon);
        terupyoLogicThread = new Thread(terupyo);
        nagachamLogicThread = new Thread(nagacham);
        player.setArea(this);
        player.setKeyDetector(controls);
        playerLogicThread.start();
        ballomLogicThread.start();
        boyonLogicThread.start();
        terupyoLogicThread.start();
        nagachamLogicThread.start();
    }

    public void stop(){
        player.stopSound();
        player.paused = true;
    }

    public void finish(){
        player.thread = false;
        ballom.thread = false;
        boyon.thread = false;
        terupyo.thread = false;
        nagacham.thread = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void completeArea() {
        this.completed = true;
    }

    public void setNextArea(Area nextArea) {
        this.nextArea = nextArea;
    }

    public Area getNextArea() {
        return nextArea;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public String getMapDirection() {
        return mapDirection;
    }

    public static int getEntitySize() {
        return ENTITY_SIZE;
    }
    public Player getPlayer() {
        return player;
    }
    public Enemy getBallom() {
        return ballom;
    }

    public Enemy getBoyon() {
        return boyon;
    }

    public Enemy getTerupyo() {
        return terupyo;
    }

    public Enemy getNagacham() {
        return nagacham;
    }

    public static int getFPS() {
        return FPS;
    }

    public Map getMap() {
        return map;
    }
}
