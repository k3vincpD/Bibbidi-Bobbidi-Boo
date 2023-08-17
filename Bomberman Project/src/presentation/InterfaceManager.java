package presentation;

import logic.Area;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class InterfaceManager extends JPanel{
    private final Thread graphicThread;
    private EnemyGraphicInterface ballomGraphicInterface, boyonGraphicInterface, terupyoGraphicInterface, nagachamGraphicInterface;
    private GraphicInterface playerGraphicInterface, blockGraphicInterface;
    private MapGraphicInterface mapGraphicInterface;
    private Area area;
    private final GamePanel menu;
    private final KeyDetector controls;
    private final JButton backButton;

    public InterfaceManager(GamePanel menu) {
        this.menu = menu;
        controls = new KeyDetector();
        backButton = new JButton();
        addKeyListener(controls);
        setBackground(Color.black);
        mapGraphicInterface = new MapGraphicInterface(this);
        pauseInterface();
        graphicThread = new Thread(mapGraphicInterface);
        playerGraphicInterface = new PlayerGraphicInterface();
        ballomGraphicInterface = new EnemyGraphicInterface("pochita");
        boyonGraphicInterface = new EnemyGraphicInterface("max");
        terupyoGraphicInterface = new EnemyGraphicInterface("benji");
        nagachamGraphicInterface = new EnemyGraphicInterface("doge");
        blockGraphicInterface = new FrameGraphicInterface();
        graphicThread.start();
    }

    public void start(Area area) {
        this.area = area;
        mapGraphicInterface.setArea(area);
        playerGraphicInterface.setArea(area);
        ballomGraphicInterface.setEnemy(area.getBallom());
        boyonGraphicInterface.setEnemy(area.getBoyon());
        terupyoGraphicInterface.setEnemy(area.getTerupyo());
        nagachamGraphicInterface.setEnemy(area.getNagacham());
        blockGraphicInterface.setArea(area);
        setFocusable(true);
        unpauseInterface();
        area.startArea(controls);
        unpauseInterface();
        SwingUtilities.getWindowAncestor(this).setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ballomGraphicInterface.update();
        boyonGraphicInterface.update();
        terupyoGraphicInterface.update();
        nagachamGraphicInterface.update();
        mapGraphicInterface.drawSprite(g2);
        playerGraphicInterface.drawSprite(g2);
        ballomGraphicInterface.drawSprite(g2);
        boyonGraphicInterface.drawSprite(g2);
        terupyoGraphicInterface.drawSprite(g2);
        nagachamGraphicInterface.drawSprite(g2);
        blockGraphicInterface.drawSprite(g2);
    }

    public void configureButton() {
        Font bombermanFont;
        try {
            bombermanFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\USER\\projects\\Bibbidi-Bobbidi-Boo\\Bomberman Project\\src\\res\\pixel-nes.otf"));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InterfaceManager interfaceManager = this;
        setLayout(null);
        backButton.setBounds(600, 15, 80, 20);
        backButton.setFont(bombermanFont.deriveFont(12f));
        backButton.setText("Menu");
        backButton.setBackground(new Color(245, 227, 35));
        backButton.setBorder(new LineBorder(Color.BLACK));
        backButton.addActionListener(e -> {
            pauseInterface();
            area.stop();
            SwingUtilities.getWindowAncestor(interfaceManager).dispose();
            //menu.reconfigure();
        });
        add(backButton);
    }

    public void resume() {
        setFocusable(true);
        SwingUtilities.getWindowAncestor(this).setVisible(true);
        mapGraphicInterface.unpause();
    }

    public void unpauseInterface() {
        mapGraphicInterface.unpause();
    }

    public void pauseInterface() {
        mapGraphicInterface.pause();
    }
}

