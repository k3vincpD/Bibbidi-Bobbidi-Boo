package logic;

public enum Difficulty {
    EASY(1),
    INTERMEDIATE(2),
    HARD(3),
    POLITECNICA(4);

    private final int speedValue;

    Difficulty(int speedValue){
        this.speedValue = speedValue;
    }
    public int getSpeedValue() {
        return speedValue;
    }
}
