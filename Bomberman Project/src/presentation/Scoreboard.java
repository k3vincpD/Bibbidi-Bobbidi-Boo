package presentation;

import logic.FileManager;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
Window that show the top 10 highscores in the game
*/

public class Scoreboard extends JFrame {
    private File file = new File("res/puntuacion.txt");
    private ArrayList<String> data;
    private final JPanel panel;
    private JButton backButton;
    private Font bombermanFont;
    private final GamePanel menu;
    private final FileManager fileManager;

    public Scoreboard(GamePanel menu) {
        this.menu = menu;
        this.fileManager = new FileManager(file);
        data = new ArrayList<>();
        this.panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(bombermanFont.deriveFont(18f));
                g2.setColor(Color.YELLOW);
                int textWidth = g2.getFontMetrics().stringWidth("SCORES");
                g2.drawString("SCORES", (getWidth() / 2) - (textWidth / 2), 36);
                g2.setColor(Color.WHITE);
                int i = 1;
                for (String line : data) {
                    i += 2;
                    String[] linePart = line.split(" ");
                    g2.drawString((data.indexOf(line) + 1) + "." + linePart[0], 58, 26 * i);
                    g2.drawString(linePart[1], 296, 26 * i);
                }
            }
        };
        loadFont();
        this.data = fileManager.readFile();
        addMenuButton();
        panel.setBackground(Color.BLACK);
        add(panel);
        setMinimumSize(new Dimension(440, 620));
        setResizable(false);
        setTitle("Score");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMenuButton() {
        backButton = new JButton();
        backButton.setFont(bombermanFont.deriveFont(10f));
        backButton.setText("Menu");
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(69, 178, 245));
        backButton.setBorder(new LineBorder(Color.BLACK));
        backButton.setBounds(15, 15, 80, 30);
        backButton.addActionListener(e -> {
            dispose();
            menu.setVisible(true);
            //menu.playSoundInLoop();
        });
        this.add(backButton);
    }

    private void loadFont() {
        try {
            bombermanFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/pixel-nes.otf"));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showScores() {
        setVisible(true);
    }
}

