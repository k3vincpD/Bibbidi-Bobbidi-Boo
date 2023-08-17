package presentation;

import logic.Bomberman;
import logic.Area;
import logic.FileManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
Window that connects the graphic section of the game with the logic part and
allows saving a game, loading it, or restarting levels.
*/

public class GamePanel extends JFrame{
    private final Bomberman bomberman;
    private JButton startButton, saveButton, loadButton, resetButton, scoreButton;
    private final JFrame graphicLevel;
    private final InterfaceManager interfaceManager;
    private final ArrayList<Area> areas;
    private Area currentArea;
    private final Sound sound;

    public GamePanel(Bomberman bomberman, Dimension dimension) {
        this.bomberman = bomberman;
        areas = new ArrayList<>();
        graphicLevel = new JFrame();
        interfaceManager = new InterfaceManager(this);
        addAreas();
        configureGraphicLevel();
        graphicLevel.add(interfaceManager);
        setMinimumSize(dimension);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Pacman");
        setLocationRelativeTo(null);
        setLayout(null);
        addButtons();
        setVisible(true);
        sound = new Sound();
        sound.getSounds(TypeOfSound.MENU);
        playSoundInLoop();
    }

    private void configureGraphicLevel() {
        graphicLevel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphicLevel.setResizable(false);
    }

    private void addButtons() {
        Font bombermanFont;
        try {
            bombermanFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\USER\\projects\\Bibbidi-Bobbidi-Boo\\Bomberman Project\\src\\res\\pixel-nes.otf"));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Start Button
        startButton = new JButton();
        startButton.setBounds((getWidth() / 2) - 200, 200, 150, 62);
        configureButton(startButton, "boton-de-inicio", 150);
        this.add(startButton);

        // Score Button
        scoreButton = new JButton();
        scoreButton.setBounds((getWidth() / 2) + 50, 178, 100, 100);
        configureButton(scoreButton, "score", 100);
        this.add(scoreButton);

        // Save Game Button
        saveButton = new JButton();
        saveButton.setBounds((getWidth() / 2) - 250, 300, 100, 30);
        saveButton.setFont(bombermanFont.deriveFont(12f));
        saveButton.setText("Guardar");
        saveButton.setForeground(Color.BLACK);
        saveButton.setBackground(new Color(245, 227, 35));
        saveButton.setBorder(new LineBorder(Color.BLACK));
        this.add(saveButton);

        // Load Game Button
        loadButton = new JButton();
        loadButton.setBounds((getWidth() / 2) - 50, 300, 100, 30);
        loadButton.setFont(bombermanFont.deriveFont(12f));
        loadButton.setText("Cargar");
        loadButton.setForeground(Color.BLACK);
        loadButton.setBackground(new Color(245, 203, 98));
        loadButton.setBorder(new LineBorder(Color.BLACK));
        this.add(loadButton);

        // Reset Game Button
        resetButton = new JButton();
        resetButton.setBounds((getWidth() / 2) + 150, 300, 100, 30);
        resetButton.setFont(bombermanFont.deriveFont(12f));
        resetButton.setText("Reset");
        resetButton.setForeground(Color.BLACK);
        resetButton.setBackground(new Color(69, 178, 245));
        resetButton.setBorder(new LineBorder(Color.BLACK));
        this.add(resetButton);
        placeLabels();
        generateButtonEvents();
    }

    private Area getCurrentArea() {
        if (currentArea == null) {
            for (Area area : areas) {
                if (!area.isCompleted()) {
                    return area;
                }
            }
            return null;
        }
        return currentArea.getNextArea();
    }

    private void configureButton(JButton button, String url, int size) {
        ImageIcon image = getImage(url);
        button.setIcon(new ImageIcon(image.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(null);
        ImageIcon imageOff = getImage(url + "Off");
        button.setPressedIcon(new ImageIcon(imageOff.getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));
    }

    private ImageIcon getImage(String imageUrl) {
        try {
            return new ImageIcon(getClass().getResourceAsStream("/res/imagenesMenu/" + imageUrl + ".png").readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     Method executed with the start button.
     Adds an action listener to handle button clicks.
     */

    public int getWidth(Area area) {
        return Area.getEntitySize() * area.getMap().getColumns();
    }

    public int getHeight(Area area) {
        return Area.getEntitySize() * area.getMap().getRows();
    }

    public void generateButtonEvents() {
        GamePanel menu = this;
        startButton.addActionListener(e -> {
            if (currentArea != null && !currentArea.isCompleted()) {
                //stopSound();
                interfaceManager.resume();
                dispose();
                return;
            }
            currentArea = getCurrentArea();
            if (currentArea == null) {
                return;
            }
            stopSound();
            manageArea(currentArea);
            dispose();
        });
        scoreButton.addActionListener(e -> {
            stopSound();
            Scoreboard score = new Scoreboard(menu);
            score.showScores();
            dispose();
        });
        saveButton.addActionListener(e -> {
            if (currentArea == null) {
                return;
            }
            FileManager.saveArea(currentArea);
        });
        loadButton.addActionListener(e -> {
            if (currentArea != null) {
                currentArea.finish();
            }
            currentArea = FileManager.loadArea();
            manageArea(currentArea);
            dispose();
            //stopSound();
        });
        resetButton.addActionListener(e -> {
            if (currentArea != null) {
                currentArea.finish();
            }
            this.currentArea = null;
            bomberman.resetAreas();
            addAreas();
        });
    }

    private void addAreas() {
        areas.clear();
        for (Area area : bomberman.getAreas()) {
            areas.add(area);
        }
    }

    public void manageArea(Area area) {
        interfaceManager.setPreferredSize(new Dimension(getWidth(area), getHeight(area)));
        interfaceManager.configureButton();
        graphicLevel.pack();
        interfaceManager.start(area);
    }


    /*
    Method called from the constructor
    Adds images and uses Java libraries for assistance
    */

    private void placeLabels() {
        // Image of the logo that says Bomberman
        ImageIcon logoImage = getImage("logo.pac_man");
        JLabel label = new JLabel(logoImage);
        label.setBounds(148, 0, 400, 213);
        label.setIcon(new ImageIcon(logoImage.getImage().getScaledInstance(400, 213, Image.SCALE_DEFAULT)));
        this.add(label);

        // Background image
        ImageIcon backgroundImage = getImage("pacman2");
        JLabel label2 = new JLabel(backgroundImage);
        label2.setBounds(0, 0, 700, 400);
        label2.setIcon(new ImageIcon(backgroundImage.getImage().getScaledInstance(700, 400, Image.SCALE_DEFAULT)));
        this.add(label2);
    }

    public void playSoundInLoop() {
        sound.loop();
    }

    public void stopSound() {
        sound.stop();
    }

    public void reconfigure() {
        setVisible(true);
        playSoundInLoop();
    }

}
