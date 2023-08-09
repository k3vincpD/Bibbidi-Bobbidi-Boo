package logic;

public enum Item {
    PAC_DOT(2, 10),
    POWER_PELLET(3, 50),
    FRUIT(4, 100);

    private final int code;
    private final int score;

    Item(int code, int score) {
        this.code = code;
        this.score = score;
    }

    public int getCode() {
        return code;
    }

    public int getScore() {
        return score;
    }
}
