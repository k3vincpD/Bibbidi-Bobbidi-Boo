package Gamepad;

import Logic.Bomber;
import Logic.Enemy;
import presentation.GameObjectCollection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    private Bomber player1;
    private BufferedImage playerInfo;
    private int player1Score;
    private int remainingTimeInSeconds;
    private long lastUpdateTime;
    public boolean matchSet;

    private static final int HUD_UPDATE_INTERVAL_MS = 1000; // 1 segundo

    public GameHUD() {
        player1 = null;
        playerInfo = null;
        player1Score = 0;
        remainingTimeInSeconds = 240; // 4 minutos en segundos (4 * 60)
        lastUpdateTime = System.currentTimeMillis();
        matchSet = false;
    }

    public void init() {
        int height = GameWindow.HUD_HEIGHT;
        int infoWidth = GamePanel.panelWidth;
        playerInfo = new BufferedImage(infoWidth, height, BufferedImage.TYPE_INT_RGB);
    }

    public void assignPlayer(Bomber player) {
        player1 = player;
    }

    public void updateScore() {
        int deadEnemies = (int) GameObjectCollection.enemyObjects.stream().filter(Enemy::isDead).count();
        int totalEnemies = GameObjectCollection.enemyObjects.size();

        if (deadEnemies == totalEnemies) {
            player1Score++;
            matchSet = true;
        }
    }

    public void drawHUD() {
        updateRemainingTime();
        Graphics playerGraphics = playerInfo.createGraphics();

        playerGraphics.setColor(Color.BLACK);
        playerGraphics.fillRect(0, 0, playerInfo.getWidth(), playerInfo.getHeight());

        Font font = new Font("Courier New", Font.BOLD, 18);
        playerGraphics.setFont(font);
        playerGraphics.setColor(Color.WHITE);
        playerGraphics.drawString("Score: " + player1Score, 10, 30);
        playerGraphics.drawString("Time: " + formatTime(remainingTimeInSeconds), 10, 50);
        playerGraphics.drawString("Lives: " + player1.getLives(), 10, 70);

        playerGraphics.dispose();
    }

    private void updateRemainingTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastUpdateTime;

        if (elapsedTime >= HUD_UPDATE_INTERVAL_MS) {
            remainingTimeInSeconds--;
            lastUpdateTime = currentTime;
        }
    }

    private String formatTime(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
