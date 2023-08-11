package logic;

import exceptions.NotSymmetricNumberOfColumnsException;

import javax.swing.text.Position;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
Class that contains a map, two-dimensional array of numbers that is obtained from a txt file
where there are various coordinates and values that other classes require
*/

public class Map implements Serializable {
    public int[][] map;
    private int tpRightRow, tpRightCol, tpLeftRow, tpLeftCol, enemySpawnRow, enemySpawnCol,
            playerSpawnRow, playerSpawnCol, numberOfPacDots, ballomDotRow, ballomDotCol, boyonDotRow, boyonDotCol,
            terupyoDotRow, terupyoDotCol, nagachamDotRow, nagachamDotCol, readyMessageRow, readyMessageCol, enemySquareSpawnCol, enemySquareSpawnRow;

    public Map(String pathName) {
        loadMap(pathName);
        getCoordinatesOfValues();
    }

    private void getCoordinatesOfValues() {
        for (int rows = 0; rows < getRows(); rows++) {
            for (int columns = 0; columns < getColumns(); columns++) {
                if (map[rows][columns] == 5) {
                    this.tpRightRow = rows;
                    this.tpRightCol = columns;
                } else if (map[rows][columns] == 6) {
                    this.tpLeftRow = rows;
                    this.tpLeftCol = columns;
                } else if (map[rows][columns] == 7) {
                    this.enemySpawnRow = rows;
                    this.enemySpawnCol = columns;
                } else if (map[rows][columns] == 8) {
                    this.playerSpawnRow = rows;
                    this.playerSpawnCol = columns;
                } else if (map[rows][columns] == 2) {
                    numberOfPacDots += 1;
                } else if (map[rows][columns] == 9) {
                    this.ballomDotRow = rows;
                    this.ballomDotCol = columns;
                } else if (map[rows][columns] == 10) {
                    this.boyonDotRow = rows;
                    this.boyonDotCol = columns;
                } else if (map[rows][columns] == 11) {
                    this.terupyoDotRow = rows;
                    this.terupyoDotCol = columns;
                } else if (map[rows][columns] == 12) {
                    this.nagachamDotRow = rows;
                    this.nagachamDotCol = columns;
                } else if (map[rows][columns] == 13) {
                    this.readyMessageRow = rows;
                    this.readyMessageCol = columns;
                } else if (map[rows][columns] == 14) {
                    this.enemySquareSpawnCol = columns;
                    this.enemySquareSpawnRow = rows;
                }
            }
        }
    }

    private void loadMap(String pathName) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(pathName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            ArrayList<String> rows = new ArrayList<>();

            String line;
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    rows.add(line);
                }
            } while (line != null);

            int columns = rows.get(0).split(" ").length;
            map = new int[rows.size()][columns];
            for (int i = 0; i < rows.size(); i++) {
                String[] column = rows.get(i).split(" ");
                checkColumnsSymmetry(columns, column);
                for (int a = 0; a < column.length; a++) {
                    map[i][a] = Integer.parseInt(column[a]);
                }
            }
        } catch (NotSymmetricNumberOfColumnsException e) {
            throw new RuntimeException(e);
        } catch (IOException i) {
            throw new RuntimeException(i);
        }
    }

    private static void checkColumnsSymmetry(int columns, String[] column) throws NotSymmetricNumberOfColumnsException {
        if (column.length != columns) {
            throw new NotSymmetricNumberOfColumnsException();
        }
    }

    public int getMapValue(int row, int column) {
        return this.map[row][column];
    }

    public int changeValueTo0(int row, int column) {
        if (this.map[row][column] != 5 && this.map[row][column] != 6) {
            int i = this.map[row][column];
            this.map[row][column] = 0;
            return i;
        }
        return 0;
    }

    public int getTpRightRow() {
        return tpRightRow;
    }

    public int getTerupyoDotCol() {
        return terupyoDotCol;
    }

    public int getTerupyoDotRow() {
        return terupyoDotRow;
    }

    public int getNagachamDotCol() {
        return nagachamDotCol;
    }

    public int getNagachamDotRow() {
        return nagachamDotRow;
    }

    public int getTpRightCol() {
        return tpRightCol;
    }

    public int getTpLeftRow() {
        return tpLeftRow;
    }

    public int getTpLeftCol() {
        return tpLeftCol;
    }

    public int getReadyMessageRow() {
        return ((readyMessageRow + 1) * 24) - 5;
    }

    public int getReadyMessageCol() {
        return ((readyMessageCol - 1) * 24);
    }

    public int getEnemySquareSpawnCol() {
        return enemySquareSpawnCol * 24;
    }

    public int getEnemySquareSpawnRow() {
        return enemySquareSpawnRow * 24;
    }

    public int getBoyonDotCol() {
        return boyonDotCol;
    }

    public int getBoyonDotRow() {
        return boyonDotRow;
    }

    public int getEnemySpawnRow() {
        return enemySpawnRow * 24;
    }

    public int getEnemySpawnCol() {
        return enemySpawnCol * 24;
    }

    public int getPlayerSpawnRow() {
        return playerSpawnRow * 24;
    }

    public int getPlayerSpawnCol() {
        return playerSpawnCol * 24;
    }

    public int getNumberOfPacDots() {
        return numberOfPacDots;
    }

    public int getBallomDotCol() {
        return ballomDotCol;
    }

    public int getBallomDotRow() {
        return ballomDotRow;
    }

    public int getRows() {
        return map.length;
    }

    public int getColumns() {
        return map[0].length;
    }

    public void clearBombAtPosition(Bomb bomb) {
        map[bomb.getRowMap()][bomb.getColumnMap()] = 0;
    }

    public List<Position> getExplosionPositions() {
        List<Position> explosionPositions = new ArrayList<>();
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                if (map[row][column] == 3) {
                    explosionPositions.add(() -> {
                        return 0;
                    });
                }
            }
        }
        return explosionPositions;
    }



    public int getRowForPosition(Bomb bomb) {
        return bomb.getRowMap();
    }

    public int getColumnForPosition(Bomb bomb) {
        return bomb.getColumnMap();
    }

    public void setExplosionAtPosition(int bombRow, int bombCol) {
        //TODO: Implement the logic to set explosion at the given position
    }
}

