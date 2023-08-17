package logic;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
public class FileManager implements Serializable {

    private File file;
    private final ArrayList<String> fileData;
    private final ArrayList<Integer> scores;
    private FileWriter fileWriter;

    public FileManager(File file) {
        this.file = file;
        this.fileData = new ArrayList<>();
        this.scores = new ArrayList<>();
        readFile();
    }

    public void saveName(String name, int score) {
        try {
            this.fileWriter = new FileWriter(file, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        orderFile(name, score);
        if (scores.size() < 10) {
            writeScore(fileData.size());
        } else {
            writeScore(10);
        }
    }

    public void saveScore(int score) {
        if (scores.isEmpty()) {
            return;
        }
        Collections.sort(scores);
        if (score >= scores.get(0) || scores.size() < 10) {
            JFrame frame = new JFrame("Guardar Puntuacion");
            JLabel label = new JLabel();
            JLabel label2 = new JLabel();
            JPanel panel = new JPanel();
            JTextField nameField = new JTextField();
            JButton saveButton = new JButton("Puntaje Guardar");


            frame.setSize(500, 700);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            panel.setLayout(null);
            panel.setBackground(Color.BLACK);

            label.setBounds(60, 60, 400, 200);
            label.setText("> INGRESA TU NOMBRE <");
            label.setFont(new Font("Creepster", 1, 30));
            label2.setBounds(150, 250, 400, 200);
            label2.setText(score + " PUNTOS");//
            label2.setFont(new Font("Creepster", 1, 30));


            nameField.setBounds(90, 210, 300, 100);
            nameField.setFont(new Font("Creepster", 1, 28));
            nameField.setHorizontalAlignment(JTextField.CENTER);
            nameField.setSelectedTextColor(Color.BLUE);

            saveButton.setBounds(90, 400, 300, 90);
            saveButton.setFont(new Font("Creepster", 1, 20));
            saveButton.setContentAreaFilled(false);
            saveButton.setFocusPainted(false);

            panel.add(label2);
            panel.add(label);
            panel.add(nameField);
            panel.add(saveButton);
            frame.add(panel);
            frame.setVisible(true);

            saveButton.addActionListener(e -> {
                String name = nameField.getText();
                saveName(name, score);
                frame.dispose();
            });
        }
    }

    public void writeScore(int limit) {
        try {
            String currentLine;
            int i = 0;
            while (limit != i) {
                currentLine = fileData.get(i);
                i += 1;
                fileWriter.write(currentLine + System.getProperty("line.separator"));
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> readFile() {
        fileData.clear();
        this.file = new File(this.file.getPath());
        String[] gameScore;
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            gameScore = line.split(" ");
            if (gameScore.length == 2) {
                scores.add(Integer.parseInt(gameScore[1]));
                fileData.add(line);
            }
        }
        return fileData;
    }

    public int getHighScore() {
        orderData();
        String[] highScore = fileData.get(0).split(" ");
        if (highScore.length == 2) {
            return Integer.parseInt(highScore[1]);
        }
        return 0;
    }

    public void orderFile(String name, int score) {
        if (!(scores.size() < 10)) {
            fileData.remove(fileData.size() - 1);
        }
        fileData.add(name + " " + score);
        orderData();
    }

    public void orderData() {
        String[] dataToOrder = fileData.toArray(new String[0]);
        Arrays.sort(dataToOrder, (s1, s2) -> {
            int number1 = Integer.parseInt(s1.split(" ")[1]);
            int number2 = Integer.parseInt(s2.split(" ")[1]);
            return Integer.compare(number2, number1);
        });
        fileData.clear();
        Collections.addAll(fileData, dataToOrder);
    }

    public static void saveArea(Area area) {
        File file = new File("res/partida");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObjectOutputStream writeHandler = null;
        try {
            writeHandler = new ObjectOutputStream(outputStream);
            writeHandler.writeObject(area);
            writeHandler.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Area loadArea() {
        Area area;
        File file = new File("res/partida");
        ObjectInputStream readHandler;
        try {
            readHandler = new ObjectInputStream(new FileInputStream(file));
            area = (Area) readHandler.readObject();
            readHandler.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return area;
    }
}

