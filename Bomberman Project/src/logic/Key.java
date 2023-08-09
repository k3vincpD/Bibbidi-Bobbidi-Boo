package logic;

public enum Key {
    UP(119),
    DOWN(115),
    RIGHT(100),
    LEFT(97),
    PAUSE(112);

    private final int code;

    Key(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
