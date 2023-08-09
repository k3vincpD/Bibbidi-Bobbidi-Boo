package presentation;

import logic.Map;
import logic.Area;
import logic.Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Specialization of GraphicInterface responsible for drawing the Map and the items contained within it.
 * This is the only GraphicInterface that can be executed as a Thread.
 */
public class MapGraphicInterface extends GraphicInterface implements Runnable {
    protected JPanel gamePanel;
    private Map map;
    private BufferedImage fruit;
    private BufferedImage[] walls;
    private boolean paused;


    public MapGraphicInterface(JPanel panel) {
        super();
        gamePanel = panel;
        paused = false;
        loadFruits();
        this.walls = new BufferedImage[14];
        getMapImage();
    }

    /**
     * Method that displays the graphical representations of the draw method
     * 70 times per second
     */
    @Override
    public void run() {
        while (true) {
            if (!paused) {
                gamePanel.repaint();
            }
            Character.checkTime();
        }
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    @Override
    public void setArea(Area area) {
        map = area.getMap();
        this.area = area;
    }

    @Override
    public void drawSprite(Graphics2D g2) {
        int tempX = 0;
        int tempY = 0;
        int entitySize = Area.getEntitySize();
        for (int i = 0; i < map.getRows(); i++) {
            for (int a = 0; a < map.getColumns(); a++) {
                if (map.getMapValue(i, a) == 0 || map.getMapValue(i, a) == 6 || map.getMapValue(i, a) == 8 || map.getMapValue(i, a) == 13
                        || map.getMapValue(i, a) == 14 || map.getMapValue(i, a) == 7) {
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 2 || map.getMapValue(i, a) == 9 || map.getMapValue(i, a) == 10
                        || map.getMapValue(i, a) == 11 || map.getMapValue(i, a) == 12) {
                    g2.setColor(Color.yellow);
                    g2.setStroke(new BasicStroke(2f));
                    g2.fillOval(tempX + 9, tempY + 9, 6, 6);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 1) {
                    g2.drawImage(walls[0], tempX, tempY, Area.getEntitySize(), Area.getEntitySize() - 1, null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 15) {
                    g2.drawImage(walls[1], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 16) {
                    g2.drawImage(walls[3], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 17) {
                    g2.drawImage(walls[2], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 18) {
                    g2.drawImage(walls[4], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 19) {
                    g2.drawImage(walls[5], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 20) {
                    g2.drawImage(walls[9], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 21) {
                    g2.drawImage(walls[8], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 22) {
                    g2.drawImage(walls[6], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 23) {
                    g2.drawImage(walls[7], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 24) {
                    g2.drawImage(walls[10], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 25) {
                    g2.drawImage(walls[11], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 26) {
                    g2.drawImage(walls[12], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 27) {
                    g2.drawImage(walls[13], tempX, tempY, Area.getEntitySize(), Area.getEntitySize(), null);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 3) {
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(6f));
                    g2.fillOval(tempX + 7, tempY + 7, 10, 10);
                    tempX += entitySize;
                } else if (map.getMapValue(i, a) == 4) {
                    g2.drawImage(fruit, tempX, tempY, Area.getEntitySize(), Area.getEntitySize() - 5, null);
                    tempX += entitySize;
                }
            }
            tempX = 0;
            tempY += entitySize;
        }
    }

    public void loadFruits() {
        String imageDir = "apple";
        try {
            this.fruit = ImageIO.read(getClass().getResourceAsStream("/res/fruits/" + imageDir + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getMapImage() {
        String imageDir = "map";
        try {
            walls[0] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/normal_x.png"));
            walls[1] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/normal_y.png"));
            walls[2] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/right_edge.png"));
            walls[3] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/left_edge.png"));
            walls[4] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/bottom_left.png"));
            walls[5] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/bottom_right.png"));
            walls[6] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/corner_bottom.png"));
            walls[7] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/corner_top.png"));
            walls[8] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/corner_left.png"));
            walls[9] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/corner_right.png"));
            walls[10] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/top.png"));
            walls[11] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/bottom.png"));
            walls[12] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/left.png"));
            walls[13] = ImageIO.read(getClass().getResourceAsStream("/res/" + imageDir + "/right.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
